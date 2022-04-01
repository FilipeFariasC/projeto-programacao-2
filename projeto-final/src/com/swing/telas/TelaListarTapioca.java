package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.sistema.*;
import com.swing.*;
import com.swing.paineis.*;

public class TelaListarTapioca extends JFramePadrao {
	private JTabbedPanePadrao abas;
	private ArrayList<Pedido> pedidos;
	private Central central;
	private ArrayList<ListarTapiocaPanel> paineis = new ArrayList<>();
	public TelaListarTapioca(Central central, ArrayList<Pedido> pedidos) {
		this.central = central;
		this.pedidos = pedidos;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(getTitle()+"PEDIDOS");
		setResizable(true);
		setLayout(new BorderLayout());
		setSize(500, 400);
		adicionarAbas();
		setVisible(true);
	}
	private void adicionarAbas(){
		abas = new JTabbedPanePadrao(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		for(Pedido p: pedidos){
			ListarTapiocaPanel painel = new ListarTapiocaPanel(central, central.getLogado(), p.getTapiocas(), this);
			paineis.add(painel);
			String format = String.format("%s - %s - %s", p.getCliente().getNome(), p.getData(), p.getStatusDoPedido().toString());
			abas.addTab(format, painel);
		}
		add(abas, BorderLayout.CENTER);
		adicionarListener();
	}
	private void adicionarListener() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e) {}
			public void windowGainedFocus(WindowEvent e) {
				for(ListarTapiocaPanel panel: paineis){
					panel.refresh();
				}
			}
		});
		
	}
}
