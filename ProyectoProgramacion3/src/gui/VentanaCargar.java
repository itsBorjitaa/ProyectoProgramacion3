package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.util.Random;
import java.util.logging.Logger;

public class VentanaCargar extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar barraProgreso;
	private JLabel lblProgreso;
	private Logger logger = Logger.getLogger(VentanaInicioSesion.class.getName());
	
	public VentanaCargar () {
		/*CREACION DE PANELES*/
		JPanel pArriba = new JPanel();
		JPanel pAbajo = new JPanel();
		getContentPane().add(pArriba, BorderLayout.NORTH);
		getContentPane().add(pAbajo, BorderLayout.SOUTH);
		logger.info("Paneles creados");
		
		/*CREACION DE COMPONENETES*/
		barraProgreso = new JProgressBar();
		lblProgreso = new JLabel("PRUEBAS");
		pAbajo.add(lblProgreso);
		pArriba.add(barraProgreso);
		logger.info("Añadidos barra de progreso y label al panel principal");
		
		/*ESPECIFICACION VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(450, 300, 600, 100);
		setTitle("DeustoFinanzas");
		setVisible(true);
		
		ThreadProgreso thread = new ThreadProgreso(barraProgreso, lblProgreso, this);
		thread.start();
	}
	
}

class ThreadProgreso extends Thread {
	private JProgressBar barraProgreso;
    private JLabel label;
    private VentanaCargar ventanaCargar;

    public ThreadProgreso(JProgressBar barraProgreso, JLabel label, VentanaCargar ventanaCargar) {
		this.barraProgreso = barraProgreso;
		this.label = label;
		this.ventanaCargar = ventanaCargar;
	}

	@Override
    public void run() {
        Random random = new Random();
        int progreso = 0;

        while (progreso < 100) {
            try {
                Thread.sleep(50); 

                progreso += random.nextInt(10);
                progreso = Math.min(progreso, 100);

                final int progresoFinal = progreso;

                SwingUtilities.invokeLater(() -> {
                    barraProgreso.setValue(progresoFinal);
                    label.setText("Progreso: " + progresoFinal + "%");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> {
        	ventanaCargar.dispose();
        	new VentanaPrincipal(VentanaInicioSesion.usuario);
        });
    }
}

