package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;


import com.toedter.calendar.JDateChooser;

import db.BaseDatos;
import domain.Categoria;
import domain.Factura;

public class VentanaAnyadirDiaCalendario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonAnyadir,botonCancelar;
	private JTextField textoConcepto;
	private JSpinner floatCoste;
	private JComboBox<Categoria> seleccionadorCategoria;
	private JDateChooser dateChooser;
	private JPanel panelBotones, panelValores;
	private Logger logger = Logger.getLogger(VentanaAnyadirDiaCalendario.class.getName());
	private Connection con;
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaAnyadirDiaCalendario(Date fecha,String usuario,String fechaSeleccionada) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		/*Inicializamos la BD*/
		con=BaseDatos.initBD(RUTA_DB);

		/*Creamos los paneles*/
		panelBotones=new JPanel(new GridLayout(1,2));
		panelValores=new JPanel(new GridLayout(4,2));
		logger.info("Creados los paneles");
		
		/*Añadimos los elementos de los paneles*/
		dateChooser = new JDateChooser(fecha);
		textoConcepto=new JTextField(20);
		seleccionadorCategoria=new JComboBox<>();
		floatCoste=new JSpinner(new SpinnerNumberModel(0.00,0.00,null,1));
		botonAnyadir=new JButton("Añadir");
		botonCancelar=new JButton("Cancelar");
		logger.info("Añadidos los elementos a los paneles");
		
		/*Cargamos las categorias con la función*/
		for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) { 
			seleccionadorCategoria.addItem(c);
		}
		logger.info("Cargadas las categorías");
		
		/*Creamos un SpinnerModel para que muestre 2 decimales, use la respuesta de:
		"https://stackoverflow.com/a/24915447" para hacerlo*/
		JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(floatCoste,"0.00");
		floatCoste.setEditor(numberEditor);
		
		/*Añadimos eventos a los botones*/
			botonAnyadir.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!textoConcepto.getText().isBlank()) {
					//Añadimos la factura a la BD
					logger.info("Añadida factura");
					BaseDatos.insertarFacturaBD(con, new Factura(textoConcepto.getText(),(double) floatCoste.getValue(),(Categoria) seleccionadorCategoria.getSelectedItem()), usuarioActual, new Date(dateChooser.getDate().getTime()).toString());
					JOptionPane.showMessageDialog(null, "Factura añadida exitosamente","FACTURA AÑADIDA",JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "El nombre del concepto no puede estar en blanco","ERROR AL AÑADIR UNA NUEVA FACTURA",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Volver a ventana dia calendario");
				BaseDatos.closeBD(con);
				new VentanaDiaCalendario(usuarioActual,fechaSeleccionada);
				dispose();
			}
		});
		
		/*Creamos los elementos de los paneles y los añadimos*/
		JLabel labelConcepto=new JLabel("Concepto:");
		panelValores.add(labelConcepto);
		panelValores.add(textoConcepto);
		JLabel labelCoste=new JLabel("Coste:");
		panelValores.add(labelCoste);
		panelValores.add(floatCoste);
		JLabel labelTipo=new JLabel("Categoria:");
		panelValores.add(labelTipo);
		panelValores.add(seleccionadorCategoria);
		JLabel labelFecha=new JLabel("Fecha:");
		panelValores.add(labelFecha);
		panelValores.add(dateChooser);
		
		panelBotones.add(botonAnyadir);
		panelBotones.add(botonCancelar);
		
		/*Añadimos los paneles al JFrame*/
		add(panelValores,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		logger.info("Añadidos los paneles al JFrame");
		
		/*Características del Frame*/
		setVisible(true);
		setTitle("DeustoFinanzas");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	/*Utilizo el metodo cargarCategorias que Borja creo para la clase Categorias
	Lo modifico para que añada elementos a la JComboBox en vez de a una lista*/
}