package oisisi_project_2021.View;

import java.awt.BorderLayout;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import oisisi_project_2021.Model.Show;

public class SearchShow extends JFrame {

    private JFrame frame;
    private static final int JEXIT_ON_CLOSE = 0;
    private String[] column = {"name", "date", "price"};


    public SearchShow() {}

    public void display(JButton button, ArrayList<Show> shows) {

        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JTable jTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        shows.forEach(show -> {
            Object[] objs = {show.name, show.date, show.price};
            tableModel.addRow(objs);
        });
    
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(jTable.getModel());
    
        JTextField jtfFilter = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Specify a word to match:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);

        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

        });

        pack();
        setDefaultCloseOperation(JEXIT_ON_CLOSE);

        setBounds(400, 400, 400, 400);

        setLocationRelativeTo(null);
        setVisible(true);

        jTable.setRowSorter(rowSorter);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) { 
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to close this window?", "Close Window?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                     button.setEnabled(true);
                     dispose();
                }
            }
        });

    }

    

}