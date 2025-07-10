package co.com.pragma.usersms.redis.template;

import co.com.pragma.usersms.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ReactiveRedisTemplateAdapterOperationsTest {

    @Mock
    private ReactiveRedisConnectionFactory connectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveRedisTemplateAdapter adapter;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .idReqres(1L)
                .firstName("name")
                .avatar("http://www.google.com.co/")
                .email("email")
                .build();
        MockitoAnnotations.openMocks(this);

        when(objectMapper.map("value", Object.class)).thenReturn("value");

        adapter = new ReactiveRedisTemplateAdapter(connectionFactory, objectMapper);
    }

    @Test
    void testSave() {
        StepVerifier.create(adapter.save("key", user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void testSaveWithExpiration() {

        StepVerifier.create(adapter.save("key", user, 2))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void testFindById() {

        StepVerifier.create(adapter.findById("key"))
                .verifyComplete();
    }

}