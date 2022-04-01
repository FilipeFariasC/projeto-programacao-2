package com.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {
	private static XStream xstream = new XStream(new DomDriver());
	private static File arquivo;
	private static final String padrao = "Central.xml";
	
	public static boolean salvarCentral(Central cDI){
		String xml = xstream.toXML(cDI);
		
		try {
			if (!arquivo.getName().equals("Central.xml")){
				arquivo = new File (padrao);
			}
			arquivo.createNewFile();
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	public static Central recuperarCentral(){
		arquivo = new File(padrao);
		try {
			FileInputStream c = new FileInputStream(arquivo);
			return (Central) xstream.fromXML(c);
		} 
		catch (FileNotFoundException e) {
		}
		return new Central();
	}
}
