package gestionFacturas;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class BaseDatos {
	private static List<Usuario> usuarios = new ArrayList<>();
	private static Set<Factura> facturas = new TreeSet<>();
	private static Map<Usuario, List<Factura>> facturasUsuario = new TreeMap<>();
	
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
				return o1.getUsuario().compareTo(o2.getUsuario());
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
			if(u.getUsuario().equals(usuario)) {
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
				pw.println(u.getUsuario()+";"+u.getContrasenya());
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
