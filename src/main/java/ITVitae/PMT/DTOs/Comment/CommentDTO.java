package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;

public record CommentDTO(
    @NotBlank(message = "Content is required")
    String content,
    Account author
) {
    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
            comment.getContent(),
            comment.getAuthor()
        );
    }
}
