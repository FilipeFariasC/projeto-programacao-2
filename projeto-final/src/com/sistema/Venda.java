package com.sistema;

/**
 * Representa uma tapioca e sua compras;
 * @author Filipe Farias
 *
 */
public class Venda {
	/**A tapioca vendida
	 */
	private final Tapioca tapioca;
	/**
	 * A quantidade de vezes que essa tapioca foi comprada;
	 */
	private int quantidadeDeVendas = 0;
	/**
	 * A quantidade arrecadada com essa tapioca;
	 */
	private double arrecadado = 0;
	/**
	 * Inicializa a tapioca a ser vendida;
	 * @param tapioca a tapioca;
	 */
	public Venda(Tapioca tapioca) {
		this.tapioca = tapioca;
	}
	/**
	 * Adiciona uma compra a essa tapioca, aumentando seu atributo {@link #quantidadeDeVendas} em 1, e aumentando o valor do atributo {@link #arrecadado} de acordo com o preço atual da tapioca;
	 */
	public void adicionarCompra(){
		quantidadeDeVendas++;
		arrecadado += tapioca.getPreco();
	}
	/**
	 * Retorna o atributo tapioca;
	 * @return {@link #tapioca};
	 */
	public Tapioca getTapioca() {
		return tapioca;
	}
	/**
	 * Retorna o retorno do método equals do atributo {@link #tapioca};
	 * @param tap tapioca a ser testada;
	 * @return retorno do método equals da tapioca;
	 */
	public boolean equals(Tapioca tap) {
		return tapioca.equals(tap);
	}
	/**
	 * Retorna o atributo arrecadadp;
	 * @return {@link #arrecadado};
	 */
	public double getArrecadado() {
		return arrecadado;
	}
	/**
	 * Retorna o atributo quantidadeDeVendas;
	 * @return {@link #quantidadeDeVendas};
	 */
	public int getQuantidadeDeVendas() {
		return quantidadeDeVendas;
	}
}
