package com.swing;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelPadrao extends DefaultTableModel {
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
