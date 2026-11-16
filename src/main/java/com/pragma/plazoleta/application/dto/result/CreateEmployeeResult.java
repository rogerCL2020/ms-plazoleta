package com.pragma.plazoleta.application.dto.result;

import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.model.Restaurant;

public record CreateEmployeeResult(
        Employee employee,
        Restaurant restaurant
) {}