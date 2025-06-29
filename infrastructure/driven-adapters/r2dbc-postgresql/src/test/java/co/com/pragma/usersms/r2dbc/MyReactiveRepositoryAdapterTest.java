package co.com.pragma.usersms.r2dbc;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.r2dbc.adapter.MyReactiveRepositoryAdapter;
import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import co.com.pragma.usersms.r2dbc.repository.MyReactiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private User user;
    private UserEntity userEntity;

    @Test
    void mustFindValueById() {


        when(repository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<User> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(userEntity));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<User> result = repositoryAdapter.findByExample(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }
}
