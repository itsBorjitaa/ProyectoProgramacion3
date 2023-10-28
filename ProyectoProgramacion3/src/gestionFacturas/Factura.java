package gestionFacturas;

import java.util.Date;

public class Factura {
	private int codigo;
	private String concepto;
	private float coste;
	private Categoria categoria;
	private Date fecha;
	
	public Factura(int codigo, String concepto, float coste, Categoria categoria, Date fecha) {
		super();
		this.codigo = codigo;
		this.concepto = concepto;
		this.coste = coste;
		this.categoria = categoria;
		this.fecha = fecha;
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
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Factura [codigo=" + codigo + ", concepto=" + concepto + ", coste=" + coste + ", categoria=" + categoria
				+ ", fecha=" + fecha + "]";
	}
	
}
