package co.com.pragma.usersms.usecase.users;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.ReqresRepository;
import co.com.pragma.usersms.model.users.gateways.UserRedisRepository;
import co.com.pragma.usersms.model.users.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final ReqresRepository reqresRepository;
    private final UserRedisRepository userRedisRepository;

    public Mono<User> getUserByIdentifier(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByIdentifierInRedis(Long id) {
        return userRedisRepository.getUserRedis(id.toString())
                .map(user -> {
                    System.out.println("Se obtuvo usuario de redis: ".concat(user.getFirstName()));
                    return user;
                })
                .switchIfEmpty(userRepository.findByIdReqres(id)
                                .flatMap(user -> userRedisRepository.saveRedis(id.toString(), user))
                );
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public Mono<User> createUser(Long id) {
        return reqresRepository.getUserById(id)
                .flatMap(user -> userRepository.findByIdReqres(user.getIdReqres())
                        .switchIfEmpty(userRepository.save(user))
                );
    }

}
