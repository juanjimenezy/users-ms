package co.com.pragma.usersms.consumer.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqresResponseDTO {

    private UserData data;

    @Getter
    @Setter
    public static class UserData {
        private Long id;
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String avatar;
    }
}
