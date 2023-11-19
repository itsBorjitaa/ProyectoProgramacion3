package ventanas;

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
		logger.info("Añaidos barra de progreso y label al panel principal");
		
		/*ESPECIFICACION VENTANA*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        int progress = 0;

        while (progress < 100) {
            try {
                Thread.sleep(50); // Simulate some work being done

                progress += random.nextInt(10);
                progress = Math.min(progress, 100);

                final int finalProgress = progress;

                SwingUtilities.invokeLater(() -> {
                    barraProgreso.setValue(finalProgress);
                    label.setText("Progress: " + finalProgress + "%");
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

