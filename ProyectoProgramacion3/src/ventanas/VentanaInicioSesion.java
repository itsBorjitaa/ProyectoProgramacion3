package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
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

public class VentanaInicioSesion extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonInicioSesion, botonRegistro, botonCerrar;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenya;
	private JLabel labelUsuario, labelContrasenya;
	private JPanel pDatos, pBotones;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaInicioSesion.class.getName());
	
	private static final String RUTA_DATOS = "datos/";
	
	public static String usuario;
	
	public static Connection con;
	public VentanaInicioSesion() {
		con = BaseDatos.initBD(RUTA_DATOS + "BaseDatos.db");
		BaseDatos.crearTablaUsuariosBD(con);
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
		
		botonInicioSesion = new JButton("INICIAR SESIÓN");
		botonRegistro = new JButton("REGISTRARSE");
		botonCerrar = new JButton("SALIR");
		pBotones.add(botonInicioSesion);
		pBotones.add(botonRegistro);
		pBotones.add(botonCerrar);
		logger.info("Añadidos los botones iniciar sesión, registrarse y salir al panel de botones");
		
		/*CARGAMOS LAS COLECCIONES CON LOS DATOS INICIALES*/
		BaseDatos.cargarFicheroUsuariosEnLista(RUTA_DATOS+"BDUsuario.csv");
		logger.info("Cargados los usuarios de la base de datos");
		
		/*EVENTOS*/
		botonCerrar.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_DATOS+"BDUsuario.csv");
			logger.info("Aplicación cerrada correctamente");
			BaseDatos.closeBD(con);
			System.exit(0);
		});
		
		botonInicioSesion.addActionListener((e)->{
			String nombre = txtUsuario.getText();
			String contrasenya = txtContrasenya.getText();
			Usuario u = BaseDatos.buscarUsuarioBD(con, nombre);
			//Usuario u = BaseDatos.buscarUsuario(nombre); //Método usando ficheros
			if(u == null) {
				JOptionPane.showMessageDialog(null, "El usuario no está registrado","ERROR EN EL INICIO DE SESIÓN",JOptionPane.ERROR_MESSAGE);
				logger.info("No se ha podido iniciar sesión");
			}else if(u.getContrasenya().equals(contrasenya)) {
				JOptionPane.showMessageDialog(null, "Bienvenido!","INICIO DE SESIÓN",JOptionPane.INFORMATION_MESSAGE);
				usuario = txtUsuario.getText();
				logger.info("Se ha iniciado sesión con el usuario: "+usuario);
				new VentanaPrincipal();
				vActual.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "La contraseña no es correcta","ERROR EN EL INICIO DE SESIÓN",JOptionPane.WARNING_MESSAGE);
				logger.info("No se ha podido iniciar sesión");
			}
			limpiarCampos();
		});
		
		botonRegistro.addActionListener((e)->{
			logger.info("Abierta la ventana de registro");
			new VentanaRegistro();
			vActual.dispose();
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 100);
		setTitle("VentanaInicioSesion");
		setVisible(true);
	}
	
	private void limpiarCampos() {
		txtUsuario.setText("");
		txtContrasenya.setText("");
	}
}