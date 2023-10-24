package gestionFacturas;

public class Categoria implements Comparable<Categoria>{
	private String nombre;
	
	public Categoria(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Categoria() {
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int compareTo(Categoria o) {
		return this.nombre.compareTo(o.nombre); //ascendentemente
	}
	
}
