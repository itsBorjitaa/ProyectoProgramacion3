package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistro extends JFrame{
	private JButton botonRegistro, botonVolver;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenya;
	private JLabel labelUsuario, labelContrasenya;
	private JPanel pDatos, pBotones;
	private JFrame vActual;
	
	private static final String RUTA_FICHEROS = "datos/";
	
	public VentanaRegistro() {
		vActual = this;
		/*CREACIÓN DE PANELES Y COMPONENTES*/
		pDatos = new JPanel(new GridLayout(2,2));
		pBotones = new JPanel(new GridLayout(1,3));
		getContentPane().add(pDatos, BorderLayout.NORTH);
		getContentPane().add(pBotones, BorderLayout.SOUTH);
		
		labelUsuario = new JLabel("USUARIO: ");
		labelContrasenya = new JLabel("CONTRASEÑA: ");
		txtUsuario = new JTextField();
		txtContrasenya = new JPasswordField();
		pDatos.add(labelUsuario);
		pDatos.add(labelContrasenya);
		pDatos.add(txtUsuario);
		pDatos.add(txtContrasenya);
		
		botonRegistro = new JButton("REGISTRARSE");
		botonVolver = new JButton("VOLVER");
		pBotones.add(botonRegistro);
		pBotones.add(botonVolver);
		
		/*CARGAMOS LAS COLECCIONES CON LOS DATOS INICIALES*/
		BaseDatos.cargarFicheroUsuariosEnLista(RUTA_FICHEROS+"BDUsuario.csv");
		
		/*EVENTOS*/
		botonVolver.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_FICHEROS+"BDUsuario.csv");
			new VentanaInicioSesion();
			vActual.dispose();
		});
		
		botonRegistro.addActionListener((e)->{
			String usuario = txtUsuario.getText();
			String contrasenya = txtContrasenya.getText();
			Usuario u = new Usuario(usuario, contrasenya);
			
			if((usuario.length()>1)&(contrasenya.length()>1)&(BaseDatos.buscarUsuario(usuario) == null)) { //mediante esta condición compruebo que el usuario no esté en el fichero para añadirlo
				BaseDatos.anyadirUsuario(u);
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente","REGISTRO",JOptionPane.INFORMATION_MESSAGE);
			}else if((usuario.length()<1)&(contrasenya.length()<1)){ //mediante esta condición detectamos si se ha dejado algún hueco del registro sin rellenar
				JOptionPane.showMessageDialog(null, "No dejes ningún hueco en blanco para el registro","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
			}else if((usuario.length()>1)&(contrasenya.length()>1)&!(BaseDatos.buscarUsuario(usuario) == null)){ 
				JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
			}else if(usuario.length()<1) {
				JOptionPane.showMessageDialog(null, "Escribe el nombre del usuario","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
			}else if(contrasenya.length()<1) {
				JOptionPane.showMessageDialog(null, "Escribe la contraseña del usuario","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
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
