package com.swing;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class JDateChooserPadrao extends JDateChooser{
	private Period tempo;
	private JTextFieldDateEditor editorData;
	public JDateChooserPadrao() {
		setLocale(new Locale("pt", "BR"));
		setSize(260, 25);
		editarData();
		setVisible(true);
	}

	private void editarData() {
		editorData = (JTextFieldDateEditor) getDateEditor();
		editorData.setHorizontalAlignment(JTextField.CENTER);
		editorData.setFont(ConstantesSwing.TIMES_TEXTO);
		editorData.setOpaque(true);
		editorData.setDateFormatString("dd/MM/yyyy"); 
	}
	public Period getTempo() {
		setTempo();
		return tempo;
	}
	public void setTempo() {
		String data = editorData.getText();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = formato.parse(data);
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(date);
			int dia = calendario.get(Calendar.DATE),
				mes = calendario.get(Calendar.MONTH)+1,
				ano = calendario.get(Calendar.YEAR);
			LocalDate dataPassada = LocalDate.of(ano, mes, dia);
			LocalDate atual = LocalDate.now();
			Period diferenca = Period.between(dataPassada, atual);

			tempo = diferenca;
		} catch (ParseException e) {
			tempo = null;
		}
	}
}
