package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountLoginReturnDTO(
        @NotNull
        Long id,
        @NotBlank
        String name
) {
    public static AccountLoginReturnDTO fromEntity(Account account) {
        return new AccountLoginReturnDTO(
                account.getId(),
                account.getName()
        );
    }
}
