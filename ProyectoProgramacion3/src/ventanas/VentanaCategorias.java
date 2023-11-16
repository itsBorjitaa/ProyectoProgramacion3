package ventanas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestionFacturas.BaseDatos;
import gestionFacturas.Categoria;

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
	
	public VentanaCategorias() {
		vActual = this;
		
		/*CREACIÓN DE PANELES*/
		panelDerechaBotones = new JPanel(new GridLayout(4,1));
		panelIzquierda = new JPanel(new BorderLayout());
		logger.info("Panel de botones de la derecha y panel de la izquierda creados");
		
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
		cargarCategorias();
		panelIzquierda.add(scrollListaCategorias);
		logger.info("Creada la JList de categorías y asociada al panel de la izquierda");
		
		/*RENDERER DE LA JLIST*/
		
		listaCategorias.setCellRenderer(new DefaultListCellRenderer() {
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
				new VentanaAnyadirCategoria();
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana de añadir categorías");
				
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Eliminada la categoría de la JList");
				
			}
		});
		
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaModificarCategoria();
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana de modificar categorías");
				
			}
		});
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaPrincipal();
				vActual.dispose();
				logger.info("Cerrada la ventana de categorías y abierta la ventana principal");
				
			}
		});
		
		/*AÑADIMOS PANELES A VENTANA*/
		getContentPane().add(panelIzquierda, BorderLayout.WEST);
		getContentPane().add(panelDerechaBotones, BorderLayout.EAST);
		logger.info("Añadidos los paneles a la ventana");
		
		/*ESPECIFICACION VENTANA*/
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("Ventana Categorias");
		setVisible(true);
	}
	
	private void cargarCategorias() {
		//Recorremos el conjunto de categorias de la clase Base Datos para cargarlos en la JList de esta ventana
		for(Categoria c: BaseDatos.getCategorias()) { //Por cada Categoria que haya en la lista de categorias
			modeloListaCategorias.addElement(c); //Lo añadimos al modelo de la JList
		}
	}
}