package io.github.kwisatzx.springmvccompany.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @Column(name = "client_name")
    private String name;
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public Long getId() {
        return id;
    }

    public void setId(Long client_id) {
        this.id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String client_name) {
        this.name = client_name;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
