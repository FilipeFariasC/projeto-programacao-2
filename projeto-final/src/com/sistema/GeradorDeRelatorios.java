package com.sistema;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.pdf.*;
import com.swing.ConstantesSwing;


public class GeradorDeRelatorios {
	private static Font titulo = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD),
						subtitulo = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD),
			 			texto = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	public static void gerarRelatorio(Central central, String path){
		
		if(!path.contains(".")){
			path = path.concat(".pdf");
		} else if (!path.endsWith(".pdf")){
//			throw new ExtensaoNaoSuportadaException();
		}
		Document doc = new Document(PageSize.A4);
		
		try {
			OutputStream saida = new FileOutputStream(path);
			PdfWriter.getInstance(doc, saida);
			
			doc.open();
			Paragraph vendidas = new Paragraph("MAIS VENDIDAS");
			Paragraph receita = new Paragraph("MAIS RENTAVEIS");

			setParagrafo(vendidas, titulo, Element.ALIGN_CENTER);
			setParagrafo(receita, titulo, Element.ALIGN_CENTER);
			
			PdfPTable tabelaMaisVendida = new PdfPTable(4);
			PdfPTable tabelaMaisLucrativa = new PdfPTable(4);
			
			PdfPCell vend = new PdfPCell(vendidas),
					 lucra = new PdfPCell(receita);
			
			vend.setColspan(4);
			vend.setHorizontalAlignment(Element.ALIGN_CENTER);
			lucra.setColspan(4);
			lucra.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			tabelaMaisVendida.addCell(vend);
			adicionarColunasBase(tabelaMaisVendida, subtitulo);
			tabelaMaisLucrativa.addCell(lucra);
			adicionarColunasBase(tabelaMaisLucrativa, subtitulo);
			
			ArrayList<Venda> vendas = central.getVendas();
			ArrayList<Integer> quantidades = new ArrayList<>();
			ArrayList<Double> arrecadacoes = new ArrayList<>();
			ArrayList<String> nomesQuant = new ArrayList<>();
			ArrayList<String> nomesProfit = new ArrayList<>();
			for(Venda venda: vendas){
				quantidades.add(venda.getQuantidadeDeVendas());
				arrecadacoes.add(venda.getArrecadado());
				String nome = venda.getTapioca().getNome();
				nomesQuant.add(nome);
				nomesProfit.add(nome);
			}
			Collections.sort(quantidades, Collections.reverseOrder());
			Collections.sort(arrecadacoes, Collections.reverseOrder());
			
			Integer num = 1;
			for(int c = 0; c < quantidades.size(); c++){
				Integer quant = quantidades.get(c);
				for(Venda venda: vendas){
					Integer amount = venda.getQuantidadeDeVendas();
					Double profit = venda.getArrecadado();
					String nome = venda.getTapioca().getNome();
					
					Paragraph position = new Paragraph(num.toString()),
							  tapioca = new Paragraph(nome),
							  quantidade = new Paragraph(amount.toString()),
							  total = new Paragraph(profit.toString());
					if(quant.equals(amount)){
						int indiceQuant = nomesQuant.indexOf(nome);
						if(indiceQuant > -1){
							tabelaMaisVendida.addCell(position);
							tabelaMaisVendida.addCell(tapioca);
							tabelaMaisVendida.addCell(quantidade);
							tabelaMaisVendida.addCell(total);
							nomesQuant.remove(indiceQuant);
							c = 0;
							num++;
							break;
						}
					}
				}
			}
			num = 1;
			for(int c = 0; c < arrecadacoes.size(); c++){
				Double valor = arrecadacoes.get(c);
				for(Venda venda: vendas){
					Integer amount = venda.getQuantidadeDeVendas();
					Double profit = venda.getArrecadado();
					String nome = venda.getTapioca().getNome();
					
					Paragraph position = new Paragraph(num.toString()),
							  tapioca = new Paragraph(nome),
							  quantidade = new Paragraph(amount.toString()),
							  total = new Paragraph(profit.toString());
					if(valor.equals(profit)){
						int indiceProfit = nomesProfit.indexOf(nome);
						if(indiceProfit > -1){
							tabelaMaisLucrativa.addCell(position);
							tabelaMaisLucrativa.addCell(tapioca);
							tabelaMaisLucrativa.addCell(quantidade);
							tabelaMaisLucrativa.addCell(total);
							nomesProfit.remove(indiceProfit);
							c = 0;
							num++;
							break;
						}
					}
				}
			}
			
			doc.add(tabelaMaisVendida);
			doc.add(new Paragraph("\n"));
			doc.add(tabelaMaisLucrativa);
			doc.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "O ARQUIVO PODE ESTAR SENDO USADO EM OUTRO PROCESSO", "ERRO",  0, ConstantesSwing.ERRO);
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
	}
	private static void adicionarColunasBase(PdfPTable tabela, Font fonte){
		Paragraph pos = new Paragraph("POSIÇÃO"),
				  name = new Paragraph("NOME"),
				  quanti = new Paragraph("QUANTIDADE"),
				  valor = new Paragraph("RECEITA");
		setParagrafo(pos, fonte, Element.ALIGN_CENTER);
		setParagrafo(name, fonte, Element.ALIGN_CENTER);
		setParagrafo(quanti, fonte, Element.ALIGN_CENTER);
		setParagrafo(valor, fonte, Element.ALIGN_CENTER);
		PdfPCell posicao = new PdfPCell(pos),
				 nome = new PdfPCell(name),
				 quant = new PdfPCell(quanti),
				 arrecadado = new PdfPCell(valor);
		setCell(posicao);
		setCell(nome);
		setCell(quant);
		setCell(arrecadado);
		
		
		tabela.addCell(posicao);
		tabela.addCell(nome);
		tabela.addCell(quant);
		tabela.addCell(arrecadado);
	}
	private static void setCell(PdfPCell celula){
		celula.setHorizontalAlignment(Element.ALIGN_CENTER);
		celula.setVerticalAlignment(Element.ALIGN_CENTER);
	}
	private static void setParagrafo(Paragraph paragrafo, Font fonte, int alinhamento){
		paragrafo.setFont(fonte);
		paragrafo.setAlignment(alinhamento);
	}
}
