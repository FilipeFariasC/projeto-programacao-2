package com.sistema;
/**
 * Representa��o de um status de entrega;
 * @author Filipe Farias
 *
 */
public enum Status {
	/**
	 * Representa que o pedido n�o foi entregue;
	 */
	ABERTO("EM ABERTO"), 
	/**
	 * Representa que o pedido foi entregue;
	 */
	FECHADO("ENTREGUE");
	/**
	 * Representa��o em String do status;
	 */
	private String status;
	
	/**
	 * Inicializa o atributo {@link #status};
	 * @param s atribu�do a {@link #status};
	 */
	Status(String s){
		status = s;
	}
	/**
	 * @return {@link #status};
	 */
	public String toString(){
		return status;
	}
}
