package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;

public record CommentDTO(
    @NotBlank(message = "Content is required")
    String content,
    AccountShortDTO author
) {
    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
            comment.getContent(),
            AccountShortDTO.fromEntity(comment.getAuthor())
        );
    }
}
