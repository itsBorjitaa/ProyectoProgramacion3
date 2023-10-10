package GestionFacturas;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class VentanaPrincipal extends JFrame{
	
	private JPanel panelBotones;
	private JPanel panelCalendario;
	private JButton botonCategorias;
	private JButton botonGastos;
	private JButton botonCerrarSesion;
	private JButton botonMeses;
	private JButton botonAños;
	
	public VentanaPrincipal() {
		
		/**
		 * Paneles de la ventana principal
		 */
		panelBotones = new JPanel();
		panelCalendario = new JPanel();
		
		/**
		 * Botones del panel de botones
		 */
		botonCategorias = new JButton();
		botonGastos = new JButton();
		botonCerrarSesion = new JButton();
		
		/**
		 * Botones del panel del calendario
		 */
		botonMeses = new JButton();
		botonAños = new JButton();
		
		setBounds(415,200,700,500);
		setTitle("Aplicación de Gestión de Facturas");
		setVisible(true);
	}
}
