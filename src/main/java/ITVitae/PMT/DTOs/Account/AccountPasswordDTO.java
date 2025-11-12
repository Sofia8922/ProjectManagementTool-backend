package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;

public record AccountPasswordDTO(
        @NotBlank
        String password
) {
    public static AccountPasswordDTO fromEntity(Account account) {
        return new AccountPasswordDTO(
                account.getPassword()
        );
    }
}
