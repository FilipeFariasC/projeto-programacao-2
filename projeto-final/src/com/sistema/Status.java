package com.sistema;
/**
 * Representação de um status de entrega;
 * @author Filipe Farias
 *
 */
public enum Status {
	/**
	 * Representa que o pedido não foi entregue;
	 */
	ABERTO("EM ABERTO"), 
	/**
	 * Representa que o pedido foi entregue;
	 */
	FECHADO("ENTREGUE");
	/**
	 * Representação em String do status;
	 */
	private String status;
	
	/**
	 * Inicializa o atributo {@link #status};
	 * @param s atribuído a {@link #status};
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
