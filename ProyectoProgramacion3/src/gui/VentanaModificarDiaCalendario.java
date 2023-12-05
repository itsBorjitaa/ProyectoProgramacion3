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

import main.BaseDatos;
import main.Categoria;
import main.Factura;

public class VentanaModificarDiaCalendario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonModificar,botonCancelar;
	private JTextField textoConcepto;
	private JSpinner floatCoste;
	private JComboBox<Categoria> seleccionadorCategoria;
	private JDateChooser dateChooser;
	private JPanel panelBotones, panelValores;
	private Logger logger = Logger.getLogger(VentanaModificarDiaCalendario.class.getName());
	private Connection con;
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaModificarDiaCalendario(Factura factura,String usuario, Date fecha, Integer codigo,String fechaSeleccionada) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		/*Inicializamos la BD*/
		con=BaseDatos.initBD(RUTA_DB);
		
		/*Creamos los paneles*/
		panelBotones=new JPanel(new GridLayout(1,2));
		panelValores=new JPanel(new GridLayout(4,2));
		
		/*Añadimos los elementos de los paneles*/
		textoConcepto=new JTextField(factura.getConcepto(),20);
		botonModificar=new JButton("Modificar");
		botonCancelar=new JButton("Cancelar");
		seleccionadorCategoria=new JComboBox<>();
		dateChooser = new JDateChooser(fecha);
		floatCoste=new JSpinner(new SpinnerNumberModel((double) factura.getCoste(),0.00,null,1));
		
		/*Cargamos las categorias con la función*/
		for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) { 
			seleccionadorCategoria.addItem(c);
		}
		
		/*Cargamos los valores de coste y categoria*/
		seleccionadorCategoria.setSelectedItem(factura.getCategoria().getNombre());
		
		/*Creamos un SpinnerModel para que muestre 2 decimales, use la respuesta de:
		"https://stackoverflow.com/a/24915447" para hacerlo*/
		JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(floatCoste,"0.00");
		floatCoste.setEditor(numberEditor);
		
		/*Añadimos eventos a los botones*/
		
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textoConcepto.getText().isBlank()) {
				//Modificamos la factura de la BD
				logger.info("Factura modificada");
				Factura nuevaFactura=new Factura(textoConcepto.getText(),(double) floatCoste.getValue(),(Categoria) seleccionadorCategoria.getSelectedItem());
				BaseDatos.modificarFacturaBD(con, nuevaFactura, new Date(dateChooser.getDate().getTime()),codigo);
				BaseDatos.closeBD(con);
				new VentanaDiaCalendario(usuarioActual,fechaSeleccionada);
				dispose();
				} else {
					JOptionPane.showMessageDialog(null, "El nombre del concepto no puede estar en blanco","ERROR AL MODIFICAR LA FACTURA",JOptionPane.ERROR_MESSAGE);
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
		
		panelBotones.add(botonModificar);
		panelBotones.add(botonCancelar);
		
		/*Añadimos los paneles al JFrame*/
		add(panelValores,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		
		/*Características del Frame*/
		setTitle("DeustoFinanzas");
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
	}
	
	/*Utilizo el metodo cargarCategorias que Borja creo para la clase Categorias
	Lo modifico para que añada elementos a la JComboBox en vez de a una lista*/
}