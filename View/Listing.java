package View;

import Model.My_Connection;
import Model.Show;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;

public class Listing extends JFrame {
    private JFrame frame;

    public Listing() {
        this.frame = this;
    }

    public void display(JButton button) {
    	ArrayList<Show> shows = this.getShows();
        String column[]={"id", "name","date","price","description","show more"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JTable jTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        shows.forEach(show -> {
            Object[] objs = {show.id, show.name, show.date, show.price, show.description, "info"};
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

        Action showInfoAction = new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int row = table.getSelectedRow();

                Long id = (Long) table.getModel().getValueAt(row, 0);
                String showName = table.getModel().getValueAt(row, 1).toString();
                Date showDate = (Date) table.getModel().getValueAt(row, 2);
                float ticketPrice = (float) table.getModel().getValueAt(row, 3);
                String showDescription = table.getModel().getValueAt(row, 4).toString();

                Show show = new Show(id, showName, showDescription, showDate, ticketPrice);

                new ShowInfo(show, button);

            }
        };
        
        ButtonColumn buttonColumn = new ButtonColumn(jTable, showInfoAction, 5);
        //buttonColumn.setMnemonic(KeyEvent.VK_D);

        getContentPane().add(scrollPane, 0);
        //this.add(addShow);
        
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
                     dispose();
                }
            }
        });
       
        
     
    }


    private void disposeFrame() {
    	this.dispose();
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
          } catch (SQLException ex) {
              Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
          }
          return shows;
    }

}
