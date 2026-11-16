
package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Employee;

public interface CreateEmployeeUseCase {

    Employee create(
            Employee employee,
            String password,
            Long ownerId,
            Long restaurantId
    );
}
