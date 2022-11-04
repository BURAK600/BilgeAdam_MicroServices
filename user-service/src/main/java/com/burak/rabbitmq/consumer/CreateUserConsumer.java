package com.burak.rabbitmq.consumer;


import com.burak.rabbitmq.model.CreateProfile;
import com.burak.rabbitmq.model.Message;
import com.burak.repository.entity.UserProfile;
import com.burak.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {


private final UserProfileService userProfileService;

    @RabbitListener(queues = "queue-auth-create-user")
    public void consumeMessage(CreateProfile profile){

        userProfileService.save(UserProfile.builder()
                        .authId(profile.getAuthId())
                        .email(profile.getEmail())
                        .userName(profile.getUserName())
                .build());

    }


}
