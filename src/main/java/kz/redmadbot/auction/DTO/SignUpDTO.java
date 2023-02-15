package kz.redmadbot.auction.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpDTO {
    @NotBlank(message = "Email should not be empty")
    @Size(min = 5, message = "Username should contain at least 5 characters")
    private String email;

    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;

    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String passwordConfirmation;

    public SignUpDTO(String email, String password, String passwordConfirmation) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public SignUpDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
