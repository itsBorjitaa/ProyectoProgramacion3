package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.JDatePicker;

import gestionFacturas.BaseDatos;
import gestionFacturas.Categoria;
import gestionFacturas.Factura;

public class VentanaModificarDiaCalendario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonAnyadir,botonCancelar;
	private JTextField textoConcepto;
	private JSpinner floatCoste;
	private JComboBox<String> seleccionadorCategoria;
	private JDatePicker datePicker;
	private JPanel panelBotones, panelValores;
	private Logger logger = Logger.getLogger(VentanaModificarDiaCalendario.class.getName());
	
	public VentanaModificarDiaCalendario(Factura factura) {
		
		/*Creamos los paneles*/
		panelBotones=new JPanel(new GridLayout(1,2));
		panelValores=new JPanel(new GridLayout(4,2));
		
		/*Añadimos los elementos de los paneles*/
		textoConcepto=new JTextField(factura.getConcepto(),20);
		botonAnyadir=new JButton("Añadir");
		botonCancelar=new JButton("Cancelar");
		seleccionadorCategoria=new JComboBox<>();
		datePicker=new JDatePicker(factura.getFecha());
		floatCoste=new JSpinner(new SpinnerNumberModel((double) factura.getCoste(),0.00,null,1));
		
		/*Cargamos las categorias con la función*/
		cargarCategorias();
		
		/*Cargamos los valores de coste y categoria*/
		seleccionadorCategoria.setSelectedItem(factura.getCategoria().getNombre());
		
		/*Creamos un SpinnerModel para que muestre 2 decimales, use la respuesta de:
		"https://stackoverflow.com/a/24915447" para hacerlo*/
		JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(floatCoste,"0.00");
		floatCoste.setEditor(numberEditor);
		
		/*Añadimos eventos a los botones*/
		
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Añadimos la factura a la BD
				new VentanaDiaCalendario();
				dispose();
			}
		});
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaDiaCalendario();
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
		panelValores.add(datePicker);
		
		panelBotones.add(botonAnyadir);
		panelBotones.add(botonCancelar);
		
		/*Añadimos los paneles al JFrame*/
		add(panelValores,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		
		/*Características del Frame*/
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
	}
	
	/*Utilizo el metodo cargarCategorias que Borja creo para la clase Categorias
	Lo modifico para que añada elementos a la JComboBox en vez de a una lista*/
	private void cargarCategorias() {
		for(Categoria c: BaseDatos.buscarCategoriasPorUsuario(VentanaInicioSesion.usuario)) { 
			seleccionadorCategoria.addItem(c.getNombre());
		}
	}
}
