package ro.anaxim.axmstore.user.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Pass cannot be empty")
    private String password;
}
