package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.util.logging.Logger;

public class VentanaCargar extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar barraProgreso;
	private JLabel lblProgreso;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaInicioSesion.class.getName());
	
	public VentanaCargar () {
		vActual = this;
		/*CREACION DE PANELES*/
		JPanel pArriba = new JPanel();
		JPanel pAbajo = new JPanel();
		getContentPane().add(pArriba, BorderLayout.NORTH);
		getContentPane().add(pAbajo, BorderLayout.SOUTH);
		logger.info("Paneles creados");
		
		/*CREACION DE COMPONENETES*/
		barraProgreso = new JProgressBar();
		lblProgreso = new JLabel("PRUEBAS");
		pAbajo.add(lblProgreso);
		pArriba.add(barraProgreso);
		logger.info("Añaidos barra de progreso y label al panel principal");
		
		/*ESPECIFICACION VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 100);
		setTitle("DeustoFinanzas");
		setVisible(true);
	}
	
	
}
