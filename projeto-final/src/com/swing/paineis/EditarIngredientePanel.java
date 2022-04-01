package com.swing.paineis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.excecoes.IngredienteCadastradoException;
import com.excecoes.StringVaziaException;
import com.excecoes.ValorAbaixoDeZeroException;
import com.sistema.Central;
import com.sistema.Disponibilidade;
import com.sistema.Ingrediente;
import com.sistema.Persistencia;
import com.sistema.Tapioca;
import com.sistema.Util;
import com.swing.ConstantesSwing;
import com.swing.JButtonPadrao;
import com.swing.JLabelPadrao;
import com.swing.JPanelPadrao;
import com.swing.JRadioButtonPadrao;
import com.swing.JTabbedPanePadrao;
import com.swing.JTextFieldPadrao;
import com.swing.Pesquisavel;
import com.swing.Refreshable;
import com.swing.listeners.RadioButtonListener;

public class EditarIngredientePanel extends JPanelPadrao {
	private Ingrediente ingrediente;
	private JTabbedPanePadrao abas;
	private Window main;
	private JTextFieldPadrao nome,
							 preco,
							 calorias;
	private JButtonPadrao confirmar,
				  		  cancelar;
	private JRadioButtonPadrao disponivel,
							   indisponivel;
	private JPanelPadrao confirm,
						 avaliable;
	private Central central;
	public EditarIngredientePanel(Central central, Ingrediente ingrediente, JTabbedPanePadrao abas, Window main){
		this.central = central;
		this.ingrediente = ingrediente;
		this.abas = abas;
		this.main = main;
		setSize(300, 300);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		base();
		setVisible(true);
	}
	private class ConfirmarCancelarEdicaoListener implements ActionListener{
		private Component frame;
		public ConfirmarCancelarEdicaoListener(Component frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent event) {
			String txt = event.getActionCommand();
			try{
				String texto = abaNome(ingrediente);

				int indice = abas.indexOfTab(texto);
				if(txt.equals("CONFIRMAR")){
					Util.eVazia(nome.getText());
					double price = Double.parseDouble(preco.getText()),
						   calories = Double.parseDouble(calorias.getText());
					if(price <= 0){
						throw new ValorAbaixoDeZeroException(price);
					}
					if(calories <= 0){
						throw new ValorAbaixoDeZeroException(calories);
					}
					int escolha = JOptionPane.showConfirmDialog(frame, "DESEJA SALVAR AS ALTERAÇÕES?", "CONFIRMAR", JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);

					if (escolha == JOptionPane.OK_OPTION){
						Disponibilidade disp = (disponivel.isSelected())? Disponibilidade.DISPONIVEL: Disponibilidade.INDISPONIVEL;
						ingrediente.setDisponibilidade(disp);
						
						ingrediente.setNome(nome.getText());
						ingrediente.setPrecoPorCemGrama(price);
						ingrediente.setValorCalorico(calories);
						escolha = JOptionPane.showConfirmDialog(frame, "FECHAR ABA?", "CONFIRMAR", JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
						if (escolha == JOptionPane.YES_OPTION){
							abas.remove(indice);
							if (abas.getTabCount() == 0){
								main.dispose();
							}
						} else{
							texto = abaNome(ingrediente);
							abas.setTitleAt(indice, texto);
						}
						Persistencia.salvarCentral(central);
					} 
				} else{
					int escolha = JOptionPane.showConfirmDialog(frame, "DESEJA FECHAR ESSA ABA?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						abas.remove(indice);
						if (abas.getTabCount() == 0){
							main.dispose();
						}
					}
				}
			} catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "DIGITE CARACTERES VÁLIDOS!", "ERRO", 0, ConstantesSwing.ERRO);
			} catch (ValorAbaixoDeZeroException | StringVaziaException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
			}
		}
	}
	private String abaNome(Ingrediente i){
		return String.format("%s - %s - R$ %.2f - %.2f cal", 
				i.getNome(), i.getDisponibilidade().toString(), 
				i.getPrecoPorGrama(), i.getValorCalorico());
	}
	private void base() {
		adicionarNome();
		adicionarPreco();
		adicionarCalorias();
		adicionarRadioButton();
		adicionarButtons();
	}
	private void adicionarNome(){
		JPanelPadrao panel = gerarPanel();
		panel.setLayout(new BorderLayout());
		JLabelPadrao nome = new JLabelPadrao("NOME");
		this.nome = new JTextFieldPadrao(ingrediente.getNome());
		nome.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nome, BorderLayout.CENTER);
		add(panel);
		add(this.nome);
	}
	private void adicionarPreco(){
		JPanelPadrao panel = gerarPanel();
		JLabelPadrao preco = new JLabelPadrao("PREÇO");
		this.preco = new JTextFieldPadrao(""+ingrediente.getPrecoPorGrama());
		preco.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(preco, BorderLayout.CENTER);
		add(panel);
		add(this.preco);
	}
	private void adicionarCalorias(){
		JPanelPadrao panel = gerarPanel();
		JLabelPadrao calorias = new JLabelPadrao("VALOR CALÓRICO");
		calorias.setHorizontalAlignment(SwingConstants.CENTER);
		this.calorias = new JTextFieldPadrao(""+ingrediente.getValorCalorico());
		panel.add(calorias, BorderLayout.CENTER);
		add(panel);
		add(this.calorias);
	}

	private void adicionarButtons(){
		confirm = new JPanelPadrao();
		confirm.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		confirmar = new JButtonPadrao("CONFIRMAR");
		cancelar = new JButtonPadrao("FECHAR");
		cancelar.setBounds(10, 220, 120, 30);
		confirmar.setBounds(170, 220, 120, 30);
		ConfirmarCancelarEdicaoListener list = new ConfirmarCancelarEdicaoListener(this);
		confirmar.addActionListener(list);
		cancelar.addActionListener(list);
		confirm.add(cancelar);
		confirm.add(confirmar);
		add(confirm);
	}
	private void adicionarRadioButton(){
		avaliable = new JPanelPadrao();
		avaliable.setLayout(new FlowLayout(FlowLayout.CENTER));

		disponivel = new JRadioButtonPadrao("DISPONÍVEL");
		indisponivel = new JRadioButtonPadrao("INDISPONÍVEL");
		if(ingrediente.getDisponibilidade() == Disponibilidade.DISPONIVEL){
			disponivel.setSelected(true);
		} else{
			indisponivel.setSelected(true);
		}
		disponivel.setHorizontalAlignment(SwingConstants.CENTER);
		indisponivel.setHorizontalAlignment(SwingConstants.CENTER);
		new RadioButtonListener(disponivel, indisponivel, null);
		avaliable.add(disponivel);
		avaliable.add(indisponivel);
		add(avaliable);
	}
	private JPanelPadrao gerarPanel(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new BorderLayout());
		return panel;
	}
}