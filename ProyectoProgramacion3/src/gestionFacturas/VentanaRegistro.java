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
		BaseDatos.cargarFicheroUsuariosEnLista("./datos/BaseDeDatos.csv");
		
		/*EVENTOS*/
		botonVolver.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero("./datos/BaseDeDatos.csv");
			new VentanaInicioSesion();
			vActual.dispose();
		});
		
		botonRegistro.addActionListener((e)->{
			String usuario = txtUsuario.getText();
			String contrasenya = txtContrasenya.getText();
			Usuario u = new Usuario(usuario, contrasenya);
			if(BaseDatos.buscarUsuario(usuario) == null) {
				BaseDatos.anyadirUsuario(u);
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente","REGISTRO",JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre","ERROR EN EL REGISTRO",JOptionPane.ERROR_MESSAGE);
			}
			limpiarCampos();
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(300, 200, 600, 100);
		setTitle("VentanaRegistro");
		setVisible(true);
	}
	
	private void limpiarCampos() {
		txtUsuario.setText("");
		txtContrasenya.setText("");
	}
}
