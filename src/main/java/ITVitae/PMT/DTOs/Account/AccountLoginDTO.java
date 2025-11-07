package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;

public record AccountLoginDTO(
        @NotBlank
        String password
) {
    public static AccountLoginDTO fromEntity(Account account) {
        return new AccountLoginDTO(
                account.getPassword()
        );
    }
}
