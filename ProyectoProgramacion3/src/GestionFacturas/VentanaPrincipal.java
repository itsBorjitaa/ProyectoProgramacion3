package GestionFacturas;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;

public class VentanaPrincipal extends JFrame{
	private JTable tabla;
	private List<Factura> facturas;
	
	public VentanaPrincipal() {
		setBounds(500,200,600,500);
		setTitle("Aplicación de Gestión de Facturas");
		setVisible(true);
	}
}
