package com.sistema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.excecoes.*;
import com.swing.JTextFieldPadrao;
/**
 * Classe que possui todos os atributos necessários para o projeto;
 * @author Filipe Farias
 * 
 */
public class Central {
	/** ArrayList de que possui todos os usuários cadastrados.
	 */
	private ArrayList<Pessoa> cadastrados = new ArrayList<>();
	/**
	 * Representa o usuário administrador.
	 */
	private Administrador administrador = null;
	/**
	 * ArrayList de que possui todos os clientes cadastrados.
	 */
	private ArrayList<Cliente> clientes = new ArrayList<>();
	/**
	 * ArrayList de que possui todos os ingredientes cadastrados.
	 */
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	/**
	 * ArrayList de que possui todas as tapiocas cadastradas.
	 */
	private ArrayList<Tapioca> tapiocas = new ArrayList<>();
	/**
	 * ArrayList que possui todos os pedidos já feitos.
	 */
	private ArrayList<Pedido> pedidos = new ArrayList<>();
	/**
	 * ArrayList que possui todas as vendas de tapiocas já feitas.
	 */
	private ArrayList<Venda> vendas = new ArrayList<>();
	/**
	 * Representa o usuário logado atualmente.
	 */
	private Pessoa logado = null;
	
	/**
	 * Cadastra um ingrediente após fazer uma varredura na lista de {@link #ingredientes} cadastrados e não haver algum ingrediente cadastrado de mesmo nome.
	 * @param nome Nome do ingrediente a ser cadastrado;
	 * @param calorias Valor calórico do ingrediente a ser cadastrado;
	 * @param price Preço do ingrediente a ser cadastrado;
	 * @param disp Disponibilidade do ingrediente a ser cadastrado;
	 * @throws IngredienteCadastradoException Caso já exista um ingrediente cadastrado de mesmo nome;
	 */
	public void cadastrarIngrediente(String nome, double calorias, double price, Disponibilidade disp) throws IngredienteCadastradoException{
		for (int c = 0; c < ingredientes.size(); c++){
			Ingrediente ing = ingredientes.get(c);
			if (ing.equals(nome)){
				throw new IngredienteCadastradoException();
			}
		}
		ingredientes.add(new Ingrediente(nome, calorias, price, disp));
	}
	/**
	 * Cadastra o administrador do sistema;
	 * @param adm Um objeto do tipo Administrador a ser cadastrado como o {@link #administrador} do sistema.
	 */
	public void cadastrarAdministrador(Administrador adm){
		cadastrados.add(adm);
		administrador = adm;
	}
	/**
	 * Varre a lista de {@link #cadastrados} e caso o Cliente parâmetro não possua um login e/ou login igual a de um cliente já cadastrado;
	 * @param cliente Um objeto Cliente a ser cadastrado;
	 * @return <code>true</code> Caso não seja lançada exceção;
	 * @throws ClienteJaCadastradoException  Caso o Cliente parâmetro utilize um login ou email já cadastrado;
	 */
	public boolean cadastrarCliente(Cliente cliente) throws ClienteJaCadastradoException{
		for (Pessoa p: cadastrados){
			if (cliente.equals(p)){
				throw new ClienteJaCadastradoException();
			}
		}
		clientes.add(cliente);
		cadastrados.add(cliente);
		return true;
	}
	/**
	 * Recebe um objeto do tipo Ingrediente a ser excluído da lista de ingredientes({@link#ingredientes}). <br>
	 * Caso ele esteja em uma tapioca da lista ({@link #tapiocas}), será lançado uma Exceção. <br>
	 * Caso não, exclui o ingrediente da lista e retorna true;
	 * @param ingrediente ingrediente a ser excluído;
	 * @return <code>true</code> Caso não seja lançada exceção;
	 * @throws IngredienteEmTapiocaException Caso o ingrediente esteja cadastrado em uma tapioca;
	 */
	public boolean deletarIngrediente(Ingrediente ingrediente) throws IngredienteEmTapiocaException{
		for (Tapioca t: tapiocas){
			ArrayList<Ingrediente> ingre = t.getIngredientes();
			if (ingre.indexOf(ingrediente) != -1){
				throw new IngredienteEmTapiocaException();
			}
		}
		ingredientes.remove(ingrediente);
		return true;
	}
	/**
	 * Recebe um Objeto do tipo Tapioca a ser cadastrada; <br>
	 * Caso esse objeto possua o mesmo nome de uma tapioca já cadastrada em {@link #tapiocas} será lançada uma exceção; <br> 
	 * Caso não, adiciona a tapioca na lista de tapiocas e vendas e retornará true.
	 * @param tapioca tapioca a ser cadastrada;
	 * @return <code> true </code> Caso não seja lançada exceções;
	 * @throws TapiocaCadastradaException Caso o parâmetro possua o mesmo nome de uma tapioca já cadastrada;
	 */
	public boolean cadastrarTapioca(Tapioca tapioca) throws TapiocaCadastradaException{
		for (Tapioca tap: tapiocas){
			if (tap.equals(tapioca)){
				throw new TapiocaCadastradaException();
			}
		}
		tapiocas.add(tapioca);
		vendas.add(new Venda(tapioca));
		
		return true;
	}
	/**
	 * Recebe um objeto do tipo Cliente como parâmetro, caso ele não possua tapiocas no carrinho retorna false.<br>
	 * Caso tenha, varre toda a lista ({@link #tapiocas}) para ver sua disponibilidade, se alguma tapioca estiver indisponível lança uma exceção;<br> 
	 * Se todas estiverem disponíveis, será realizada a compra, e adiciona aos pedidos esse pedido realizado, além de atualizar as vendas;<br>
	 * @param usuario cliente que irá realizar a compra;
	 * @return false Caso a lista ({@link tapiocas}) não possua tapiocas;<br>
	 * 		   true Caso a exceção não seja lançada;
	 * @throws TapiocaNaoDisponivelException Se uma tapioca não esteja disponível;
	 */
	public boolean comprarTapiocas(Cliente usuario) throws TapiocaNaoDisponivelException{
		ArrayList<Tapioca> clone = usuario.getCarrinhoClone();
		if (clone.size() > 0){
			ArrayList<Tapioca> carrinho = usuario.getCarrinho();
			for(Tapioca t: clone){
				 switch(t.getDisponibilidade()){
				 case INDISPONIVEL:
					 throw new TapiocaNaoDisponivelException();
				 }
			}
			Pedido pedido = new Pedido(usuario, clone);
			usuario.getPedidos().add(pedido);
			pedidos.add(pedido);
			for(Tapioca t: carrinho){
				for(Venda venda: vendas){
					if(venda.equals(t)){
						venda.adicionarCompra();
					}
				}
			}
			carrinho.clear();
			return true;
		}
		return false;
	}
	/**
	 * Retorna o atributo pedidos;
	 * @return {@link #pedidos}
	 */
	public ArrayList<Pedido> getPedidos(){
		return pedidos;
	}
	/**
	 * Retorna o atributo ingredientes;
	 * @return {@link #ingredientes};
	 */
	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}
	/**
	 * Retorna o atributo cadastrados;
	 * @return {@link #cadastrados};
	 */
	public ArrayList<Pessoa> getCadastrados() {
		return cadastrados;
	}
	/**
	 * Retorna o atributo administrador
	 * @return {@link #administrador};
	 */
	public Administrador getAdministrador() {
		return administrador;
	}
	/**
	 * Retorna o atributo tapiocas;
	 * @return {@link #tapiocas};
	 */
	public ArrayList<Tapioca> getTapiocas() {
		return tapiocas;
	}
	/**
	 * Atribui o objeto parâmetro log ao atributo {@link #logado};
	 * @param log Objeto do tipo Pessoa que está logado;
	 */
	public void setLogado(Pessoa log) {
		logado = log;
	}
	/**
	 * Retorna o atributo logado;
	 * @return {@link #logado};
	 */
	public Pessoa getLogado() {
		return logado;
	}
	/**
	 * Retorna o atributo pedidos;
	 * @return {@link #pedidos};
	 */
	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
	/**
	 * retorna o atributo vendas;
	 * @return {@link #vendas};
	 */
	public ArrayList<Venda> getVendas() {
		return vendas;
	}
}
