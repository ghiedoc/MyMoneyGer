/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmsystem;

/**
 *
 * @author Gillian
 */
public class Transactions {
    
     private String transaction;
    private String date;
    private String category;
    private String amount;
    private String remark;

    public Transactions() {
    }

    public Transactions(String transaction, String date, String category, String amount, String remark) {
        this.transaction = transaction;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.remark = remark;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String s) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String s) {
        this.date = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String s) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String s) {
        this.remark = remark;
    }
    
}
