package co.com.pragma.usersms.r2dbc.adapter;

import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.model.users.gateways.UserRepository;
import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import co.com.pragma.usersms.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.usersms.r2dbc.repository.MyReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, MyReactiveRepository> implements UserRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Flux<User> findByName(String name) {
        return repository.findByFirstNameOrLastNameLike(name)
                .map(userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> findById(Long id) {
        return repository.findById(id).map(
                userEntity -> mapper.map(userEntity, User.class)
        );
    }

    @Override
    public Mono<User> findByIdReqres(Long idReqres) {
        return repository.findByIdReqres(idReqres).map(
                userEntity -> mapper.map(userEntity, User.class)
        );
    }
}
