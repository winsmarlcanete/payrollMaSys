package Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyler {
    public static void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setGridColor(new Color(0, 128, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 128, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}