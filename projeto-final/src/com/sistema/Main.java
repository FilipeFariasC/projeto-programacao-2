package com.sistema;

import com.swing.telas.TelaCadastrarAdministrador;
import com.swing.telas.TelaLogin;

public class Main {
	public static void main(String[] args) {
		Central gerenciador = Persistencia.recuperarCentral();
		
		if (gerenciador.getAdministrador() == null){
			new TelaCadastrarAdministrador(gerenciador);
		} else{
			new TelaLogin(gerenciador);
		}
	}
}
