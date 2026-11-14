package com.pragma.plazoleta.application.port.out;


public interface UserServicePort {

    boolean existsOwnerById(Long ownerId);
}