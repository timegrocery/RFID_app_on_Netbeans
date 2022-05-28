/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BUS.AuditBUS;
import BUS.InfoBalloon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.ProductBUS;
import DTO.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author WindZ
 */
public class ProductUI extends JPanel implements KeyListener {
    private ProductBUS productBUS = new ProductBUS();
    AuditBUS auditBUS = new AuditBUS();
    private int lastAuditID;
    private JTable tbl;
    private BufferedImage i = null;//Hình ảnh chọn từ file
    private JLabel img;
    private String imgName = "null";
    private JTextField txtProduct_id, txtName, txtStock, txtCount, txtColor, txtGap, txtImage, txtSearch;
    private DefaultTableModel model;
    private int DEFALUT_WIDTH;
    private JTextField sortMaSP;

    public ProductUI(int width) {
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
/****************************** PHẦN HIỂN THỊ THÔNG TIN ******************************************/

        JPanel ItemView = new JPanel(null);
        ItemView.setBounds(new Rectangle(30, 20, this.DEFALUT_WIDTH - 220 , 310));
        ItemView.setBackground(Color.WHITE);

        /******** Tao Cac Label & TextField ************************/
        JLabel lbProduct_id = new JLabel("Product ID");
        lbProduct_id.setBounds(new Rectangle(250,0,200,30));
        lbProduct_id.setFont(font0);
        txtProduct_id = new JTextField("");
        txtProduct_id.setBounds(new Rectangle(350,0,220,30));
        txtProduct_id.setFont(font0);
        txtProduct_id.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_numberOnly, txtProduct_id, InfoBalloon.filter_numberOnly, InfoBalloon.limit_ID);


        JLabel lbName = new JLabel("Name");
        lbName.setBounds(new Rectangle(250,80,200,30));
        lbName.setFont(font0);
        txtName = new JTextField("");
        txtName.setBounds(new Rectangle(350,80,220,30));
        txtName.setFont(font0);
        txtName.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_numberOnly, txtName, InfoBalloon.filter_numberOnly, InfoBalloon.limit_price);

        JLabel lbStock = new JLabel("Stock");
        lbStock.setBounds(new Rectangle(250,120,200,30));
        lbStock.setFont(font0);
        txtStock = new JTextField("");
        txtStock.setBounds(new Rectangle(350,120,220,30));
        txtStock.setFont(font0);
        txtStock.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_invalidName, txtStock, InfoBalloon.filter_all, InfoBalloon.limit_name);

        JLabel lbCount = new JLabel("Count");
        lbCount.setBounds(new Rectangle(250,160,200,30));
        lbCount.setFont(font0);
        txtCount = new JTextField("");
        txtCount.setBounds(new Rectangle(350,160,220,30));
        txtCount.setFont(font0);
        txtCount.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_invalidName, txtCount, InfoBalloon.filter_all, InfoBalloon.limit_name);

        JLabel lbColor = new JLabel("Color");
        lbColor.setBounds(new Rectangle(250,200,200,30));
        lbColor.setFont(font0);
        txtColor = new JTextField("");
        txtColor.setBounds(new Rectangle(350,200,220,30));
        txtColor.setFont(font0);
        txtColor.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_invalidName, txtColor, InfoBalloon.filter_all, InfoBalloon.limit_name);

        JLabel lbGap = new JLabel("Gap");
        lbGap.setBounds(new Rectangle(250,240,200,30));
        lbGap.setFont(font0);
        txtGap = new JTextField("");
        txtGap.setBounds(new Rectangle(350,240,220,30));
        txtGap.setFont(font0);
        txtGap.setEditable(false);
        //new InfoBalloon(InfoBalloon.errTxt_invalidName, txtGap, InfoBalloon.filter_all, InfoBalloon.limit_name);
        
        JLabel lbImage = new JLabel("Image");
        lbImage.setBounds(new Rectangle(250,280,200,30));
        lbImage.setFont(font0);
        txtImage = new JTextField("");
        txtImage.setBounds(new Rectangle(350,280,220,30));
        txtImage.setFont(font0);
        txtImage.setEditable(false);

        img = new JLabel("Image");
        img.setBorder(createLineBorder(Color.BLACK));
        img.setBounds(new Rectangle(0,0,200,230));

        // THÊM VÀO PHẦN HIỂN THỊ
        ItemView.add(img);
        ItemView.add(lbProduct_id);
        ItemView.add(txtProduct_id);
        ItemView.add(lbName);
        ItemView.add(txtName);
        ItemView.add(lbStock);
        ItemView.add(txtStock);
        ItemView.add(lbCount);
        ItemView.add(txtCount);
        ItemView.add(lbColor);
        ItemView.add(txtColor);
        ItemView.add(lbGap);
        ItemView.add(txtGap);
        ItemView.add(lbImage);
        ItemView.add(txtImage);
//        ItemView.add(txtNCC);
        /************************************************************/

        /**************** TẠO CÁC BTN THÊM ,XÓA, SỬA ********************/
        JLabel btnAdd = new JLabel(new ImageIcon("./src/main/java/image/btnAdd.png"));
        btnAdd.setBounds(new Rectangle(620,0,200,50));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JLabel btnEdit = new JLabel(new ImageIcon("./src/main/java/image/btnEdit.png"));
        btnEdit.setBounds(new Rectangle(620,70,200,50));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JLabel btnDelete = new JLabel(new ImageIcon("./src/main/java/image/btnDelete.png"));
        btnDelete.setBounds(new Rectangle(620,140,200,50));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //ItemView.add(btnAdd);
        //ItemView.add(btnEdit);
        //ItemView.add(btnDelete);

        JLabel btnConfirm= new JLabel(new ImageIcon("./src/main/java/image/btnConfirm.png"));
        btnConfirm.setVisible(false);
        btnConfirm.setBounds(new Rectangle(620,0,200,50));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel btnBack = new JLabel(new ImageIcon("./src/main/java/image/btnBack.png"));
        btnBack.setVisible(false);
        btnBack.setBounds(new Rectangle(620,70,200,50));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel btnFile = new JLabel(new ImageIcon("./src/main/java/image/btnFile.png"));
        btnFile.setVisible(false);
        btnFile.setBounds(new Rectangle(620,140,200,50));
        btnFile.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel btnXuatExcel = new JLabel(new ImageIcon("./src/main/java/image/btnXuatExcel.png"));
        btnXuatExcel.setBounds(new Rectangle(820,0,200,50));
        btnXuatExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));


        //ItemView.add(btnConfirm);
        //ItemView.add(btnBack);
        ItemView.add(btnFile);
        ItemView.add(btnXuatExcel);


        // MouseClick btnDelete
        btnDelete.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa","Alert",JOptionPane.YES_NO_OPTION);
                if(i == 0) {
                    productBUS.deleteProduct(Integer.parseInt(txtProduct_id.getText()));
                    cleanView();
                    editableStatus(false);
                    tbl.clearSelection();
                    outModel(model, productBUS.getList());
                }
            }
        });

        // MouseClick btnEdit
        btnEdit.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                if(txtProduct_id.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa !!!");
                    return;
                }

                editableStatus(true);

                btnAdd.setVisible(false);
                btnEdit.setVisible(false);
                btnDelete.setVisible(false);

                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                btnFile.setVisible(true);

//              tbl.clearSelection();
                tbl.setEnabled(false);
            }
        });

        // MouseClick btnFile
        btnFile.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG images", "jpg", "png");
                fc.setFileFilter(filter);
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    try {
                        File file = fc.getSelectedFile(); //Lấy URL hình
                        i = ImageIO.read(file); // Lấy hình
                        imgName = txtProduct_id.getText().concat(".jpg"); //Tên hình

                        // Thay đổi hình hiển thị
                        img.setText("");
                        img.setIcon(new ImageIcon(i.getScaledInstance(200, 230, Image.SCALE_DEFAULT)));

                        revalidate();
                        repaint();
                    } catch (IOException ex) {
                        Logger.getLogger(ProductUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        //MouseClick btnBack
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                cleanView();

                editableStatus(false);
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);

                btnConfirm.setVisible(false);
                btnBack.setVisible(false);
                btnFile.setVisible(false);

                tbl.setEnabled(true);
            }
        });

        btnXuatExcel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                productBUS.ExportExcelDatabase(ProductBUS.productList, AuditBUS.getLastAudit());
                JOptionPane.showMessageDialog(null, "Xuất file excel thành công");
            }
        });

        /****************************************************************/

/************************* PHẦN TABLE *************************************/
/************** TẠO MODEL VÀ HEADER *********************/
        Vector header = new Vector();
        header.add("Product ID");
        header.add("Name");
        header.add("Stock");
        header.add("Count");
        header.add("Color");
        header.add("Gap");
        header.add("IMG");
        model = new DefaultTableModel(header,0) {
            public Class getColumnClass(int column) {
                switch(column) {
                    case 0:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 5:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        tbl = new JTable(model);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
        tbl.setRowSorter(rowSorter);
        listSP(); //Đọc từ database lên table
/*********************************************************/

/**************** TẠO TABLE ************************************************************/

        // Chỉnh width các cột
        tbl.getColumnModel().getColumn(0).setPreferredWidth(20);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(80);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(20);
        tbl.getColumnModel().getColumn(3).setPreferredWidth(20);
        tbl.getColumnModel().getColumn(4).setPreferredWidth(20);
        tbl.getColumnModel().getColumn(5).setPreferredWidth(20);
        tbl.getColumnModel().getColumn(6).setPreferredWidth(20);

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        tbl.getColumnModel().getColumn(0).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(3).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(4).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(5).setCellRenderer(leftAlign);
        tbl.getColumnModel().getColumn(6).setCellRenderer(leftAlign);

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
        scroll.setBounds(new Rectangle(30, 450, this.DEFALUT_WIDTH - 400 , 200));
        scroll.setBackground(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(5,100));
        add(scroll);
        add(ItemView);
/*****************************************************************************************/


        tbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                int i = tbl.getSelectedRow();
                if(tbl.getRowSorter() != null) {
                    try {
                        i = tbl.getRowSorter().convertRowIndexToModel(i);
                    } catch (Exception ex) {

                    }
                }
                try {
                    imgName = tbl.getModel().getValueAt(i, 6).toString();
                } catch (Exception ex) {

                }
                Image newImage ;
                try {
                    newImage = new ImageIcon("./src/main/java/image/SanPham/" + imgName).getImage().getScaledInstance(200, 230, Image.SCALE_DEFAULT);
                } catch(NullPointerException E) {
                    newImage = new ImageIcon("./src/main/java/image/SanPham/NoImage.jpg").getImage().getScaledInstance(200, 230, Image.SCALE_DEFAULT);
                }
                try {
                    txtProduct_id.setText(tbl.getModel().getValueAt(i, 0).toString());
                    txtName.setText(tbl.getModel().getValueAt(i, 1).toString());
                    txtStock.setText(tbl.getModel().getValueAt(i, 2).toString());
                    txtCount.setText(tbl.getModel().getValueAt(i, 3).toString());
                    txtColor.setText(tbl.getModel().getValueAt(i, 4).toString());
                    txtGap.setText(tbl.getModel().getValueAt(i, 5).toString());
                    txtImage.setText(tbl.getModel().getValueAt(i, 6).toString());

                    img.setText("");
                    img.setIcon(new ImageIcon(newImage));
                } catch (Exception ex) {

                }
            }
        });
/********************* THANH SEARCH ***********************************************/

//         Tạo Search Box
        JPanel searchBox = new JPanel(null);
        searchBox.setBackground(null);
        searchBox.setBounds(new Rectangle(620,200,250, 30));
        searchBox.setBorder(createLineBorder(Color.BLACK)); //Chỉnh viền

        //Phần TextField
        txtSearch = new JTextField();
        txtSearch.setBounds(new Rectangle(5,0,200,30));
        txtSearch.setBorder(null);
        txtSearch.setOpaque(false);
        txtSearch.setFont(new Font("Segoe UI",Font.PLAIN,15));

        // Custem Icon search
        JLabel searchIcon = new JLabel(new ImageIcon("./src/main/java/image/search_25px.png"));
        searchIcon.setBounds(new Rectangle(200,-9,50,50));
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add tất cả vào search box
        searchBox.add(searchIcon);
        searchBox.add(txtSearch);

        //bắt sự kiện Focus vào search box
        txtSearch.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e)
            {
                searchIcon.setIcon(new ImageIcon("./src/main/java/image/search_25px_focus.png")); //Đổi màu icon
                searchBox.setBorder(createLineBorder(new Color(232,57,99))); // Đổi màu viền
            }
            public void focusLost(FocusEvent e) //Trờ về như cũ
            {
                searchIcon.setIcon(new ImageIcon("./src/main/java/image/search_25px.png"));
                searchBox.setBorder(createLineBorder(Color.BLACK));
            }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtSearch.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ text +".*", 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtSearch.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ text +".*", 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        ItemView.add(searchBox);
/*********************************************************************************/
/*********************** PHẦN SEARCH TABLE *****************************/
        JPanel sort = new JPanel(null);
        sort.setBackground(null);
        sort.setBounds(30,350,this.DEFALUT_WIDTH - 400,100);

        JLabel sortTitle = new JLabel("___________________________________________________________________________ TÌM KIẾM THÔNG TIN _________________________________________________________________________",JLabel.CENTER); // Mỗi bên 74 dấu ( - )
        sortTitle.setFont(font1);
        sortTitle.setBounds(new Rectangle(0,0,this.DEFALUT_WIDTH - 400,30));
        sort.add(sortTitle);

        /******** SORT MAKH **************/
        JLabel lbSortProductID = new JLabel("Product ID:");
        lbSortProductID.setFont(font0);
        lbSortProductID.setBounds(0,40,80,30);
        sort.add(lbSortProductID);

        sortMaSP = new JTextField();
        sortMaSP.setFont(font0);
        sortMaSP.setBounds(new Rectangle(90,40,100,30));
        sortMaSP.addKeyListener(this);
        new InfoBalloon(InfoBalloon.errTxt_numberOnly, sortMaSP,InfoBalloon.filter_numberOnly, InfoBalloon.limit_ID);
        sort.add(sortMaSP);
        /*************************************/


        /*************************************/

        /************ SORT THEO GIÁ ***************/
        JLabel btnSearch = new JLabel(new ImageIcon("./src/main/java/image/btnSearch_45px.png"));
        btnSearch.setBounds(new Rectangle(840,26,63,63));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                search();
            }
        });
        sort.add(btnSearch);

        add(sort);
        /*******************************************************************/
        this.lastAuditID = auditBUS.getLastAuditID();
        if (!MainUI.isThreadRunning) {
            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int currentAuditID = auditBUS.getLastAuditID();
                    /*
                    if (currentAuditID != lastAuditID) {
                        lastAuditID = currentAuditID;
                        auditBUS.listLastAuditProducts();
                        try {
                            Thread.sleep(1500);
                        } catch (Exception e) {

                        }
                        outModel(model, productBUS.productList);
                    }
                    */
                    lastAuditID = currentAuditID;
                    auditBUS.listLastAuditProducts();
                    /*
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                    */
                    outModel(model, productBUS.productList);
                }
            };
            Timer timer = new Timer(3000 ,task);
            timer.setRepeats(true);
            timer.start();
            MainUI.isThreadRunning = true;
        }
        
    }

    public void saveIMG()
    {
        try {
            if(i != null)
            {
                File save = new File("src/main/java/image/SanPham/"+ imgName);//Tạo file
                ImageIO.write(i,"jpg",save); // Lưu hình i vào đường dẫn file save

                i = null; //Xóa hình trong bộ nhớ
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // toggle editable field
    public void editableStatus(boolean flag) {
        txtProduct_id.setEditable(false);
        txtName.setEditable(flag);
        txtStock.setEditable(flag);
        txtCount.setEditable(flag);
        txtColor.setEditable(flag);
        txtGap.setEditable(flag);
        txtImage.setEditable(flag);
    }
    //Xóa trắng các TextField
    public void cleanView() {
        txtProduct_id.setText("");
        txtName.setText("");
        txtStock.setText("");
        txtCount.setText("");
        txtColor.setText("");
        txtGap.setText("");
        txtImage.setText("");
        img.setIcon(null);
        img.setText("Image");
        imgName = "null";
    }
    public void outModel(DefaultTableModel model , ArrayList<Product> sp){ // Xuất ra Table từ ArrayList
        Vector data;
        model.setRowCount(0);
        for(Product s:sp){
            data = new Vector();
            data.add(s.getProduct_id());
            data.add(s.getName());
            data.add(s.getStock());
            data.add(s.getCount());
            data.add(s.getColor());
            data.add(s.getCount() - s.getStock());
            data.add(s.getImg());
            model.addRow(data);
        }
        tbl.setModel(model);
    }
    
    public void listSP() { // Chép ArrayList lên table
        if(productBUS.getList() == null)
            productBUS.list();
        ArrayList<Product> sp = productBUS.getList();
        model.setRowCount(0);
        outModel(model, sp);
    }

    public void addCombo(JComboBox cmb,ArrayList list){
        for(Object a:list){
            cmb.addItem(a);
        }
    }

    public void search() {
        try {
            int masp = Integer.parseInt(sortMaSP.getText());
            outModel(model, productBUS.searchSP(masp));
        } catch (Exception e) {
            outModel(model, productBUS.productList);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args){
        JFrame f=new JFrame();
        ProductUI hd=new ProductUI(700);
        hd.init();
        f.add(hd);
        f.setSize(700,700);
        f.setVisible(true);
    }
}
