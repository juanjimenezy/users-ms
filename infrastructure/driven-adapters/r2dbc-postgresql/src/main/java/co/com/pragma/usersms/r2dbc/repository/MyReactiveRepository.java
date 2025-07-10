package co.com.pragma.usersms.r2dbc.repository;

import co.com.pragma.usersms.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MyReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {
    Flux<UserEntity> findByFirstName(String name);
    Mono<UserEntity> findByIdReqres(Long idReqres);

    @Query("SELECT u.* FROM user_table u WHERE u.first_name ILIKE CONCAT('%', :name, '%') OR u.last_name ILIKE CONCAT('%', :name, '%')")
    Flux<UserEntity> findByFirstNameOrLastNameLike(String name);

}
