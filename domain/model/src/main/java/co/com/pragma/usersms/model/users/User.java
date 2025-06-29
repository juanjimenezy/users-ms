package co.com.pragma.usersms.model.users;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private Long idReqres;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
