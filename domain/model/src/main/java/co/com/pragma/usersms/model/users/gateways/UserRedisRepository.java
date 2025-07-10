package co.com.pragma.usersms.model.users.gateways;

import co.com.pragma.usersms.model.users.User;
import reactor.core.publisher.Mono;

public interface UserRedisRepository {

    Mono<User> getUserRedis(String key);
    Mono<User> saveRedis(String key, User user);

}
