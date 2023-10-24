package gestionFacturas;

public class ClasesTickets {
	private String nombre, descripcion;

	public ClasesTickets(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public ClasesTickets() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "ClasesTickets [nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
}
