package co.com.pragma.usersms.redis.template;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.UserRedisRepository;
import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import co.com.pragma.usersms.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveRedisTemplateAdapter extends ReactiveTemplateAdapterOperations<User, String, UserEntity> implements UserRedisRepository
{

    private final Long expirationMillies;

    public ReactiveRedisTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper,@Value("${spring.data.redis.expirationMillis}") Long expirationMillies) {
        super(connectionFactory, mapper, d -> mapper.map(d, User.class));
        this.expirationMillies = expirationMillies;
    }

    @Override
    public Mono<User> getUserRedis(String key) {
        return findById(key);
    }

    @Override
    public Mono<User> saveRedis(String key, User user) {
        return save(key, user,expirationMillies);
    }
}
