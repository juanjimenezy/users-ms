package co.com.pragma.usersms.redis.template;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveRedisTemplateAdapterOperationsTest {

    @Mock
    private ReactiveRedisConnectionFactory connectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveCacheTemplateAdapter adapter;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        Long expirationMillis = 60000L;
        user = User.builder()
                .id(1L)
                .idReqres(1L)
                .firstName("name")
                .avatar("http://www.google.com.co/")
                .email("email")
                .build();

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setIdReqres(1L);
        userEntity.setFirstName("name");
        userEntity.setAvatar("http://www.google.com.co/");
        userEntity.setEmail("email");

        when(objectMapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(objectMapper.map(userEntity, User.class)).thenReturn(user);

        adapter = new ReactiveCacheTemplateAdapter(connectionFactory, objectMapper, expirationMillis);
    }


}