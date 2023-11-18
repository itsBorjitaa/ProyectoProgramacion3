package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAnyadirCategoria extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonAnyadir, botonCancelar;
	private JLabel lblTitulo, lblDescripcion;
	private JTextField txtTitulo, txtDescripcion;
	private JPanel panelArriba, panelAbajo;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaAnyadirCategoria.class.getName());
	
	public VentanaAnyadirCategoria() {
		vActual = this;
		
		/*CREACION DE PANELES*/
		panelArriba = new JPanel(new GridLayout(2,2));
		panelAbajo = new JPanel(new GridLayout(1,2));
		logger.info("Creados los paneles");
		
		/*CREACION DE COMPONENTES*/
		botonAnyadir = new JButton("Añadir");
		botonCancelar = new JButton("Cancelar");
		lblTitulo = new JLabel("Titulo");
		lblDescripcion = new JLabel("Descripción");
		txtTitulo = new JTextField();
		txtDescripcion = new JTextField();
		logger.info("Creados los componentes");
		
		/*AÑADIR COMPONENTES A PANELES*/
		panelArriba.add(lblTitulo);
		panelArriba.add(txtTitulo);
		panelArriba.add(lblDescripcion);
		panelArriba.add(txtDescripcion);
		panelAbajo.add(botonAnyadir);
		panelAbajo.add(botonCancelar);
		logger.info("Añadidos los componentes a los paneles");
		
		/*AÑADIR FUNCIONALIDADES A BOTONES*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias();
				vActual.dispose();
				logger.info("Cerrada la ventana de añadir categorías y abierta la ventana categorías");
				
			}
		});
		
		/*AÑADIR PANELES A VENTANA*/
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		logger.info("Añadidos los paneles a la ventana");
		
		/*ESPECIFICACIÓN VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("Ventana añadir categorias");
		setVisible(true);
	}
	
}