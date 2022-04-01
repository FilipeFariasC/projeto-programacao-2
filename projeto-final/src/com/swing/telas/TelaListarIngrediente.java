package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sistema.*;
import com.swing.*;
import com.swing.paineis.*;
import com.swing.telas.*;

public class TelaListarIngrediente extends JFramePadrao {
	private ArrayList<Tapioca> tapiocas;
	private JTabbedPanePadrao abas;
	private Component main;
	private Central central;
	private ArrayList<ListarIngredientePanel> paineis = new ArrayList<>();
	public TelaListarIngrediente(Central central, ArrayList<Tapioca> tapiocas, Component main){
		this.central = central;
		this.tapiocas = tapiocas;
		this.main = main;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("INGREDIENTES");
		setSize(400, 400);
		setLayout(new BorderLayout());
		adicionarAbas();
		setVisible(true);
	}
	private void adicionarAbas(){
		abas = new JTabbedPanePadrao(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		abas.setVisible(true);
		for(Tapioca t: tapiocas){
			ListarIngredientePanel painel = new ListarIngredientePanel(central, t.getIngredientes(), main);
			paineis.add(painel);
			abas.addTab(t.getNome(), painel);
		}
		add(abas);
		adicionarListener();
	}
	private void adicionarListener() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e) {}
			public void windowGainedFocus(WindowEvent e) {
				for(ListarIngredientePanel panel: paineis){
					panel.refresh();
				}
			}
		});

	}
}
