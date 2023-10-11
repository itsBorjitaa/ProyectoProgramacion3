package gestionFacturas;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaInicioSesion extends JFrame{
	private JButton botonAceptar, botonRegistro, botonCerrar;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenya;
	private JLabel labelUsuario, labelContrasenya;
	private JPanel pDatos, pBotones;
	
	public VentanaInicioSesion() {
		
		/*CREACIÓN DE PANELES Y COMPONENTES*/
		pDatos = new JPanel(new GridLayout(2,2));
		pBotones = new JPanel(new GridLayout(1,3));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE); //cambiarlo más tarde a DO_NOTHING_ON_CLOSE
		setBounds(300, 200, 600, 400);
		setTitle("Aplicación de Gestión de Facturas");
		setVisible(true);
	}
}
