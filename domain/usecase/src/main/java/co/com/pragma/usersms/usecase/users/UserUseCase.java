package co.com.pragma.usersms.usecase.users;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.ReqresRepository;
import co.com.pragma.usersms.model.users.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final ReqresRepository reqresRepository;

    public Mono<User> getUserByIdentifier(Long id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public Mono<User> createUser(Long id) {
        return reqresRepository.getUserById(id)
                .flatMap(u -> {
                    System.out.println(">>>" + u.getEmail());
                    return userRepository.save(u);
                });
    }

}
