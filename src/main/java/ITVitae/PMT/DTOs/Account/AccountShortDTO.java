package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.DTOs.Comment.CommentShortDTO;
import ITVitae.PMT.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AccountShortDTO(
        @NotBlank(message = "Content is required")
        String name,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull(message = "Role is required")
        Account.UserRole role,
        List<CommentShortDTO> comments
) {
    public static AccountShortDTO fromEntity(Account account) {
        return new AccountShortDTO(
                account.getName(),
                account.getEmail(),
                account.getPassword(),
                account.getRole(),
                account.getCommentList().stream().map(CommentShortDTO::fromEntity).toList()
        );
    }
}
