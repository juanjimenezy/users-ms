package co.com.pragma.usersms.consumer;

import co.com.pragma.usersms.consumer.object.ReqresResponseDTO;
import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.ReqresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ReqresRepository{
    private final WebClient client;

    @Override
    public Mono<User> getUserById(Long id) {
        return client.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(ReqresResponseDTO.class)
                .flatMap(p -> {
                    User user = new User();
                    user.setIdReqres(p.getData().getId());
                    user.setEmail(p.getData().getEmail());
                    user.setFirstName(p.getData().getFirstName());
                    user.setLastName(p.getData().getLastName());
                    user.setAvatar(p.getData().getAvatar());
                    return Mono.just(user);
                });
    }
}
