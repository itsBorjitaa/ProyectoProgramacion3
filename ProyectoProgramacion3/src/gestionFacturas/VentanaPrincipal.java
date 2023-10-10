package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame{
	private DefaultTableModel tablaModelo;
	private JTable tabla;
	private JPanel panelBotones;
	private JPanel panelCalendario;
	private JButton botonCategorias;
	private JButton botonGastos;
	private JButton botonCerrarSesion;
	private JButton botonMeses;
	private JButton botonAños;
	
	public VentanaPrincipal() {
		
		tablaModelo = new DefaultTableModel(); //creamos el modelo
		Object[] titulos = {"CALENDARIO", "ACCIONES"}; //creamos los identificadores de columna
		tablaModelo.setColumnIdentifiers(titulos); //asignamos al modelo los identificadores de columna
		tabla = new JTable(tablaModelo); //creamos la tabla y le asociamos el modelo creado
		
		/**
		 * Paneles de la ventana principal
		 */
		panelBotones = new JPanel(new GridLayout(3,1));
		panelCalendario = new JPanel(new GridLayout(1,2));
		
		/**
		 * Botones del panel de botones
		 */
		botonCategorias = new JButton("CATEGORÍAS");
		botonGastos = new JButton("GASTOS");
		botonCerrarSesion = new JButton("CERRAR SESIÓN");
		
		/**
		 * Botones del panel del calendario
		 */
		botonMeses = new JButton("MESES");
		botonAños = new JButton("AÑOS");
		
		/**
		 * Añadimos los botones al panel de botones
		 */
		panelBotones.add(botonCategorias);
		panelBotones.add(botonGastos);
		panelBotones.add(botonCerrarSesion);
		getContentPane().add(panelBotones, BorderLayout.EAST);
		
		/**
		 * Añadimos los botones al panel de calendario
		 */
		panelCalendario.add(botonMeses);
		panelCalendario.add(botonAños);
		getContentPane().add(panelCalendario, BorderLayout.WEST);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(415,200,700,500);
		setTitle("Aplicación de Gestión de Facturas");
		setVisible(true);
	}
}
