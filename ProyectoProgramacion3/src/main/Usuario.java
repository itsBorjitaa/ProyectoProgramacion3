package main;

import java.util.Objects;

public class Usuario {
	private String nombre, contrasenya;

	public Usuario(String nombre, String contrasenya) {
		super();
		this.nombre = nombre;
		this.contrasenya = contrasenya;
	}
	
	public Usuario() {
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasenya=" + contrasenya + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasenya, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	
}
