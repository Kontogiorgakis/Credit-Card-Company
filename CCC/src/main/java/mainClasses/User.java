/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

/**
 *
 * @author ntigo
 */
public abstract class User {

    String name;
    String account_number;
    String exp_date;
    int credit_limit;
    double amount_due;
    double available_balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    public String getExpDate() {
        return exp_date;
    }

    public void setExpDate(String exp_date) {
        this.exp_date = exp_date;
    }

    public int getCreditLimit() {
        return credit_limit;
    }

    public void setCreditLimit(int credit_limit) {
        this.credit_limit = credit_limit;
    }

    public double getAmountDue() {
        return amount_due;
    }

    public void setAmountDue(double amount_due) {
        this.amount_due = amount_due;
    }

    public double getAvailableBalance() {
        return available_balance;
    }

    public void setAvailableBalance(double available_balance) {
        this.available_balance = available_balance;
    }

}
