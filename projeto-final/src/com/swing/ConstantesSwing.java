package com.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

public interface ConstantesSwing{
	//FONTES
	public static final Font CONSTANTIA_TITULO = new Font("CONSTANTIA", Font.BOLD, 20);
	public static final Font CONSOLAS_BOTAO = new Font("CONSOLAS", Font.PLAIN, 14);
	public static final Font TIMES_TEXTO = new Font("TIMES", Font.PLAIN, 12);
	public static final Font TIMES_NEW_ROMAN_CHECK_BOX = new Font("TIMES NEW ROMAN", Font.PLAIN, 8);
	public static final Font ARIAL_RADIO_BUTTON = new Font ("ARIAL", Font.BOLD, 10);

	//icones
	public static ImageIcon ADD = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("add.png"));;
	public static ImageIcon ADD_CARRINHO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("addcarrinho.png"));
	public static ImageIcon ADD_USER = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("add_user.png"));
	public static ImageIcon ALTERAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("alterar.png"));
	public static ImageIcon BEM_VINDO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("bem-vindo.png"));
	public static ImageIcon CANCELAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("cancel.png"));
	public static ImageIcon CARDAPIO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("cardapio.png"));
	public static ImageIcon CARRINHO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("carrinho.png"));
	public static ImageIcon CONFIG = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("conf.png"));
	public static ImageIcon EDITAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("editar.png"));
	public static ImageIcon EMAIL_1 = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("email.png"));
	public static ImageIcon EMAIL_2 = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("mail.png"));
	public static ImageIcon ERRO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("error.png"));
	public static ImageIcon EXCLUIR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("excluir.png"));
	public static ImageIcon EXIT = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("exit.png"));	
	public static ImageIcon INFO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("info.png"));
	public static ImageIcon INGREDIENTES = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("ingredientes.png"));
	public static ImageIcon INVISIVEL = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("invisivel.png"));
	public static ImageIcon LIMPAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("clear.png"));
	public static ImageIcon LISTAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("listar.png"));
	public static ImageIcon LOGIN = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("login.png"));
	public static ImageIcon MUDAR_VISUAL = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("mudarvisual.png"));
	public static ImageIcon PEDIDOS = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("pedidos.png"));
	public static ImageIcon PESQUISAR = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("pesquisar.png"));
	public static ImageIcon QUESTAO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("question.png"));
	public static ImageIcon RELATORIO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("relatorio.png"));
	public static ImageIcon REMOVER_CARRINHO = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("removercarrinho.png"));
	public static ImageIcon SELECIONAR_TODOS = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("select_all.png"));
	public static ImageIcon SIGN_IN = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("enter.png"));
	public static ImageIcon SOBRE = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("about.png"));
	public static ImageIcon TAPIOCA_1 = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("taciopa.png"));
	public static ImageIcon TAPIOCA_2 = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("tapioca.png"));
	public static ImageIcon VISIVEL = new ImageIcon(ConstantesSwing.class.getClassLoader().getResource("visivel.png"));
	
	//PALAVRAS
	public static final String TAPIOCA_MAKER = "TAPIOCA MAKER";
	
	// CORES
	public static final Color ESMERALDA = new Color(4, 99, 6);

}
