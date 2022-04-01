package com.sistema;
/**
 * Representa a disponibilidade de uma classe;
 * @author Filipe Farias
 *
 */
public enum Disponibilidade {
	/**
	 * Constante que representa que uma classe está disponível;
	 */
	DISPONIVEL(true, "DISPONÍVEL"),
	/**
	 * Constante que representa que uma classe está indisponível;
	 */
	INDISPONIVEL(false, "INDISPONÍVEL");
	
	/**
	 * Atributo booleano que representa se uma está disponível (<code>true</code>) ou indisponível (<code>false</code>);
	 */
	private boolean disponibilidade;
	/**
	 * Representação em String da disponibilidade;
	 */
	private String disponivel;
	/**
	 * Construtor para inicializar as constantes do enumerador;
	 * @param disponibilidade parâmetro a ser atribuído a {@link #disponibilidade};
	 * @param disponivel parâmetro a ser atribuído a {@link #disponivel};
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
