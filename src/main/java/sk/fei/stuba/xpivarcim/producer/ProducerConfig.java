package sk.fei.stuba.xpivarcim.producer;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    public static final String RESULTS_QUEUE = "Result";
    public static final String ASSIGNMENTS_QUEUE = "Assignment";

    @Bean
    public Queue resultsQueue() {
        return new Queue(RESULTS_QUEUE);
    }

    @Bean
    public Queue assignmentsQueue() {
        return new Queue(ASSIGNMENTS_QUEUE);
    }

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean(name = "producerTemplate")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        amqpAdmin.declareQueue(resultsQueue());
        amqpAdmin.declareQueue(assignmentsQueue());
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

}
