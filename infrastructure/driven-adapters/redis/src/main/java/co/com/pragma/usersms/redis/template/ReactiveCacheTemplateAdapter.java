package co.com.pragma.usersms.redis.template;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.UserCacheRepository;
import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import co.com.pragma.usersms.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveCacheTemplateAdapter extends ReactiveTemplateAdapterOperations<User, String, UserEntity> implements UserCacheRepository
{

    private final Long expirationMillies;

    public ReactiveCacheTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper, @Value("${spring.data.redis.expirationMillis}") Long expirationMillies) {
        super(connectionFactory, mapper, d -> mapper.map(d, User.class));
        this.expirationMillies = expirationMillies;
    }

    @Override
    public Mono<User> getUser(String key) {
        return findById(key);
    }

    @Override
    public Mono<User> saveUser(String key, User user) {
        return save(key, user,expirationMillies);
    }
}
