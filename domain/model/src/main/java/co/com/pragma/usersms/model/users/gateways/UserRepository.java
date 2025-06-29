package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> findAll();
    Mono<User> findById(Long id);
    Mono<User> findByIdReqres(Long idReqres);
    Flux<User> findByName(String name);
    Mono<User> save(User user);
}