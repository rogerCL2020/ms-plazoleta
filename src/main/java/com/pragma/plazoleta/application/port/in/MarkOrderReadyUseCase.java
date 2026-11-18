
package com.pragma.plazoleta.application.port.in;

import com.pragma.plazoleta.domain.model.Order;

public interface MarkOrderReadyUseCase {

    Order markAsReady(Long employeeId, Long orderId);

}
