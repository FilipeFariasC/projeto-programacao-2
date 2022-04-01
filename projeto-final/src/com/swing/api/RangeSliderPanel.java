package com.swing.api;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.swing.JFramePadrao;
import com.swing.JLabelPadrao;
import com.swing.JPanelPadrao;
import com.swing.JTextFieldPadrao;
import com.swing.Pesquisavel;
import com.swing.api.RangeSlider;
import com.swing.paineis.ListarTapiocaPanel;

public class RangeSliderPanel extends JPanelPadrao {
	private RangeSlider rangeSlider;
	private JTextFieldPadrao minimo,
							 maximo;
	
	public RangeSliderPanel(int min, int max, 
							int minimium, int maximium, 
							Pesquisavel panel) {
		base();
		rangeSlider = new RangeSlider(min, max, minimo, maximo, panel);
		rangeSlider.setValue(minimium);
		rangeSlider.setUpperValue(maximium);
		minimo.setText("MIN: "+getValorMinimo());
		maximo.setText("MAX: "+getValorMaximo());
		add(rangeSlider, BorderLayout.CENTER);
		setVisible(true);
	}
	private void base(){
		setLayout(new BorderLayout());
		adicionarTextFields();
	}
	private void adicionarTextFields(){
		minimo = new JTextFieldPadrao("");
		maximo = new JTextFieldPadrao("");
		setarTextField(minimo);
		setarTextField(maximo);
		add(minimo, BorderLayout.WEST);
		add(maximo, BorderLayout.EAST);
	}
	private void setarTextField(JTextFieldPadrao txt){
		txt.setSize(80, 30);
		txt.setOpaque(true);
		txt.setFocusable(false);
		txt.setBackground(Color.WHITE);
		txt.setEditable(false);
		txt.setHorizontalAlignment(SwingConstants.CENTER);
	}
	public void setValorMinimo(int min){
		rangeSlider.setValue(min);
	}
	public int getValorMinimo(){
		return rangeSlider.getValue();
	}
	public void setValorMaximo(int max){
		rangeSlider.setUpperValue(max);
	}
	public int getValorMaximo(){
		return rangeSlider.getUpperValue();
	}
	public void setValorMinimoTotal(int min){
		rangeSlider.setMinimum(min);
	}
	public int getValorMinimoTotal(){
		return rangeSlider.getMinimum();
	}
	public void setValorMaximoTotal(int max){
		rangeSlider.setMaximum(max);
	}
	public int getValorMaximoTotal(){
		return rangeSlider.getMaximum();
	}
}
