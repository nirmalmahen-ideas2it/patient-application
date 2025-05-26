package com.ideas2it.training.patient.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ.
 * <p>
 * This class sets up the RabbitMQ messaging components, including the queue, exchange,
 * routing key, and message converter. It ensures that messages are sent and received
 * in a structured and reliable manner.
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Configuration
public class RabbitMQConfig {

    /**
     * The name of the exchange used for RabbitMQ messaging.
     */
    public static final String EXCHANGE = "patient-exchange";
    /**
     * The routing key used to bind the queue to the exchange.
     */
    public static final String ROUTING_KEY = "patient.key";
    /**
     * The name of the patient queue, injected from the application properties.
     */
    @Value("${app.queue.patient}")
    private String patientQueue;

    /**
     * Defines the RabbitMQ queue.
     *
     * @return a durable queue with the name specified in the application properties.
     */
    @Bean
    public Queue queue() {
        return new Queue(patientQueue, true);
    }

    /**
     * Defines the RabbitMQ direct exchange.
     *
     * @return a direct exchange with the specified name.
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * Binds the queue to the exchange using the specified routing key.
     *
     * @param queue    the RabbitMQ queue
     * @param exchange the RabbitMQ exchange
     * @return the binding between the queue and the exchange
     */
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * Configures the RabbitTemplate with a JSON message converter.
     *
     * @param connectionFactory the RabbitMQ connection factory
     * @return a RabbitTemplate configured with a Jackson2JsonMessageConverter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}