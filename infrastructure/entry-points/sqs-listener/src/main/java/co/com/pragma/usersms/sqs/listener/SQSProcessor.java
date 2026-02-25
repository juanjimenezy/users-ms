package co.com.pragma.usersms.sqs.listener;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.usecase.users.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final UserUseCase userUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        System.out.println("LLEGO EL MENSAJE::" + message.body());
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), User.class))
                .flatMap(userUseCase::saveUserInDynamo)
                .then();
        // return myUseCase.doAny(message.body());
    }
}
