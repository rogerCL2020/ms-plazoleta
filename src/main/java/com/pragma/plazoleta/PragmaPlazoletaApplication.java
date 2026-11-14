package com.pragma.plazoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PragmaPlazoletaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PragmaPlazoletaApplication.class, args);
    }

}
