package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;

public record CommentShortDTO(
    @NotBlank(message = "Content is required")
    String content,
    Account author
) {
    public static CommentShortDTO fromEntity(Comment comment) {
        return new CommentShortDTO(
            comment.getContent(),
            comment.getAuthor()
        );
    }
}
