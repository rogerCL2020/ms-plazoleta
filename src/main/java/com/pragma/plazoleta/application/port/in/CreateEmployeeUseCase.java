
package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.application.dto.result.CreateEmployeeResult;
import com.pragma.plazoleta.domain.model.Employee;

public interface CreateEmployeeUseCase {

    CreateEmployeeResult create(
            Employee employee,
            String password,
            Long ownerId,
            Long restaurantId
    );
}
