package gestionFacturas;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaGastos extends JFrame{
	
	private JPanel panelTabla,panelBotones;
	private DefaultTableModel tablaGastos;
	private JRadioButton botonMes,botonTrimestre,botonAnyo;
	private ButtonGroup grupoBotones;
	private JButton botonVolver;
	
	public VentanaGastos() {
		/**
		 * Paneles de la ventana gastos
		 */
		panelBotones=new JPanel(new GridLayout(5,1));
		panelTabla=new JPanel();
		
		/**
		 * Elementos del panel de botones
		 */
		botonVolver=new JButton("Volver");
		botonMes=new JRadioButton("Mensual");
		botonTrimestre=new JRadioButton("Trimestral"); 
		botonAnyo=new JRadioButton("Anual");
		grupoBotones=new ButtonGroup();
		
		/**
		 * Configuramos el funcionamiento d elos botones
		 */
		grupoBotones.add(botonMes); grupoBotones.add(botonTrimestre); grupoBotones.add(botonAnyo);
		botonMes.setSelected(true); //Hacemos que el boton del mes este seleccionado desde el principio
		
		/**
		 * Añadimos los elementos al panel de botones
		 */
		JLabel textoBotones=new JLabel("Elige el intervalo que quieres ver:");
		panelBotones.add(textoBotones);
		panelBotones.add(botonMes);
		panelBotones.add(botonTrimestre);
		panelBotones.add(botonAnyo); 
		panelBotones.add(botonVolver);
		panelBotones.setBorder(BorderFactory.createBevelBorder(1));
		
		/**
		 * Añadimos la tabla a la ventana
		 */
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaPrincipal();
				dispose();
			}
		});
		/**
		
		/**
		 * Añadimos los paneles
		 */
		add(panelTabla,BorderLayout.NORTH);//Añadimos los paneles
		add(panelBotones,BorderLayout.SOUTH);
		
		setTitle("Ventana Gastos");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setVisible(true);
	}
}
