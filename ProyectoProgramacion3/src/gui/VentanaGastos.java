package gui;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.BaseDatos;
import main.Categoria;
import main.Factura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
		listaCategorias.add("INTERVALO");//Esta sera la primera columna
		for(Categoria c: BaseDatos.cargarCategoriasPorUsuario(con, usuarioActual)) {//Recorreremos el set de columnas de la base de datos
			listaCategorias.add(c.getNombre());//añadiremos a la lista sus nombres
		}
		/*Creamos los valores iniciales del modelo*/
		Object [] [] datosTabla={ 
		cargarCosteMes(1, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),//Vamos añadiendo los gastos de cada mes
		cargarCosteMes(2, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(3, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(4, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(5, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(6, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(7, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(8, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(9, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(10, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(11, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
		cargarCosteMes(12, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray() };
		
		TableModel modeloTabla= new DefaultTableModel(datosTabla,listaCategorias.toArray());
		/*Creamos la tabla y el scroll*/
		tablaGastos=new JTable(modeloTabla);
		tablaGastos.setCellSelectionEnabled(false);
		tablaGastos.setDefaultRenderer(Object.class, new RendererGastos());
		tablaGastos.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				l.setOpaque(true);
				l.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
				l.setBackground(Color.LIGHT_GRAY);
				l.setHorizontalAlignment(JLabel.CENTER);
				return l;
			}
		});
		
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
				Object [] [] datosTabla={ 
						cargarCosteMes(1, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(2, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(3, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(4, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(5, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(6, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(7, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(8, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(9, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(10, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(11, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteMes(12, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray() };
				TableModel modeloTabla= new DefaultTableModel(datosTabla,listaCategorias.toArray());
				tablaGastos.setModel(modeloTabla);
			}
			
		});
		
		botonTrimestre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Seleccionado intervalo trimestral");
				Object [] [] datosTabla={ 
						cargarCosteTrimestre(1, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteTrimestre(2, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteTrimestre(3, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray(),
						cargarCosteTrimestre(4, BaseDatos.cargarFacturaBD(con, usuarioActual)).toArray()};
				TableModel modeloTabla= new DefaultTableModel(datosTabla,listaCategorias.toArray());
				tablaGastos.setModel(modeloTabla);
				
			}
		});
		
		botonAnyo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logger.info("Seleccionado intervalo anual");
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
	/*Crearemos un metodo para cargar el coste de cada mes*/
	@SuppressWarnings("deprecation")
	public List<Object> cargarCosteMes(int fechaNumeral, HashMap<Date, ArrayList<Factura>> facturas) {
		List<Object> resultado=new ArrayList<>();//Este sera la lista de resultado que devolvemos
		switch(fechaNumeral) {//Dependiendo del valor numeral de mes que introdujamos el nombre del mes cambiara
		case 1:resultado.add("Enero");
		break;
		case 2:resultado.add("Febrero");
		break;
		case 3:resultado.add("Marzo");
		break;
		case 4:resultado.add("Abril");
		break;
		case 5:resultado.add("Mayo");
		break;
		case 6:resultado.add("Junio");
		break;
		case 7:resultado.add("Julio");
		break;
		case 8:resultado.add("Agosto");
		break;
		case 9:resultado.add("Septiembre");
		break;
		case 10:resultado.add("Octubre");
		break;
		case 11:resultado.add("Noviembre");
		break;
		case 12:resultado.add("Diciembre");
		break;
		}
		ArrayList<ArrayList<Factura>> listaFacturas=new ArrayList<ArrayList<Factura>>();//En esta lista tendremos las 
		LocalDate fechaActual=LocalDate.now();//Sacaremos el año actual con este LocalDate
		//facturas de la fecha que buscamos
		for(Date fechaLista: facturas.keySet()) {//Buscaremos las fechas del HashMap
			if(fechaLista.getMonth()+1==fechaNumeral && fechaLista.getYear()+1900==fechaActual.getYear()) {//El +1 es por la estructura de java, si la fecha es la que buscamos:
				listaFacturas.add(facturas.get(fechaLista));//Entonces añadimos la factura a nuestra lista
			}
		}
		for(int i=1;i<listaCategorias.size();i++) {//Ahora recorreremos la lista de categorias por elemento
			double coste=0;
			for (ArrayList<Factura> arrayFacturas: listaFacturas) {//Recorreremos la lista de facturas para conseguir sus arrays
				for(Factura factura:arrayFacturas) {//Ahora recorremos los arrays para conseguir facturas individuales
					if(factura.getCategoria().getNombre().equals(listaCategorias.get(i))) {//Si la categoria coincide:
						coste=coste+factura.getCoste();//Entonces le añadimos su valor al Coste
					}
				}
			}
			resultado.add(coste);
		}
		return resultado;
	}
	
	public List<Object> cargarCosteTrimestre(int numTrimestre, HashMap<Date, ArrayList<Factura>> facturas) {
		List<Object> resultado=new ArrayList<>();
		switch(numTrimestre) {
		case 1: resultado.add("1er Trimestre");
		break;
		case 2: resultado.add("2do Trimestre");
		break;
		case 3: resultado.add("3er Trimestre");
		break;
		case 4: resultado.add("4rto Trimestre");
		break;
		}
		ArrayList<ArrayList<Factura>> listaFacturas=new ArrayList<ArrayList<Factura>>();//En esta lista tendremos las 
		LocalDate fechaActual=LocalDate.now();//Sacaremos el año actual con este LocalDate
		//facturas de la fecha que buscamos
		for(Date fechaLista: facturas.keySet()) {//Buscaremos las fechas del HashMap
			if((fechaLista.getMonth()+1)<=(numTrimestre*3) && (numTrimestre-1)*3<(fechaLista.getMonth()+1) && fechaLista.getYear()+1900==fechaActual.getYear()) {//El +1 es por la estructura de java, si la fecha es la que buscamos:
				listaFacturas.add(facturas.get(fechaLista));//Entonces añadimos la factura a nuestra lista
			}
		}
		for(int i=1;i<listaCategorias.size();i++) {//Ahora recorreremos la lista de categorias por elemento
			double coste=0;
			for (ArrayList<Factura> arrayFacturas: listaFacturas) {//Recorreremos la lista de facturas para conseguir sus arrays
				for(Factura factura:arrayFacturas) {//Ahora recorremos los arrays para conseguir facturas individuales
					if(factura.getCategoria().getNombre().equals(listaCategorias.get(i))) {//Si la categoria coincide:
						coste=coste+factura.getCoste();//Entonces le añadimos su valor al Coste
					}
				}
			}
			resultado.add(coste);
		}
		return resultado;
	}
}