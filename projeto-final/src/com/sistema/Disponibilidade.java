package com.sistema;
/**
 * Representa a disponibilidade de uma classe;
 * @author Filipe Farias
 *
 */
public enum Disponibilidade {
	/**
	 * Constante que representa que uma classe est� dispon�vel;
	 */
	DISPONIVEL(true, "DISPON�VEL"),
	/**
	 * Constante que representa que uma classe est� indispon�vel;
	 */
	INDISPONIVEL(false, "INDISPON�VEL");
	
	/**
	 * Atributo booleano que representa se uma est� dispon�vel (<code>true</code>) ou indispon�vel (<code>false</code>);
	 */
	private boolean disponibilidade;
	/**
	 * Representa��o em String da disponibilidade;
	 */
	private String disponivel;
	/**
	 * Construtor para inicializar as constantes do enumerador;
	 * @param disponibilidade par�metro a ser atribu�do a {@link #disponibilidade};
	 * @param disponivel par�metro a ser atribu�do a {@link #disponivel};
	 */
	Disponibilidade(boolean disponibilidade, String disponivel) {
		this.disponibilidade = disponibilidade;
		this.disponivel = disponivel;
	}
	/**
	 * Retorna o valor do atributo disponibilidade;
	 * @return {@link #disponibilidade};
	 */
	public boolean isDisponibilidade() {
		return disponibilidade;
	}
	/**
	 * Retorna o atributo disponilidade;
	 * @return {@link #disponibilidade};
	 */
	public String toString() {
		return disponivel;
	}
}
