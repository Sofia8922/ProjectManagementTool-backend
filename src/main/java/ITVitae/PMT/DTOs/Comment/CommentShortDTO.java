package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentShortDTO(
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Content is required")
    String content
) {
    public static CommentShortDTO fromEntity(Comment comment) {
        return new CommentShortDTO(
            comment.getId(),
            comment.getContent()
        );
    }
}
