package com.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.excecoes.TapiocaNaoDisponivelException;
import com.sistema.Central;
import com.sistema.Cliente;
import com.sistema.GeradorDeRelatorios;
import com.sistema.Main;
import com.sistema.Mensageiro;
import com.sistema.Pedido;
import com.sistema.Persistencia;
import com.sistema.Tapioca;
import com.swing.telas.*;

public class MenuPadrao extends JMenuBarPadrao{
	private JMenuPadrao opcoes,
						sobre,
						config;
	private Central central;
	public MenuPadrao(Central central, Window comp) {
		super(comp);
		this.central = central;
		setSize(comp.getWidth()+20, 30);
		adicionarMenu();
	}
	private class LookAndFeelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String look = event.getActionCommand();
			try {
	            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	                if (look.equalsIgnoreCase(info.getName())) {
	                    UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
			SwingUtilities.updateComponentTreeUI(getMain());
			
		}
	}
	private class ADMButtonsListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String txt = event.getActionCommand();
			if(txt.equals("TAPIOCA")){
				if(central.getIngredientes().size() > 0){
					new TelaAdicionarTapioca(central, central.getIngredientes());
				} else{
					JOptionPane.showMessageDialog(getMain(), "CRIE AO MENOS UM INGREDIENTE PARA PODER CADASTRAR UMA TAPIOCA", null, 0, ConstantesSwing.INFO);
				}
			} else if (txt.equals("INGREDIENTE")){
				new TelaAdicionarIngrediente(central);
			} else if (txt.equals("ENVIAR MALA DIRETA")){
				if(central.getClientes().size() > 0){
					new TelaEnviarEmail(central);
				} else{
					JOptionPane.showMessageDialog(getMain(), "NÃO HÁ CLIENTES CADASTRADOS", null, 0, ConstantesSwing.INFO);
				}
			} else{
				if(central.getVendas().size() != 0){
					JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
					chooser.setSelectedFile(new File("RELATÓRIO TAPIOCA MAKER.pdf"));
					FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivo PDF", "pdf");
					chooser.setFileFilter(filtro);
					int escolha = chooser.showSaveDialog(getMain());
					
					if(escolha == JFileChooser.APPROVE_OPTION){
						String nomeArquivo = chooser.getSelectedFile().getName();
						String path = chooser.getCurrentDirectory().getAbsolutePath();
						StringBuilder caminho = new StringBuilder();
						for(char letra: path.toCharArray()){
							char l = letra;
							if(l == '\\'){
								l = '/';
							}
							caminho.append(letra);
						}
						caminho.append('/'+nomeArquivo);
						GeradorDeRelatorios.gerarRelatorio(central, caminho.toString());
					}
				} else {
					JOptionPane.showMessageDialog(getMain(), "NÃO HÁ TAPIOCAS CADASTRADAS", null, 0, ConstantesSwing.ERRO);
				}
			}
		}
	}
	private class RealizarPedidoListerner implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Cliente cliente= ((Cliente)central.getLogado());
				if (cliente.getCarrinho().size() > 0){
					int escolha = JOptionPane.showConfirmDialog(getMain(), "DESEJA REALIZAR A COMPRA?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						central.comprarTapiocas(cliente);

						ArrayList<Pedido> pedidos = cliente.getPedidos();
						
						StringBuilder msg = new StringBuilder();
						String formatado = String.format("%s\nTAPIOCAS:\n\n", cliente.getNome());
						msg.append(formatado);
						try{
							Pedido pedido = pedidos.get(pedidos.size()-1);
							for(Tapioca tap: pedido.getTapiocas()){
								String format = String.format("NOME: %s\t\tPREÇO: R$ %.2f\n", tap.getNome(), tap.getPreco());
								msg.append(format);
							}
							msg.append(String.format("\nQUANTIDADE DE TAPIOCAS COMPRADAS: %d\nTOTAL: R$ %.2f", pedido.getQuant(), pedido.getPreco()));
							
							Mensageiro.enviarEmail(central.getAdministrador().getEmail(), "COMPRA REALIZADA NO TAPIOCA MAKER", msg.toString());
							Mensageiro.enviarEmail(cliente.getEmail(), "COMPRA REALIZADA NO TAPIOCA MAKER", msg.toString());
							JOptionPane.showMessageDialog(getMain(), "OBRIGADO POR COMPRAR NO TAPIOCA MAKER", "COMPRA BEM SUCEDIDA", 0, ConstantesSwing.INFO);
							Persistencia.salvarCentral(central);
						} catch(ArrayIndexOutOfBoundsException e){
							
						}

					}
				} else{
					JOptionPane.showMessageDialog(getMain(), "ADICIONE TAPIOCAS AO CARRINHO PARA PODER REALIZAR O PEDIDO", null, 0, ConstantesSwing.INFO);
				}
			} catch (TapiocaNaoDisponivelException e) {
				JOptionPane.showMessageDialog(getMain(), e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
			} catch (MessagingException e) {
				JOptionPane.showMessageDialog(getMain(), "ERRO NO ENVIO DO EMAIL DE CONFIRMAÇÃO", "ERRO", 0, ConstantesSwing.ERRO);
			}
		}
	}
	private void adicionarMenu(){
		opcoes = new JMenuPadrao("OPÇÕES");
		sobre = new JMenuPadrao("SOBRE");
		adicionarConfiguracoes();
		sobre.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {	}
			public void mousePressed(MouseEvent e)  {	}
			public void mouseExited(MouseEvent e)   {	}
			public void mouseEntered(MouseEvent e)  {	}
			public void mouseClicked(MouseEvent e)  {
				JDialogPadrao dialogo = new JDialogPadrao(getMain());
				dialogo.setTitle("SOBRE");
				dialogo.setSize(350, 200);
				dialogo.setLayout(new BorderLayout());
				JLabelPadrao txt = new JLabelPadrao("<html> <div style=\"text-align:center\">TAPIOCA MAKER<br/><br/>"+
					"DIREITOS RESERVADOS - FILIPE FARIAS CHAGAS<br/>"+
					"CONTATO:<br />"+
					"filipe.farias.chagas@hotmail.com<br />"+
					"filipe.farias.chagas@gmail.com<br />"+
					"Twitter: @FilipeFariasC<br />"+
					"GitHub: FilipeTRSFarias<br />"+
					"PROJETO - PROGRAMAÇÃO II - 2019.1<br /></html>", ConstantesSwing.ARIAL_RADIO_BUTTON);
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				dialogo.add(txt, BorderLayout.CENTER);
				dialogo.setVisible(true);
			}
		});

		if (!(getMain() instanceof TelaLogin)){
			if(getMain() instanceof TelaAdministrador){
				adicionarADMButtons();
			} else{
				adicionarClienteButtons();
			}
			adicionarSair();
		}
		add(opcoes);
		add(sobre);
	}
	private void adicionarConfiguracoes(){
		config = new JMenuPadrao("MUDAR VISUAL");
		JMenuItemPadrao metal = new JMenuItemPadrao("METAL"),
						nimbus = new JMenuItemPadrao("NIMBUS"),
						cdeMotif = new JMenuItemPadrao("CDE/MOTIF"),
						windows = new JMenuItemPadrao("WINDOWS"),
						windowsClassic = new JMenuItemPadrao("WINDOWS CLASSIC");
		LookAndFeelListener list = new LookAndFeelListener();
		config.setIcon(ConstantesSwing.MUDAR_VISUAL);
		metal.addActionListener(list);
		nimbus.addActionListener(list);
		cdeMotif.addActionListener(list);
		windows.addActionListener(list);
		windowsClassic.addActionListener(list);
		config.add(metal);
		config.add(nimbus);
		config.add(cdeMotif);
		config.add(windows);
		config.add(windowsClassic);
		
		/*Metal
		  Nimbus
		  CDE/Motif
		  Windows
		  Windows Classic
		*/
		opcoes.add(config);
	}
	private void adicionarSair(){
		JMenuItemPadrao sair = new JMenuItemPadrao("SAIR");
		sair.setIcon(ConstantesSwing.EXIT);
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showConfirmDialog(getMain(), "DESEJA SAIR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
				if (op == JOptionPane.OK_OPTION){
					central.setLogado(null);
					
					Window [] windows = Window.getWindows(); 
					for(int i=0;i < windows.length;i++){ 
						windows[i].dispose(); 
					} 
					Persistencia.salvarCentral(central);
					new TelaLogin(central);
				}
			}
		});
		opcoes.add(sair);
	}

	private void adicionarADMButtons(){
		JMenuPadrao adicionar= new JMenuPadrao("ADICIONAR");
		JMenuItemPadrao adicionarTapioca = new JMenuItemPadrao("TAPIOCA"),
						adicionarIngrediente = new JMenuItemPadrao("INGREDIENTE"),
						enviarMalaDireta = new JMenuItemPadrao("ENVIAR MALA DIRETA"),
						gerarRelatorio = new JMenuItemPadrao("GERAR RELATÓRIO");
		ADMButtonsListener list = new ADMButtonsListener();
		adicionarTapioca.addActionListener(list);
		adicionarIngrediente.addActionListener(list);
		enviarMalaDireta.addActionListener(list);
		gerarRelatorio.addActionListener(list);
		
		adicionar.setIcon(ConstantesSwing.ADD);
		enviarMalaDireta.setIcon(ConstantesSwing.EMAIL_2);
		gerarRelatorio.setIcon(ConstantesSwing.RELATORIO);
		
		adicionar.add(adicionarIngrediente);
		adicionar.add(adicionarTapioca);
		opcoes.add(adicionar);
		opcoes.add(enviarMalaDireta);
		opcoes.add(gerarRelatorio);
	}
	private void adicionarClienteButtons(){
		JMenuItemPadrao realizarPedido = new JMenuItemPadrao("REALIZAR PEDIDO");
		realizarPedido.setIcon(ConstantesSwing.CARRINHO);
		realizarPedido.addActionListener(new RealizarPedidoListerner());
		opcoes.add(realizarPedido);
	}
}
