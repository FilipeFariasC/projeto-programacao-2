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
	 * Representa o preco da tapioca, ou seja a soma do pre�o todos os {@link #ingredientes} na sua receita;
	 */
	private double preco;
	/**
	 * Representa o valor cal�rico da tapioca, ou seja a soma do valor cal�rico de todos os {@link #ingredientes} na sua receita;
	 */
	private double valorCalorico;
	/**
	 * Representa todos os ingredientes na receita da tapioca;
	 */
	private ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	/**
	 * Representa a disponibilidade de uma tapioca;<br>
	 * Caso algum ingrediente estiver indispon�vel, toda a tapioca estar� indispon�vel.<br>
	 * Caso todos os ingredientes estejam dispon�vel, a tapioca tamb�m estar� dispon�vel;
	 */
	private Disponibilidade disponibilidade;
	
	/**
	 * Inicializa��o de alguns atributos e a utiliza��o dos m�todos {@link #checarDisponibilidade()}, {@link #checarPreco()} e {@link #checarValorCalorico()};
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
	 * Atribui ao atributo nome o par�metro n;
	 * @param n nome a ser atribuido;
	 */
	public void setNome(String n) {
		nome = n;
	}
	
	/**
	 * Ap�s utilizar o m�todo {@link #checarPreco()}, retorna o atributo nome;
	 * @return {@link #preco};
	 */
	public double getPreco(){
		checarPreco();
		return preco;
	}
	/**
	 * Atribui a preco 0 e ap�s isso, percorre toda a lista de {@link #ingredientes} e vai adicionando a preco o pre�o do ingrediente;
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
	 * Atribui a {@link #valorCalorico} 0 e ap�s isso, percorre toda a lista de {@link #ingredientes} e vai adicionando a valorCalorico o valor cal�rico dos ingredientes;
	 */
	private void checarValorCalorico() {
		valorCalorico = 0;
		for (Ingrediente i: ingredientes){
			valorCalorico += i.getValorCalorico();
		}

	}
	/**
	 * Atribui ao atributo {@link #ingredientes} o par�metro iT;
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
	 * Atribui a {@link #disponibilidade} Dispon�vel, se ao percorrer toda a lista de {@link #ingredientes} algum ingrediente estiver Indispon�vel, atribui a disponibilidade Indispon�vel;
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
	 * Atribui ao atributo disponibilidade o par�metro disp;
	 * @param disp disponibilidade a ser atribuido;
	 */
	public void setDisponibilidade(Disponibilidade disp){
		disponibilidade = disp;
	}
	/**
	 * Ap�s utilizar o m�todo {@link #checarDisponibilidade()}, retorna o atributo disponibilidade;
	 * @return {@link #disponibilidade};
	 */
	public Disponibilidade getDisponibilidade(){
		checarDisponibilidade();
		return disponibilidade;
	}
	/**
	 * Testa a String par�metro para ver se ela � igual ou n�o com o atributo {@link #nome};
	 * @param nome String a ser comparada;
	 * @return {@code true} caso o par�metro seja igual ao nome;<br>
	 * {@code false} caso o par�metro n�o seja igual ao nome;
	 */
	public boolean equals(String nome){
		return this.nome.equals(nome);
	}
	/**
	 * Testa a Tapioca par�metro para ver se o atributo nome dela � igual ou n�o com o atributo {@link #nome};
	 * @param tapioca Tapioca a ser comparada;
	 * @return {@code true} caso o par�metro seja igual ao nome;<br>
	 * {@code false} caso o par�metro n�o seja igual ao nome;
	 */
	public boolean equals(Tapioca tapioca){
		return equals(tapioca.getNome());
	}
	/**
	 * Retorna uma representa��o de toda a Tapioca, desde os nome, aos seus ingredientes e seus nome, e o pre�o da tapioca;
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
		retornar += String.format("Pre�o: %.2f\n", preco);
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
