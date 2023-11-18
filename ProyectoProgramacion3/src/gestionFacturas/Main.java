package gestionFacturas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.formdev.flatlaf.FlatLightLaf;

import ventanas.VentanaInicioSesion;

public class Main {
	
	private static final String PROPIEDADES = "config.properties";
	
	public static void main(String[] args) {
		Properties propiedades = new Properties();
		
		try (InputStream entrada = Main.class.getClassLoader().getResourceAsStream(PROPIEDADES)) {

            if (entrada == null) {
                System.out.println("No se puede encontrar el archivo " + PROPIEDADES);
                return;
            }

            // Cargar las propiedades desde el archivo
            propiedades.load(entrada);

            // Obtener los valores de las propiedades
            String nombreApp = propiedades.getProperty("app.nombre");
            String versionApp = propiedades.getProperty("app.version");
            String fechaActualizacion = propiedades.getProperty("app.fecha_actualizacion");

            // Mostrar las propiedades
            System.out.println("Nombre de la aplicación: " + nombreApp);
            System.out.println("Versión de la aplicación: " + versionApp);
            System.out.println("Fecha de última actualización: " + fechaActualizacion);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		FlatLightLaf.setup();
		VentanaInicioSesion vis = new VentanaInicioSesion();
	}
}