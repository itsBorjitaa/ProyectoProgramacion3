package gestionFacturas;

import com.formdev.flatlaf.FlatLightLaf;

import ventanas.VentanaInicioSesion;

public class Main {
	public static void main(String[] args) {
		
		
		FlatLightLaf.setup();
		VentanaInicioSesion vis = new VentanaInicioSesion();
	}
}