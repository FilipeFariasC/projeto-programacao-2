package com.sistema;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Representa um pedido feito por um cliente, a data do mesmo e quais as tapiocas nele.
 * @author Desktop
 *
 */
public class Pedido {
	/**
	 * Uma lista com a(s) tapioca(s) comprada(s) pelo {@link #cliente};
	 */
	private ArrayList<Tapioca> tapiocas;
	/**
	 * o cliente que realizou o pedido;
	 */
	private Cliente cliente;
	/**
	 * String que representa a data da realização do pedido;
	 */
	private String data;
	/**
	 * Identificador do pedido;
	 */
	private String id;
	/**
	 * Status atual do pedido;
	 */
	private Status statusDoPedido;
	/**
	 * Preço total do pedido;
	 */
	private double preco = 0;
	/**
	 * Quantidade de tapiocas no pedido;
	 */
	private int quant = 0;
	
	/**
	 * Inicializa todos atributos da classe;
	 * @param cliente O cliente que realizou o pedido;
	 * @param tapiocas A lista de tapiocas compradas;
	 */
	public Pedido (Cliente cliente, ArrayList<Tapioca> tapiocas){
		this.cliente = cliente;
		this.tapiocas = tapiocas;
		setData();
		setPreco();
		id = Util.criarID();
		statusDoPedido = Status.ABERTO;
	}
	/**
	 * Ajusta a data da realização do pedido;
	 */
	private void setData(){
		Locale local = new Locale("pt", "pt-br");
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.SHORT, DateFormat.SHORT, local);
		data = dateFormat.format(new Date());
	}
	/**
	 * Retorna o cliente que realizou o pedido;
	 * @return {@link #cliente};
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * Retorna a data de realização do pedido;
	 * @return {@link #data};
	 */
	public String getData() {
		return data;
	}
	/**
	 * Retorna as tapiocas compradas;
	 * @return {@link #tapiocas};
	 */
	public ArrayList<Tapioca> getTapiocas() {
		return tapiocas;
	}
	/**
	 * Retorna o identificador do pedido;
	 * @return {@link #id};
	 */
	public String getId() {
		return id;
	}
	/**
	 * Testa para saber se o parâmetro possui mesmos caracteres que o nome OU identificador;
	 * @param nomeDoClienteOuId String a ser comparada com o nome do {@link #cliente}, ou com o identificador do pedido ({@link #id});
	 * @return {@code true} se a String parâmetro for igual ao nome OU identificador;
	 */
	public boolean equals(String nomeDoClienteOuId) {
		return (id.equals(nomeDoClienteOuId) ||
				cliente.getNome().equals(nomeDoClienteOuId));
	}
	/**
	 * Recebe um Status parâmetro e o atributo a {@link #statusDoPedido};
	 * @param status valor a ser atribuido;
	 */
	public void setStatus(Status status){
		statusDoPedido = status;
	}
	/**
	 * Retorna o atual status do pedido;
	 * @return {@link #statusDoPedido};
	 */
	public Status getStatusDoPedido() {
		return statusDoPedido;
	}
	/**
	 * Atribui a {@link #preco} a soma do preço de todas as tapiocas no pedido;
	 */
	private void setPreco(){
		quant = tapiocas.size();
		for(Tapioca tapioca: tapiocas){
			preco += tapioca.getPreco();
		}
	}
	/**
	 * Retorna o preço total do pedido;
	 * @return {@link #preco};
	 */
	public double getPreco() {
		return preco;
	}
	/**
	 * Retorna a quantidade de tapiocas do pedido;
	 * @return {@link #quant};
	 */
	public int getQuant() {
		return quant;
	}
}
