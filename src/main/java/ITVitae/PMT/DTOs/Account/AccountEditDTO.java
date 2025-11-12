package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountEditDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Password is required")
        String password
) {
    public Account toEntity() {
        Account user = new Account();
        user.setName(this.name);
        user.setPassword(this.password);
        return user;
    }
}
