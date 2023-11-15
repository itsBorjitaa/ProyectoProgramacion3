package gestionFacturas;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;

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
		String sql = "CREATE TABLE IF NOT EXISTS Usuario (usuario String ,contrasenya String)";
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
	
	public static void aniadirCategorias() {
		
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
		ArrayList<Categoria> categoriasTemporal = new ArrayList<Categoria>();
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			
			//fila = usuario;categoria:categoria:categoria...
			String linea;
			while (sc.hasNext()) {
				categoriasTemporal.clear();
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				String usuario = partes[1];
				String [] categorias = partes[2].split(":");
				for (int categoria = 0; categoria < categorias.length; categoria++) {
					Categoria c = new Categoria(categorias[categoria]);
					categoriasTemporal.add(c);
				}
				if (!categoriasConUsuario.containsKey(usuario)) {
					categoriasConUsuario.put(usuario,categoriasTemporal);
				}
				
				if(!categoriasConUsuario.get(usuario).containsAll(categoriasTemporal)) {
					categoriasConUsuario.replace(usuario, categoriasTemporal);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			
		}
	}
	
	public static List<Categoria> buscarCategoriasPorUsuario(String UsuarioNom) {
		return categoriasConUsuario.get(UsuarioNom);
	}
	
	
	
}
