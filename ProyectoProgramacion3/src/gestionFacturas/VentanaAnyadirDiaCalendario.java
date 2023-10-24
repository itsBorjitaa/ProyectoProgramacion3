package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.JDatePicker;

public class VentanaAnyadirDiaCalendario extends JFrame {
	private JButton botonAnyadir,botonCancelar;
	
	private JTextField textoConcepto;
	private JSpinner floatCoste;
	private JComboBox<String> seleccionadorTipo;
	private JDatePicker datePicker;
	
	private JPanel panelBotones, panelValores;
	
	public VentanaAnyadirDiaCalendario() {
		panelBotones=new JPanel(new GridLayout(1,2));
		panelValores=new JPanel(new GridLayout(4,2));
		datePicker=new JDatePicker(new Date());
		textoConcepto=new JTextField(20);
		seleccionadorTipo=new JComboBox<>();
		
		botonAnyadir=new JButton("Añadir");
		botonCancelar=new JButton("Cancelar");
		/**
		 * Añadimos todos los tipos al JComboBox
		 */
		//for(Tipo tipo:BDTipos{seleccionadorTipo.addItem(tipo)}
		
		floatCoste=new JSpinner(new SpinnerNumberModel(0.00,0.00,null,1));
		/**
		 * Creamos un SpinnerModel para que muestre 2 decimales, use la respuesta de:
		 * "https://stackoverflow.com/a/24915447"
		 * para hacerlo
		 */
		JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(floatCoste,"0.00");
		floatCoste.setEditor(numberEditor);
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
		
		JLabel labelConcepto=new JLabel("Concepto:");
		panelValores.add(labelConcepto);
		panelValores.add(textoConcepto);
		JLabel labelCoste=new JLabel("Coste:");
		panelValores.add(labelCoste);
		panelValores.add(floatCoste);
		JLabel labelTipo=new JLabel("Tipo:");
		panelValores.add(labelTipo);
		panelValores.add(seleccionadorTipo);
		JLabel labelFecha=new JLabel("Fecha:");
		panelValores.add(labelFecha);
		panelValores.add(datePicker);
		
		panelBotones.add(botonAnyadir);
		panelBotones.add(botonCancelar);
		
		add(panelValores,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
	}
}
