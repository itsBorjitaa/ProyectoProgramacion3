package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.BaseDatos;
import domain.Categoria;

public class VentanaAnyadirCategoria extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonAnyadir, botonCancelar;
	private JLabel lblTitulo;
	private JTextField txtTitulo;
	private JPanel panelArriba, panelAbajo;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaAnyadirCategoria.class.getName());
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaAnyadirCategoria(String usuario) {
		Connection con = BaseDatos.initBD(RUTA_DB);
		
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		vActual = this;
		/*CREACION DE PANELES*/
		panelArriba = new JPanel(new GridLayout(2,2));
		panelAbajo = new JPanel(new GridLayout(1,2));
		logger.info("Creados los paneles");
		
		/*CREACION DE COMPONENTES*/
		botonAnyadir = new JButton("Añadir");
		botonCancelar = new JButton("Cancelar");
		lblTitulo = new JLabel("Titulo");
		
		txtTitulo = new JTextField();
		logger.info("Creados los componentes");
		
		/*AÑADIR COMPONENTES A PANELES*/
		panelArriba.add(lblTitulo);
		panelArriba.add(txtTitulo);
		panelAbajo.add(botonAnyadir);
		panelAbajo.add(botonCancelar);
		logger.info("Añadidos los componentes a los paneles");
		
		/*AÑADIR FUNCIONALIDADES A BOTONES*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Categoria> categoriasActuales=BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual);
				ArrayList<String> arrayCategorias=new ArrayList<String>();
				for(Categoria categoria:categoriasActuales) {
					arrayCategorias.add(categoria.getNombre());
				}
				if (!txtTitulo.getText().isBlank()&&!arrayCategorias.contains(txtTitulo.getText())) {
				BaseDatos.insertarCategoriasBD(con, new Categoria(txtTitulo.getText().toUpperCase()));
				BaseDatos.insertarCategoriasPorUsuario(con, usuarioActual, new Categoria(txtTitulo.getText()));
				txtTitulo.setText("");
				JOptionPane.showMessageDialog(null, "Categoria añadida exitosamente","CATEGORIA AÑADIDA",JOptionPane.INFORMATION_MESSAGE);
				} else if(arrayCategorias.contains(txtTitulo.getText())) {
					JOptionPane.showMessageDialog(null, "Esta categoria ya existe","ERROR AL MODIFICAR CATEGORIAS",JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "El nombre de la categoria no puede estar en blanco","ERROR AL MODIFICAR CATEGORIAS",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana de añadir categorías y abierta la ventana categorías");
				BaseDatos.closeBD(con);
			}
		});
		
		/*AÑADIR PANELES A VENTANA*/
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		logger.info("Añadidos los paneles a la ventana");
		
		/*ESPECIFICACIÓN VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("DeustoFinanzas");
		setVisible(true);
	}
	
}