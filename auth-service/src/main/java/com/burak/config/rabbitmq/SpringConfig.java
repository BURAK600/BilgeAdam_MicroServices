package com.burak.config.rabbitmq;

import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;

@Configuration
public class SpringConfig {

    private String authDirectExchange = "authDirectExchange";
    private String bindingKeyCreateUser = "bindingKeyCreateUser";
    private String queueCreateUser = "queue-auth-create-user";

    @Bean
    DirectExchange authDirectExchange(){
        return new DirectExchange(authDirectExchange);
    }
    @Bean
    Queue queueCreateUser(){
        return new Queue(queueCreateUser);
    }

    @Bean
    public Binding bindingCreateUser(final Queue queueCreateUser, final DirectExchange authDirectExchange){
        /**
         * Exchange ile Queue arasında binding işlemi yapılır
         * su queue ile bu exchange i bu anahtar ile bağla
         */
        return BindingBuilder.bind(queueCreateUser).to(authDirectExchange).with(bindingKeyCreateUser);
    }

}
