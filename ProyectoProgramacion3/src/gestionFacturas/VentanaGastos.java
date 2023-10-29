package gestionFacturas;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaGastos extends JFrame{
	
	private JPanel panelTabla,panelBotones;
	private JTable tablaGastos;
	private JScrollPane scrollTabla;
	private JRadioButton botonMes,botonTrimestre,botonAnyo;
	private ButtonGroup grupoBotones;
	private JButton botonVolver;
	private List<String> listaCategorias;
	public VentanaGastos() {
		
		/*Paneles de la ventana gastos*/

		panelBotones=new JPanel(new GridLayout(5,1));
		panelTabla=new JPanel(new BorderLayout());
		
		/*Elementos del panel de botones*/
		botonVolver=new JButton("Volver");
		botonMes=new JRadioButton("Mensual");
		botonTrimestre=new JRadioButton("Trimestral"); 
		botonAnyo=new JRadioButton("Anual");
		grupoBotones=new ButtonGroup();
		
		/*Configuramos el funcionamiento de los botones*/
		grupoBotones.add(botonMes); grupoBotones.add(botonTrimestre); grupoBotones.add(botonAnyo);
		botonMes.setSelected(true); //Hacemos que el boton del mes este seleccionado desde el principio
		
		
		
		 /*Creamos el modelo de la tabla y cargamos las categorias*/
		listaCategorias=new ArrayList<String>();
		cargarCategoriasTabla();
		TableModel modeloTabla= new AbstractTableModel() {
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return null;
					}
			
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return listaCategorias.size()+1;
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
		scrollTabla=new JScrollPane(tablaGastos);
		
		/*Añadimos funcionalidades a los botones*/
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaPrincipal();
				dispose();
			}
		});
		
		botonMes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tablaGastos.repaint();
			}
		});
		botonTrimestre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			tablaGastos.repaint();	
			}
		});
		botonAnyo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
		
		/*Añadimos los paneles*/
		
		add(panelTabla,BorderLayout.NORTH);
		add(panelBotones,BorderLayout.SOUTH);
		
		setTitle("Ventana Gastos");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 800, 600);
		setVisible(true);
	}
	private void cargarCategoriasTabla() { //Usaremos este metodo para crear un array con las columas de la tabla
		listaCategorias.add("Mes/Intervalo");//Esta sera la primera columna
		for(Categoria c: BaseDatos.getCategorias()) {//Recorreremos el set de columnas de la base de datos
			listaCategorias.add(c.getNombre());//añadiremos a la lista sus nombres
		}
	}
}
