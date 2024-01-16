package db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import domain.Categoria;
import domain.Factura;
import domain.Usuario;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

public class BaseDatos {
	/* BASE DE DATOS FINAL */
	public static Connection initBD(String nombreBD) {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+nombreBD+"?foreign_keys=on");
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void closeBD(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void crearTablaUsuariosBD(Connection con) {
		try {
			PreparedStatement crearTabalaUsuario = con.prepareStatement("CREATE TABLE IF NOT EXISTS Usuario (usuario VARCHAR(255) NOT NULL,"
					+ "contrasenya VARCHAR(255) NOT NULL," + "PRIMARY KEY(usuario))");
			crearTabalaUsuario.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Usuario buscarUsuarioBD(Connection con, String nombre) {
	    Usuario p = null;
	    try {
	        PreparedStatement buscarUsuario = con.prepareStatement("SELECT * FROM Usuario WHERE usuario = ?");
	        buscarUsuario.setString(1, nombre);
	        ResultSet rs = buscarUsuario.executeQuery();
	        if(rs.next()) {
	            String usuario = rs.getString("usuario");
	            String contrasenya = rs.getString("contrasenya");
	            p = new Usuario(usuario, contrasenya);
	        }
	        rs.close();
	        buscarUsuario.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return p;
	}
	
	public static void insertarUsuarioBD(Connection con, Usuario u) {
		if(buscarUsuarioBD(con, u.getNombre()) == null) {
			try {
				PreparedStatement insertarUsuario = con.prepareStatement("INSERT INTO Usuario (usuario, contrasenya) VALUES (?, ?)");
				insertarUsuario.setString(1, u.getNombre());
				insertarUsuario.setString(2, u.getContrasenya());
				insertarUsuario.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void eliminarUsuarioBD(Connection con, String nombre) {
	    try {
		    for(Categoria categoria:BaseDatos.cargarCategoriasPorUsuario(con, nombre)) {
		    	BaseDatos.borrarCategoriasPorUsuario(con, nombre, categoria);
		    }
	        PreparedStatement eliminarUsuario = con.prepareStatement("DELETE FROM Usuario WHERE usuario = ?");
	        eliminarUsuario.setString(1, nombre);
	        eliminarUsuario.executeUpdate();
	        eliminarUsuario.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void modificarNombreUsuarioBD(Connection con, String usuario, String nuevoNombre) {
        try {
        	Usuario datosAntiguoUsuario=buscarUsuarioBD(con, usuario);
        	Usuario usuarionuevo=new Usuario(nuevoNombre,datosAntiguoUsuario.getContrasenya());
        	BaseDatos.insertarUsuarioBD(con, usuarionuevo);
        	PreparedStatement modificarFacturasUsuario = con.prepareStatement("UPDATE Facturas SET usuarioF = ? WHERE usuarioF = ?");
        	modificarFacturasUsuario.setString(1, usuarionuevo.getNombre());
            modificarFacturasUsuario.setString(2, usuario);
            modificarFacturasUsuario.executeUpdate();
            PreparedStatement modificarcategoriasUsuario = con.prepareStatement("UPDATE categoriasUsuario SET usuario_cu = ? WHERE usuario_cu = ?");
            modificarcategoriasUsuario.setString(1, usuarionuevo.getNombre());
            modificarcategoriasUsuario.setString(2, usuario);
            modificarcategoriasUsuario.executeUpdate();
            BaseDatos.eliminarUsuarioBD(con, usuario);            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }

    public static void modificarContrasenyaUsuarioBD(Connection con, String usuario, String nuevaContrasenya) {
        try {
            PreparedStatement modificarContrasenyaUsuario = con.prepareStatement("UPDATE Usuario SET contrasenya = ? WHERE usuario = ?");
            modificarContrasenyaUsuario.setString(1, nuevaContrasenya);
            modificarContrasenyaUsuario.setString(2, usuario);
            modificarContrasenyaUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	/* BASE DE DATOS FACTURA POR FECHA */
	
	/* FUNCIÓN PARA CREAR LA TABLA SI NO ESTA CREADA */
	public static void crearTablaFacturasBD(Connection con) {
		String sql = "CREATE TABLE IF NOT EXISTS Facturas (codigo INTEGER NOT NULL DEFAULT 0, usuarioF TEXT NOT NULL, fecha TEXT NOT NULL, concepto TEXT NOT NULL, "
				+ "coste REAL NOT NULL, categoria TEXT NOT NULL,"
				+ "PRIMARY KEY(codigo), FOREIGN KEY(usuarioF) REFERENCES Usuario(usuario) ON DELETE CASCADE)";
		try {
			Statement pragma = con.createStatement();
			pragma.execute("PRAGMA foreign_keys = ON;");
			
			PreparedStatement crearTablaFacturas = con.prepareStatement(sql);
			crearTablaFacturas.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA INSERTAR DATOS A LA TABLA */
	public static void insertarFacturaBD(Connection con, Factura f, String usuario, String fecha) {
			Integer codigo=0;
			try {
				PreparedStatement generadorCodigo = con.prepareStatement("SELECT codigo FROM Facturas");
				ResultSet rs = generadorCodigo.executeQuery();
				while(rs.next()){
					codigo=rs.getInt("codigo");
				}
				rs.close();
				generadorCodigo.close();
				}			 
			catch (SQLException e) {
				e.printStackTrace();
				}
			try {
				PreparedStatement insertarFactura = con.prepareStatement("INSERT INTO Facturas (codigo, usuarioF, fecha, concepto, coste, categoria)"
						+ " VALUES (?, ?, ?, ?, ?, ?)");
				insertarFactura.setInt(1, codigo+1);
				insertarFactura.setString(2, usuario);
				insertarFactura.setString(3, fecha);
				insertarFactura.setString(4, f.getConcepto());
				insertarFactura.setDouble(5, f.getCoste());
				insertarFactura.setString(6, f.getCategoria().getNombre());
				insertarFactura.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Usuario no encontrado");
			}
		}
	
	/* FUNCIÓN PARA MODIFICAR DATOS */
	public static void modificarFacturaBD(Connection con, Factura nuevaF, String nuevaFecha, Integer codigo) {
		try {
			PreparedStatement modificarFactura = con.prepareStatement("UPDATE Facturas SET fecha = ?, concepto = ?, coste = ?, categoria = ? WHERE codigo = ?");
			modificarFactura.setString(1, nuevaFecha);
			modificarFactura.setString(2, nuevaF.getConcepto());
			modificarFactura.setDouble(3, nuevaF.getCoste());
			modificarFactura.setString(4, nuevaF.getCategoria().getNombre());
			modificarFactura.setInt(5, codigo);
			modificarFactura.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA ELIMINAR DATOS */
	public static void eliminarFacturaBD(Connection con,Integer codigo) {
		try {
			PreparedStatement eliminarFactura = con.prepareStatement("DELETE FROM Facturas WHERE codigo = ?");
			eliminarFactura.setInt(1, codigo);
			eliminarFactura.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA CARGAR LOS DATOS */
	public static HashMap<Date, ArrayList<Factura>> cargarFacturaBD(Connection con, String usuario) {
		HashMap<Date, ArrayList<Factura>> facturasPorFecha= new HashMap<>();
		try {
			PreparedStatement cargarFactura = con.prepareStatement("SELECT codigo, fecha, concepto, coste, categoria "
					+ "FROM Facturas WHERE usuarioF = ?");
			cargarFactura.setString(1, usuario);
			ResultSet rs= cargarFactura.executeQuery();
			while(rs.next()) {
				String stringFecha=rs.getString("fecha");
				String [] arrayFecha=stringFecha.split("-");
				@SuppressWarnings("deprecation")
				Date fechaDate=new Date(Integer.parseInt(arrayFecha[0])-1900, Integer.parseInt(arrayFecha[1])-1, Integer.parseInt(arrayFecha[2]));
				facturasPorFecha.putIfAbsent(fechaDate, new ArrayList<Factura>());
				Factura f=new Factura(rs.getString("concepto"),rs.getDouble("coste"), new Categoria(rs.getString("categoria")));
				f.setCodigo(rs.getInt("codigo"));
				facturasPorFecha.get(fechaDate).add(f);
			}
			rs.close();
			cargarFactura.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return facturasPorFecha;
	}

	
	/* BASE DE DATOS CATEGORIAS*/
	
	/* FUNCION CREAR TABLA DE CATEGORIAS*/
	public static void crearTablaCategoriasBD(Connection con) {
		try {
			PreparedStatement crearTablaCategorias = con.prepareStatement("CREATE TABLE IF NOT EXISTS Categorias (id_c INTEGER NOT NULL, "
					+ "categoria STRING NOT NULL, PRIMARY KEY(id_c))");
			crearTablaCategorias.executeUpdate();
			PreparedStatement llenarTablaCategorias = con.prepareStatement("INSERT INTO Categorias VALUES(?,?)");
			llenarTablaCategorias.setInt(1, 1);
			llenarTablaCategorias.setString(2, "AGUA");
			llenarTablaCategorias.executeUpdate();
			llenarTablaCategorias.setInt(1, 2);
			llenarTablaCategorias.setString(2, "ALIMENTACION");
			llenarTablaCategorias.executeUpdate();
			llenarTablaCategorias.setInt(1, 3);
			llenarTablaCategorias.setString(2, "GAS");
			llenarTablaCategorias.executeUpdate();
			llenarTablaCategorias.setInt(1, 4);
			llenarTablaCategorias.setString(2, "LUZ");
			llenarTablaCategorias.executeUpdate();
			llenarTablaCategorias.setInt(1, 5);
			llenarTablaCategorias.setString(2, "OCIO");
			llenarTablaCategorias.executeUpdate();
			
		} catch (SQLException e) {
			
		}
	}
	
	/* FUNCION BUSCAR CATEGORIAS EN TABLA*/
	public static Categoria buscarCategoriaBD(Connection con, String nombre) {
		Categoria categoria = null;
		
		try {
			PreparedStatement buscarCategoria = con.prepareStatement("SELECT * FROM Categorias WHERE categoria = ?");
			buscarCategoria.setString(1, nombre);
			ResultSet rs = buscarCategoria.executeQuery();
			if(rs.next()) {
				String c = rs.getString("categoria");
				categoria = new Categoria(c);
			}
			rs.close();
			buscarCategoria.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}
	
	/* FUNCION AÑADIR CATEGORIAS A LA TABLA */
	public static void insertarCategoriasBD(Connection con, Categoria categoria) {
		Integer id_c = 0;
		String consegirID = "SELECT id_c FROM Categorias";
		try {
			PreparedStatement buscarId = con.prepareStatement(consegirID);
			ResultSet rs = buscarId.executeQuery();
			while(rs.next()){
				id_c=rs.getInt("id_c");
			}
			rs.close();
			buscarId.close();
			}			 
		catch (SQLException e) {
			e.printStackTrace();
			}
		if (buscarCategoriaBD(con, categoria.getNombre()) == null) {
			try {
				PreparedStatement insertarCategoria = con.prepareStatement("INSERT INTO Categorias VALUES(?, ?)");
				insertarCategoria.setString(1, String.valueOf(id_c+1));
				insertarCategoria.setString(2, categoria.getNombre());
				insertarCategoria.executeUpdate();
				insertarCategoria.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* FUNCION AÑADIR CATEGORIA A TABLA USURIO*/
	public static void insertarCategoriasPorUsuario(Connection con, String usuario, Categoria categoria) {
		Integer id = 0;
		
		try {
			PreparedStatement buscarIdCategoria = con.prepareStatement("SELECT id_c FROM categorias WHERE categoria = ?");
			buscarIdCategoria.setString(1, categoria.getNombre());
			ResultSet rs = buscarIdCategoria.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id_c");
			}
			rs.close();
			buscarIdCategoria.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			PreparedStatement insertarCategoriaUsuario = con.prepareStatement("INSERT INTO categoriasUsuario VALUES(?, ?)");
			insertarCategoriaUsuario.setString(1, usuario);
			insertarCategoriaUsuario.setString(2, String.valueOf(id));
			insertarCategoriaUsuario.executeUpdate();
			insertarCategoriaUsuario.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* CREAR TABLA DE USUARIOS Y CATEGORIAS*/
	public static void crearTablaCategoriasUsuarioBD(Connection con) {
		
		try {
			PreparedStatement crearTablaCategoriasUsuario = con.prepareStatement("CREATE TABLE IF NOT EXISTS categoriasUsuario (usuario_cu String NOT NULL, id_c_cu INTEGER NOT NULL,PRIMARY KEY(usuario_cu, id_c_cu), FOREIGN KEY(usuario_cu) REFERENCES Usuario(usuario) ON DELETE CASCADE, FOREIGN KEY(id_c_cu) REFERENCES  Categorias(id_c) ON DELETE CASCADE)");
			crearTablaCategoriasUsuario.executeUpdate();
			crearTablaCategoriasUsuario.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* AÑADIR USUARIO NUEVOS A TABLA */
	public static void anyadirCategoriasUsuarioNuevo(Connection con, String usuario) {
				
		try {
			PreparedStatement anyadirCategoriasPorDefecto = con.prepareStatement("INSERT INTO categoriasUsuario VALUES(?, ?)");
			anyadirCategoriasPorDefecto.setString(1, usuario);
			anyadirCategoriasPorDefecto.setInt(2, 1);
			anyadirCategoriasPorDefecto.executeUpdate();
			anyadirCategoriasPorDefecto.setInt(2, 2);
			anyadirCategoriasPorDefecto.executeUpdate();
			anyadirCategoriasPorDefecto.setInt(2, 3);
			anyadirCategoriasPorDefecto.executeUpdate();
			anyadirCategoriasPorDefecto.setInt(2, 4);
			anyadirCategoriasPorDefecto.executeUpdate();
			anyadirCategoriasPorDefecto.setInt(2, 5);
			anyadirCategoriasPorDefecto.executeUpdate();
			anyadirCategoriasPorDefecto.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCION CARGAR CATEGORIAS POR USURIO */
	public static ArrayList<Categoria> cargarCategoriasPorUsuario(Connection con, String usuario) {
		ArrayList<Integer> listaIdCategorias = new ArrayList<Integer>();
		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();
				
		try {
			PreparedStatement seleccionarIdCategoria = con.prepareStatement("SELECT id_c_cu FROM categoriasUsuario WHERE usuario_cu = ?");
			seleccionarIdCategoria.setString(1, usuario);
			ResultSet rs = seleccionarIdCategoria.executeQuery();
			while (rs.next()) {
				listaIdCategorias.add(rs.getInt("id_c_cu"));
			}
			rs.close();
			seleccionarIdCategoria.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Integer id: listaIdCategorias) {
			
			try {
				PreparedStatement seleccionaCategoria = con.prepareStatement("SELECT categoria FROM Categorias WHERE id_c = ?");
				seleccionaCategoria.setString(1, String.valueOf(id));
				ResultSet rs = seleccionaCategoria.executeQuery();
				while (rs.next()) {
					listaCategorias.add(new Categoria(rs.getString("categoria")));
				}
				rs.close();
				seleccionaCategoria.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaCategorias;
	}
	
	/* FUNCION BORRAR CATEGORIAS POR USUARIO */
	public static void borrarCategoriasPorUsuario(Connection con, String usuario, Categoria categoria) {
		int id = 0;
		
		try {										//BORRA LA LINEA DE CATEGORIA DE LA TABLA categoriausuario
			PreparedStatement borrarFacturasCategoria = con.prepareStatement("DELETE FROM Facturas where categoria = ? AND usuarioF = ?");
			borrarFacturasCategoria.setString(1, categoria.getNombre());
			borrarFacturasCategoria.setString(2, usuario);
			borrarFacturasCategoria.executeUpdate();
			borrarFacturasCategoria.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		try {										//BUSCA LA ID DE LA CATEGORIA TENIENDO EN CUENTA EL NOMBRE
			PreparedStatement seleccionaIdCategoria = con.prepareStatement("SELECT id_c FROM categorias WHERE categoria = ?");
			seleccionaIdCategoria.setString(1, categoria.getNombre());
			ResultSet rs = seleccionaIdCategoria.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id_c");
			}
			rs.close();
			seleccionaIdCategoria.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		try {										//BORRA LA LINEA DE CATEGORIA DE LA TABLA categoriausuario
			PreparedStatement eliminaCategoria = con.prepareStatement("DELETE FROM categoriasUsuario where id_c_cu = ? AND usuario_cu = ?");
			eliminaCategoria.setString(1, String.valueOf(id));
			eliminaCategoria.setString(2, usuario);
			eliminaCategoria.executeUpdate();
			eliminaCategoria.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Integer cantidad = 0;
		
		try {										//CUENTA CUANTAS VECES APARECE UNA CATEGORIA EN LA TABLA categoriasusuario
			PreparedStatement contarCategorias = con.prepareStatement("SELECT COUNT(id_c_cu) AS cantidad FROM categoriasusuario WHERE id_c_cu = ?");
			contarCategorias.setString(1, String.valueOf(id));
			ResultSet rs = contarCategorias.executeQuery();
			cantidad = rs.getInt("cantidad");
			rs.close();
			contarCategorias.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (id >= 6 && cantidad == 0) {
			try {									//SI NO ES UNA CATEGORIA POR DEFECTO Y NO LA USA NINGUN OTRO USUARIO, BORRA ESA CATEGORIA DE LA TABLA categorias
				PreparedStatement eliminarCategoriaTablaCategoria = con.prepareStatement("DELETE FROM categorias WHERE id_c = ?");
				eliminarCategoriaTablaCategoria.setString(1, String.valueOf(id));
				eliminarCategoriaTablaCategoria.executeUpdate();
				eliminarCategoriaTablaCategoria.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	/* METODO MODIFICAR CATEGORIAS POR USUARIO */
	public static void modificarCategoriaPorUsuario(Connection con, String usuario, Categoria categoriaNueva, Categoria categoriaVieja) {
		int id = 0;
		
		ArrayList<String> listaCategorias = new ArrayList<String>();
		listaCategorias.add("AGUA");
		listaCategorias.add("ALIMENTACION");
		listaCategorias.add("GAS");
		listaCategorias.add("LUZ");
		listaCategorias.add("OCIO");
		
		try {										//BUSCA LA ID DE LA CATEGORIA TENIENDO EN CUENTA EL NOMBRE
			PreparedStatement buscaCategoriaSegunNombre = con.prepareStatement("SELECT id_c FROM categorias WHERE categoria = ?");
			buscaCategoriaSegunNombre.setString(1, categoriaVieja.getNombre());
			ResultSet rs = buscaCategoriaSegunNombre.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id_c");
			}
			rs.close();
			buscaCategoriaSegunNombre.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Integer cantidad = 0;
		
		try {										//CUENTA CUANTAS VECES APARECE UNA CATEGORIA EN LA TABLA categoriasusuario
			PreparedStatement cuentaCategorias = con.prepareStatement("SELECT COUNT(id_c_cu) AS cantidad FROM categoriasusuario WHERE id_c_cu = ?");
			cuentaCategorias.setString(1, String.valueOf(id));
			ResultSet rs = cuentaCategorias.executeQuery();
			cantidad = rs.getInt("cantidad");
			rs.close();
			cuentaCategorias.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if (cantidad == 1 && id >= 6 && !listaCategorias.contains(categoriaNueva.getNombre())) {
			
			try {
				PreparedStatement modificaNombreCategoria = con.prepareStatement("UPDATE categorias SET categoria = ? WHERE categoria = ?");
				modificaNombreCategoria.setString(1, categoriaNueva.getNombre());
				modificaNombreCategoria.setString(2, categoriaVieja.getNombre());
				modificaNombreCategoria.executeUpdate();
				modificaNombreCategoria.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!listaCategorias.contains(categoriaNueva.getNombre())){
			BaseDatos.insertarCategoriasBD(con, categoriaNueva);
			
			int id_nuevo = 0;
			
			try {										//BUSCA LA ID DE LA CATEGORIA TENIENDO EN CUENTA EL NOMBRE
				PreparedStatement buscarCategoriaSegunNombre = con.prepareStatement("SELECT id_c FROM categorias WHERE categoria = ?");
				buscarCategoriaSegunNombre.setString(1, categoriaNueva.getNombre());
				ResultSet rs = buscarCategoriaSegunNombre.executeQuery();
				if (rs.next()) {
					id_nuevo = rs.getInt("id_c");
				}
				rs.close();
				buscarCategoriaSegunNombre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				PreparedStatement actualizarCategoria = con.prepareStatement("UPDATE categoriasUsuario SET id_c_cu = ? WHERE id_c_cu = ? AND usuario_cu = ?");
				actualizarCategoria.setString(1, String.valueOf(id_nuevo));
				actualizarCategoria.setString(2, String.valueOf(id));
				actualizarCategoria.setString(3, usuario);
				actualizarCategoria.executeUpdate();
				actualizarCategoria.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			int id_nuevo = 0;
			
			try {										//BUSCA LA ID DE LA CATEGORIA TENIENDO EN CUENTA EL NOMBRE
				PreparedStatement buscarCategoriaSegunNombre = con.prepareStatement("SELECT id_c FROM categorias WHERE categoria = ?");
				buscarCategoriaSegunNombre.setString(1, categoriaNueva.getNombre());
				ResultSet rs = buscarCategoriaSegunNombre.executeQuery();
				if (rs.next()) {
					id_nuevo = rs.getInt("id_c");
				}
				rs.close();
				buscarCategoriaSegunNombre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				PreparedStatement actualizarCategoria = con.prepareStatement("UPDATE categoriasUsuario SET id_c_cu = ? WHERE id_c_cu = ? AND usuario_cu = ?");
				actualizarCategoria.setString(1, String.valueOf(id_nuevo));
				actualizarCategoria.setString(2, String.valueOf(id));
				actualizarCategoria.setString(3, usuario);
				actualizarCategoria.executeUpdate();
				actualizarCategoria.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try {									//SI NO ES UNA CATEGORIA POR DEFECTO Y NO LA USA NINGUN OTRO USUARIO, BORRA ESA CATEGORIA DE LA TABLA categorias
				PreparedStatement eliminarCategoriaTablaCategoria = con.prepareStatement("DELETE FROM categorias WHERE id_c = ?");
				eliminarCategoriaTablaCategoria.setString(1, String.valueOf(id));
				eliminarCategoriaTablaCategoria.executeUpdate();
				eliminarCategoriaTablaCategoria.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	/* BASE DE DATOS USUARIOS CON FICHERO */
	private static List<Usuario> usuarios = new ArrayList<>();
	private static Set<Factura> facturas = new TreeSet<>();
	private static List<Categoria> categorias = new ArrayList<>();
	private static Map<String,List<Categoria>> categoriasConUsuario = new HashMap<>();
	
	public static void anyadirUsuario(Usuario u) {
		usuarios.add(u);
	}
	
	public static void imprimirUsuario() {
		for(Usuario u: usuarios) {
			System.out.println(u);
		}
	}
	
	
	public static void anyadirFactura(Factura f) {
		facturas.add(f);
	}
	
	public static void imprimirFactura() {
		for(Factura f: facturas) {
			System.out.println(f);
		}
	}
	
	public static void ordenarListaUsuarios() {
		Comparator<Usuario> u = new Comparator<Usuario>() {
			@Override
			public int compare(Usuario o1, Usuario o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
		};
		Collections.sort(usuarios, u);
	}
	
	/*Este método busca en la lista el Usuario cuyo usuario recibe por parámetro, 
	 * si lo encuentra, devuelve el objeto Usuario y si no lo encuentra devuelve un null*/
	public static Usuario buscarUsuario(String usuario) {
		boolean enc = false;
		int pos = 0;
		Usuario u = null;
		while(!enc && pos<usuarios.size()) {
			u = usuarios.get(pos);
			if(u.getNombre().equals(usuario)) {
				enc = true;
			}else {
				pos++;
			}
		}
		if(enc) {
			return u;
		}else {
			return null;
		}
	}
	
	public static void cargarFicheroUsuariosEnLista(String nomfich) {
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			//linea = usuario;contrasenya
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				String usuario = partes[0];
				String contrasenya = partes[1];
				Usuario u = new Usuario(usuario, contrasenya);
				if(buscarUsuario(usuario) == null)
					usuarios.add(u);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			
		}
	}
	
	public static void guardarListaUsuariosEnFichero(String nomfich) {
		try {
			PrintWriter pw = new PrintWriter(nomfich);
			for(Usuario u: usuarios) {
				pw.println(u.getNombre()+";"+u.getContrasenya());
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/* BASE DE DATOS CATEGORIAS */
	
	public static List<Categoria> getCategorias(){
		return categorias;
	}
	
	public static void aniadirCategorias(Categoria categoria) {
	}
	
	public static void guardarListaCategoriasEnFichero(String nomfich) {
		try {
			PrintWriter pw = new PrintWriter(nomfich);
			for (Entry<String, List<Categoria>> entry : categoriasConUsuario.entrySet()) {
				String nomUsuario = entry.getKey();
				List<Categoria> listaCategorias = entry.getValue();
				String listaEscrituraCategorias = ";";
				
				for (int categoria = 0; categoria < listaCategorias.size(); categoria++) {
					listaEscrituraCategorias = listaEscrituraCategorias + categoria + ":";
				}
				
				listaEscrituraCategorias = listaEscrituraCategorias.replace(listaEscrituraCategorias.substring(listaEscrituraCategorias.length()-1), "");
				pw.println(nomUsuario + listaEscrituraCategorias);
			}
			pw.flush();
			pw.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarFicheroCategoriasEnLista(String nomfich) {
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			
			//fila = usuario;categoria:categoria:categoria...
			String linea;
			while (sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				@SuppressWarnings("unused")
				String usuario = partes[1];
				String [] categorias = partes[2].split(":");
				for (int categoria = 0; categoria < categorias.length; categoria++) {
					@SuppressWarnings("unused")
					Categoria c = new Categoria(categorias[categoria]);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			
		}
	}
	
}