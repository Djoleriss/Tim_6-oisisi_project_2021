package oisisi_project_2021.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.NumberFormatter;

import java.awt.event.*;

import oisisi_project_2021.Model.Show;

public class SearchShow extends JFrame {

    private JFrame frame;
    private static final int JEXIT_ON_CLOSE = 0;
    private String[] column = {"name", "date", "price"};
    private String firstPrice = "";
    private String secondPrice = "";


    public SearchShow() {}

    public void display(JButton button, ArrayList<Show> shows, int typeOfDisplay) {

        switch(typeOfDisplay) {
            case 1:
                displayNameSearch(shows, button);
                break;
            case 2:
                displayPriceSearch(shows, button);
                break;
            case 3:
                break;
            default:
                break;
        }

    }

    public void displayPriceSearch(ArrayList<Show> shows, JButton button) {
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
    
        
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        JFormattedTextField jtfFilter = new JFormattedTextField(formatter);
        JFormattedTextField jtfFilter2 = new JFormattedTextField(formatter);

        jtfFilter.setPreferredSize(new Dimension(100, 20));
        jtfFilter2.setPreferredSize(new Dimension(100, 20));

        jtfFilter.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) { 
                Pattern pattern = Pattern.compile("^[0-9, ]+$");
                char command = e.getKeyChar();
                Matcher m = pattern.matcher(String.valueOf(command));

                if(!m.matches()) {
                    System.out.println("Nope.");
                    return;
                }

                firstPrice += String.valueOf(command);
            }

            public void keyReleased(KeyEvent e) { /* ... */ }

            public void keyTyped(KeyEvent e) { /* ... */ }
        });

        jtfFilter2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) { 
                Pattern pattern = Pattern.compile("^[0-9, ]+$");
                char command = e.getKeyChar();
                Matcher m = pattern.matcher(String.valueOf(command));

                if(!m.matches()) {
                    System.out.println("Nope.");
                    return;
                }

                secondPrice += String.valueOf(command);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

   
            
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Specify a word to match:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.EAST);
        panel.add(jtfFilter2, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");

        panel.add(searchButton);

        JScrollPane jsp = new JScrollPane(jTable);

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    firstPrice = firstPrice == "" ? String.valueOf(0) : firstPrice;
                    secondPrice = secondPrice == "" ? String.valueOf(0) : secondPrice;
                if(Double.parseDouble(firstPrice) > Double.parseDouble(secondPrice)) {
                    JOptionPane.showMessageDialog(frame, "First value needs to be larger then second value!");
                } else {
                    ArrayList<Show>  newShows =  (ArrayList<Show>) shows.stream().filter(show -> show.price > Double.parseDouble(firstPrice) && show.price < Double.parseDouble(secondPrice)).collect(Collectors.toList());
                    remove(jsp);
                    displayPriceSearch(newShows, button);
                }
            }
            
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(jsp, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JEXIT_ON_CLOSE);

        setBounds(600, 600, 600, 600);

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


    public void displayNameSearch(ArrayList<Show> shows, JButton button) {
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

        setBounds(600, 600, 600, 600);

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