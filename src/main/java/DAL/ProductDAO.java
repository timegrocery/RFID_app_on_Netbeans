/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DTO.Audit;
import DTO.Product;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WindZ
 */
public class ProductDAO {
    private MySQLConnect mySQL = new MySQLConnect();
    public ProductDAO(){
    }
    
    public ArrayList<Product> list() {
        ArrayList<Product> productList = new ArrayList<Product>();
        try {
            String sql = "SELECT * FROM product WHERE 1";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next()){
                int product_id = rs.getInt("product_id");
                String tag_id = rs.getString("tag_id");
                String name = rs.getString("name");
                String color = rs.getString("color");
                String img = rs.getString("img");
                
                boolean flag = true;
                int i = -1;
                for(Product p : productList) {
                    if (p.getProduct_id() == product_id) {
                        flag = false;
                        i = productList.indexOf(p);
                    }
                }
                
                if (flag) {
                    Product product = new Product(product_id, tag_id, name, 1, 0, color, img);
                    productList.add(product);
                } else {
                    productList.get(i).setStock(productList.get(i).getStock() + 1);
                }
            }
            rs.close();
            mySQL.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productList;
    }

    public void add(Product ct) {
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "INSERT INTO laptop VALUES (";
        sql += "'"+ct.getProduct_id()+"',";
        sql += "'"+ct.getTag_id()+"',";
        sql += "'"+ct.getName()+"',";
        sql += "'"+ct.getStock()+"',";
        sql += "'"+ct.getCount()+"',";
        sql += "'"+ct.getColor()+"',";
        sql += "'"+ct.getImg()+"')";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
    }

    public void delete(int product_id) {
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "DELETE FROM product WHERE product_id='"+product_id+"'";
        mySQL.executeUpdate(sql);
        System.out.println(sql);
    }

    public void set(Product sp) {
        String sql = "UPDATE product SET ";
        sql += "tag_id='"+sp.getTag_id()+"', ";
        sql += "name='"+sp.getName()+"', ";
        sql += "stock='"+sp.getStock()+"', ";
        sql += "count='"+sp.getCount()+"', ";
        sql += "color='"+sp.getColor()+"', ";
        sql += "img='"+sp.getImg()+"' ";
        sql += "WHERE product_id='"+sp.getProduct_id()+"'";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
    }

    public void ExportExcelDatabase(ArrayList<Product> productList, Audit audit){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ProductAudit");

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell;
            cell = row.createCell(0);
            cell.setCellValue("Date/Time");
            cell = row.createCell(1);
            cell.setCellValue(audit.getDatetime());
            cell = row.createCell(3);
            cell.setCellValue("Staff ID");
            cell = row.createCell(4);
            cell.setCellValue("4001");
            
            row = sheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue("product_id");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("name");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("stock");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("count");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("gap");
            cell.setCellStyle(style);
            
            int i = 3;
            
            for(Product product : productList) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(product.getProduct_id());
                cell = row.createCell(1);
                cell.setCellValue(product.getName());
                cell = row.createCell(2);
                cell.setCellValue(product.getStock());
                cell = row.createCell(3);
                cell.setCellValue(product.getCount());
                cell = row.createCell(4);
                cell.setCellValue(product.getCount()-product.getStock());
                
                i++;
            }
            

            for(int colNum = 0;colNum < row.getLastCellNum();colNum++) {
                sheet.autoSizeColumn((short) (colNum));
            }
            
            String path = "C://Users//dell//Desktop//report//";
            path += audit.getDatetime().replace(":", "-");
            path = path.replace("\s", "-");
            path += ".xlsx";
            File file = new File(path);
            file.createNewFile();
            try (OutputStream os = new FileOutputStream(file)) {
                workbook.write(os);
                System.out.println("Xuất file thành công:" + path);
            }
            Desktop.getDesktop().open(file);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
