/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DTO.Audit;
import DTO.Product;
import DTO.Tag_Audit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WindZ
 */
public class AuditDAO {
    private MySQLConnect mySQL = new MySQLConnect();
    public AuditDAO(){}
    public ArrayList<Audit> list(){
        ArrayList<Audit> dshd = new ArrayList<>();
        try {
            String sql = "SELECT * FROM audit WHERE 1";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next()){
                int audit_id = rs.getInt("audit_id");
                String datetime = rs.getString("datetime");

                Audit ct = new Audit(audit_id, datetime);
                dshd.add(ct);
            }
            rs.close();
            mySQL.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dshd;
    }
    
    public Audit getLastAudit() {
        MySQLConnect mySQL = new MySQLConnect();
        int audit_id = -1;
        String datetime = "";
        try {
            String sql = "SELECT * FROM audit ORDER BY audit_id DESC LIMIT 0, 1";
            System.out.println(sql);
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next()){
                audit_id = rs.getInt("audit_id");
                datetime = rs.getString("datetime");
            }
            rs.close();
            mySQL.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (audit_id != -1) {
            Audit result = new Audit(audit_id, datetime);
            return result;
        } else {
            System.out.println("Failed to get last audit");
            return null;
        }
    }
    
    public ArrayList<Tag_Audit> getLastAuditTags() {
        Audit lastAudit = this.getLastAudit();
        ArrayList<Tag_Audit> result = new ArrayList<>();
        
        if (lastAudit == null) {
            return result;
        }
        
        MySQLConnect mySQL = new MySQLConnect();
        
        try {
            String sql = "SELECT * FROM tag_audit WHERE audit_id=" + String.valueOf(lastAudit.getAudit_id());
            System.out.println(sql);
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next()){
                int audit_id = rs.getInt("audit_id");
                String tag_id = rs.getString("tag_id");
                result.add(new Tag_Audit(audit_id, tag_id));
            }
            rs.close();
            mySQL.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public int getLastAuditID() {
        return getLastAudit().getAudit_id();
    }
    
    public int add() {
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "INSERT INTO hoadon() VALUES ()";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
        int audit_id = -1;
        try {
            sql = "SELECT * FROM audit ORDER BY audit_id DESC LIMIT 0, 1";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next()){
                audit_id = rs.getInt("audit_id");
                String datetime = rs.getString("datetime");
            }
            rs.close();
            mySQL.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (audit_id != -1) {
            return audit_id;
        } else {
            return -1;
        }
    }
    
    public void set(Audit audit){
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "UPDATE audit SET ";
            sql += "datetime=" + audit.getDatetime() + ", ";
            sql += "WHERE MAHD='"+audit.getAudit_id()+"'";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
    }
    public void delete(String audit_id) {
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "DELETE FROM audit WHERE audit_id='"+audit_id+"'";
        mySQL.executeUpdate(sql);
        System.out.println(sql);
    }
}
