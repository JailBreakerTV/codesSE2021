package org.hbrs.se1.ws21.uebung4.employee;

import lombok.RequiredArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public final class EmployeeServiceImpl implements EmployeeService {
    private final Container<Employee> container;

    @Override
    public @NotNull Employee create() {
        return this.create(this.generateId());
    }

    @Override
    public @NotNull Employee create(Integer id) {
        return new Employee(id);
    }

    @Override
    public @NotNull Integer generateId() {
        final Integer generated = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
        if (this.container.hasMember(generated)) {
            return this.generateId();
        }
        return generated;
    }
}
