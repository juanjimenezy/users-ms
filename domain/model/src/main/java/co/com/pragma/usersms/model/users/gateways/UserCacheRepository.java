package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Mono;

public interface UserCacheRepository {

    Mono<User> getUser(String key);
    Mono<User> saveUser(String key, User user);

}
