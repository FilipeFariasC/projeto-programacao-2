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
	 * Valor cal�rico do ingrediente para cada 100 gramas;
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
	 * @param n par�metro a ser atribuido ao atributo {@link #nome} do ingrediente;
	 * @param vC par�metro a ser atribuido ao atributo {@link #valorCalorico} do ingrediente;
	 * @param pPCG par�metro a ser atribuido ao atributo {@link #precoPorCemGrama} do ingrediente;
	 * @param disp par�metro a ser atribuido ao atributo {@link #disponibilidade} do ingrediente;
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
	 * Atribui a {@link #nome} o par�metro n;
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
	 * Atribui a {@link #valorCalorico} o par�metro vC;
	 * @param vC {@code double} que representa o valor cal�rico do ingrediente;
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
	 * Atribui a {@link #precoPorCemGrama} o par�metro pPCG;
	 * @param pPCG {@code double} que representa o pre�o por cem gramas do ingrediente;
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
	 * Representa��o em {@code String} do ingrediente.
	 * @return O nome seguido do valor cal�rico e pre�o/100g do ingrediente;
	 */
	public String toString(){
		return(String.format("Ingrediente: %s \t"
						   + "Valor Cal�rico: %.2f\t"
						   + "Pre�o/100g: %.2f",
						   nome, valorCalorico, precoPorCemGrama));
	}
	
	/**Testa para ver se um Ingrediente � igual a outro.
	 * @param i um ingrediente a ser comparado com o que chamou;
	 * @return <code>true</code> Se o ingrediente par�metro possuir o mesmo nome do ingrediente que chama o m�todo;
	 */
	public boolean equals(Ingrediente i){
		return nome.equals(i.getNome());
	}
	/**Testa para ver se um Ingrediente possui o mesmo {@link #nome} que a String par�metro.
	 * @param nome um ingrediente a ser comparado com o que chamou;
	 * @return <code>true</code> Se o ingrediente par�metro possuir o mesmo nome do ingrediente que chama o m�todo;
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
	 * Atribui a {@link #disponibilidade} o par�metro disponibilidade;
	 * @param disponibilidade par�metro a representa uma disponibilidade;
	 */
	public void setDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	/**
	 * @return um clone do objeto que chama o m�todo como todos os seus atributos;
	 */
	public Ingrediente clone(){
		return new Ingrediente(nome, valorCalorico, precoPorCemGrama, disponibilidade);
	}
}
