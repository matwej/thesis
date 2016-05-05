package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.files.SourceFile;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.ProducerConfig;

import java.io.*;

@Component
public class Consumer {

    @Autowired
    Producer producer;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "solutions", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "orderRoutingKey")
    )
    public void processSolution(Solution solution) throws IOException {
        System.out.println(solution.getSolutionId());
        SourceFile file = solution.getSourceFiles()[0];
        System.out.println(new String(file.getContent(), "UTF-8"));
        producer.send(ProducerConfig.ASSIGNMENTS_QUEUE, file.getName());
//        FileOutputStream f = new FileOutputStream(file.getName());
//        DataOutputStream dataOutputStream =  new DataOutputStream(f);
//        String c = new String(file.getContent(), "UTF-8");
//        dataOutputStream.writeUTF(c);
    }

}
