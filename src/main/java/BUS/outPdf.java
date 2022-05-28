/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Audit;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author WindZ
 */
public class outPdf {
    private String file;// = "./report/test.pdf";
    private ArrayList<Audit> audit_list = new ArrayList<Audit>();
    private Audit audit;
    private BaseFont bf;
    private ProductBUS productBUS = new ProductBUS();
    public outPdf() {
        productBUS.list();
    }

    public outPdf(Audit audit)  {
        this.audit = audit;
        file = Handler.getFullPath("report/bill"+audit.getAudit_id()+".pdf");
        System.out.println(file);
        this.audit_list = audit_list;
        productBUS.list();
    }
    
    public void print() {
        String uderline = "*";
        try {
            //Tạo Font
            bf = BaseFont.createFont("./font/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            
            // Tạo tài liệu
            Document bill = new Document(PageSize.A4, 15, 15, 10, 10);
            
            String line = "";
            for(int i = 0 ; i < bill.getPageSize().getWidth()/5; i++){
                line += uderline;
            }
            //Tạo đối tượng writter
            PdfWriter.getInstance(bill, new FileOutputStream(file));
            
            //Mở document
            bill.open();
            
            Paragraph header = new Paragraph("Cửa hàng đồ gia dụng",new Font(bf,35));
            header.setAlignment(1);
            bill.add(header);
            
            Paragraph info = new Paragraph("Mã kiểm kê: " + audit.getAudit_id()+"          Ngày: " + audit.getDatetime(),new Font(bf,15));
            bill.add(info);
            
            Paragraph l = new Paragraph(line);
            l.setAlignment(1);
            bill.add(l);
            
            String[] cellHeader = {"Mã SP","Tên SP", "Số lượng lý thuyết", "Số lượng thực tế", "Gap"};
            
            PdfPTable t = new PdfPTable(cellHeader.length);
            t.setSpacingAfter(10);
            t.setSpacingBefore(10);
            int[] relativeWidths = {20,80,10,40};
            t.setWidths(relativeWidths);
            
            for(String s : cellHeader) {
                t.addCell(createCell(s, new Font(bf,13)));
            }    
            for(Audit ct : audit_list){
                t.addCell( createCell(String.valueOf(ct.getAudit_id())) );
                t.addCell( createCell(productBUS.getProduct(ct.getAudit_id()).getName()) );
                t.addCell( createCell(String.valueOf(ct.getAudit_id())) );
                t.addCell( createCell(String.valueOf(ct.getAudit_id())) );
            }
            bill.add(t);
            
            bill.add(l);          
            
            bill.close();
            
                    
            JOptionPane.showMessageDialog(null, "In hoàn tất");
            System.out.println("Done");
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(outPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(outPdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    public PdfPCell createCell(String s) {
        PdfPCell cell = new PdfPCell(new Phrase(s,new Font(bf,13)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);
        
        return cell;
    }
    public PdfPCell createCell(String s,Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(s,font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);
        return cell;
    }
}
