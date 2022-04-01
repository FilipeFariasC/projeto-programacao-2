package com.sistema;

import java.util.Date;
import java.util.ArrayList;

/**
 * Classe que representa o Cliente;
 * @author Filipe Farias
 *
 */
public class Cliente extends Pessoa{
	/**
	 * Atributo que representa a lista de todas as tapiocas no carrinho do Cliente;
	 */
	private ArrayList<Tapioca> carrinho = new ArrayList<>();
	/**
	 * Atributo que representa a lista de todos pedidos já feitos pelo cliente;
	 */
	private ArrayList<Pedido> pedidos = new ArrayList<>();
	/**
	 * Atributo que representa se o cliente deseja receber mala direta;
	 */
	private boolean newsletter;
	
	/**
	 * Construtor que inicializa todos os atributos do supertipo, além de inicializar o atributo da classe {@link #newsletter};
	 * @param nome Nome do Cliente;
	 * @param email Email do Cliente;
	 * @param data Data do Cliente;
	 * @param login Login do Cliente;
	 * @param senha Senha do Cliente;
	 * @param sexo Sexo do Cliente;
	 * @param nl Atributo a ser atribuido a {@link #newsletter} do Cliente;
	 */
	public Cliente(String nome, String email, Date data, String login, String senha, SEXO sexo, boolean nl) {
		super(nome, email, data, login, senha, sexo, false);
		newsletter = nl;
	}
	/**
	 * Recebe uma tapioca e a adiciona a {@link #carrinho};
	 * @param tapioca Tapioca a ser adicionar a lista;
	 * @return true;
	 */
	public boolean adicionarAoCarrinho(Tapioca tapioca){
		carrinho.add(tapioca);
		return true;
	}
	/**
	 * Cria um ArrayList de tapioca e adiciona a ele um clone de todas as tapiocas no {@link #carrinho};
	 * @return um clone do atributo {@link #carrinho};
	 */
	public ArrayList<Tapioca> getCarrinhoClone() {
		ArrayList<Tapioca> carrinho = new ArrayList<>();
		for(Tapioca tapioca: this.carrinho){
			carrinho.add(tapioca.clone());
		}
		return carrinho;
	}
	/**
	 * Retorna o atributo carrinho;
	 * @return {@link #carrinho};
	 */
	public ArrayList<Tapioca> getCarrinho(){
		return carrinho;
	}
	/**
	 * Retorna o atributo pedidos;
	 * @return {@link #pedidos};
	 */
	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}
	/**
	 * Atribui a newsletter o parâmetro <code>boolean</code>;
	 * @param newsletter parâmetro a ser atribuído a {@link #newsletter};
	 */
	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}
	/**
	 * Retorna o valor do atributo newsletter;
	 * @return {@link #newsletter};
	 */
	public boolean isNewsletter() {
		return newsletter;
	}
	
}
