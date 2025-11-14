package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import ITVitae.PMT.miscellaneous.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountCreateDTO(
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Email is required")
        String name,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull(message = "Role is required")
        Constants.UserRole role
) {
    public Account toEntity() {
        Account user = new Account();
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setRole(this.role);
        return user;
    }
}
