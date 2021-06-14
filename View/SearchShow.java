package View;

import Model.My_Connection;
import Model.Show;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                displayPriceSearch(shows, button, 0);
                break;
            case 3:
                displayDateSearch(shows, button);
                break;
            default:
                break;
        }

    }

    private void displayDateSearch(ArrayList<Show> shows, JButton button) {
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        shows = this.getShows();
        
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

        JPanel panel = new JPanel();

        UtilDateModel model = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        panel.add(new JLabel("Specify a date range:"),
                BorderLayout.WEST);
        panel.add(datePicker, BorderLayout.EAST);
        panel.add(datePicker2, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");

        panel.add(searchButton);

        JScrollPane jsp = new JScrollPane(jTable);

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date startDate = (Date) datePicker.getModel().getValue();
                Date endDate = (Date) datePicker2.getModel().getValue();
                int currentYear = new Date().getYear();

                if(startDate.getYear() != currentYear || endDate.getYear() != currentYear) {
                    JOptionPane.showMessageDialog(frame, "You must choose dates in the current year ("
                            + (currentYear +1900) + ")!");
                }
                else {
                    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                    filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate));
                    filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate));

                    TableRowSorter<TableModel> tr
                            = new TableRowSorter<>(jTable.getModel());
                    jTable.setRowSorter(tr);
                    RowFilter<Object, Object> rf = RowFilter.andFilter(filters);
                    tr.setRowFilter(rf);
                }
            }

        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(jsp, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JEXIT_ON_CLOSE);

        setBounds(600, 600, 800, 600);

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
                    dispose();
                }
            }
        });
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    public void displayPriceSearch(ArrayList<Show> shows, JButton button, int type) {
    	
        if(type == 0) shows = this.getShows();
    	
        final ArrayList<Show> showTemp = shows;
        
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
                    
                    System.out.println(firstPrice);
                    System.out.println(secondPrice);
                if(Double.parseDouble(firstPrice) > Double.parseDouble(secondPrice)) {
                    JOptionPane.showMessageDialog(frame, "First value needs to be smaller then second value!");
                } else {
                	ArrayList<Show>  newShows =  (ArrayList<Show>) showTemp.stream().filter(show -> show.price > Double.parseDouble(firstPrice) && show.price < Double.parseDouble(secondPrice)).collect(Collectors.toList());
                    remove(jsp);
                    displayPriceSearch(newShows, button, 1);
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
        
        shows = this.getShows();

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
    
    private ArrayList<Show> getShows() {
  	  String query = "SELECT * FROM `show`";
        Statement st;
        ResultSet rs;
        ArrayList<Show> shows = new ArrayList<Show>();
  	  
        try {
            st = My_Connection.getInstance().createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
               Show s = new Show();
          	 s.name = rs.getString(2);
          	 s.date = new Date();
          	 s.price = Float.parseFloat(rs.getString(4));
          	 s.description = rs.getString(5);
          	 s.id = rs.getLong(1);
          	 shows.add(s);
            }
            return shows;
        } catch (SQLException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shows;
  }

}
