package domain;

public class Factura {
	private Integer codigo;
	private String concepto;
	private double coste;
	private Categoria categoria;
	
	public Factura(String concepto, double coste, Categoria categoria) {
		super();
		this.concepto = concepto;
		this.coste = coste;
		this.categoria = categoria;
	}
	public Factura() {
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public double getCoste() {
		return coste;
	}
	public void setCoste(double coste) {
		this.coste = coste;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "Concepto: " + concepto + ", coste: " + coste + ", categoria: " + categoria;
	}
	
}
