package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;

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
	Categoria pruebaCategoria = new Categoria("prueba");
	Factura factura = new Factura("pruebaFactura", 20, agua);
	String[] categoriasPorDefecto = {"AGUA", "ALIMENTACION", "GAS", "LUZ", "OCIO"};
	
	@Before
	public void setUp() throws Exception {
		/*CREACIÓN DE TABLAS*/
		BaseDatos.crearTablaUsuariosBD(con);
		BaseDatos.crearTablaFacturasBD(con);
		BaseDatos.crearTablaCategoriasBD(con);
		BaseDatos.crearTablaCategoriasUsuarioBD(con);
		
		/*INSERTAR USUARIOS DE PRUEBA*/
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
	public void testBuscarCategoriaBD() {
		assertTrue(BaseDatos.buscarCategoriaBD(con, "AGUA").getNombre().equals("AGUA"));
	}

	@Test
	public void testInsertarCategoriasBD() {
		BaseDatos.insertarCategoriasBD(con, new Categoria("prueba"));
		assertTrue(BaseDatos.buscarCategoriaBD(con, "PRUEBA").getNombre().equals("PRUEBA"));
	}

	@Test
	public void testInsertarCategoriasPorUsuario() {
		BaseDatos.insertarCategoriasPorUsuario(con, "prueba4", pruebaCategoria);
		ArrayList<Categoria> listaCategorias = BaseDatos.cargarCategoriasPorUsuario(con, "prueba4");
		for (Categoria categoria: listaCategorias) {
			assertTrue(categoria.getNombre().equals(pruebaCategoria.getNombre()));
		}
		BaseDatos.borrarCategoriasPorUsuario(con, "prueba4", pruebaCategoria);
	}
	
	@Test
	public void testAnyadirCategoriasUsuarioNuevo() {
		BaseDatos.anyadirCategoriasUsuarioNuevo(con, "prueba1");
		ArrayList<Categoria> listaCategorias = BaseDatos.cargarCategoriasPorUsuario(con, "prueba1");
		for (int i = 1; i < categoriasPorDefecto.length; i++) {
			assertEquals(listaCategorias.get(i), categoriasPorDefecto[i]);
		}
		BaseDatos.eliminarUsuarioBD(con, "prueba1");
	}
	
	@After
	public void tearDown() throws Exception {
		BaseDatos.closeBD(con);
	}

}
