package ITVitae.PMT.DTOs.Task;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.DTOs.Comment.CommentShortDTO;
import ITVitae.PMT.DTOs.Project.ProjectShortDTO;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TaskDTO(
        @NotNull(message =  "Id is required")
        Long id,
        @NotBlank(message = "Task must have a name")
        String name,
        String content,
        @NotNull(message = "Task must have an status")
        Constants.TaskStatus status,
        @NotNull(message = "Task must have a project")
        ProjectShortDTO project,
        List<CommentShortDTO> comments,
        @NotNull(message = "Task must have an creator")
        AccountShortDTO creator,
        //tags
        List<AccountShortDTO> assignedDevelopers
) {
    public static TaskDTO fromEntity(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getContent(),
                task.getTaskStatus(),
                ProjectShortDTO.fromEntity(task.getProject()),
                task.getCommentList().stream().map(CommentShortDTO::fromEntity).toList(),
                AccountShortDTO.fromEntity(task.getTaskCreator()),
                task.getAssignedDevelopers().stream().map(AccountShortDTO::fromEntity).toList()
        );
    }
}
