package co.com.pragma.usersms.usecase.users;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final ReqresRepository reqresRepository;
    private final UserCacheRepository userCacheRepository;
    private final UserNotificationGateway userNotificationGateway;
    private final UserGateway userGateway;

    public Mono<User> getUserByIdentifier(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByIdentifierInRedis(Long id) {
        return userCacheRepository.getUser(id.toString())
                .map(user -> {
                    System.out.println("Se obtuvo usuario de redis: ".concat(user.getFirstName()));
                    return user;
                })
                .switchIfEmpty(userRepository.findByIdReqres(id)
                                .flatMap(user -> userCacheRepository.saveUser(id.toString(), user))
                );
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public Mono<User> createUser(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(reqresRepository.getUserById(id)
                        .flatMap(userRepository::save)
                        .flatMap(userSaved -> userNotificationGateway.sendUserCreatedEvent(userSaved).thenReturn(userSaved))
                );
    }

    public Mono<User> saveUserInDynamo(User user) {
        return userGateway.saveUser(user);
    }

}
