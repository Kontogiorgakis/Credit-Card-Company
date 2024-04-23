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
public class Merchant {

    int merchant_id;
    String name;
    String account_number;
    double supply;
    double total_profit;
    double amount_due;

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

    public double getSupply() {
        return supply;
    }

    public void setSupply(double supply) {
        this.supply = supply;
    }

    public double getTotalProfit() {
        return total_profit;
    }

    public void setTotalProfit(double total_profit) {
        this.total_profit = total_profit;
    }

    public double getAmountDue() {
        return amount_due;
    }

    public void setAmountDue(double amount_due) {
        this.amount_due = amount_due;
    }

}
