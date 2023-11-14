package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Test;

import gestionFacturas.*;

public class testBaseDatos {
	/* BASE DE DATOS USUARIOS */
	@Test
	public void testCargarFicheroUsuariosEnLista() throws Exception {
		
		/*Comprobamos el funcionamiento general*/
		BaseDatos.cargarFicheroUsuariosEnLista("test/BDUsuarioTest.csv");
		
		/*Comprobamos que suelta una excepcion al cargar un fichero vacio*/
		BaseDatos.cargarFicheroUsuariosEnLista("A");
		
	}
	@Test
	public void testbuscarUsuario() {
		BaseDatos.cargarFicheroUsuariosEnLista("test/BDUsuarioTest.csv");
		
		/*Comprobamos el funcionamiento general*/
		assertEquals(BaseDatos.buscarUsuario("prueba"), new Usuario("prueba","12345"));
		
		/*Comprobamos que devuelva null si no se encuentra el usuario*/
		assertEquals(BaseDatos.buscarUsuario("pepe"), null);
	}
	@Test
	public void testguardarListaUsuariosEnFichero() {
		
		/*Comprobamos el funcionamiento general*/
		
		//Cargamos los usuarios para que no se borren los contenidos del csv
		BaseDatos.cargarFicheroUsuariosEnLista("test/BDUsuarioTest.csv");
		
		BaseDatos.guardarListaUsuariosEnFichero("test/BDUsuarioTest.csv");
		
		/*Creamos una ruta falsa para ver si se lanza la excepcion
		 * Ya que al ser un printwriter solo lanza FileNotFound al no detectar la ruta
		 */
		//BaseDatos.guardarListaUsuariosEnFichero("prueba:\\user\\Desktop\\dir1\\dir2\\filename.txt");
	}
	/* BASE DE DATOS CATEGORIAS */
	@Test
	public void testguardarListaCategoriasEnFichero() {
		BaseDatos.guardarListaCategoriasEnFichero("test/BDCategoriasTest.csv");
		/*Creamos una ruta falsa para ver si se lanza la excepcion
		 * Ya que al ser un printwriter solo lanza FileNotFound al no detectar la ruta
		 */
		//BaseDatos.guardarListaCategoriasEnFichero("prueba:\\user\\Desktop\\dir1\\dir2\\filename.txt");
	}
}
