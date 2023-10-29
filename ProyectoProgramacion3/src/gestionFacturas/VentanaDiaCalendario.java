package gestionFacturas;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Date;
public class VentanaDiaCalendario extends JFrame {
	private JButton botonVolver;
	private JButton botonModificar;
	private JButton botonBorrar;
	private JButton botonAnyadir;
	
	private DefaultListModel<Date> defaultListaFechas;
	private JList<Date> listaFechas;
	private JScrollPane scrollFechas;

	private DefaultListModel<Factura> defaultListaTickets;
	private JList<Factura> listaTickets;
	private JScrollPane scrollTickets;
	
	private JPanel panelBotones,panelListas;
	
	public VentanaDiaCalendario() {
		
		/*Creamos los paneles*/
		panelBotones=new JPanel(new GridLayout(4,1));
		panelListas=new JPanel(new GridLayout(4,1));
		
		/*Añadimos los elementos de los paneles*/
		botonVolver=new JButton("Volver");
		botonModificar=new JButton("Modificar factura");
		botonBorrar=new JButton("Borrar factura");
		botonAnyadir=new JButton("Añadir factura");
		
		/*Creamos las listas*/
		defaultListaFechas=new DefaultListModel<>();	
		listaFechas=new JList<>(defaultListaFechas);
		scrollFechas=new JScrollPane(listaFechas);
		
		defaultListaTickets=new DefaultListModel<>();
		listaTickets=new JList<Factura>(defaultListaTickets);
		scrollTickets=new JScrollPane(listaTickets);
		
		/*Creo fechas genericas para comprobar el funcionamiento de la ventana
		 Se eliminaran despues*/
			defaultListaFechas.addElement(new Date(123,9,10));
			defaultListaFechas.addElement(new Date(123,8,3));
		
		/*Añadimos eventos a los botones*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaFechas.isSelectionEmpty()) { //Si una fecha esta seleccionada entonces la factura se creara con esta
					new VentanaAnyadirDiaCalendario(listaFechas.getSelectedValue());
				}
				else {//En caso contrario se utiliza la de hoy
					new VentanaAnyadirDiaCalendario(new Date());
				}
				dispose();
			}
		});
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!listaTickets.isSelectionEmpty()) {//Si no hay ningun valor seleccionado no se ejecuta
					new VentanaModificarDiaCalendario(listaTickets.getSelectedValue());
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
				new VentanaPrincipal();
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
					/*Creo una factura generica para comprobar que se carguen los valores en la ventana de modificar
					 Se eliminara despues*/
					defaultListaTickets.addElement(new Factura((int)( Math.random()*25+1), "prueba", (int)( Math.random()*8+1), new Categoria("Ocio"), fecha));
					defaultListaTickets.addElement(new Factura((int)( Math.random()*25+1), "prueba2", (int)( Math.random()*8+1), new Categoria("Agua"), fecha));
				}
			}
		});
		
		/*Creamos los elementos de los paneles y los añadimos*/
		
		panelBotones.add(botonAnyadir);
		panelBotones.add(botonModificar);
		panelBotones.add(botonBorrar);
		panelBotones.add(botonVolver);
		
		JLabel textoFecha=new JLabel("Selecciona una fecha:");
		panelListas.add(textoFecha);
		panelListas.add(scrollFechas);
		JLabel textoTickets=new JLabel("Selecciona una factura de este dia:");
		panelListas.add(textoTickets);
		panelListas.add(scrollTickets);
		
		/*Añadimos los paneles al JFrame*/
		add(panelBotones,BorderLayout.EAST);
		add(panelListas);
		
		/*Características del Frame*/
		setTitle("Ventana dias");
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
	}
}
