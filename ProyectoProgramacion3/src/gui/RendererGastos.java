package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererGastos implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(column == 0) {
			//JLabel l = new JLabel(value.toString());
			JLabel l = new JLabel();
			l.setOpaque(true);
			l.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			l.setBackground(Color.yellow);
			l.setBorder(BorderFactory.createBevelBorder(0, Color.black, Color.black));
			return l;
		}else {
			JLabel l = new JLabel();
			return l;
		}
	}
}
