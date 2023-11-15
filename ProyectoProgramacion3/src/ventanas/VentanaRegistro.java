package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gestionFacturas.BaseDatos;
import gestionFacturas.Usuario;

public class VentanaRegistro extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonRegistro, botonVolver;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenya;
	private JLabel labelUsuario, labelContrasenya;
	private JPanel pDatos, pBotones;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaRegistro.class.getName());
	
	private static final String RUTA_DATOS = "datos/";
	
	public VentanaRegistro() {
		vActual = this;
		/*CREACIÓN DE PANELES Y COMPONENTES*/
		pDatos = new JPanel(new GridLayout(2,2));
		pBotones = new JPanel(new GridLayout(1,3));
		getContentPane().add(pDatos, BorderLayout.NORTH);
		getContentPane().add(pBotones, BorderLayout.SOUTH);
		logger.info("Panel de datos y panel de botones creados");
		
		labelUsuario = new JLabel("USUARIO: ");
		labelContrasenya = new JLabel("CONTRASEÑA: ");
		txtUsuario = new JTextField();
		txtContrasenya = new JPasswordField();
		pDatos.add(labelUsuario);
		pDatos.add(labelContrasenya);
		pDatos.add(txtUsuario);
		pDatos.add(txtContrasenya);
		logger.info("Añadidos los campos de usuario y contraseña al panel de datos");
		
		botonRegistro = new JButton("REGISTRARSE");
		botonVolver = new JButton("VOLVER");
		pBotones.add(botonRegistro);
		pBotones.add(botonVolver);
		logger.info("Añadidos los botones registrarse y volver al panel de botones");
		
		/*CARGAMOS LAS COLECCIONES CON LOS DATOS INICIALES*/
		BaseDatos.cargarFicheroUsuariosEnLista(RUTA_DATOS+"BDUsuario.csv");
		logger.info("Cargados los usuarios de la base de datos");
		
		/*EVENTOS*/
		botonVolver.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_DATOS+"BDUsuario.csv");
			logger.info("Cerrada la ventana de registro y abierta la ventana de inicio de sesión");
			new VentanaInicioSesion();
			vActual.dispose();
		});
		
		botonRegistro.addActionListener((e)->{
			String usuario = txtUsuario.getText();
			String contrasenya = txtContrasenya.getText();
			Usuario u = new Usuario(usuario, contrasenya);
			
			if((usuario.length()>1)&(contrasenya.length()>1)&(BaseDatos.buscarUsuarioBD(VentanaInicioSesion.con, usuario) == null)) { //mediante esta condición compruebo que el usuario no esté en el fichero para añadirlo
				//BaseDatos.anyadirUsuario(u); //Método usando ficheros
				BaseDatos.insertarUsuarioBD(VentanaInicioSesion.con, u);
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente","REGISTRO",JOptionPane.INFORMATION_MESSAGE);
				logger.info("Usuario registrado");
			}else if((usuario.length()<1)&(contrasenya.length()<1)){ //mediante esta condición detectamos si se ha dejado algún hueco del registro sin rellenar
				JOptionPane.showMessageDialog(null, "No dejes ningún hueco en blanco para el registro","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
				logger.info("Error en el registro");
			}else if((usuario.length()>1)&(contrasenya.length()>1)&!(BaseDatos.buscarUsuario(usuario) == null)){ 
				JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
				logger.info("Error en el registro");
			}else if(usuario.length()<1) {
				JOptionPane.showMessageDialog(null, "Escribe el nombre del usuario","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
				logger.info("Error en el registro");
			}else if(contrasenya.length()<1) {
				JOptionPane.showMessageDialog(null, "Escribe la contraseña del usuario","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
				logger.info("Error en el registro");
			}
			
			limpiarCampos();
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 100);
		setTitle("VentanaRegistro");
		setVisible(true);
	}
	
	private void limpiarCampos() {
		txtUsuario.setText("");
		txtContrasenya.setText("");
	}
}
