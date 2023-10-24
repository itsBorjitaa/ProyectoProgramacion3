package gestionFacturas;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
	public static void main(String[] args) {
		
		//Categorías por defecto
		Categoria c1 = new Categoria("Ocio");
		Categoria c2 = new Categoria("Alimentación");
		Categoria c3 = new Categoria("Luz");
		Categoria c4 = new Categoria("Agua");
		Categoria c5 = new Categoria("Gas");
		BaseDatos.aniadirCategorias(c1);
		BaseDatos.aniadirCategorias(c2);
		BaseDatos.aniadirCategorias(c3);
		BaseDatos.aniadirCategorias(c4);
		BaseDatos.aniadirCategorias(c5);
		
		FlatLightLaf.setup();
		VentanaInicioSesion vis = new VentanaInicioSesion();
	}
}