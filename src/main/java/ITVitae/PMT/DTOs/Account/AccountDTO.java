package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDTO(
        @NotBlank(message = "Content is required")
        String name,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull(message = "Role is required")
        Account.UserRole role
) {
    public static AccountDTO fromEntity(Account account) {
        return new AccountDTO(
            account.getName(),
            account.getEmail(),
            account.getPassword(),
            account.getRole()
        );
    }
}
