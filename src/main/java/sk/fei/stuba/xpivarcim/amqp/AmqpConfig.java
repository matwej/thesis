package sk.fei.stuba.xpivarcim.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Autowired
    AmqpSettings amqpSettings;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqpSettings.getHost());
        connectionFactory.setUsername(amqpSettings.getUsername());
        connectionFactory.setPassword(amqpSettings.getPassword());
        return connectionFactory;
    }

}
