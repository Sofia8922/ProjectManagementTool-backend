package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountShortDTO(
        @NotNull(message =  "Id is required")
        Long id,
        @NotBlank(message = "Email is required")
        String email,
        String name,
        @NotNull(message = "Role is required")
        Constants.UserRole role
) {
    public static AccountShortDTO fromEntity(Account account) {
        return new AccountShortDTO(
                account.getId(),
                account.getEmail(),
                account.getName(),
                account.getRole()
        );
    }
}
