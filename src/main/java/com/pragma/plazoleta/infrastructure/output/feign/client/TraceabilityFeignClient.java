package com.pragma.plazoleta.infrastructure.output.feign.client;

import com.pragma.plazoleta.infrastructure.output.feign.config.TraceabilityFeignConfiguration;
import com.pragma.plazoleta.infrastructure.output.feign.dto.OrderTraceabilityFeignRequest;
import com.pragma.plazoleta.infrastructure.output.feign.dto.OrderTraceabilityFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "ms-traceability",
        url = "${microservices.traceability.url}",
        configuration = TraceabilityFeignConfiguration.class
)
public interface TraceabilityFeignClient {

    @PostMapping("/api/v1/traceability/events")
    void save(@RequestBody OrderTraceabilityFeignRequest request);

    @GetMapping("/api/v1/traceability/orders/{orderId}")
    List<OrderTraceabilityFeignResponse> getByOrderIdAndClientId(
            @PathVariable("orderId") Long orderId,
            @RequestParam("clientId") Long clientId
    );

    @GetMapping("/api/v1/traceability/orders")
    List<OrderTraceabilityFeignResponse> getByClientId(
            @RequestParam("clientId") Long clientId
    );
}