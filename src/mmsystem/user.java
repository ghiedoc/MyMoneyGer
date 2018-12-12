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
public class user {
    
    public String username;
    public String details;
    public String currentBalance;

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }
    

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public user() {
        
    }
    private static final user INSTANCE = new user();
    
    public static user getINSTANCE() {
        return INSTANCE;
    }
}
