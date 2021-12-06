package io.github.kwisatzx.springmvccompany.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "works_with")
public class WorksWith implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Id
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @JoinColumn(name = "total_sales")
    private Double totalSales;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double total_sales) {
        this.totalSales = total_sales;
    }
}
