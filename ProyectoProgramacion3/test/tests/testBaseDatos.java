package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import main.*;

public class testBaseDatos {
	//Primero inicializaremos la BD
	Connection con= BaseDatos.initBD("test/BaseDatosTest.db");

	/* BASE DE DATOS*/
	
	@Test
	public void crearTablasBD() {
	/*Añadiremos las tablas a la BD si no estan creadas*/
	BaseDatos.crearTablaUsuariosBD(BaseDatos.initBD("test/BaseDatosTest.db"));
	BaseDatos.crearTablaFacturasBD(BaseDatos.initBD("test/BaseDatosTest.db"));
	}
	
	@Test
	public void testbuscarUsuarioBD() {
		
		/*Comprobamos que encuentre el usuario de nombre prueba y que devuelva su nombre y contraseña*/
		assertEquals(BaseDatos.buscarUsuarioBD(con, "prueba"),new Usuario("prueba","12345"));
		
	}
	
	@Test	
	public void testInsertarUsuarioBD(){
		/*Crearemos un usuario y despues usaremos buscarUsuarioBD para saber que existe*/
		BaseDatos.insertarUsuarioBD(con, new Usuario("Juan","contrasenya"));//Primero insertaremos el usuario
		assertEquals(BaseDatos.buscarUsuarioBD(con,"Juan"), new Usuario("Juan", "contrasenya"));//Ahora lo buscamos
	}
	@Test
	public void testInsertarFacturaBD() {
		/*Insertamos una factura a la BD*/
		
		BaseDatos.insertarFacturaBD(con, new Factura("Agua", 50.00, new Categoria("Agua")),"prueba", new Date(2023-10-2));
		
		//Con este segundo saltara un usuario no encontrado:"
		
		BaseDatos.insertarFacturaBD(con, new Factura("Agua", 50.00, new Categoria("Agua")),"Pepe", new Date(2023-10-2));
		
		BaseDatos.insertarFacturaBD(con, new Factura("Luz", 50.00, new Categoria("Luz")),"Juan", new Date(2023-10-2));
	}
	@Test
	public void testModificarFacturaBD() {
		/*Modificaremos el usuario Juan en base a su codigo*/
		BaseDatos.modificarFacturaBD(con, new Factura("Comida",20.00, new Categoria("Comida")), new Date(2023-10-2), 2);
	}
	@Test
	public void eliminarFacturaBD() {
		BaseDatos.eliminarFacturaBD(con, 3);//Esto hara que se borren los anteriores 3 y 4
		BaseDatos.eliminarFacturaBD(con, 4);//Debido a que introducimos otras 2 categorias las borradas seran sustituidas
											//por las nuevas
	}
	@Test 
	public void testCargarFacturaBD() {
		List<ArrayList<Factura>> facturas=new ArrayList<>();//Crearemos una lista de arrays ya que se nos devuelve esto
		ArrayList<Factura> FacturaActual=new ArrayList<>();//Este 
		
		//Primero comprobaremos que nos devuelva los datos correctos para el usuario prueba
		HashMap<Date, ArrayList<Factura>> ticketsPorFecha= BaseDatos.cargarFacturaBD(con, "prueba");
		for(ArrayList<Factura> facturasFor: ticketsPorFecha.values()) {
			facturas.add(facturasFor);
		}
		FacturaActual=facturas.get(0);
		Factura factura1=FacturaActual.get(0);
		assertEquals(factura1.getCodigo(), 1,0);
		assertEquals(factura1.getConcepto(), "Agua");
		assertEquals(factura1.getCoste(), 50.00,0);
		assertEquals(factura1.getCategoria().getNombre(), "Agua");
		
		//Ahora comprobamos que nos devuelva la factura del usuario Juan, de paso podemos comprobar si se modifico
		HashMap<Date, ArrayList<Factura>> ticketsPorFecha2= BaseDatos.cargarFacturaBD(con, "Juan");
		for(ArrayList<Factura> facturasFor: ticketsPorFecha2.values()) {
			facturas.add(facturasFor);
		}
		
		FacturaActual=facturas.get(1);
		Factura factura2=FacturaActual.get(0);
		assertEquals(factura2.getCodigo(), 2,0);
		assertEquals(factura2.getConcepto(), "Comida");
		assertEquals(factura2.getCoste(), 20.00,0);
		assertEquals(factura2.getCategoria().getNombre(), "Comida");
	}
}
