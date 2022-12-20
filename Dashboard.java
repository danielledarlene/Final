package databasemanipulation;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Dashboard implements ActionListener{
    String db_username = "root";
    String db_password = "";
    String db_url = "jdbc:mysql://localhost:3306/dummy";
    Connection conn;
    
    ResultSet rs = null;
    
    JFrame frame;
    JLabel lbl_id, lbl_name, lbl_ic, lbl_icon, lbl_fetch;
    JTextField txt_id, txt_name, txt_ic, txt_fetch;
    JButton btn_insert, btn_update, btn_delete, btn_fetch, btn_cancel;
    JPanel pnl_north, pnl_west, pnl_east;
    JPanel pnl_in_box_1, pnl_in_box_2, pnl_in_box_3, pnl_in_box_btn;
    JPanel pnl_fetch;
    ImageIcon logo_1;
    JSeparator sep_1;
    JScrollPane sp;
    JTable table;
    TitledBorder border_1, border_2, border_3;
    
    Dashboard(){
        frame  = new JFrame();
        
        border_1 = BorderFactory.createTitledBorder("Data List");
        border_2 = BorderFactory.createTitledBorder("Fetch Data");
        border_3 = BorderFactory.createTitledBorder("Personal Data");
        /////////////////////////////PANEL NORTH
        pnl_north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logo_1 = new ImageIcon("C:\\Users\\prude\\OneDrive\\Documents\\NetBeansProjects\\LabActivityTopic4\\src\\labactivitytopic4\\graduated.png");
        lbl_icon = new JLabel(logo_1);
        pnl_north.add(lbl_icon);
        
        pnl_west = new JPanel();
        pnl_west.setLayout(new BoxLayout(pnl_west, BoxLayout.Y_AXIS));
        
        pnl_in_box_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl_id = new JLabel("ID : ");
        lbl_id.setPreferredSize(new Dimension(50, 25));
        txt_id = new JTextField(20);
        txt_id.setEditable(false);
        pnl_in_box_1.add(lbl_id);
        pnl_in_box_1.add(txt_id);
        
        pnl_in_box_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl_ic = new JLabel("IC : ");
        lbl_ic.setPreferredSize(new Dimension(50, 20));
        txt_ic = new JTextField(20);
        pnl_in_box_2.add(lbl_ic);
        pnl_in_box_2.add(txt_ic);
        
        pnl_in_box_3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl_name = new JLabel("NAME : ");
        lbl_name.setPreferredSize(new Dimension(50, 20));
        txt_name = new JTextField(20);
        pnl_in_box_3.add(lbl_name);
        pnl_in_box_3.add(txt_name);
        
        pnl_in_box_btn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btn_insert = new JButton("Insert");
        btn_insert.addActionListener(this);
        btn_update = new JButton("Update");
        btn_update.addActionListener(this);
        btn_delete = new JButton("Delete");
        btn_delete.addActionListener(this);
        btn_cancel = new JButton("Cancel");
        btn_cancel.addActionListener(this);
        pnl_in_box_btn.add(btn_insert);
        pnl_in_box_btn.add(btn_update);
        pnl_in_box_btn.add(btn_delete);
        pnl_in_box_btn.add(btn_cancel);
        
        pnl_fetch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lbl_fetch = new JLabel("ID");
        txt_fetch = new JTextField(5);
        btn_fetch = new JButton("Fetch");
        btn_fetch.addActionListener(this);
        pnl_fetch.add(lbl_fetch);
        pnl_fetch.add(txt_fetch);
        pnl_fetch.add(btn_fetch);
        pnl_fetch.setBorder(border_2);
        //sep_1 = new JSeparator();
        
        pnl_west.add(pnl_fetch);
        //pnl_west.add(sep_1);
        pnl_west.add(pnl_in_box_1);
        pnl_west.add(pnl_in_box_2);
        pnl_west.add(pnl_in_box_3);
        pnl_west.add(pnl_in_box_btn);
        /////////////////////////////END OF WEST
        
        
        /////////////////////////////PANEL EAST
        pnl_east = new JPanel();
        try{
            table = new JTable();
            draw_table();
            sp = new JScrollPane(table);
            sp.setPreferredSize(new Dimension(600,250));
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        }catch(Exception ex){
            
        }
        pnl_east.add(sp);
        pnl_east.setBorder(border_1);
        
        frame.add(pnl_north, BorderLayout.NORTH);
        frame.add(pnl_west, BorderLayout.WEST);
        frame.add(pnl_east, BorderLayout.EAST);
        
        hide_delete_update();
        
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public  DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        String[] column_title = {"No.", "NRIC No.", "Student Name", "Mazda"};
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("ID");
        columnNames.add("IC");
        columnNames.add("Name");


        int columnCount = metaData.getColumnCount();

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
    public void fetch_all(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            String sql = "SELECT * FROM students";
            PreparedStatement statement = conn.prepareStatement(sql);
            
            rs = statement.executeQuery();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error : " + ex);
        }
    }
    
    public void draw_table() throws SQLException{
        fetch_all();
        table.setModel(buildTableModel(rs));
        
    }
    
    public static void main(String[] args) {
       Dashboard apps = new Dashboard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn_insert){
            //
            insert_data();
        }
        if(e.getSource()==btn_fetch){
            set_data();
        }
        if(e.getSource()==btn_update){
            update();
        }
        if(e.getSource()==btn_delete){
            delete();
        }
        if(e.getSource()==btn_cancel){
            display_insert();
            hide_delete_update();
            clear_form();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    public void delete(){
        try{
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            
            //database operation
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txt_id.getText()));
            
            int delete;
            delete = stmt.executeUpdate();
            
            if(delete > 0){
                //success
                JOptionPane.showMessageDialog(frame, "Delete Operation Success!");
                clear_form();
            }else{
                //fail
                JOptionPane.showMessageDialog(frame, "Delete Operation Failed!");
            }
            
            draw_table();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, " Error : " + ex);
        }
    }
    
    public void update(){
        try{
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            
            //database operation
            String sql = "UPDATE students SET ic = ?, name = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txt_ic.getText());
            stmt.setString(2, txt_name.getText());
            stmt.setInt(3, Integer.parseInt(txt_id.getText()));
            
            int update;
            update = stmt.executeUpdate();
            
            if(update > 0){
                //success
                JOptionPane.showMessageDialog(frame, "Update Operation Success!");
                clear_form();
            }else{
                //fail
                JOptionPane.showMessageDialog(frame, "Update Operation Failed!");
            }
            
            draw_table();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, " Error : " + ex);
        }
    }
    
    public void set_data(){
        //fetch single data
        if(validation_fetch() == false){
            int id;
        //set uid var
        id = Integer.parseInt(txt_fetch.getText());
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, id);
            //set data from database into result set
            rs = statement.executeQuery();
            
            //reading the data from result set to form
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                
                txt_id.setText(rs.getString("id"));
                txt_ic.setText(rs.getString("ic"));
                txt_name.setText(rs.getString("name"));
            }
                JOptionPane.showMessageDialog(frame, "Data Fetched!");
                display_delete_update();
                hide_insert();
            }else{
                JOptionPane.showMessageDialog(frame, "There is no data associate with the ID!");
            }
            
        } catch (Exception ex){
            JOptionPane.showMessageDialog(frame, " Error : " + ex);
        }
        
        }else{
            JOptionPane.showMessageDialog(frame, " Please enter ID");
        }
        
    }
    
    public void insert_data(){
        if(validation_form() == false){
            try{
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            
            //database operation
            String sql_insert = "INSERT INTO students (name, ic) VALUES(?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql_insert);
            stmt.setString(1, txt_name.getText());
            stmt.setString(2, txt_ic.getText());
            
            int insert;
            insert = stmt.executeUpdate();
            
            if(insert > 0){
                //success
                JOptionPane.showMessageDialog(frame, "Insert Operation Success!");
            }else{
                //fail
                JOptionPane.showMessageDialog(frame, "Insert Operation Failed!");
            }
            
            draw_table();
            clear_form();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error : " + ex);
        }
            
        }else{
            JOptionPane.showMessageDialog(frame, "Please enter form data");
        }
        
    }
    
    public void clear_form(){
        txt_ic.setText("");
        txt_fetch.setText("");
        txt_id.setText("");
        txt_name.setText("");
    }
    
    public void hide_delete_update(){
        btn_update.setVisible(false);
        btn_delete.setVisible(false);
        btn_cancel.setVisible(false);
        frame.pack();
    }
    
    public void display_delete_update(){
        btn_update.setVisible(true);
        btn_delete.setVisible(true);
        btn_cancel.setVisible(true);
        frame.pack();
    }
    
    public void hide_insert(){
        btn_insert.setVisible(false);
        
    }
    
    public void display_insert(){
        btn_insert.setVisible(true);
        
    }
    
    public boolean validation_fetch(){
        boolean is_empty = true;
        String param1 = txt_fetch.getText();
        if (param1 == "" || param1.length()== 0){
            is_empty = true;
            
        }else{
            is_empty = false;
    }
        return is_empty;
    }
    
    public boolean validation_form(){
        boolean is_empty = true;
 
        if (txt_ic.getText().length() == 0 || txt_name.getText().length() == 0){
            is_empty = true;
            
        }else{
            is_empty = false;
    }
        return is_empty;
    }
}