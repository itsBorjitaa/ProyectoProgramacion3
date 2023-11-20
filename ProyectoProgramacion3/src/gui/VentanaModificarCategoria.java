package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.BaseDatos;
import main.Categoria;

public class VentanaModificarCategoria extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonModificar, botonCancelar;
	private JLabel lblTitulo;
	private JTextField txtTitulo;
	private JPanel panelArriba, panelAbajo;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaPrincipal.class.getName());
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaModificarCategoria(String usuario, Categoria categoriaVieja) {
		Connection con = BaseDatos.initBD(RUTA_DB);
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		vActual = this;
		
		/*CREACION DE PANELES*/
		panelArriba = new JPanel(new GridLayout(2,2));
		panelAbajo = new JPanel(new GridLayout(1,2));
		logger.info("Creados los paneles");
		
		/*CREACION DE COMPONENTES*/
		botonModificar = new JButton("Modificar");
		botonCancelar = new JButton("Cancelar");
		lblTitulo = new JLabel("Titulo");
		txtTitulo = new JTextField(categoriaVieja.toString());
		logger.info("Creados los componentes");
		
		/*AÑADIR COMPONENTES A PANELES*/
		panelArriba.add(lblTitulo);
		panelArriba.add(txtTitulo);
		panelAbajo.add(botonModificar);
		panelAbajo.add(botonCancelar);
		logger.info("Componentes añadidos a paneles");
		
		/*AÑADIR FUNCIONALIDADES A BOTONES*/
		botonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtTitulo.getText().isBlank()) {
				BaseDatos.modificarCategoriaPorUsuario(con, usuarioActual, new Categoria(txtTitulo.getText()), categoriaVieja);
				txtTitulo.setText("");
				JOptionPane.showMessageDialog(null, "Categoria modificada exitosamente","CATEGORIA MODIFICADA",JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "El nombre de la categoria no puede estar en blanco","ERROR AL MODIFICAR CATEGORIAS",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana de modificar categorías y abierta la ventana de categorías");
				
			}
		});
		
		/*AÑADIR PANELES A VENTANA*/
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		logger.info("Añadidos los paneles a la ventana");
		
		/*ESPECIFICACIÓN VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("DeustoFinanzas");
		setVisible(true);
	}
}
