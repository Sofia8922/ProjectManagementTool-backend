package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentEditDTO(
        @NotBlank(message = "Content is required")
        String content
) {
    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }
}
