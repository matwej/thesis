package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "solution", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "orderRoutingKey")
    )
    public void processSolution(InboundMessage inboundMessage) {
        System.out.println(inboundMessage.getSolutionId());
    }

}
