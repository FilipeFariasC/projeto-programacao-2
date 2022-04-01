package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.sistema.*;
import com.swing.*;

public class TelaAlterarStatus extends JFramePadrao {
	private ArrayList<Pedido> pedidos;
	private JTabbedPanePadrao abas;
	private Central central;
	public TelaAlterarStatus(Central central, ArrayList<Pedido> pedidos){
		this.pedidos = pedidos;
		this.central = central;
		setSize(400, 350);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		adicionarAbas();
		setVisible(true);
	}
	private class StatusChange implements ActionListener{
		private JRadioButtonPadrao aberto,
								   fechado;
		public StatusChange(JRadioButtonPadrao aberto, JRadioButtonPadrao fechado) {
			this.aberto = aberto;
			this.fechado = fechado;
		}
		public void actionPerformed(ActionEvent event) {
			Object objeto = event.getSource();
			if(objeto == aberto){
				fechado.setSelected(false);
			} else{
				aberto.setSelected(false);
			}
		}
	}
	private class ConfirmarListener implements ActionListener{
		private Pedido pedido;
		private JRadioButtonPadrao aberto,
		   						   fechado;
		private Component frame;
		public ConfirmarListener(Component frame, Pedido pedido, JRadioButtonPadrao aberto, JRadioButtonPadrao fechado) {
			this.frame = frame;
			this.pedido = pedido;			
			this.aberto = aberto;
			this.fechado = fechado;
		}
		public void actionPerformed(ActionEvent arg0) {
			Status st;
			String titulo = abaNome(pedido);
			int indice = abas.indexOfTab(titulo);
			int escolha = JOptionPane.showConfirmDialog(frame, "DESEJA SALVAR AS ALTERAÇÕES?", null, JOptionPane.YES_NO_OPTION,0, ConstantesSwing.QUESTAO);
			if(escolha == JOptionPane.YES_OPTION){
				if(aberto.isSelected()){
					st = Status.ABERTO;
				} else{
					st = Status.FECHADO;
				}
				pedido.setStatus(st);
				escolha = JOptionPane.showConfirmDialog(frame, "DESEJA FECHAR A ABA?", null, JOptionPane.YES_NO_OPTION,0, ConstantesSwing.QUESTAO);
				titulo = abaNome(pedido);
				if(escolha == JOptionPane.YES_OPTION){
					abas.remove(indice);
					if(abas.getTabCount() == 0){
						dispose();
					}
				} else{
					abas.setTitleAt(indice, titulo);
				}
				Persistencia.salvarCentral(central);
			}
		}
	}
	private String abaNome(Pedido p){
		return String.format("%s - %s - %s", p.getCliente().getNome(), p.getId(), p.getStatusDoPedido().toString());
	}
	private void adicionarAbas(){
		abas = new JTabbedPanePadrao(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		adicionarPedidos();
		add(abas, BorderLayout.CENTER);
	}
	private void adicionarPedidos(){
		for(Pedido p: pedidos){
			JPanelPadrao painel = new JPanelPadrao();
			String titulo = abaNome(p);
			abas.addTab(titulo, painel);
			adicionarJTextArea(painel, p);
		}
	}
	private void adicionarJTextArea(JPanelPadrao painel, Pedido pedi){
		JTextAreaPadrao base = new JTextAreaPadrao("");
		for(Tapioca in: pedi.getTapiocas()){
			base.setText(base.getText()+in.toString()+"\n");
		}
		base.setEditable(false);
		base.setFont(ConstantesSwing.TIMES_TEXTO);
		painel.setLayout(new BorderLayout());
		painel.add(base, BorderLayout.CENTER);
		adicionarOpcoes(painel, pedi);
	}
	private void adicionarOpcoes(JPanelPadrao panel, Pedido pedido){
		JPanelPadrao botoes = new JPanelPadrao();
		botoes.setLayout(new FlowLayout());
		
		JButtonPadrao confirmar = new JButtonPadrao("CONFIRMAR");
		JRadioButtonPadrao emAberto = new JRadioButtonPadrao("EM ABERTO"),
				  	 	   fechado = new JRadioButtonPadrao("FECHADO");
		StatusChange status = new StatusChange(emAberto, fechado);
		ConfirmarListener conf = new ConfirmarListener(this, pedido, emAberto, fechado);
		emAberto.addActionListener(status);
		fechado.addActionListener(status);
		
		confirmar.addActionListener(conf);
		
		botoes.add(fechado);
		botoes.add(emAberto);
		botoes.add(confirmar);
		botoes.setOpaque(true);
		panel.add(botoes, BorderLayout.NORTH);
	}
}
