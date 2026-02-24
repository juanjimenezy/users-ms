package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Mono;

public interface UserSqsGateway {
    Mono<Void> sendUserCreatedEvent(User user);
}
