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
public class Transaction {

    int transaction_id;
    String customer_name;
    String customer_property; // Civilian or Company
    String product_name;
    String merchant_name;
    String transaction_date;
    double transaction_amount;
    String transaction_type; // Charge or Credit
    int returned; //If the product has been returned

    public int getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getProductName(){ return product_name; }

    public void setProductName(String product_name){ this.product_name = product_name; }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomerProperty(){ return customer_property;}

    public void setCustomerProperty(String customer_property){ this.customer_property=customer_property;}

    public String getMerchantName() {
        return merchant_name;
    }

    public void setMerchantName(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getTransactionDate() {
        return transaction_date;
    }

    public void setTransactionDate(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public double getTransactionAmount() {
        return transaction_amount;
    }

    public void setTransactionAmount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransactionType() {
        return transaction_type;
    }

    public void setTransactionType(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public int getReturned(){ return returned;}

    public void setReturned(int returned){ this.returned=returned;}
}
