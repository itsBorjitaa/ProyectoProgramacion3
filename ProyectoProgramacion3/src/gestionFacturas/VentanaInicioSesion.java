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

public class VentanaInicioSesion extends JFrame{
	private JButton botonInicioSesion, botonRegistro, botonCerrar;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenya;
	private JLabel labelUsuario, labelContrasenya;
	private JPanel pDatos, pBotones;
	private JFrame vActual;
	
	private static final String RUTA_FICHEROS = "datos/";
	
	public VentanaInicioSesion() {
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
		
		botonInicioSesion = new JButton("INICIAR SESIÓN");
		botonRegistro = new JButton("REGISTRARSE");
		botonCerrar = new JButton("SALIR");
		pBotones.add(botonInicioSesion);
		pBotones.add(botonRegistro);
		pBotones.add(botonCerrar);
		
		/*CARGAMOS LAS COLECCIONES CON LOS DATOS INICIALES*/
		BaseDatos.cargarFicheroUsuariosEnLista(RUTA_FICHEROS+"BaseDeDatos.csv");
		
		/*EVENTOS*/
		botonCerrar.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_FICHEROS+"BaseDeDatos.csv");
			System.exit(0);
		});
		
		botonInicioSesion.addActionListener((e)->{
			String usuario = txtUsuario.getText();
			String contrasenya = txtContrasenya.getText();
			Usuario u = BaseDatos.buscarUsuario(usuario);
			if(u == null) {
				JOptionPane.showMessageDialog(null, "El usuario no está registrado","ERROR EN EL INICIO DE SESIÓN",JOptionPane.ERROR_MESSAGE);
			}else if(u.getContrasenya().equals(contrasenya)) {
				JOptionPane.showMessageDialog(null, "Bienvenido!","INICIO DE SESIÓN",JOptionPane.INFORMATION_MESSAGE);
				new VentanaPrincipal();
				vActual.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "La contraseña no es correcta","ERROR EN EL INICIO DE SESIÓN",JOptionPane.WARNING_MESSAGE);
			}
			limpiarCampos();
		});
		
		botonRegistro.addActionListener((e)->{
			new VentanaRegistro();
			vActual.dispose();
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(300, 200, 600, 100);
		setTitle("VentanaInicioSesion");
		setVisible(true);
	}
	
	private void limpiarCampos() {
		txtUsuario.setText("");
		txtContrasenya.setText("");
	}
}
