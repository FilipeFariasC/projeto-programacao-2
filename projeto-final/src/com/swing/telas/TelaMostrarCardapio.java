package com.swing.telas;

import java.awt.BorderLayout;
import com.swing.JFramePadrao;
import com.swing.paineis.ListarTapiocaCardapioPanel;;

public class TelaMostrarCardapio extends JFramePadrao {
	public TelaMostrarCardapio(ListarTapiocaCardapioPanel panel) {
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
