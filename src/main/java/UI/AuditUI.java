/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BUS.AuditBUS;
import BUS.outPdf;
import DTO.Audit;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import BUS.InfoBalloon;
import com.toedter.calendar.JDateChooser;
import javax.swing.JFrame;

/**
 *
 * @author WindZ
 */
public class AuditUI extends JPanel {
    private AuditBUS auditBUS = new AuditBUS();
    private JTable tbl;
    private JTextField txtAudit_id,txtDatetime,txtMaNV;
    private DefaultTableModel model;
    private Choice yearChoice,monthChoice;
    private int DEFALUT_WIDTH;
    private JTextField txtMaSP;
    private JLabel btnView;
    private JLabel btnBill;
    private JLabel btnConfirm;
    private JLabel btnBack;
    private JDateChooser dateChoice;
    
    public AuditUI(int width)
    {
        DEFALUT_WIDTH = width;
        init();

    }
    public void init()
    {        
        setLayout(null);
        setBackground(null);
        setBounds(new Rectangle(50, 0, this.DEFALUT_WIDTH - 220, 1000));
        Font font0 = new Font("Segoe UI",Font.PLAIN,13);
        Font font1 = new Font("Segoe UI",Font.BOLD,13);
       
/*********************** PHẦN VIEW THÔNG TIN *****************************/
        JPanel itemView = new JPanel(null);
        itemView.setBackground(null);
        itemView.setBounds(new Rectangle(30,40,this.DEFALUT_WIDTH - 200,120));

        JLabel lbMaHD = new JLabel("Audit ID");
        lbMaHD.setFont(font0);
        lbMaHD.setBounds(0,0,60,30);
        txtAudit_id = new JTextField();
        txtAudit_id.setFont(font0);
        txtAudit_id.setBounds(new Rectangle(65,0,80,30));
        //new InfoBalloon(InfoBalloon.errTxt_numberOnly, txtAudit_id, InfoBalloon.filter_numberOnly, InfoBalloon.limit_ID);
        itemView.add(lbMaHD);
        itemView.add(txtAudit_id);

        JLabel lbDatetime = new JLabel("Date/Time");
        lbDatetime.setFont(font0);
        lbDatetime.setBounds(155,0,60,30);
        txtDatetime = new JTextField();
        txtDatetime.setFont(font0);
        txtDatetime.setBounds(new Rectangle(215,0,150,30));
        //new InfoBalloon(InfoBalloon.errTxt_numberOnly, txtDatetime, InfoBalloon.filter_numberOnly, InfoBalloon.limit_ID);
        itemView.add(lbDatetime);
        itemView.add(txtDatetime);

        JLabel lbMaNV = new JLabel("Staff ID");
        lbMaNV.setFont(font0);
        lbMaNV.setBounds(365,0,60,30);
        txtMaNV = new JTextField();
        txtMaNV.setFont(font0);
        txtMaNV.setBounds(new Rectangle(425,0,80,30));
        //new InfoBalloon(InfoBalloon.errTxt_numberOnly, txtMaNV, InfoBalloon.filter_numberOnly, InfoBalloon.limit_ID);
        itemView.add(lbMaNV);
        itemView.add(txtMaNV);

        
        dateChoice = new JDateChooser();
        dateChoice.setBounds(new Rectangle(80,40,375,30));
        dateChoice.setVisible(false);
        itemView.add(dateChoice);


        add(itemView);
        /**************** TẠO CÁC BTN XÓA, SỬA, VIEW, IN BILL ********************/
        
        btnView = new JLabel(new ImageIcon(("./src/main/java/image/btnView.png")));
        btnView.setBounds(new Rectangle(730,0,200,50));
        btnView.setFont(font0);
        btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBill = new JLabel(new ImageIcon(("./src/main/java/image/btnBill.png")));
        btnBill.setBounds(new Rectangle(730,60,200,50));
        btnBill.setFont(font0);
        btnBill.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnConfirm= new JLabel(new ImageIcon("./src/main/java/image/btnConfirm.png"));
        btnConfirm.setVisible(false);
        btnConfirm.setFont(font0);
        btnConfirm.setBounds(new Rectangle(500,0,200,50));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBack = new JLabel(new ImageIcon("./src/main/java/image/btnBack.png"));
        btnBack.setVisible(false);
        btnBack.setFont(font0);
        btnBack.setBounds(new Rectangle(500,60,200,50));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //itemView.add(btnView);
        //itemView.add(btnBill);
        
        //itemView.add(btnConfirm);
        //itemView.add(btnBack);
        setEditable(false);
        
        /*
        // Xem Chi Tiết Audit
        btnView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ChiTietHoaDonAudit chitiet = new ChiTietHoaDonAudit(txtAudit_id.getText());
            }
        });
        */
        
        // In Bill
        btnBill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int audit_id = Integer.parseInt(txtAudit_id.getText());
                String datetime = txtDatetime.getText();
                Audit audit = new Audit(audit_id, datetime);
                outPdf bill = new outPdf(audit);
                bill.print();
            }
        });
        
        /*************************************************************************/
/****************** TẠO MODEL VÀ HEADER *********************************************/
        Vector header = new Vector();
        header.add("Audit ID");
        header.add("Datetime");
        header.add("Staff ID");
        model = new DefaultTableModel(header, 3)
        {
            public Class getColumnClass(int column)
            {
                switch(column){
                    case 1:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }              
        };
        tbl = new JTable(model);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
        tbl.setRowSorter(rowSorter);
        list(); //Đọc từ database lên table 
        
/*********************************************************/
        
/**************** TẠO TABLE ************************************************************/

        // Chỉnh width các cột 
        tbl.getColumnModel().getColumn(0).setPreferredWidth(40);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(40);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(40);
        
        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        tbl.getColumnModel().getColumn(0).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
        
        // Custom table
        tbl.setFocusable(false);
        tbl.setIntercellSpacing(new Dimension(0,0));     
        tbl.getTableHeader().setFont(font1);
        tbl.setRowHeight(30);
        tbl.setShowVerticalLines(false);              
        tbl.getTableHeader().setOpaque(false);
        tbl.setFillsViewportHeight(true);
        tbl.getTableHeader().setBackground(new Color(57, 127, 232));
        tbl.getTableHeader().setForeground(Color.WHITE);
        tbl.setSelectionBackground(new Color(232,57,99));          
        
        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbl);
        scroll.setBounds(new Rectangle(30, 270, this.DEFALUT_WIDTH - 400 , 400));
        scroll.setBackground(null);
        
        add(scroll);
/*****************************************************************************************/

        
        tbl.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent e)
             {
                int i = tbl.getSelectedRow();
                if(tbl.getRowSorter() != null)
                {
                    try {
                        i = tbl.getRowSorter().convertRowIndexToModel(i);
                    } catch (Exception ex) {
                        
                    }
                }
                txtAudit_id.setText(tbl.getModel().getValueAt(i, 0).toString());
                try
                {
                    txtDatetime.setText(tbl.getModel().getValueAt(i, 1).toString());
                }
                catch(NullPointerException ex)
                {
                    txtDatetime.setText("");
                }
                txtMaNV.setText(tbl.getModel().getValueAt(i, 2).toString()); 
             }
        });
/*********************** SORT TABLE *****************************/
        JPanel sort = new JPanel(null);
        sort.setBackground(null);
        sort.setBounds(30,170,this.DEFALUT_WIDTH - 400,140);
        
        JLabel sortTitle = new JLabel("------------------------------------------------------------------------------------ BỘ LỌC ------------------------------------------------------------------------------------",JLabel.CENTER); // Mỗi bên 84 dấu ( - )
        sortTitle.setFont(font1);
        sortTitle.setBounds(new Rectangle(0,0,this.DEFALUT_WIDTH - 400,30));
        sort.add(sortTitle);
        
        /******** SORT THỜI GIAN **************/
        JLabel sortTime = new JLabel("Thời gian :");
        sortTime.setFont(font0);
        sortTime.setBounds(0,40,80,30);
        sort.add(sortTime);
        // Choice Tháng
        int month = Calendar.getInstance().get(Calendar.MONTH);// Lấy tháng hiện tại
        monthChoice = new Choice();
        monthChoice.setFont(font0);
        monthChoice.add("Không");
        for(int i = 1 ; i <= 12 ; i++ )
        {
            monthChoice.add("Tháng "+i);
        }
        monthChoice.select(0);
        monthChoice.setBounds(new Rectangle(80,42,80,40));
        sort.add(monthChoice);
        // Choice Năm
        int year = Calendar.getInstance().get(Calendar.YEAR);//Lấy năm hiện tại
        yearChoice = new Choice();
        yearChoice.setFont(font0);
        yearChoice.add("Không");
        for(int i = year ; i >= 1999 ; i--)
        {
            yearChoice.add(String.valueOf(i));
        }
        yearChoice.select(0);
        yearChoice.setBounds(new Rectangle(170,42,80,40));
        sort.add(yearChoice);
        /*************************************/
        
        JSeparator sepPrice = new JSeparator(JSeparator.HORIZONTAL);
        sepPrice.setBounds(490, 56, 10, 6);
        sort.add(sepPrice);
        
//        /******************************************/
        JLabel btnSearch = new JLabel(new ImageIcon("./src/main/java/image/btnSearch_45px.png"));
        btnSearch.setBounds(new Rectangle(840,20,63,63));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e)
           {
               search();
           }
        });
        sort.add(btnSearch);
        btnSearch.setFont(font0);
        add(sort);

/****************************************************************/
        
    }
    public void search()
    {
        int mm = monthChoice.getSelectedIndex()-1;
        int yyy ;
        try {
            yyy = Integer.parseInt(yearChoice.getSelectedItem());
        } catch (NumberFormatException ex) {
            yyy = 0;
        }
        
        outModel(model,auditBUS.search(mm, yyy));
    }
    
    public void cleanView()
    {
        txtAudit_id.setText("");
        txtDatetime.setText("");
        txtMaNV.setText("");
    }
    public void setEditable(boolean flag) {
        txtAudit_id.setEditable(false);
        txtDatetime.setEditable(flag);
        txtMaNV.setEditable(flag);
    }
    public void outModel(DefaultTableModel model , ArrayList<Audit> hd) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for(Audit h:hd)
        {
            data = new Vector();
            data.add(h.getAudit_id());
            data.add(h.getDatetime());
            data.add(String.valueOf(MainUI.userID));
            model.addRow(data);
        }
        tbl.setModel(model);
    }
    public void list() { // Chép ArrayList lên table
        if (auditBUS.getList()== null) auditBUS.list();
        ArrayList<Audit> hd = auditBUS.getList();
        model.setRowCount(0);
        outModel(model,hd);
    }
    public void isEdit(boolean flag) {
        btnBill.setVisible(!flag);
        btnView.setVisible(!flag);
        tbl.setEnabled(!flag);
        
        btnConfirm.setVisible(flag);
        btnBack.setVisible(flag);
        dateChoice.setVisible(flag);
        
    }
    public static void main(String[] args){
        JFrame f=new JFrame();
        AuditUI audit=new AuditUI(700);
        
        f.add(audit);
        f.setSize(700,700);
        f.setVisible(true);
    }
}
