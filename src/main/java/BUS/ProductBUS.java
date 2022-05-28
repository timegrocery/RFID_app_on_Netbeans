/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAL.ProductDAO;
import DTO.Audit;
import DTO.Product;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author WindZ
 */
public class ProductBUS {
    public static ArrayList<Product> productList;
    
    public ProductBUS() {
        list();
    }
    
    public static void list() {
        ProductDAO productDAO = new ProductDAO();
        productList = new ArrayList<>();
        productList = productDAO.list();
    }
    
    public int getSize(){
        return this.productList.size();
    }
    
    public void addProduct(Product product) {
        productList.add(product);
        ProductDAO productDAO = new ProductDAO();
        productDAO.add(product);
    }

    public void deleteProduct(int product_id) {
        for(Product product : productList ) {
            if(product.getProduct_id() == product_id) {
                productList.remove(product);
                ProductDAO productDAO = new ProductDAO();
                productDAO.delete(product_id);
                return;
            }
        }
    }
    public void setProduct(Product product) {
        for(int i = 0 ; i < productList.size() ; i++) {
            if(productList.get(i).getProduct_id() == product.getProduct_id()) {
                productList.set(i, product);
                ProductDAO productDAO = new ProductDAO();
                productDAO.set(product);
                return;
            }
        }
    }

    public boolean checkProductID(int productID){
        for(Product product : productList) {
            if(product.getProduct_id() == productID) {
                return true;
            }
        }
        return false;
    }
    
    public Product getProduct(int product_id) {
        for(Product product : productList) {
            if(product.getProduct_id() == product_id) {
                return product;
            }
        }
        return null;
    }
    
    public ArrayList<Product> searchSP(int product_id) {
        ArrayList<Product> search = new ArrayList<>();
        product_id = (product_id== 0)?product_id = 0: product_id;
        for(Product product : productList) {
            if(product.getProduct_id() == product_id) {
                search.add(product);
            }
        }
        return search;
    }

    public ArrayList<Product> getList() {
        return productList;
    }

    public void ExportExcelDatabase(ArrayList<Product> productList, Audit audit){
        ProductDAO productDAO = new ProductDAO();
        productDAO.ExportExcelDatabase(productList, AuditBUS.getLastAudit());
    }

}
