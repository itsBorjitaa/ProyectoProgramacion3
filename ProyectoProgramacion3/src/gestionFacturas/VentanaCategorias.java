package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaCategorias extends JFrame {
	private JButton botonVolver, botonModificar, botonBorrar, botonAnyadir;
	private JScrollPane scrollListaCategorias;
	private JPanel panelDerechaBotones, panelIzquierda;
	private DefaultListModel<Categoria> modeloListaCategorias;
	private JList<Categoria> listaCategorias;
	private JFrame vActual;
	
	public VentanaCategorias() {
		vActual = this;
		
		/*CREACIÓN DE PANELES*/
		panelDerechaBotones = new JPanel(new GridLayout(4,1));
		panelIzquierda = new JPanel(new BorderLayout());
		
		/*CREACION COMPONENTES DE BOTONES*/
		botonVolver = new JButton("Volver");
		botonModificar = new JButton("Modificar");
		botonBorrar = new JButton("Borrar");
		botonAnyadir = new JButton("Añadir");
		
		/*CREACIÓN DE LA JLIST DE CATEGORIAS*/
		modeloListaCategorias = new DefaultListModel<>();
		listaCategorias = new JList<>(modeloListaCategorias);
		scrollListaCategorias = new JScrollPane(listaCategorias);
		scrollListaCategorias.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollListaCategorias.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		cargarCategorias();
		panelIzquierda.add(scrollListaCategorias);
		
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
		
		/*AÑADIR FUNCIONALIDADES BOTONES*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAnyadirCategoria();
				vActual.dispose();
				
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaModificarCategoria();
				vActual.dispose();
				
			}
		});
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaPrincipal();
				vActual.dispose();
				
			}
		});
		
		/*AÑADIMOS PANELES A VENTANA*/
		getContentPane().add(panelIzquierda, BorderLayout.WEST);
		getContentPane().add(panelDerechaBotones, BorderLayout.EAST);
		
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
