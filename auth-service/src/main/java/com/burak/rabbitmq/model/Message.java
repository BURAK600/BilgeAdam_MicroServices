package com.burak.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
/**
 * RabbitMQ ile pojo, iletişim yaparken, java sınıflarının serileştirilmesi gereklidir.
 * çünkü bu bilgi json olarak iletilir ve cözülür.
 */

public class Message implements Serializable {

    String message;
    Long code;
}
