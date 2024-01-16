package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import db.BaseDatos;
import domain.Categoria;

public class VentanaCategorias extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonVolver, botonModificar, botonBorrar, botonAnyadir;
	private JScrollPane scrollListaCategorias;
	private JPanel panelDerechaBotones, panelIzquierda;
	private DefaultListModel<Categoria> modeloListaCategorias;
	private JList<Categoria> listaCategorias;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaCategorias.class.getName());
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaCategorias(String usuario) {
		Connection con = BaseDatos.initBD(RUTA_DB);
		
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		vActual = this;
		
		/*CREACIÓN DE PANELES*/
		panelDerechaBotones = new JPanel(new GridLayout(4,1));
		panelIzquierda = new JPanel(new BorderLayout());
		logger.info("Creados los paneles");
		
		/*CREACION COMPONENTES DE BOTONES*/
		botonVolver = new JButton("Volver");
		botonModificar = new JButton("Modificar");
		botonBorrar = new JButton("Borrar");
		botonAnyadir = new JButton("Añadir");
		logger.info("Creados los botones del panel de botones");
		
		/*CREACIÓN DE LA JLIST DE CATEGORIAS*/
		modeloListaCategorias = new DefaultListModel<>();
		listaCategorias = new JList<>(modeloListaCategorias);
		scrollListaCategorias = new JScrollPane(listaCategorias);
		scrollListaCategorias.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollListaCategorias.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//Recorremos el conjunto de categorias de la clase Base Datos para cargarlos en la JList de esta ventana
		for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) { //Por cada Categoria que haya en la lista de categorias
			modeloListaCategorias.addElement(c);} //Lo añadimos al modelo de la JList
		panelIzquierda.add(scrollListaCategorias);
		logger.info("Creada la JList de categorías y asociada al panel de la izquierda");
		
		/*RENDERER DE LA JLIST*/
		
		listaCategorias.setCellRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
				
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				return c;
				
			}
		});
		
		/*AÑADIR COMPONENTES A PANEL BOTONES*/
		panelDerechaBotones.add(botonAnyadir);
		panelDerechaBotones.add(botonBorrar);
		panelDerechaBotones.add(botonModificar);
		panelDerechaBotones.add(botonVolver);
		logger.info("Añadidos los botones al panel de botones");
		
		/*AÑADIR FUNCIONALIDADES BOTONES*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAnyadirCategoria(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana de añadir categorías");
				
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listaCategorias.getSelectedValue() != null) {
						logger.info("Eliminada la categoría de la JList");
						BaseDatos.borrarCategoriasPorUsuario(con, usuarioActual, listaCategorias.getSelectedValue());
						modeloListaCategorias.clear();
						for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) { 
							modeloListaCategorias.addElement(c);}
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna categoria","ERROR AL ELIMINAR CATEGORIA",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listaCategorias.getSelectedValue() != null) {
				new VentanaModificarCategoria(usuarioActual, listaCategorias.getSelectedValue());
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana de modificar categorías");
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna categoria","ERROR AL MODIFICAR CATEGORIA",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaPrincipal(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana principal");
				BaseDatos.closeBD(con);
			}
		});
		
		/*AÑADIMOS PANELES A VENTANA*/
		getContentPane().add(panelIzquierda, BorderLayout.WEST);
		getContentPane().add(panelDerechaBotones, BorderLayout.EAST);
		logger.info("Añadidos los paneles a la ventana");
		
		/*ESPECIFICACION VENTANA*/
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("DeustoFinanzas");
		setVisible(true);
	}
	
}