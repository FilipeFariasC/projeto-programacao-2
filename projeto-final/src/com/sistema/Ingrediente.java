package com.sistema;
/**
 * Classe representa um ingrediente;
 * @author Filipe Farias
 *
 */
public class Ingrediente {
	/**
	 * Nome do ingrediente;
	 */
	private String nome;
	/**
	 * Valor calórico do ingrediente para cada 100 gramas;
	 */
	private double valorCalorico;
	/**
	 * Preco do ingrediente para cada 100 gramas;
	 */
	private double precoPorCemGrama;
	/**
	 * Disponibilidade do Ingrediente;
	 */
	private Disponibilidade disponibilidade;
	
	/**
	 * Inicializa os atributos da classe;
	 * @param n parâmetro a ser atribuido ao atributo {@link #nome} do ingrediente;
	 * @param vC parâmetro a ser atribuido ao atributo {@link #valorCalorico} do ingrediente;
	 * @param pPCG parâmetro a ser atribuido ao atributo {@link #precoPorCemGrama} do ingrediente;
	 * @param disp parâmetro a ser atribuido ao atributo {@link #disponibilidade} do ingrediente;
	 */
	public Ingrediente(String n, double vC, double pPCG, Disponibilidade disp){
		nome = n;
		valorCalorico = vC;
		precoPorCemGrama = pPCG;
		disponibilidade = disp;
	}
	/**
	 * retorna o atributo nome;
	 * @return {@link #nome};
	 */
	public String getNome(){
		return nome;
	}
	/**
	 * Atribui a {@link #nome} o parâmetro n;
	 * @param n {@code String} que representa o nome do ingrediente;
	 */
	public void setNome(String n){
		nome = n;
	}
	/**
	 * retorna o atributo valorCalorico;
	 * @return {@link #valorCalorico};
	 */
	public double getValorCalorico(){
		return valorCalorico;
	}
	/**
	 * Atribui a {@link #valorCalorico} o parâmetro vC;
	 * @param vC {@code double} que representa o valor calórico do ingrediente;
	 */
	public void setValorCalorico(double vC){
		valorCalorico = vC;
	}
	/**
	 * retorna o atributo precoporCemGrama;
	 * @return {@link #precoPorCemGrama};
	 */
	public double getPrecoPorGrama(){
		return precoPorCemGrama;
	}
	/**
	 * Atribui a {@link #precoPorCemGrama} o parâmetro pPCG;
	 * @param pPCG {@code double} que representa o preço por cem gramas do ingrediente;
	 */
	public void setPrecoPorCemGrama(double pPCG){
		precoPorCemGrama = pPCG;
	}
	/**
	 * retorna o atributo disponibilidade;
	 * @return {@link #disponibilidade};
	 */
	public Disponibilidade checarDisponibilidade() {
		return disponibilidade;
	}
	/**
	 * Representação em {@code String} do ingrediente.
	 * @return O nome seguido do valor calórico e preço/100g do ingrediente;
	 */
	public String toString(){
		return(String.format("Ingrediente: %s \t"
						   + "Valor Calórico: %.2f\t"
						   + "Preço/100g: %.2f",
						   nome, valorCalorico, precoPorCemGrama));
	}
	
	/**Testa para ver se um Ingrediente é igual a outro.
	 * @param i um ingrediente a ser comparado com o que chamou;
	 * @return <code>true</code> Se o ingrediente parâmetro possuir o mesmo nome do ingrediente que chama o método;
	 */
	public boolean equals(Ingrediente i){
		return nome.equals(i.getNome());
	}
	/**Testa para ver se um Ingrediente possui o mesmo {@link #nome} que a String parâmetro.
	 * @param nome um ingrediente a ser comparado com o que chamou;
	 * @return <code>true</code> Se o ingrediente parâmetro possuir o mesmo nome do ingrediente que chama o método;
	 */
	public boolean equals(String nome){
		return (this.nome.equals(nome));
	}
	/**
	 * Retorna o atributo disponilidade;
	 * @return {@link #disponibilidade};
	 */
	public Disponibilidade getDisponibilidade() {
		return disponibilidade;
	}
	/**
	 * Atribui a {@link #disponibilidade} o parâmetro disponibilidade;
	 * @param disponibilidade parâmetro a representa uma disponibilidade;
	 */
	public void setDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	/**
	 * @return um clone do objeto que chama o método como todos os seus atributos;
	 */
	public Ingrediente clone(){
		return new Ingrediente(nome, valorCalorico, precoPorCemGrama, disponibilidade);
	}
}
