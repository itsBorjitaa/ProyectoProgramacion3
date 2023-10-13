package gestionFacturas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaCategorias extends JFrame {
	private JButton botonVolver, botonModificar, botonBorrar, botonAnyadir;
	private JScrollPane scrollClases;
	private JPanel panelDerechaBotones, panelIzquierda;
	private ArrayList<ClasesTickets> listaClases;
	private JFrame vActual;
	
	public VentanaCategorias() {
		vActual = this;
		
		/*CREACIÓN DE PANELES*/
		panelDerechaBotones = new JPanel(new GridLayout(4,1));
		panelIzquierda = new JPanel();
		
		/*CREACION COMPONENTES*/
		scrollClases = new JScrollPane();
		botonVolver = new JButton("Volver");
		botonModificar = new JButton("Modificar");
		botonBorrar = new JButton("Borrar");
		botonAnyadir = new JButton("Añadir");
		
		/*AÑADIR COMPONENTES A PANELES*/
		panelIzquierda.add(scrollClases);
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
		setBounds(300, 200, 600, 400);
		setTitle("Ventana Categorias");
		setVisible(true);
	}
}
