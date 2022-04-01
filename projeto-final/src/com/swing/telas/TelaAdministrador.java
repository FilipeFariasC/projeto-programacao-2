package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sistema.*;
import com.swing.*;
import com.swing.paineis.*;

public class TelaAdministrador extends JFramePadrao {
	private JTabbedPanePadrao abas;
	private ListarPedidosPanel painelListarPedidos;
	private ListarTapiocaPanel painelListarTapioca;
	private ListarIngredientePanel painelListarIngredientes;
	private Central central;
	private MenuPadrao padraoADM;
	public TelaAdministrador(Central atual) {
		central = atual;
		central.setLogado(central.getAdministrador());
		padraoADM = new MenuPadrao(central, this);
		setTitle(ConstantesSwing.TAPIOCA_MAKER+" - "+central.getAdministrador().getNome());
		add(padraoADM);
		setSize(650, 550);
		adicionarAbas();
		setVisible(true);
	}
	private void adicionarAbas(){
		abas = new JTabbedPanePadrao(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		abas.setBounds(15, 150, 615, 365);
		
		adicionarLabel();
		
		adicionarPedidos();
		adicionarListarTapiocas();
		adicionarListarIngredientes();
		adicionarListener();

		add(abas);
	}
	private void adicionarLabel(){
		JLabelPadrao tapiocaMaker = new JLabelPadrao(ConstantesSwing.TAPIOCA_MAKER, ConstantesSwing.CONSTANTIA_TITULO);
		tapiocaMaker.setBounds(210, 60, 300, 50);
		tapiocaMaker.setForeground(ConstantesSwing.ESMERALDA);
		tapiocaMaker.setIcon(ConstantesSwing.TAPIOCA_2);;
		tapiocaMaker.setOpaque(true);
		add(tapiocaMaker);
	}
	private void adicionarPedidos(){
		painelListarPedidos = new ListarPedidosPanel(central, central.getLogado(), central.getPedidos());
		setAba(painelListarPedidos, "PEDIDOS", ConstantesSwing.PEDIDOS);
	}
	private void adicionarListarTapiocas(){
		painelListarTapioca = new ListarTapiocaPanel(central, central.getLogado(), central.getTapiocas(), this);
		setAba(painelListarTapioca, "TAPIOCAS", ConstantesSwing.TAPIOCA_1);
	}
	private void adicionarListarIngredientes(){
		painelListarIngredientes = new ListarIngredientePanel(central, central.getIngredientes(), this);
		setAba(painelListarIngredientes, "INGREDIENTES", ConstantesSwing.INGREDIENTES);
	}
	private void setAba(JPanelPadrao panel, String txt, ImageIcon icone){
		abas.addTab(txt, panel);
		int indice = abas.indexOfTab(txt);
		abas.setIconAt(indice, icone);
	}
	private void adicionarListener(){
		addWindowFocusListener(new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e) { }
			public void windowGainedFocus(WindowEvent e) {
				painelListarIngredientes.refresh();
				painelListarPedidos.refresh();
				painelListarTapioca.refresh();
			}
		});
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {	}
			public void windowIconified(WindowEvent e) {	}
			public void windowDeiconified(WindowEvent e) {	}
			public void windowDeactivated(WindowEvent e) {	}
			public void windowClosing(WindowEvent e) {
				Window [] windows = Window.getWindows(); 
				for(int i=0;i < windows.length;i++){ 
					windows[i].dispose(); 
				} 
			}
			public void windowClosed(WindowEvent e) {	}
			public void windowActivated(WindowEvent e) {	}
		});
	}
}
