package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Mono;

public interface ReqresRepository {
     Mono<User> getUserById(Long id);

}
