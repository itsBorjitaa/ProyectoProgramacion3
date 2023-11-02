package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

public class VentanaPrincipal extends JFrame{
	private DefaultTableModel tablaModelo;
	private JTable tabla;
	private JPanel panelCalendario, panelBotones,panelDias;
	private JButton botonCategorias, botonGastos, botonDias, botonCerrarSesion, botonAjustes;
	private JFrame vActual, vAnterior;
	private JCalendar calendario;
	
	private static final String RUTA_FICHEROS = "datos/";
	
	public VentanaPrincipal() {
		vActual = this;
		
		tablaModelo = new DefaultTableModel(); //creamos el modelo
		Object[] titulos = {"CALENDARIO", "ACCIONES"}; //creamos los identificadores de columna
		tablaModelo.setColumnIdentifiers(titulos); //asignamos al modelo los identificadores de columna
		tabla = new JTable(tablaModelo); //creamos la tabla y le asociamos el modelo creado
		
		/**
		 * Paneles de la ventana principal
		 */
		panelBotones = new JPanel(new GridLayout(4,1));
		panelCalendario = new JPanel(new GridLayout(1,2));
		
		/**
		 * Botones del panel de botones
		 */
		botonDias=new JButton("TICKETS POR FECHA");
		botonCategorias = new JButton("CATEGORÍAS");
		botonGastos = new JButton("GASTOS");
		botonCerrarSesion = new JButton("CERRAR SESIÓN");
		
		/**
		 * Añadimos los botones al panel de botones
		 */
		panelBotones.add(botonDias);
		panelBotones.add(botonCategorias);
		panelBotones.add(botonGastos);
		panelBotones.add(botonCerrarSesion);
		getContentPane().add(panelBotones, BorderLayout.EAST);
		
		/**
		 * Añadimos calendario
		 */

		calendario = new JCalendar(new Date());
		panelCalendario.add(calendario);
		getContentPane().add(panelCalendario, BorderLayout.WEST);
		
		/*EVENTOS*/
		botonCerrarSesion.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_FICHEROS+"BDUsuario.csv");
			new VentanaInicioSesion();
			vActual.dispose();
		});
		
		botonGastos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaGastos();
				vActual.dispose();
			}
		});
		
		botonCategorias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias();
				vActual.dispose();
			}
		});
		botonDias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaDiaCalendario();
				vActual.dispose();
			}
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("VentanaPrincipal");
		setVisible(true);
	}
}
