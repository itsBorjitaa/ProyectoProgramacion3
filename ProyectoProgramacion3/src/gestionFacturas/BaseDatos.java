package gestionFacturas;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
			con = DriverManager.getConnection("jdbc:sqlite:"+nombreBD);
					
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
		String sql = "CREATE TABLE IF NOT EXISTS Usuario (usuario String NOT NULL ,contrasenya String NOT NULL)";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Usuario buscarUsuarioBD(Connection con, String nombre) {
		String sql = String.format("SELECT * FROM Usuario WHERE usuario = '%s'", nombre);
		Usuario p = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql); //Se ejecuta la consulta
			if(rs.next()) { //Select devuelve una o más tuplas
				String usuario = rs.getString("usuario");
				String contrasenya = rs.getString("contrasenya");
				p = new Usuario(usuario, contrasenya);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public static void insertarUsuarioBD(Connection con, Usuario u) {
		if(buscarUsuarioBD(con, u.getNombre()) == null) {
			String sql = String.format("INSERT INTO Usuario VALUES('%s','%s')", u.getNombre(),u.getContrasenya());
			try {
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* BASE DE DATOS TICKETS POR FECHA */
	
	/* FUNCIÓN PARA CREAR LA TABLA SI NO ESTA CREADA */
	public static void crearTablaFacturasBD(Connection con) {
		String sql = "CREATE TABLE IF NOT EXISTS Facturas (codigo INTEGER NOT NULL DEFAULT 0, usuarioF TEXT NOT NULL, fecha TEXT NOT NULL, concepto TEXT NOT NULL, "
				+ "coste REAL NOT NULL, categoria TEXT NOT NULL,"
				+ "PRIMARY KEY(codigo), FOREIGN KEY(usuarioF) REFERENCES Usuario(usuario) ON DELETE CASCADE)";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA INSERTAR DATOS A LA TABLA */
	public static void insertarFacturaBD(Connection con, Factura f, String usuario, Date fecha) {
			Integer codigo=0;
			String generadorCodigo=String.format("SELECT codigo FROM Facturas");
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(generadorCodigo);
				while(rs.next()){
					codigo=rs.getInt("codigo");
				}
				rs.close();
				st.close();
				}			 
			catch (SQLException e) {
				e.printStackTrace();
				}
			String sql = String.format("INSERT INTO Facturas VALUES('%s', '%s','%s','%s','%s','%s')",
					codigo+1,
					usuario,
					fecha,f.getConcepto(), 
					f.getCoste(), f.getCategoria());
			try {
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	/* FUNCIÓN PARA MODIFICAR DATOS */
	public static void modificarFacturaBD(Connection con, Factura nuevaF, Date nuevaFecha, Integer codigo) {
		String sql = String.format("UPDATE Facturas SET fecha='"+nuevaFecha+"', concepto='"+nuevaF.getConcepto()+
		"', coste='"+nuevaF.getCoste()+"', categoria='"+nuevaF.getCategoria()+"' WHERE codigo="+codigo+";");
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA ELIMINAR DATOS */
	public static void eliminarFacturaBD(Connection con,Integer codigo) {
		String sql = String.format("DELETE FROM Facturas WHERE codigo="+codigo+";");
		try {
			Statement st=con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCIÓN PARA CARGAR LOS DATOS */
	public static HashMap<Date, ArrayList<Factura>> cargarFacturaBD(Connection con, String usuario) {
		HashMap<Date, ArrayList<Factura>> ticketsPorFecha= new HashMap<>();
		String sql = String.format("SELECT codigo, fecha, concepto ,coste ,categoria FROM Facturas WHERE usuarioF='%s'", 
				usuario);
		try {
			Statement st = con.createStatement();
			ResultSet rs= st.executeQuery(sql);
			while(rs.next()) {
				String stringFecha=rs.getString("fecha");
				String [] arrayFecha=stringFecha.split("-");
				@SuppressWarnings("deprecation")
				Date fechaDate=new Date(Integer.parseInt(arrayFecha[0])-1900, Integer.parseInt(arrayFecha[1]), Integer.parseInt(arrayFecha[2]));
				ticketsPorFecha.putIfAbsent(fechaDate, new ArrayList<Factura>());
				Factura f=new Factura(rs.getString("concepto"),rs.getDouble("coste"), new Categoria(rs.getString("categoria")));
				f.setCodigo(rs.getInt("codigo"));
				ticketsPorFecha.get(fechaDate).add(f);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticketsPorFecha;
	}
	
	/* BASE DE DATOS CATEGORIAS*/
	
	/* FUNCION CREAR TABLA DE CATEGORIAS*/
	public static void crearTablaCategoriasBD(Connection con) {
		String sql = "CREATE TABLE IF NOT EXISTS Categorias (id_c INTEGER NOT NULL, categoria STRING NOT NULL, PRIMARY KEY(id_c))";
		String sql1 = "INSERT INTO Categorias VALUES('1','AGUA')";
		String sql2 = "INSERT INTO Categorias VALUES('2','ALIMENTACION')";
		String sql3 = "INSERT INTO Categorias VALUES('3','GAS')";
		String sql4 = "INSERT INTO Categorias VALUES('4','LUZ')";
		String sql5 = "INSERT INTO Categorias VALUES('5','OCIO')";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			st.executeUpdate(sql3);
			st.executeUpdate(sql4);
			st.executeUpdate(sql5);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FUNCION BUSCAR CATEGORIAS EN TABLA*/
	public static Categoria buscarCategoriaBD(Connection con, String nombre) {
		String sql = String.format("SELECT * FROM Categorias WHERE categoria = '%s'", nombre);
		Categoria categoria = null;
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				String c = rs.getString("categoria");
				categoria = new Categoria(c);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}
	
	/* FUNCION AÑADIR CATEGORIAS A LA TABLA */
	public static void insertarCategoriasBD(Connection con, Categoria categoria) {
		Integer id_c = 0;
		String consegirID=String.format("SELECT id_c FROM Categorias");
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(consegirID);
			while(rs.next()){
				id_c=rs.getInt("id_c");
			}
			rs.close();
			st.close();
			}			 
		catch (SQLException e) {
			e.printStackTrace();
			}
		if (buscarCategoriaBD(con, categoria.getNombre()) == null) {
			String sql = String.format("INSERT INTO Categorias VALUES('%s','%s')", id_c+1, categoria.getNombre());
			try {
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				st.close();
			} catch (SQLException e) {
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
				String usuario = partes[1];
				String [] categorias = partes[2].split(":");
				for (int categoria = 0; categoria < categorias.length; categoria++) {
					Categoria c = new Categoria(categorias[categoria]);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			
		}
	}
	
}