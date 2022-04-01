package com.sistema;
/**
 * Representa o sexo de um indiv�duo
 * @author Filipe Farias
 *
 */
public enum SEXO {
	/**
	 * Representa o sexo masculino;
	 */
	MASCULINO("MASCULINO"),
	/**
	 * Representa o sexo feminino;
	 */
	FEMININO("FEMININO");
	/**
	 * Representa��o em String do sexo;
	 */
	private String sexo;
	/**
	 * Construtor que inicializa o atributo {@link #sexo};
	 * @param sexo par�metro a ser atribu�do a sexo;
	 */
	SEXO(String sexo){
		this.sexo = sexo;
	}
	/**
	 * Retorna {@link #sexo};
	 */
	public String toString(){
		return sexo;
	}
}
