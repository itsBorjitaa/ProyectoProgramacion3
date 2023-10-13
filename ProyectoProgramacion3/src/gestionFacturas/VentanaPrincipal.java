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
	private JPanel panelCalendario, panelBotones;
	private JButton botonCategorias, botonGastos, botonCerrarSesion, botonAjustes;
	private JFrame vActual, vAnterior;
	
	public VentanaPrincipal() {
		vActual = this;
		
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
		 * Añadimos los botones al panel de botones
		 */
		panelBotones.add(botonCategorias);
		panelBotones.add(botonGastos);
		panelBotones.add(botonCerrarSesion);
		getContentPane().add(panelBotones, BorderLayout.EAST);
		
		/**
		 * Añadimos calendario
		 */

		getContentPane().add(panelCalendario, BorderLayout.WEST);
		
		/*EVENTOS*/
		botonCerrarSesion.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero("./datos/BaseDeDatos.csv");
			new VentanaInicioSesion();
			vActual.dispose();
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE); //cambiarlo más tarde a DO_NOTHING_ON_CLOSE
		setBounds(300, 200, 600, 400);
		setTitle("VentanaPrincipal");
		setVisible(true);
	}
}