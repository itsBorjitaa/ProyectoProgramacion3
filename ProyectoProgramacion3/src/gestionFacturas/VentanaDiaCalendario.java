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
		panelBotones=new JPanel(new GridLayout(4,1));
		panelListas=new JPanel(new GridLayout(4,1));
		
		botonVolver=new JButton("Volver");
		botonModificar=new JButton("Modificar factura");
		botonBorrar=new JButton("Borrar factura");
		botonAnyadir=new JButton("Añadir factura");
		
		defaultListaFechas=new DefaultListModel<>();	
		listaFechas=new JList<>(defaultListaFechas);
		scrollFechas=new JScrollPane(listaFechas);
		
		defaultListaTickets=new DefaultListModel<>();
		listaTickets=new JList<Factura>(defaultListaTickets);
		scrollTickets=new JScrollPane(listaTickets);
		
		/**
		 * Solo queremos mostrar las fechas que estan relacionadas con una factura
		 */
		//for(Factura factura: BaseDatos.getFacturas()){ListaFechas.addElement(factura.getFecha);}
		
		/**
		 * Añadimos las  funciones a los botones
		 */
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(!listaTickets.isSelectionEmpty()) {
					new VentanaAnyadirDiaCalendario();
					dispose();
				//}
			}
		});
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(!listaTickets.isSelectionEmpty()) {
					new VentanaModificarDiaCalendario();
					dispose();
				//}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(!listaTickets.isSelectionEmpty()) {
				defaultListaTickets.removeElement(listaTickets.getSelectedValue());
				//}
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
		
		
		/**
		 * Añadimos un Listener a la lista de fechas para que nos muestren las facturas de ese dia
		 */
		listaFechas.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!e.getValueIsAdjusting()) {//Ponemos esto para que no se dupliquen los valores
				}
			}
		});
		
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
		
		
		
		add(panelBotones,BorderLayout.EAST);
		add(panelListas);
		
		setTitle("Ventana dias");
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
	}

	public JList<Factura> getListaTickets() {
		return listaTickets;
	}

	public void setListaTickets(JList<Factura> listaTickets) {
		this.listaTickets = listaTickets;
	}
}
