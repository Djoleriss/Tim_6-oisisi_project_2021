package oisisi_project_2021.View;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;

import oisisi_project_2021.Model.Show;

import javax.swing.JScrollPane;

public class Listing extends JFrame {
    private JFrame frame;

    public Listing() {
        this.frame = this;
    }

    public void display(JButton button, ArrayList<Show> shows) {
        String column[]={"name","date","price", "show more"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JTable jTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        shows.forEach(show -> {
            Object[] objs = {show.name, show.date, show.price, "info"};
            tableModel.addRow(objs);
        });
        
        JScrollPane scrollPane = new JScrollPane(jTable);

        Action delete = new AbstractAction()
        {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            ((DefaultTableModel)table.getModel()).removeRow(modelRow);
            
        }
        };
        
        ButtonColumn buttonColumn = new ButtonColumn(jTable, delete, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        getContentPane().add(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to close this window?", "Close Window?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                     button.setEnabled(true);
                     frame.dispose();
                }
            }
        });
    }


}