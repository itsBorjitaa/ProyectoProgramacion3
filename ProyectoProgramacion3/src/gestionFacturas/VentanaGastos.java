package gestionFacturas;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class VentanaGastos extends JFrame{
	
	private JPanel panelTabla,panelBotones;
	private DefaultTableModel tablaGastos;
	private JRadioButton botonMes,botonTrimestre,botonAnyo;
	private ButtonGroup grupoBotones;
	
	public VentanaGastos() {
		/**
		 * Paneles de la ventana gastos
		 */
		panelBotones=new JPanel(new GridLayout(4,1));
		panelTabla=new JPanel();
		
		/**
		 * Elementos del panel de botones
		 */
		botonMes=new JRadioButton("Mes");
		botonTrimestre=new JRadioButton("Trimestre"); 
		botonAnyo=new JRadioButton("Año");
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
		panelBotones.setBorder(BorderFactory.createBevelBorder(1));
		
		/**
		 * Añadimos la tabla al 
		 */
		
		/**
		 * Añadimos los paneles
		 */
		add(panelTabla,BorderLayout.NORTH);//Añadimos los paneles
		add(panelBotones,BorderLayout.SOUTH);
		
		setTitle("VentanaGastos");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(450, 300, 800, 600);
		setVisible(true);
	}
}
