package com.pragma.plazoleta.infrastructure.output.event;

import com.pragma.plazoleta.application.event.OrderReadyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class NotificationListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderReady(OrderReadyEvent event) {

        log.info(
                "Notificación de pedido listo. OrderId: {}, ClientId: {}",
                event.orderId(),
                event.clientId()
        );

        // TODO: Integrar con ms-mensajeria mediante Feign para enviar
        // el SMS al cliente con el PIN de seguridad.

        log.info(
                "SMS pendiente de envío para el pedido {}. PIN generado correctamente.",
                event.orderId()
        );
    }
}