package com.consistent.migration.rate.models;

public class Marcas {
	private String marca;
	private String codigo;
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Marcas(String marca, String codigo) {
		super();
		this.marca = marca;
		this.codigo = codigo;
	}
	

}
