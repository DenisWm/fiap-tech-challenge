package com.fiap.tech.common.infra.configuration;


import com.fiap.tech.common.infra.annotations.OrderCreatedQueue;
import com.fiap.tech.common.infra.annotations.OrderEvents;
import com.fiap.tech.common.infra.annotations.OrderPayedQueue;
import com.fiap.tech.common.infra.annotations.ProductionStatusChangedQueue;
import com.fiap.tech.common.infra.configuration.amqp.QueueProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.order-created")
    @OrderCreatedQueue
    public QueueProperties orderCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.order-payed")
    @OrderPayedQueue
    public QueueProperties orderPayedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.production-status-changed")
    @ProductionStatusChangedQueue
    public QueueProperties productionStatusChangedQueueProperties() {
        return new QueueProperties();
    }


    @Configuration
    static class Admin {

        @Bean
        @OrderEvents
        public DirectExchange orderEventsExchange(@OrderCreatedQueue QueueProperties props) {
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @OrderCreatedQueue
        public Queue orderCreatedQueue(@OrderCreatedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @OrderCreatedQueue
        public Binding orderCreatedBinding(
                @OrderEvents DirectExchange exchange,
                @OrderCreatedQueue Queue queue,
                @OrderCreatedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }


        @Bean
        @OrderPayedQueue
        public Queue orderPayedQueue(@OrderPayedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @OrderPayedQueue
        public Binding orderPayedBinding(
                @OrderEvents DirectExchange exchange,
                @OrderPayedQueue Queue queue,
                @OrderPayedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}
