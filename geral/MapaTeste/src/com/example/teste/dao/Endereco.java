package com.example.teste.dao;

public class Endereco {
	private int sk_consumidor;
	private String cep;
	private String rua;
	private String numero;
	private String latitude;
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getRua() {
		return rua;
	}
	private String longitude;
	private String bairro;
	private String cidade;
	private String estado;
	private String pais;
	public int getSk_consumidor() {
		return sk_consumidor;
	}
	public void setSk_consumidor(int sk_consumidor) {
		this.sk_consumidor = sk_consumidor;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public void setRua(String rua) {
		this.rua = rua;
		
	}
	
}
