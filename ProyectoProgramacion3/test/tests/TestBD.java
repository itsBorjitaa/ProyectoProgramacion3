package tests;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.BaseDatos;
import domain.Categoria;
import domain.Factura;
import domain.Usuario;

public class TestBD {
	
	Connection con = BaseDatos.initBD("test/BaseDatosTest.db");
	Usuario prueba = new Usuario("prueba1","12345");
	Usuario prueba2 = new Usuario("prueba2", "11111");
	Categoria agua = new Categoria("AGUA");
	Factura factura = new Factura("pruebaFactura", 20, agua);
	
	@Before
	public void setUp() throws Exception {
		BaseDatos.crearTablaUsuariosBD(con);
		BaseDatos.crearTablaFacturasBD(con);
		BaseDatos.crearTablaCategoriasBD(con);
		BaseDatos.crearTablaCategoriasUsuarioBD(con);
		BaseDatos.insertarUsuarioBD(con, new Usuario("prueba1", "11111"));
		BaseDatos.insertarUsuarioBD(con, new Usuario("Juan", "1111"));
		BaseDatos.insertarUsuarioBD(con, new Usuario("prueba3", "11111"));
		BaseDatos.insertarUsuarioBD(con, new Usuario("prueba4", "11111"));
		BaseDatos.insertarUsuarioBD(con, new Usuario("prueba5", "11111"));
	}

	@Test
	public void testBuscarUsuarioBD() {
		assertEquals(BaseDatos.buscarUsuarioBD(con, "prueba1"), prueba);
	}

	@Test
	public void testInsertarUsuarioBD() {
		BaseDatos.insertarUsuarioBD(con, prueba2);
		assertEquals(BaseDatos.buscarUsuarioBD(con, "prueba2"), prueba2);
	}

	@Test
	public void testEliminarUsuarioBD() {
		BaseDatos.eliminarUsuarioBD(con, "prueba2");
		assertTrue(BaseDatos.buscarUsuarioBD(con, "prueba2") == null);
	}
	
	@Test
	public void testModificarNombreUsuarioBD() {
		BaseDatos.modificarNombreUsuarioBD(con, "Juan", "prueba2");
		assertTrue(BaseDatos.buscarUsuarioBD(con, prueba2.getNombre()).equals(prueba2));
		BaseDatos.modificarNombreUsuarioBD(con, "prueba2", "Juan");
	}
	
	
	@Test
	public void testModificarContrasenyaUsuarioBD() {
		BaseDatos.modificarContrasenyaUsuarioBD(con, "prueba3", "11112");
		assertTrue(BaseDatos.buscarUsuarioBD(con, "prueba3").getContrasenya().equals("11112"));
	}
	
	
	
	@Test
	public void testModificarFacturaBD() {
		BaseDatos.modificarFacturaBD(con, factura, "17-01-2024", 5);
	}
	
	@Test
	public void testEliminarFacturaBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testCargarFacturaBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testCrearTablaCategoriasBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarCategoriaBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertarCategoriasBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertarCategoriasPorUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testCrearTablaCategoriasUsuarioBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testAnyadirCategoriasUsuarioNuevo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCargarCategoriasPorUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testBorrarCategoriasPorUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testModificarCategoriaPorUsuario() {
		fail("Not yet implemented");
	}
	
	@After
	public void tearDown() throws Exception {
		BaseDatos.closeBD(con);
	}

}
