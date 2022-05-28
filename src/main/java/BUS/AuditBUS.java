/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAL.AuditDAO;
import DTO.Audit;
import DTO.Product;
import DTO.Tag_Audit;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author WindZ
 */
public class AuditBUS {
    public ArrayList<Audit> audit_list;
    public ArrayList<Tag_Audit> lastAuditTags;
    public ArrayList<Product> lastAuditProducts;
    HashMap<Integer, Integer> productCount = new HashMap<>();
    
    public AuditBUS() {
        list();
        listLastAuditProducts();
    }
    
    public AuditBUS(int i) {
        list();  
        listLastAuditProducts();
    }
    
    public void list() {
        AuditDAO auditDAO = new AuditDAO();
        audit_list = new ArrayList<>();
        audit_list = auditDAO.list();
    }
    
    public void listLastAuditTags() {
        AuditDAO auditDAO = new AuditDAO();
        lastAuditTags = auditDAO.getLastAuditTags();
    }
    
    public void listLastAuditProducts() {
        ProductBUS.list();
        listLastAuditTags();
        
        for (Product product : ProductBUS.productList) {
            product.setCount(0);
        }
        
        for(Tag_Audit tag : lastAuditTags) {
            for(Product product : ProductBUS.productList) {
                if (tag.getTag_id().equals(product.getTag_id())) {
                    product.setCount(product.getCount() + 1);
                }
            }
        }
    }
    
    public static Audit getLastAudit() {
        AuditDAO auditDAO = new AuditDAO();
        return auditDAO.getLastAudit();
    }
    
    public int getLastAuditID() {
        AuditDAO auditDAO = new AuditDAO();
        return auditDAO.getLastAuditID();
    }
    
    public int getSize(){
        return audit_list.size();
    }
    
    
    
    public void add(Audit audit) {
        audit_list.add(audit);
        AuditDAO auditDAO = new AuditDAO();
        auditDAO.add();
    }

    public int set(Audit s) {
        for(int i = 0 ; i < audit_list.size() ; i++) {
            if(audit_list.get(i).getAudit_id() == (s.getAudit_id())) {
                audit_list.set(i, s);
                AuditDAO hdDAO = new AuditDAO();
                hdDAO.set(s);
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<Audit> ListTime(Calendar from,Calendar to) {
        ArrayList<Audit> list = new ArrayList<>();
        for(Audit hd : audit_list) {
            Timestamp date = Timestamp.valueOf(hd.getDatetime());
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(date.getTime());
            if(checkTime(from, to, time)) {
                list.add(hd);
            }
        }
        return list;
    }
    
    public boolean checkTime(Calendar from,Calendar to,Calendar time){
        if(time.after(from) && time.before(to)) {
            return true;
        }
        return false;
    }
    
    public Audit getAuditByID(int audit_id) {
        for(Audit audit : audit_list) {
            if (audit.getAudit_id() == audit_id) {
                return audit;
            }
        }
        return null;
    } 
    
    public ArrayList<Audit> search( int mm, int yyy) {
        int mm1 = 0, mm2 = 12;
        int yyy1 = 0, yyy2 = Calendar.getInstance().get(Calendar.YEAR);
        
        if(mm != -1) {
            mm1 = mm;
            mm2 = mm;
        }
        if(yyy != 0) {
            yyy1 = yyy;
            yyy2 = yyy;
        }
        
        ArrayList<Audit> search = new ArrayList<>();
        for (Audit audit : audit_list) {
            Timestamp time = Timestamp.valueOf(audit.getDatetime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time.getTime());;

            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            if((month >= mm1 && month <= mm2)
                && (year >= yyy1 && year <= yyy2)) {
                search.add(audit);
            }
        }
        
        return search;
    }
    
    public boolean check(int audit_id) {
        for(Audit hd : audit_list) {
            if( hd.getAudit_id() == (audit_id)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Audit> getListWidthArray(ArrayList<Integer> s) {
        ArrayList<Audit> ds = new ArrayList<>();
        if(s == null) return audit_list;
        for(Audit hd : audit_list) {
            int audit_id = hd.getAudit_id();
            for(int a: s){
                if(audit_id == (a)){
                    ds.add(hd);
                }
            }
        }
        return ds;
    }
    
    public ArrayList<Audit> getList() {
        return audit_list;
    }
    
}
