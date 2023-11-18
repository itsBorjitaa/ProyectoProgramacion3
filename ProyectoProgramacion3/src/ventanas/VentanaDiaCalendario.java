package ventanas;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdatepicker.UtilDateModel;

import com.toedter.calendar.JCalendar;

import gestionFacturas.Factura;
import gestionFacturas.BaseDatos;
import gestionFacturas.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.MonthDay;
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
	private HashMap<Date, ArrayList<Factura>> ticketsPorFecha;
	private JButton botonVolver, botonModificar, botonBorrar, botonAnyadir;
	private DefaultListModel<Date> defaultListaFechas;
	private JList<Date> listaFechas;
	private JList<Factura> listaTickets;
	private JScrollPane scrollFechas, scrollTickets;
	private DefaultListModel<Factura> defaultListaTickets;
	private JPanel panelBotones,panelListas;
	private Logger logger = Logger.getLogger(VentanaDiaCalendario.class.getName());
	private Connection con;
	
	public VentanaDiaCalendario(String usuario) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		/*Inicializamos la BD*/
		con=BaseDatos.initBD("datos/BaseDatos.db");
		
		/*Creamos el HashMap*/
		ticketsPorFecha= new HashMap<>();
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
		
		defaultListaTickets=new DefaultListModel<>();
		listaTickets=new JList<Factura>(defaultListaTickets);
		scrollTickets=new JScrollPane(listaTickets);
		logger.info("Creadas las listas");
		
		/*Creo fechas genericas para comprobar el funcionamiento de la ventana
		 Se eliminaran despues*/
		
		/*Añadimos eventos a los botones*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaFechas.isSelectionEmpty()) { //Si una fecha esta seleccionada entonces la factura se creara con esta
					BaseDatos.closeBD(con);
					new VentanaAnyadirDiaCalendario(listaFechas.getSelectedValue(),usuarioActual);
				}
				else {//En caso contrario se utiliza la de hoy
					BaseDatos.closeBD(con);
					new VentanaAnyadirDiaCalendario(new Date(System.currentTimeMillis()),usuarioActual);
				}
				dispose();
			}
		});
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaTickets.isSelectionEmpty()) {//Si no hay ningun valor seleccionado no se ejecuta
					new VentanaModificarDiaCalendario(listaTickets.getSelectedValue(),usuarioActual, listaFechas.getSelectedValue(),
						listaTickets.getSelectedValue().getCodigo());
					BaseDatos.closeBD(con);
					dispose();
				}
				else {//Saltara un error para notificar al usuario que elija un ticket
					JOptionPane.showMessageDialog(rootPane, "Elige un ticket!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaTickets.isSelectionEmpty()) {
				BaseDatos.eliminarFacturaBD(con, listaTickets.getSelectedValue().getCodigo());
				defaultListaTickets.removeElement(listaTickets.getSelectedValue());
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Elige un ticket!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
					defaultListaTickets.removeAllElements();
					Date fecha=listaFechas.getSelectedValue();
					for(Entry<Date, ArrayList<Factura>> entryFacturas: ticketsPorFecha.entrySet()) {
						if(fecha==entryFacturas.getKey()) {
							for(Factura factura: entryFacturas.getValue()) {
								defaultListaTickets.addElement(factura);
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
		JLabel textoTickets=new JLabel("Selecciona una factura de este dia:");
		panelListas.add(textoTickets);
		panelListas.add(scrollTickets);
		
		/*Añadimos los paneles al JFrame*/
		add(panelBotones,BorderLayout.EAST);
		add(panelListas);
		logger.info("Añadidos los paneles a la ventana");
		
		/*Características del Frame*/
		setTitle("Ventana dias");
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		
		/*Cargamos los valores*/
		ticketsPorFecha=BaseDatos.cargarFacturaBD(con, usuarioActual);
		for(Date fecha: ticketsPorFecha.keySet()) {
			defaultListaFechas.addElement(fecha);
	}
  }
}
