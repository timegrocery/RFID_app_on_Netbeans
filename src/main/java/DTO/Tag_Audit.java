/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author dell
 */
public class Tag_Audit {
    int audit_id;
    String tag_id;

    public Tag_Audit() {
    }

    public Tag_Audit(int audit_id, String tag_id) {
        this.audit_id = audit_id;
        this.tag_id = tag_id;
    }

    public int getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(int audit_id) {
        this.audit_id = audit_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }
    
}
