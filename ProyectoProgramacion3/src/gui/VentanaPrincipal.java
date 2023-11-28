package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

public class VentanaPrincipal extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panelCalendario, panelBotones;
	private JButton botonCategorias, botonGastos, botonDias, botonCerrarSesion;
	private JFrame vActual;
	private JCalendar calendario;
	private Logger logger = Logger.getLogger(VentanaPrincipal.class.getName());
	public String fechaSeleccionada;

	public VentanaPrincipal(String usuario) {
		/*Cargamos el usuario actual*/
		String usuarioActual=usuario;
		vActual = this;
		
		/**
		 * Paneles de la ventana principal
		 */
		panelBotones = new JPanel(new GridLayout(4,1));
		panelCalendario = new JPanel(new GridLayout(1,2));
		logger.info("Creados los paneles");
		
		/**
		 * Botones del panel de botones
		 */
		botonDias=new JButton("FACTURAS POR FECHA");
		botonCategorias = new JButton("CATEGORÍAS");
		botonGastos = new JButton("GASTOS");
		botonCerrarSesion = new JButton("CERRAR SESIÓN");
		logger.info("Creados los botones");
		
		/**
		 * Añadimos los botones al panel de botones
		 */
		panelBotones.add(botonDias);
		panelBotones.add(botonCategorias);
		panelBotones.add(botonGastos);
		panelBotones.add(botonCerrarSesion);
		getContentPane().add(panelBotones, BorderLayout.EAST);
		logger.info("Añadidos los botones al panel de botones");
		
		/**
		 * Añadimos calendario
		 */
		calendario = new JCalendar();
		panelCalendario.add(calendario);
		getContentPane().add(panelCalendario, BorderLayout.CENTER);
		calendario.setDate(new Date());
		Date fecha = calendario.getDate();
 	   	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 	   	String fechaSeleccionada = sdf.format(fecha);
 	   	//System.out.println(fechaFormateada);
 	   	setFechaSeleccionada(fechaSeleccionada);
		logger.info("Añadido el calendario al panel de calendario");
		
		/*EVENTOS*/
		botonCerrarSesion.addActionListener((e)->{
			//BaseDatos.guardarListaUsuariosEnFichero(RUTA_FICHEROS+"BDUsuario.csv");
			new VentanaInicioSesion();
			vActual.dispose();
			logger.info("Se ha cerrado sesión");
		});
		
		botonGastos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaGastos(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana principal y abierta la ventana de gastos");
			}
		});
		
		botonCategorias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCategorias(usuarioActual);
				vActual.dispose();
				logger.info("Cerrada la ventana principal y abierta la ventana de categorías");
			}
		});
		
		botonDias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fechaSeleccionada!=null) {
					new VentanaDiaCalendario(usuarioActual);
					vActual.dispose();
					logger.info("Cerrada la ventana principal y abierta la ventana del día del calendario");
					System.out.println("Fecha seleccionada: " + fechaSeleccionada);
				}else {
					JOptionPane.showMessageDialog(null, "No hay ninguna fecha seleccionada","ERROR",JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		//Agregar el listener para detectar cambios en la fecha
        calendario.addPropertyChangeListener("calendar", new PropertyChangeListener() {
           @Override
           public void propertyChange(PropertyChangeEvent e) {
        	   Date fecha = calendario.getDate();
        	   SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        	   String fechaSeleccionada = sdf.format(fecha);
        	   //System.out.println(fechaFormateada);
        	   setFechaSeleccionada(fechaSeleccionada);
           }
        });
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("DeustoFinanzas");
		setVisible(true);
	}
	
	// Método para establecer la fecha seleccionada
    private void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
        System.out.println(fechaSeleccionada);
    }
	
}
