package gui;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.BaseDatos;
import domain.Factura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;


public class VentanaDiaCalendario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Date, ArrayList<Factura>> facturasPorFecha;
	private Date fechaCalendario;
	private JButton botonVolver, botonModificar, botonBorrar, botonAnyadir;
	private DefaultListModel<Date> defaultListaFechas;
	private JList<Date> listaFechas;
	private JList<Factura> listaFacturas;
	private JScrollPane scrollFechas, scrollFacturas;
	private DefaultListModel<Factura> defaultListaFacturas;
	private JPanel panelBotones,panelListas;
	private Logger logger = Logger.getLogger(VentanaDiaCalendario.class.getName());
	private Connection con;
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	@SuppressWarnings("deprecation")
	public VentanaDiaCalendario(String usuario,String fechaSeleccionada) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		/*Inicializamos la BD*/
		con=BaseDatos.initBD(RUTA_DB);
		
		/*Creamos el HashMap*/
		facturasPorFecha= new HashMap<>();
		/*Creamos los paneles*/
		panelBotones=new JPanel(new GridLayout(4,1));
		panelListas=new JPanel(new GridLayout(4,1));
		logger.info("Creados los paneles");
		
		/*Creamos los botones*/
		botonVolver=new JButton("Volver");
		botonModificar=new JButton("Modificar factura");
		botonBorrar=new JButton("Borrar factura");
		botonAnyadir=new JButton("Añadir factura");
		logger.info("Creados los botones");
		
		/*Creamos las listas*/
		defaultListaFechas=new DefaultListModel<>();	
		listaFechas=new JList<>(defaultListaFechas);
		scrollFechas=new JScrollPane(listaFechas);
		
		defaultListaFacturas=new DefaultListModel<>();
		listaFacturas=new JList<Factura>(defaultListaFacturas);
		scrollFacturas=new JScrollPane(listaFacturas);
		logger.info("Creadas las listas");
		
		/*Creo fechas genericas para comprobar el funcionamiento de la ventana
		 Se eliminaran despues*/
		
		/*Añadimos eventos a los botones*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Abierta ventana de añadir categoria");
				if(!listaFechas.isSelectionEmpty()) { //Si una fecha esta seleccionada entonces la factura se creara con esta
					BaseDatos.closeBD(con);
					new VentanaAnyadirDiaCalendario(listaFechas.getSelectedValue(),usuarioActual,fechaSeleccionada);
				}
				else {//En caso contrario se utiliza la de hoy
					BaseDatos.closeBD(con);
					new VentanaAnyadirDiaCalendario(new Date(System.currentTimeMillis()),usuarioActual,fechaSeleccionada);
				}
				dispose();
			}
		});
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaFacturas.isSelectionEmpty()) {//Si no hay ningun valor seleccionado no se ejecuta
					logger.info("Abierta ventana de modificar categoria");
					new VentanaModificarDiaCalendario(listaFacturas.getSelectedValue(),usuarioActual, listaFechas.getSelectedValue(),
						listaFacturas.getSelectedValue().getCodigo(),fechaSeleccionada);
					BaseDatos.closeBD(con);
					dispose();
				}
				else {//Saltara un error para notificar al usuario que elija una factura
					JOptionPane.showMessageDialog(rootPane, "Elige una factura!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaFacturas.isSelectionEmpty()) {
					logger.info("Eliminada factura");
				BaseDatos.eliminarFacturaBD(con, listaFacturas.getSelectedValue().getCodigo());
				defaultListaFacturas.clear();
				defaultListaFechas.clear();
				
				facturasPorFecha=BaseDatos.cargarFacturaBD(con, usuarioActual);
				for(Date fecha: facturasPorFecha.keySet()) {
					defaultListaFechas.addElement(fecha);
				}
				if(!defaultListaFechas.contains(fechaCalendario)) {
					defaultListaFechas.addElement(fechaCalendario);
				}
				
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Elige una factura!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Volver a la ventana Principal");
				new VentanaPrincipal(usuarioActual);
				dispose();
			}
		});
		
		
		/*Añadimos un evento a la lista de fechas para que muestre las facturas del dia seleccionado*/
		listaFechas.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!e.getValueIsAdjusting()) {//Ponemos esto para que no se dupliquen los valores
					defaultListaFacturas.removeAllElements();
					Date fecha=listaFechas.getSelectedValue();
					for(Entry<Date, ArrayList<Factura>> entryFacturas: facturasPorFecha.entrySet()) {
						if(fecha==entryFacturas.getKey()) {
							for(Factura factura: entryFacturas.getValue()) {
								defaultListaFacturas.addElement(factura);
							}
						}
					}
					
				}
			}
		});
		
		/*Añadimos los botones al panel de botones*/
		
		panelBotones.add(botonAnyadir);
		panelBotones.add(botonModificar);
		panelBotones.add(botonBorrar);
		panelBotones.add(botonVolver);
		logger.info("Añadidos los botones al panel de botones");
		
		JLabel textoFecha=new JLabel("Selecciona una fecha:");
		panelListas.add(textoFecha);
		panelListas.add(scrollFechas);
		JLabel textoFacturas=new JLabel("Selecciona una factura de este dia:");
		panelListas.add(textoFacturas);
		panelListas.add(scrollFacturas);
		
		/*Añadimos los paneles al JFrame*/
		add(panelBotones,BorderLayout.EAST);
		add(panelListas);
		logger.info("Añadidos los paneles a la ventana");
		
		/*Características del Frame*/
		setTitle("DeustoFinanzas");
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		
		/*Cargamos los valores*/
		facturasPorFecha=BaseDatos.cargarFacturaBD(con, usuarioActual);
		for(Date fecha: facturasPorFecha.keySet()) {
			defaultListaFechas.addElement(fecha);
		}
		//Aqui cargaremos el valor de la fecha que seleccionamos en el calendario
		String[] fechaSeleccionadaValores=fechaSeleccionada.split("-");
		//Las restas son por el formato de Date
		fechaCalendario=new Date(Integer.parseInt(fechaSeleccionadaValores[0])
		-1900,Integer.parseInt(fechaSeleccionadaValores[1])-1 , Integer.parseInt(fechaSeleccionadaValores[2]));
		//Si no esta en defaultListaFechas lo añadimos
		if(!defaultListaFechas.contains(fechaCalendario)) {
			defaultListaFechas.addElement(fechaCalendario);
		}
  }
}
