
package com.pragma.plazoleta.infrastructure.output.feign.adapter;

import com.pragma.plazoleta.application.port.out.EmployeeServicePort;
import com.pragma.plazoleta.application.port.out.UserServicePort;
import com.pragma.plazoleta.domain.exception.UserServiceUnavailableException;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.infrastructure.output.feign.client.UserFeignClient;

import com.pragma.plazoleta.infrastructure.output.feign.dto.CreateEmployeeFeignRequest;
import com.pragma.plazoleta.infrastructure.output.feign.dto.UserFeignResponse;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.pragma.plazoleta.domain.model.enums.RoleEnum.OWNER_ROLE;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements UserServicePort, EmployeeServicePort {

    private final UserFeignClient userFeignClient;

    @Override
    public boolean existsOwnerById(Long ownerId) {
        try {
            UserFeignResponse user = userFeignClient.getUserById(ownerId);
            return user != null && "PROPIETARIO".equalsIgnoreCase(user.roleName());
        } catch (FeignException.NotFound exception) {
            return false;
        }
    }


    @Override
    public Employee createEmployee(Employee employee, String password) {

        CreateEmployeeFeignRequest request = new CreateEmployeeFeignRequest(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDocumentNumber(),
                employee.getPhone(),
                employee.getEmail(),
                password
        );

        UserFeignResponse response = userFeignClient.createEmployee(request);

        return new Employee(
                response.id(),
                response.firstName(),
                response.lastName(),
                employee.getDocumentNumber(),
                employee.getPhone(),
                response.email()
        );
    }


    @Override
    public boolean existsByEmail(String email) {
        try {
            return Boolean.TRUE.equals(userFeignClient.existsByEmail(email));
        } catch (RetryableException exception) {
            throw new UserServiceUnavailableException();
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        try {
            userFeignClient.deleteEmployee(employeeId);
        } catch (FeignException exception) {
            throw new UserServiceUnavailableException();
        }
    }
}
