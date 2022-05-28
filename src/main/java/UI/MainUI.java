/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import UI.bone.header;
import UI.bone.navItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author WindZ
 */
public class MainUI extends JFrame implements MouseListener {

    public static int userID;
    private String userName;
    private String role;
    private boolean flag = true;
    private JPanel header,nav,main;
    private final int DEFAULT_HEIGHT = 730,DEFALUT_WIDTH = 1300 ;
    public static boolean isThreadRunning = false;
    private ArrayList<String> navItem = new ArrayList<String>();  //Chứa thông tin có button cho menu gồm
    private ArrayList<navItem> navObj = new ArrayList<navItem>();  //Chứa cái button trên thanh menu
    public MainUI(int userID, String userName, String role) {
        this.userID = userID;
        this.userName = userName;
        this.role = role;
        init();
    }
    
    public MainUI() {
        this.userID = 4000;
        this.userName = "Admin";
        this.role = "Admin";
        init();
    }
    
    public void init() {
        Font font = new Font("Segoe UI",Font.BOLD,14);
        setTitle("Quản lý cửa hàng");
        ImageIcon logo = new ImageIcon("./src/main/java/image/shopicon.png");
        setIconImage(logo.getImage());
        setLayout(new BorderLayout());
        setSize(DEFALUT_WIDTH,DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

/************ PHẦN HEADER *************************************/
        header = new JPanel(null);
        header.setBackground(new Color(44, 70, 112));
        header.setPreferredSize(new Dimension(DEFALUT_WIDTH,40));

        header hmain = new header(DEFALUT_WIDTH, 40, "Quản lý cửa hàng");

        if(userName != null) {
            if(role.equals("Admin")) userName = "Admin";
            JLabel user = new JLabel("Chào, " + userName);
            user.setFont(font);
            user.setForeground(Color.WHITE);
            user.setBounds(new Rectangle(DEFALUT_WIDTH - 300, -7, 150, 50));
            hmain.add(user);

            //Tạo btn Logout
            navItem btnLogOut = new navItem("", new Rectangle(DEFALUT_WIDTH-150, -8, 50, 50), "logout_25px.png", "logout_25px.png", "logout_hover_25px.png", new Color(101, 135, 235));
            hmain.add(btnLogOut.isButton());
            btnLogOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    //Login lg = new Login();
                    dispose();
                }
            });
        }

        //Tạo btn EXIT & MINIMIZE
        navItem exit = new navItem("", new Rectangle(DEFALUT_WIDTH-50, -8, 50, 50), "exit_25px.png", "exit_25px.png", "exit_hover_25px.png", new Color(240, 71, 74));
        navItem minimize = new navItem("", new Rectangle(DEFALUT_WIDTH-100, -8, 50, 50), "minimize_25px.png", "minimize_25px.png", "minimize_hover_25px.png" ,new Color(197, 240, 233));

        hmain.add(exit.isButton());
        hmain.add(minimize.isButton());

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        exit.getlb().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setState(Frame.ICONIFIED);
            }
        });

        minimize.getlb().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setState(Frame.ICONIFIED);
            }
        });

        header.add(hmain);

/****************************************************************/


/************ PHẦN NAVIGATION ( MENU ) **************************/
        nav = new JPanel(null);
        nav.setBackground(new Color(56, 121, 164));
        nav.setPreferredSize(new Dimension(220,DEFAULT_HEIGHT + 50));

        JScrollPane scroll = new JScrollPane(nav);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(1,100));
        scroll.setHorizontalScrollBarPolicy(scroll.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(10);


        //Thêm item vào thanh menu (Tên item : icon : icon hover)
        navItem = new ArrayList<>();  //Chứa thông tin có button cho menu gồm ( Tên btn : icon : icon hover )
        navItem.add("Kiểm kê Sản Phẩm:QLSP_20px.png:QLSP_20px_active.png");
        navItem.add("Quản lý nhân viên:NhanVien_20px.png:NhanVien_20px_active.png");
        if( role == null || role.equals("Admin")) {
            navItem.add("Danh sách kiểm kê:CaiDat_20px.png:CaiDat_20px_active.png");
        }

        outNav();

/************ PHẦN MAIN ( HIỂN THỊ ) **************************/
        main = new JPanel(null);
        main.setBackground(Color.WHITE);
        navObj.get(0).doActive();
        changeMainInfo(0);
/**************************************************************/

        add(header,BorderLayout.NORTH);
        add(scroll,BorderLayout.WEST);
        add(main,BorderLayout.CENTER);

        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {

        }
        MainUI ql = new MainUI();

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        for(int i = 0; i< navObj.size();i++)
        {
            navItem item = navObj.get(i); // lấy vị trí item trong menu
            if(e.getSource() == item) {
                item.doActive(); // Active NavItem đc chọn
                changeMainInfo(i); // Hiển thị ra phần main
            }
            else if (e.getSource() == item.getlb())
            {
                item.doActive();
                changeMainInfo(i);
            }
            else{
                item.noActive();
            }
        }
    }

    public void changeMainInfo(int i) //Đổi Phần hiển thị khi bấm btn trên menu
    {
        switch(i)
        {
            case 0: // KIỂM KÊ SẢN PHẨM
                main.removeAll();
                main.add(new ProductUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                System.out.println(i);
                break;
            case 1: // QUẢN LÝ NHÂN VIÊN
                main.removeAll();
                main.add(new NhanVienUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                System.out.println(i);
                break;
            case 2: // DANH SÁCH KIỂM KÊ
                main.removeAll();
                main.add(new AuditUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                System.out.println(i);
                break;
            default:
                break;
        }
    }

    public void outNav()
    {
        //Gắn cái NavItem vào NavOBJ
        navObj.clear();
        for(int i = 0 ; i < navItem.size() ; i++) {
            String s = navItem.get(i).split(":")[0];
            String icon = navItem.get(i).split(":")[1];
            String iconActive = navItem.get(i).split(":")[2];
            navObj.add(new navItem(s, new Rectangle(0,200+50*i,220,50),icon,iconActive));
            navObj.get(i).addMouseListener(this);
            navObj.get(i).getlb().addMouseListener(this);
        }
        //Đổi màu phần DropDown của thống kê
        if(!flag && navObj.size() > 8) {
            navObj.get(5).setColorNormal(new Color(109, 173, 255));
            navObj.get(6).setColorNormal(new Color(109, 173, 255));
        }

        //Xuất ra Nagivation
        nav.removeAll();
        JLabel profile = new JLabel(new ImageIcon("./src/main/java/image/profile_150px.png"));
        profile.setBounds(0,0,220,200);
        nav.add(profile);
        for(navItem n : navObj) {
            nav.add(n);
        }
        repaint();
        revalidate();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }
}
