package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;

import gui.VentanaInicioSesion;

@SuppressWarnings("unused")
public class Main {
	
	private static final String PROPIEDADES = "conf.properties";
	private static final String RUTA= "resources/db/BaseDatos.db";
	public static void main(String[] args) {
		
		Logger logger = java.util.logging.Logger.getLogger("Logger");
		
		try {
			FileInputStream fis = new FileInputStream("conf/logger.properties");
			LogManager.getLogManager().readConfiguration(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Properties propiedades = new Properties();
		Connection con=BaseDatos.initBD(RUTA);
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
		
		
	
		//Creamos la base de datos
		BaseDatos.crearTablaUsuariosBD(con);
		BaseDatos.crearTablaFacturasBD(con);
		BaseDatos.crearTablaCategoriasBD(con);
		BaseDatos.crearTablaCategoriasUsuarioBD(con);

		FlatArcIJTheme.setup(); //inicializa un tema personalizado
		VentanaInicioSesion vis = new VentanaInicioSesion();
	}
}