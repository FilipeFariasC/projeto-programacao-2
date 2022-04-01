package com.swing;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public enum Icones {
	ERRO("ERRO", new ImageIcon("./icones/error.png")),
	INFO("INFO",new ImageIcon("./icones/info.png")),
	TAPIOCA("ERRO",new ImageIcon("./icones/error.png")),
	EMAIL("EMAIL",new ImageIcon("./icones/email.png")),
	QUESTAO("QUESTÃO",new ImageIcon("./icones/question.png")),
	CARRINHO("CARRINHO",new ImageIcon("./icones/carrinho.png")),
	ADD_CARRINHO("ADICIONAR AO CARRINHO",new ImageIcon("./icones/addcarrinho.png")),
	REMOVER_CARRINHO("REMOVER DO CARRINHO",new ImageIcon("./icones/removercarrinho.png")),
	RELATORIO("RELATÓRIO",new ImageIcon("./icones/relatorio.png")),
	PEDIDOS("PEDIDO",new ImageIcon("./icones/pedidos.png")),
	;
	private ImageIcon icone;
	private String erro;
	
	Icones (String error, ImageIcon icon){
		erro = error;
		icone = icon;
	}
	public static void main(String[] args) {
		for (Icones i: Icones.values()){
			JOptionPane.showMessageDialog(null, i.getErro(), "ALGO", 0, i.getIcone());
		}
	}
	public String getErro() {
		return erro;
	}
	public ImageIcon getIcone() {
		return icone;
	}
}
