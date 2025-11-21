package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentShortDTO(
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Content is required")
    String content,
    @NotNull(message = "Comment must have an author")
    AccountShortDTO author
) {
    public static CommentShortDTO fromEntity(Comment comment) {
        return new CommentShortDTO(
            comment.getId(),
            comment.getContent(),
            AccountShortDTO.fromEntity(comment.getAuthor())
        );
    }
}
