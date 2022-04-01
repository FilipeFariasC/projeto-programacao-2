package com.sistema;

import java.util.ArrayList;
/**
 * Representa uma tapioca e suas caracteristicas;
 * @author Desktop
 */
public class Tapioca {
	/**
	 * Representa o nome dessa tapioca;
	 */
	private String nome;
	/**
	 * Representa o preco da tapioca, ou seja a soma do preço todos os {@link #ingredientes} na sua receita;
	 */
	private double preco;
	/**
	 * Representa o valor calórico da tapioca, ou seja a soma do valor calórico de todos os {@link #ingredientes} na sua receita;
	 */
	private double valorCalorico;
	/**
	 * Representa todos os ingredientes na receita da tapioca;
	 */
	private ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	/**
	 * Representa a disponibilidade de uma tapioca;<br>
	 * Caso algum ingrediente estiver indisponível, toda a tapioca estará indisponível.<br>
	 * Caso todos os ingredientes estejam disponível, a tapioca também estará disponível;
	 */
	private Disponibilidade disponibilidade;
	
	/**
	 * Inicialização de alguns atributos e a utilização dos métodos {@link #checarDisponibilidade()}, {@link #checarPreco()} e {@link #checarValorCalorico()};
	 * @param n nome da tapioca;
	 * @param ing lista de ingredientes;
	 */
	public Tapioca(String n, ArrayList<Ingrediente> ing){
		nome = n;
		ingredientes = ing;
		checarPreco();
		checarValorCalorico();
		checarDisponibilidade();
	}
	
	/**
	 * Retorna o atributo nome;
	 * @return {@link #nome};
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Atribui ao atributo nome o parâmetro n;
	 * @param n nome a ser atribuido;
	 */
	public void setNome(String n) {
		nome = n;
	}
	
	/**
	 * Após utilizar o método {@link #checarPreco()}, retorna o atributo nome;
	 * @return {@link #preco};
	 */
	public double getPreco(){
		checarPreco();
		return preco;
	}
	/**
	 * Atribui a preco 0 e após isso, percorre toda a lista de {@link #ingredientes} e vai adicionando a preco o preço do ingrediente;
	 */
	private void checarPreco() {
		preco = 0;
		for (Ingrediente i: ingredientes){
			preco += i.getPrecoPorGrama();
		}
	}
	/**
	 * Retorna o atributo valorCalorico;
	 * @return {@link #valorCalorico};
	 */
	public double getValorCalorico(){
		checarValorCalorico();
		return valorCalorico;
	}
	/**
	 * Atribui a {@link #valorCalorico} 0 e após isso, percorre toda a lista de {@link #ingredientes} e vai adicionando a valorCalorico o valor calórico dos ingredientes;
	 */
	private void checarValorCalorico() {
		valorCalorico = 0;
		for (Ingrediente i: ingredientes){
			valorCalorico += i.getValorCalorico();
		}

	}
	/**
	 * Atribui ao atributo {@link #ingredientes} o parâmetro iT;
	 * @param iT ingredientes a serem atribuidos;
	 */
	public void setIngredientesTapioca(ArrayList<Ingrediente> iT){
		if (iT != null){
			ingredientes = iT;
		}
		checarPreco();
		checarValorCalorico();
	}
	
	/**
	 * Retorna o atributo ingredientes;
	 * @return {@link #ingredientes};
	 */
	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}
	/**
	 * Atribui a {@link #disponibilidade} Disponível, se ao percorrer toda a lista de {@link #ingredientes} algum ingrediente estiver Indisponível, atribui a disponibilidade Indisponível;
	 */
	private void checarDisponibilidade(){
		disponibilidade =  Disponibilidade.DISPONIVEL;
		for(Ingrediente ing: ingredientes){
			Disponibilidade disp = ing.getDisponibilidade();
			if(disp == Disponibilidade.INDISPONIVEL){
				disponibilidade = Disponibilidade.INDISPONIVEL;
				break;
			}
		}
	}
	/**
	 * Atribui ao atributo disponibilidade o parâmetro disp;
	 * @param disp disponibilidade a ser atribuido;
	 */
	public void setDisponibilidade(Disponibilidade disp){
		disponibilidade = disp;
	}
	/**
	 * Após utilizar o método {@link #checarDisponibilidade()}, retorna o atributo disponibilidade;
	 * @return {@link #disponibilidade};
	 */
	public Disponibilidade getDisponibilidade(){
		checarDisponibilidade();
		return disponibilidade;
	}
	/**
	 * Testa a String parâmetro para ver se ela é igual ou não com o atributo {@link #nome};
	 * @param nome String a ser comparada;
	 * @return {@code true} caso o parâmetro seja igual ao nome;<br>
	 * {@code false} caso o parâmetro não seja igual ao nome;
	 */
	public boolean equals(String nome){
		return this.nome.equals(nome);
	}
	/**
	 * Testa a Tapioca parâmetro para ver se o atributo nome dela é igual ou não com o atributo {@link #nome};
	 * @param tapioca Tapioca a ser comparada;
	 * @return {@code true} caso o parâmetro seja igual ao nome;<br>
	 * {@code false} caso o parâmetro não seja igual ao nome;
	 */
	public boolean equals(Tapioca tapioca){
		return equals(tapioca.getNome());
	}
	/**
	 * Retorna uma representação de toda a Tapioca, desde os nome, aos seus ingredientes e seus nome, e o preço da tapioca;
	 */
	public String toString(){
		String retornar = String.format("%s \nIngredientes:(", nome);
		int c = 0;
		String letra;
		for (Ingrediente i: ingredientes){
			letra = ", "; 
			if (c == ingredientes.size()-1){
				letra = ")\n";
			}
			retornar += i.getNome()+letra;
			c++;
		}
		retornar += String.format("Preço: %.2f\n", preco);
		return retornar;
	}
	/**
	 * Retorna um clone da tapioca, criando uma nova tapioca de mesmo nome e com ingredientes clonados;
	 */
	public Tapioca clone(){
		ArrayList<Ingrediente> ingredientes = new ArrayList<>();
		for(Ingrediente ingrediente: this.ingredientes){
			ingredientes.add(ingrediente.clone());
		}
		return new Tapioca(nome, ingredientes);
	}
}
