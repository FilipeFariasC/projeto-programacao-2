 package com.swing.paineis;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.sistema.*;
import com.swing.telas.*;
import com.swing.*;
import com.swing.listeners.RadioButtonListener;

public class ListarPedidosPanel extends JPanelPadrao implements Refreshable, Pesquisavel{
	private DefaultTableModelPadrao model = new DefaultTableModelPadrao();
	private JTable pedido;
	private JScrollPane scroll;
	private JTextFieldPadrao buscador;
	private JButtonPadrao listar,
						  alterarStatus;
	private JRadioButtonPadrao entregue,
							emAberto;
	private ArrayList<Pedido> pedidos;
	private Pessoa pessoa;
	private JPanelPadrao botoes = new JPanelPadrao(),
						 modificar = new JPanelPadrao(),
						 painel = new JPanelPadrao();
	private Central central;
	public ListarPedidosPanel(Central central, Pessoa p, ArrayList<Pedido> pedidos) {
		this.central = central;
		this.pedidos = pedidos;
		pessoa = p;
		setLayout(new BorderLayout());
		base();
	}
	private void base(){
		adicionarColuna();
		adicionarTableScroll();
		adicionarLinha(pedidos);
		adicionarPainel();
		adicionarBuscador();
	}
	private class AlterarListarListener implements ActionListener {
		private Component main;
		public AlterarListarListener(Component comp){
			main = comp;
		}
		public void actionPerformed(ActionEvent e) {
			if (pedidos.size() > 0){
				int[] escolha = pedido.getSelectedRows();
				if (escolha.length > 0){ 
					ArrayList<Pedido> pedi = new ArrayList<>();
	
					for(int c = 0; c < escolha.length;c++){
						String id = pedido.getValueAt(escolha[c], 0).toString();
						for(Pedido in: pedidos){
							if (in.equals(id)){
								pedi.add(in);
							}
						}
					}
					if (e.getActionCommand().equals("LISTAR PEDIDO(S)")){
						new TelaListarTapioca(central, pedi);
					} else if (e.getActionCommand().equals("ALTERAR STATUS")){
						TelaAlterarStatus status = new TelaAlterarStatus(central, pedi);
						status.addWindowListener(new WindowListener() {
							public void windowOpened(WindowEvent e)    {	}
							public void windowIconified(WindowEvent e) {	}
							public void windowDeiconified(WindowEvent e) {	}
							public void windowDeactivated(WindowEvent e) { 	}
							public void windowClosing(WindowEvent e)   {	}
							public void windowClosed(WindowEvent e)    {
								refresh();
							}
							public void windowActivated(WindowEvent e) {	}
						});
					}
				} else{
					JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
				}
			}else{
				JOptionPane.showMessageDialog(main, "NÃO FORAM FEITOS PEDIDOS", null, 0, ConstantesSwing.INFO);
			}
		}
	}
	private class PesquisarListener implements KeyListener{
		public void keyPressed(KeyEvent event)  {
			
		}
		public void keyReleased(KeyEvent event) {
			char letra = event.getKeyChar();
			if (Character.isAlphabetic(letra) || letra == '\b'){
				pesquisar();
			}
		}
		public void keyTyped(KeyEvent event)    {	}
	}
	public void pesquisar(){
		ArrayList<Pedido> ent = new ArrayList<>();
		ArrayList<Pedido> abe = new ArrayList<>();
		ArrayList<Pedido> pedi = new ArrayList<>();
		for (Pedido in: pedidos){
			Status status = in.getStatusDoPedido();
			String texto = buscador.getText().toUpperCase(),
				   nome = in.getCliente().getNome(),
				   id = in.getId();

			if (buscar(nome, id, texto)){
				if(entregue.isSelected() && status == Status.FECHADO){
					ent.add(in);
				} else if(emAberto.isSelected() && status == Status.ABERTO){
					abe.add(in);
				} else{
					pedi.add(in);
				}
			}
		}
		if(entregue.isSelected()){
			refresh(ent);
		} else if(emAberto.isSelected()){
			refresh(abe);
		} else{
			refresh(pedi);
		}
	}
	private boolean buscar(String nome, String id, String txt){
		if(nome.contains(txt)||
		   id.contains(txt)){
			return true;
		}
		return false;
	}
	private void refresh(ArrayList<Pedido> pedido){
		remover();
		adicionarLinha(pedido);
	}
	public void refresh(){
		remover();
		recarregarPedidos();
		adicionarLinha(pedidos);
		pedido.repaint();
	}
	private void recarregarPedidos(){
		if(pessoa instanceof Cliente){
			pedidos = ((Cliente)pessoa).getPedidos();
		} else{
			pedidos = central.getPedidos();
		}
	}
	private void remover(){
		for(; model.getRowCount() !=0 ;){
			model.removeRow(0);
		}
	}
	private void adicionarColuna(){
		model = new DefaultTableModelPadrao();
		Object[] obj = {"ID DO PEDIDO", "NOME CLIENTE", "DATA", "STATUS DO PEDIDO"};
		for(Object in: obj){
			model.addColumn(in);
		}
	}
	private void adicionarLinha(ArrayList<Pedido> pedi) {
		for(Pedido in: pedi){
			Object[] obj = {in.getId(), in.getCliente().getNome(),
					in.getData(), in.getStatusDoPedido().toString()};
			model.addRow(obj);
		}
		DefaultTableCellRenderer meio = new DefaultTableCellRenderer();
		meio.setHorizontalAlignment(SwingConstants.CENTER);
		for (int c = 0; c < pedido.getColumnCount(); c++){
			int tamanho = 0;
			TableColumn coluna = pedido.getColumnModel().getColumn(c);
			coluna.setCellRenderer(meio);
			if(c == 0){
				tamanho = 100;
			} else if(c == 1){
				tamanho = 200;
			} else if (c == 2){
				tamanho = 120;
			} else{
				tamanho = 150;
			}
			coluna.setPreferredWidth(tamanho);
		}
		pedido.repaint();
	}
	private void adicionarTableScroll(){
		pedido = new JTable(model);
		scroll = new JScrollPane(pedido);
	}
	private void adicionarPainel(){
		modificar.setLayout(new BorderLayout());
		painel.setLayout(new GridLayout(1,2));
		
		adicionarRadioButton();
		if(pessoa instanceof Administrador){
			adicionarButtonADM();
		}
		adicionarListar();
		
		add(scroll, BorderLayout.CENTER);
		add(modificar, BorderLayout.NORTH);
	}
	private void adicionarBuscador(){
		buscador = new JTextFieldPadrao("");
		buscador.addKeyListener(new PesquisarListener());

		modificar.add(buscador, BorderLayout.SOUTH);
	}
	private void adicionarRadioButton(){
		entregue = new JRadioButtonPadrao("ENTREGUE");
		emAberto = new JRadioButtonPadrao("EM ABERTO");
		
		setRadioButton(entregue);
		setRadioButton(emAberto);
		
		new RadioButtonListener(entregue, emAberto, this);
		
		painel.add(entregue);
		painel.add(emAberto);
		
		modificar.add(painel, BorderLayout.CENTER);
	}
	private void adicionarListar(){
		botoes.setLayout(new GridLayout(1,2));
		listar = new JButtonPadrao("LISTAR PEDIDO(S)");
		AlterarListarListener listener = new AlterarListarListener(this);
		listar.setIcon(ConstantesSwing.LISTAR);
		listar.addActionListener(listener);
		botoes.add(listar);
		add(botoes, BorderLayout.SOUTH);
	}
	private void setRadioButton(JRadioButtonPadrao check){
		check.setFont(ConstantesSwing.TIMES_TEXTO);
		check.setHorizontalAlignment(SwingConstants.CENTER);
	}
	private void adicionarButtonADM(){
		alterarStatus = new JButtonPadrao("ALTERAR STATUS");
		AlterarListarListener listener = new AlterarListarListener(this);
		alterarStatus.addActionListener(listener);
		alterarStatus.setIcon(ConstantesSwing.ALTERAR);
		
		botoes.add(alterarStatus);
	}
}
