
package com.pragma.plazoleta.application.port.out;

import com.pragma.plazoleta.domain.model.Employee;

public interface EmployeeServicePort {

    Employee createEmployee(Employee employee, String password);

    boolean existsByEmail(String email);

    void deleteEmployee(Long employeeId);
}
