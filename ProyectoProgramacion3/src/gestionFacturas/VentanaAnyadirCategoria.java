package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAnyadirCategoria extends JFrame{

	private JButton botonAnyadir, botonCancelar;
	private JLabel lblTitulo, lblDescripcion;
	private JTextField txtTitulo, txtDescripcion;
	private JPanel panelArriba, panelAbajo;
	private JFrame vActual;
	
	public VentanaAnyadirCategoria() {
		vActual = this;
		
		/*CREACION DE PANELES*/
		panelArriba = new JPanel(new GridLayout(2,2));
		panelAbajo = new JPanel(new GridLayout(1,2));
		
		/*CREACION DE COMPONENTES*/
		botonAnyadir = new JButton("Añadir");
		botonCancelar = new JButton("Cancelar");
		lblTitulo = new JLabel("Titulo");
		lblDescripcion = new JLabel("Descripción");
		txtTitulo = new JTextField();
		txtDescripcion = new JTextField();
		
		/*AÑADIR COMPONENTES A PANELES*/
		panelArriba.add(lblTitulo);
		panelArriba.add(txtTitulo);
		panelArriba.add(lblDescripcion);
		panelArriba.add(txtDescripcion);
		panelAbajo.add(botonAnyadir);
		panelAbajo.add(botonCancelar);
		
		/*AÑADIR FUNCIONALIDADES A BOTONES*/
		botonAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias();
				vActual.dispose();
				
			}
		});
		
		/*AÑADIR PANELES A VENTANA*/
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		
		/*ESPECIFICACIÓN VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("Ventana añadir categorias");
		setVisible(true);
	}
	
}
