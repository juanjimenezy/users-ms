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
                .map(user -> ResponseEntity.ok(createResponseDTO(user, HttpStatus.CREATED.getReasonPhrase(), "User created successfully")))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NO_CONTENT.getReasonPhrase(), "No hay datos para el id proporcionado"), HttpStatus.OK)))
                .onErrorResume(error -> {
                    return Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NOT_FOUND.getReasonPhrase(), "Error:".concat(error.getMessage())), HttpStatus.NOT_FOUND));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDTO>> getUserById(@PathVariable("id") Long id) {
        return userUseCase.getUserByIdentifier(id)
                .map(user -> ResponseEntity.ok(createResponseDTO(user, HttpStatus.OK.getReasonPhrase(), "User retrieved successfully")))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NO_CONTENT.getReasonPhrase(), "No hay datos para el id proporcionado"), HttpStatus.OK)))
                .onErrorResume(error -> {
                    return Mono.just(new ResponseEntity<>(createResponseDTO(null, HttpStatus.NOT_FOUND.getReasonPhrase(), "Error:".concat(error.getMessage())), HttpStatus.NOT_FOUND));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<User>>> getAllUsers() {
        Flux<User> usersFlux = userUseCase.getAllUsers();
        return Mono.just(ResponseEntity.ok(usersFlux));
    }

    private UserResponseDTO createResponseDTO(User user, String codeMessage, String message) {
        if (user != null) {
            return UserResponseDTO.builder()
                    .id(user.getId())
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
