package com.rj.ecommerce_email_service.email.listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String ROUTING_KEY = "email.routing.key";

    // Email notification queue (for sending status back)
    public static final String NOTIFICATION_EXCHANGE = "email.notification.exchange";
    public static final String NOTIFICATION_QUEUE = "email.notification.queue";
    public static final String NOTIFICATION_ROUTING_KEY = "email.notification.routing.key";

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(emailExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE, true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(notificationExchange())
                .with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
