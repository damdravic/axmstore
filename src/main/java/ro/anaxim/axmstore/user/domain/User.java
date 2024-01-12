package ro.anaxim.axmstore.user.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
    @NotNull
    private Long userId;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "LAst name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email name cannot be empty")
    @Email(message = "Invalid Email.Please enter a valid mail address")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;


}
