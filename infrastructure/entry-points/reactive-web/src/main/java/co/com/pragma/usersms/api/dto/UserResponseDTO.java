package co.com.pragma.usersms.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public record UserResponseDTO(Long id,Long idReqres, String email, String firstName, String lastName, String avatar,
                              int codeMessage, String message) {

    public UserResponseDTO {
    }
}
