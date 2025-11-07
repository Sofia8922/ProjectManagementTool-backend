package ITVitae.PMT.DTOs.Comment;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.DTOs.Task.TaskShortDTO;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentDTO(
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Content is required")
    String content,
    @NotNull(message = "Comment must have an author")
    AccountShortDTO author,
    @NotNull(message = "Comment must have a task")
    TaskShortDTO task
) {
    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
            comment.getId(),
            comment.getContent(),
            AccountShortDTO.fromEntity(comment.getAuthor()),
            TaskShortDTO.fromEntity(comment.getTask())
        );
    }
}
