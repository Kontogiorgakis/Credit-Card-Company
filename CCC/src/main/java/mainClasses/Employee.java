package mainClasses;

public class Employee extends User {

    int employee_id;
    String company;

    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getCompany(){ return company; }

    public void setCompany(String company){ this.company = company;}
}
