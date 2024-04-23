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
public class Company extends User {
    int company_id;
    String employee_name; //Array/List
    String employee_identity; // maybe not String

    public int getCompanyId() {
        return company_id;
    }

    public void setCompanyId(int company_id) {
        this.company_id = company_id;
    }

    public String getEmployeeName() {
        return employee_name;
    }

    public void setEmployeeName(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployeeIdentity() {
        return employee_identity;
    }

    public void setEmployeeIdentity(String employee_identity) {
        this.employee_identity = employee_identity;
    }
}
