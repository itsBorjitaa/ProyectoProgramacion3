package gui;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import main.BaseDatos;
import main.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VentanaGastos extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelTabla,panelBotones;
	private JTable tablaGastos;
	private JScrollPane scrollTabla;
	private JRadioButton botonMes,botonTrimestre,botonAnyo;
	private ButtonGroup grupoBotones;
	private JButton botonVolver;
	private List<String> listaCategorias;
	private Connection con;
	private Logger logger = Logger.getLogger(VentanaGastos.class.getName());
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaGastos(String usuario) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		/*Inicializamos la BD*/
		con=BaseDatos.initBD(RUTA_DB);
		/*Cambiamos el color de la celdas(En Flatlaf se muestran en blanco por defecto)*/
		
		UIManager.put("Table.showHorizontalLines", true);//Con esto se veran las lineas entre celdas en las tablas
		UIManager.put("Table.showVerticalLines", true);
		UIManager.put("Table.gridColor", Color.GRAY);//El color de las lineas sera gris
		
		/*Paneles de la ventana gastos*/

		panelBotones=new JPanel(new GridLayout(5,1));
		panelTabla=new JPanel(new BorderLayout());
		logger.info("Panel de botones y panel de tabla creados");
		
		/*Elementos del panel de botones*/
		botonVolver=new JButton("Volver");
		botonMes=new JRadioButton("Mensual");
		botonTrimestre=new JRadioButton("Trimestral"); 
		botonAnyo=new JRadioButton("Anual");
		grupoBotones=new ButtonGroup();
		logger.info("Botones del panel de botones creados");
		
		/*Configuramos el funcionamiento de los botones*/
		grupoBotones.add(botonMes); grupoBotones.add(botonTrimestre); grupoBotones.add(botonAnyo);
		logger.info("Añadidos los botones al panel de botones");
		botonMes.setSelected(true); //Hacemos que el boton del mes este seleccionado desde el principio
		
		 /*Creamos el modelo de la tabla y cargamos las categorias*/
		listaCategorias=new ArrayList<String>();
		listaCategorias.add("Mes/Intervalo");//Esta sera la primera columna
		for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) {//Recorreremos el set de columnas de la base de datos
			listaCategorias.add(c.getNombre());//añadiremos a la lista sus nombres
		}
		TableModel modeloTabla= new AbstractTableModel() {
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return null;
					}
			
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				if(botonMes.isSelected()) {
					return 12;
				}
				else if(botonTrimestre.isSelected()) {
					return 4;
				}
				else {
					return 5;
				}
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return listaCategorias.size();
			}
			public String getColumnName(int index){
				return (String) listaCategorias.toArray()[index];
				
			}
			
		};
		/*Creamos la tabla y el scroll*/
		tablaGastos=new JTable(modeloTabla);
		tablaGastos.setCellSelectionEnabled(false);
		scrollTabla=new JScrollPane(tablaGastos);
		logger.info("Creada la tabla con scroll");
		
		/*Añadimos funcionalidades a los botones*/
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaPrincipal(usuarioActual);
				dispose();
				logger.info("Cerrada la ventana de gastos y abierta la ventana principal");
			}
		});
		
		botonMes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Seleccionado intervalo mensual");
				tablaGastos.repaint();
			}
		});
		
		botonTrimestre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Seleccionado intervalo trimestral");
			tablaGastos.repaint();	
			}
		});
		
		botonAnyo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Seleccionado intervalo anual");
				tablaGastos.repaint();
			}
		});
		
		/*Añadimos los elementos a los paneles*/
		
		JLabel textoBotones=new JLabel("Elige el intervalo que quieres ver:");
		panelBotones.add(textoBotones);
		panelBotones.add(botonMes);
		panelBotones.add(botonTrimestre);
		panelBotones.add(botonAnyo); 
		panelBotones.add(botonVolver);
		panelTabla.add(scrollTabla);
		logger.info("Añadidos los botones al panel de botones y el scroll al panel de tabla");
		
		/*Añadimos los paneles*/
		
		add(panelTabla,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		logger.info("Añadidos los paneles a la ventana");
		
		setTitle("DeustoFinanzas");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(350, 100, 800, 600);
		setVisible(true);
	}
}