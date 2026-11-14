
package com.pragma.plazoleta.infrastructure.output.feign.adapter;

import com.pragma.plazoleta.application.port.out.UserServicePort;
import com.pragma.plazoleta.infrastructure.output.feign.client.UserFeignClient;

import com.pragma.plazoleta.infrastructure.output.feign.dto.UserFeignResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.pragma.plazoleta.domain.model.enums.RoleEnum.OWNER_ROLE;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements UserServicePort {

    private final UserFeignClient userFeignClient;

    @Override
    public boolean existsOwnerById(Long ownerId) {
        try {
            UserFeignResponse user = userFeignClient.getUserById(ownerId);
            return user != null && OWNER_ROLE.getRoleName().equals(user.roleName());
        } catch (FeignException.NotFound exception) {
            return false;
        }
    }


}
