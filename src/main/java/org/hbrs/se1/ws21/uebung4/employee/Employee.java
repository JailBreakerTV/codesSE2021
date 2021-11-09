package org.hbrs.se1.ws21.uebung4.employee;

import lombok.Data;
import org.hbrs.se1.ws21.uebung2.Member;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Employee implements Member, Serializable, Comparable<Employee> {
    private final Integer id;
    private final List<Expertise> expertises = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String department;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public int compareTo(@NotNull Employee employee) {
        return this.id.compareTo(employee.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Employee employee = (Employee) obj;
        return Objects.equals(this.id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + this.id +
                ", expertises=" + this.expertises +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", department='" + this.department + '\'' +
                '}';
    }
}
