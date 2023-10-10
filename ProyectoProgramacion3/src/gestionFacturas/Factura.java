package gestionFacturas;

public class Factura {
	private int codigo;
	private String concepto;
	private float coste;
	private String tipo;
	public Factura(int codigo, String concepto, float coste, String tipo) {
		super();
		this.codigo = codigo;
		this.concepto = concepto;
		this.coste = coste;
		this.tipo = tipo;
	}
	public Factura() {
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public float getCoste() {
		return coste;
	}
	public void setCoste(float coste) {
		this.coste = coste;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Factura [codigo=" + codigo + ", concepto=" + concepto + ", coste=" + coste + ", tipo=" + tipo + "]";
	}
	
}
