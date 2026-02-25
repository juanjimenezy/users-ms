package co.com.pragma.usersms.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean()
public class UserEntity {

    private Long id;
    private Long idReqres;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public UserEntity() {
    }

    public UserEntity(Long id, Long idReqres, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.idReqres = idReqres;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DynamoDbAttribute("idReqres")
    public Long getIdReqres() {
        return idReqres;
    }

    public void setIdReqres(Long idReqres) {
        this.idReqres = idReqres;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "secondary_index")
    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDbAttribute("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDbAttribute("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDbAttribute("avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
