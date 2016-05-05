package sk.fei.stuba.xpivarcim.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    RabbitTemplate producerTemplate;

    public void send(String queueName, String msg) {
        producerTemplate.convertAndSend(queueName, msg);
    }


}
