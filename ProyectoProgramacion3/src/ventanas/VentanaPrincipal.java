package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import gestionFacturas.BaseDatos;

public class VentanaPrincipal extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tablaModelo;
	private JTable tabla;
	private JPanel panelCalendario, panelBotones;
	private JButton botonCategorias, botonGastos, botonDias, botonCerrarSesion, botonAjustes;
	private JFrame vActual;
	private JCalendar calendario;
	private Logger logger = Logger.getLogger(VentanaPrincipal.class.getName());
	
	private static final String RUTA_FICHEROS = "datos/";
	
	public VentanaPrincipal(String usuario) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		vActual = this;
		
		tablaModelo = new DefaultTableModel(); //creamos el modelo
		tabla = new JTable(tablaModelo); //creamos la tabla y le asociamos el modelo creado
		logger.info("Creada la tabla con el modelo creado asociado");
		
		/**
		 * Paneles de la ventana principal
		 */
		panelBotones = new JPanel(new GridLayout(4,1));
		panelCalendario = new JPanel(new GridLayout(1,2));
		logger.info("Creados los paneles");
		
		/**
		 * Botones del panel de botones
		 */
		botonDias=new JButton("FACTURAS POR FECHA");
		botonCategorias = new JButton("CATEGORÍAS");
		botonGastos = new JButton("GASTOS");
		botonCerrarSesion = new JButton("CERRAR SESIÓN");
		logger.info("Creados los botones");
		
		/**
		 * Añadimos los botones al panel de botones
		 */
		panelBotones.add(botonDias);
		panelBotones.add(botonCategorias);
		panelBotones.add(botonGastos);
		panelBotones.add(botonCerrarSesion);
		getContentPane().add(panelBotones, BorderLayout.EAST);
		logger.info("Añadidos los botones al panel de botones");
		
		/**
		 * Añadimos calendario
		 */
		calendario = new JCalendar(new Date());
		panelCalendario.add(calendario);
		getContentPane().add(panelCalendario, BorderLayout.CENTER);
		logger.info("Añadido el calendario al panel de calendario");
		
		/*EVENTOS*/
		botonCerrarSesion.addActionListener((e)->{
			BaseDatos.guardarListaUsuariosEnFichero(RUTA_FICHEROS+"BDUsuario.csv");
			new VentanaInicioSesion();
			vActual.dispose();
			logger.info("Se ha cerrado sesión");
		});
		
		botonGastos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaGastos(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana principal y abierta la ventana de gastos");
			}
		});
		
		botonCategorias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana principal y abierta la ventana de categorías");
			}
		});
		botonDias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaDiaCalendario(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana principal y abierta la ventana del día del calendario");
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("VentanaPrincipal");
		setVisible(true);
	}
}
