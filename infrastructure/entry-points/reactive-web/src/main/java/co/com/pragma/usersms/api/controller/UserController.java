package co.com.pragma.usersms.api.controller;

import co.com.pragma.usersms.api.dto.UserResponseDTO;
import co.com.pragma.usersms.model.users.User;
import co.com.pragma.usersms.usecase.users.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> createUser(@PathVariable("id") Long id) {
        return userUseCase.createUser(id)
                .map(user -> ResponseEntity.ok(createResponseDTO(user, HttpStatus.CREATED.value(), "User created successfully")))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.CONFLICT.value(), "Ya este usuario fue creado"), HttpStatus.CONFLICT)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> getUserById(@PathVariable("id") Long id) {
        return userUseCase.getUserByIdentifierInRedis(id)
                .map(user -> ResponseEntity.ok(createResponseDTO(user, HttpStatus.OK.value(), "User retrieved successfully")))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NO_CONTENT.value(), "No hay datos para el id proporcionado"), HttpStatus.OK)))
                .onErrorResume(error ->
                    Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NOT_FOUND.value(), "Error:".concat(error.getMessage())), HttpStatus.NOT_FOUND))
                );
    }

    @GetMapping("/reqres/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> getUserByIdReqres(@PathVariable("id") Long id) {
        return userUseCase.getUserByReqresId(id)
                .map(user -> ResponseEntity.ok(createResponseDTO(user, HttpStatus.OK.value(), "User retrieved successfully")))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NO_CONTENT.value(), "No hay datos para el id proporcionado"), HttpStatus.OK)))
                .onErrorResume(error ->
                     Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NOT_FOUND.value(), "Error:".concat(error.getMessage())), HttpStatus.NOT_FOUND))
                );
    }

    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<Flux<User>>> getUsersByName(@PathVariable("name") String name) {
        Flux<User> usersFlux = userUseCase.getUsersByName(name);
        return Mono.just(ResponseEntity.ok(usersFlux));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<User>>> getAllUsers() {
        Flux<User> usersFlux = userUseCase.getAllUsers();
        return Mono.just(ResponseEntity.ok(usersFlux));
    }

    private UserResponseDTO createResponseDTO(User user, int codeMessage, String message) {
        if (user != null) {
            return UserResponseDTO.builder()
                    .id(user.getId())
                    .idReqres(user.getIdReqres())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .avatar(user.getAvatar())
                    .codeMessage(codeMessage)
                    .message(message)
                    .build();
        }
        return UserResponseDTO.builder()
                .codeMessage(codeMessage)
                .message(message)
                .build();
    }

}
