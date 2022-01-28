package io.github.kwisatzx.springmvccompany.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorksWith worksWith = (WorksWith) o;

        if (!employee.equals(worksWith.employee)) return false;
        return client.equals(worksWith.client);
    }

    @Override
    public int hashCode() {
        int result = employee.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }
}
