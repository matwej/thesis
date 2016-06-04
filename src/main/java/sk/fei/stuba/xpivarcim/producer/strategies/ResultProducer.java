package sk.fei.stuba.xpivarcim.producer.strategies;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;
import sk.fei.stuba.xpivarcim.producer.messages.Result;

@Service
public class ResultProducer implements Producer<Result> {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public Result download(long id) throws MessagingResponseException {
        return null;
    }

    @Override
    public Result update(Result object) throws MessagingResponseException {
        return null;
    }

    @Override
    public void send(Result object) {
        rabbitTemplate.convertAndSend("Result", object);
    }

    @Override
    public <K> Result sendAndReceive(K object, Class<K> type) {
        return (Result) rabbitTemplate.convertSendAndReceive("Result", object);
    }
}
