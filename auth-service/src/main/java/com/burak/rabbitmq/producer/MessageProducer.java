package com.burak.rabbitmq.producer;
import com.burak.rabbitmq.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendMessage(String message, Long code){
        rabbitTemplate.convertAndSend("authDirectExchange","bindingKeyCreateUser", Message.builder()
                .code(code)
                .message(message)
                .build()

        );
    }
}
