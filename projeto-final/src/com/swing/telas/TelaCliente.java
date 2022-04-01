package com.swing.telas;


import java.awt.Component;
import java.awt.Window;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.sistema.*;
import com.swing.*;
import com.swing.paineis.*;

public class TelaCliente extends JFramePadrao{
	private Cliente logado;
	private MenuPadrao padraoCliente;
	private JTabbedPanePadrao abas;
	private ListarPedidosPanel pedidos;
	private ListarTapiocaPanel carrinho;
	private Central central;
	public TelaCliente(Central central, Cliente p) {
		logado = p;
		this.central = central;
		base();
		setVisible(true);
	}
	private void base() {
		setSize(700, 600);
		adicionarLabel();
		adicionarMenu();
		adicionarTabbedPane();
		adicionarPedidos();
		adicionarCarrinho();
		JanelaListener list = new JanelaListener(this);
		addWindowFocusListener(list);
	}
	private class JanelaListener implements WindowFocusListener, WindowListener {
		private Component main;
		
		public JanelaListener(Component main) {
			this.main = main;
		}
		public void windowOpened(WindowEvent e) {
			String parte;
			SEXO sexo = central.getLogado().getSexo();
			if(sexo == SEXO.MASCULINO){
				parte = "VINDO";
			} else{
				parte = "VINDA";
			}
			String msg = String.format("SEJA BEM %s %s!", parte, central.getLogado().getNome());
			JOptionPane.showMessageDialog(main, msg, "BEM VINDO", 0, ConstantesSwing.BEM_VINDO);
		}
		public void windowIconified(WindowEvent e) {	}
		public void windowDeiconified(WindowEvent e) {	}
		public void windowDeactivated(WindowEvent e) {	}
		public void windowClosing(WindowEvent e) {	}
		public void windowClosed(WindowEvent e) {
			Window [] windows = Window.getWindows(); 
			for(int i=0;i < windows.length;i++){ 
				windows[i].dispose(); 
			} 
		}
		public void windowActivated(WindowEvent e) {	}
		public void windowGainedFocus(WindowEvent e) {
			pedidos.refresh();
			carrinho.refresh();
		}
		public void windowLostFocus(WindowEvent e) {	}
	}
	private void adicionarLabel(){
		JLabelPadrao tapiocaMaker = new JLabelPadrao(ConstantesSwing.TAPIOCA_MAKER, ConstantesSwing.CONSTANTIA_TITULO);
		tapiocaMaker.setBounds(230, 60, 300, 50);
		tapiocaMaker.setForeground(ConstantesSwing.ESMERALDA);
		tapiocaMaker.setIcon(ConstantesSwing.TAPIOCA_2);;
		tapiocaMaker.setOpaque(true);
		add(tapiocaMaker);
	}
	private void adicionarMenu(){
		padraoCliente = new MenuPadrao(central, this);
		add(padraoCliente);
	}
	private void adicionarTabbedPane(){
		abas = new JTabbedPanePadrao(JTabbedPanePadrao.TOP, JTabbedPanePadrao.SCROLL_TAB_LAYOUT);
		abas.setBounds(20, 150, getWidth()-40, getHeight()-150-40);
		add(abas);
	}
	private void adicionarPedidos(){
		pedidos = new ListarPedidosPanel(central, logado, logado.getPedidos());
		setTab(pedidos, "PEDIDOS", ConstantesSwing.PEDIDOS);
	}
	private void adicionarCarrinho(){
		carrinho = new ListarTapiocaPanel(central, logado, logado.getCarrinho(), this);
		setTab(carrinho, "CARRINHO", ConstantesSwing.CARRINHO);
	}
	private void setTab(JPanelPadrao panel, String titulo, ImageIcon icone){
		abas.addTab(titulo, panel);
		int indice = abas.indexOfTab(titulo);
		abas.setIconAt(indice, icone);
	}
}
