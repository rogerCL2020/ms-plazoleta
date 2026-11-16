
package com.pragma.plazoleta.infrastructure.output.feign.client;

import com.pragma.plazoleta.infrastructure.output.feign.config.UserFeignConfiguration;
import com.pragma.plazoleta.infrastructure.output.feign.dto.CreateEmployeeFeignRequest;
import com.pragma.plazoleta.infrastructure.output.feign.dto.UserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ms-usuarios",
        url = "${microservices.usuarios.url}",
        configuration = UserFeignConfiguration.class
)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{id}")
    UserFeignResponse getUserById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/users/employee")
    UserFeignResponse createEmployee(@RequestBody CreateEmployeeFeignRequest request);


    @GetMapping("/api/v1/users/exists")
    Boolean existsByEmail(@RequestParam("correo") String email);

    // Compensación Saga: eliminar empleado
    @DeleteMapping("/api/v1/users/{id}")
    void deleteEmployee(@PathVariable("id") Long id);

}
