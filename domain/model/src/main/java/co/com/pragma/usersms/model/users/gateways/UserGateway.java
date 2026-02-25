package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<User> saveUser(User user);
}
