package gestionFacturas;

public class Usuario {
	private String usuario, contrasenya;

	public Usuario(String usuario, String contrasenya) {
		super();
		this.usuario = usuario;
		this.contrasenya = contrasenya;
	}
	
	public Usuario() {
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contrasenya=" + contrasenya + "]";
	}
	
	
}
