package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.files.SourceFile;

import java.io.*;

@Component
public class Consumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "solution", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "orderRoutingKey")
    )
    public void processSolution(InboundMessage inboundMessage) throws IOException {
        System.out.println(inboundMessage.getSolutionId());
        SourceFile file = inboundMessage.getSourceFiles()[0];
        System.out.println(new String(file.getContent(), "UTF-8"));
//        FileOutputStream f = new FileOutputStream(file.getName());
//        DataOutputStream dataOutputStream =  new DataOutputStream(f);
//        String c = new String(file.getContent(), "UTF-8");
//        dataOutputStream.writeUTF(c);
    }

}
