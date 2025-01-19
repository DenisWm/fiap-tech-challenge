package com.fiap.tech.configuration.amqp;

import com.fiap.tech.configuration.annotations.OrderCreatedQueue;
import com.fiap.tech.configuration.annotations.OrderEncodedQueue;
import com.fiap.tech.configuration.annotations.OrderEvents;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Qualifier;

@Configuration
public class AmqpConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.order-created")
    @OrderCreatedQueue
    public QueueProperties orderCreatedQueueProperties(){
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.order-encoded")
    public QueueProperties orderEncodedQueueProperties(){
        return new QueueProperties();
    }

    @Bean
    public String queueName(@OrderCreatedQueue QueueProperties props){
        return props.getQueue();
    }

    @Configuration
    static class Admin{

        @Bean
        @OrderEvents
        public Exchange orderEventsExchange(@OrderCreatedQueue QueueProperties props){
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @OrderCreatedQueue
        public Queue orderCreatedQueue(@OrderCreatedQueue QueueProperties props){
            return new Queue(props.getQueue());
        }

        @Bean
        @OrderCreatedQueue
        public Binding orderCreatedBinding(
                @OrderEvents DirectExchange exchange,
                @OrderCreatedQueue Queue queue,
                @OrderCreatedQueue QueueProperties props
        ){
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

        @Bean
        @OrderEncodedQueue
        public Queue orderEncodedQueue(@OrderEncodedQueue QueueProperties props){
            return new Queue(props.getQueue());
        }

        @Bean
        @OrderEncodedQueue
        public Binding orderEncodedBinding(
                @OrderEvents DirectExchange exchange,
                @OrderEncodedQueue Queue queue,
                @OrderEncodedQueue QueueProperties props
        ){
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

    }

}

