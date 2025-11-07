package ITVitae.PMT.DTOs.Account;

import ITVitae.PMT.DTOs.Comment.CommentShortDTO;
import ITVitae.PMT.DTOs.Project.ProjectShortDTO;
import ITVitae.PMT.DTOs.Task.TaskShortDTO;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AccountDTO(
        @NotNull(message =  "Id is required")
        Long id,
        @NotBlank(message = "Email is required")
        String email,
        String name,
        @NotBlank(message = "Password is required")
        String password,
        //assigned projects
        @NotNull(message = "Role is required")
        Constants.UserRole role,
        List<CommentShortDTO> madeComments,
        List<TaskShortDTO> madeTasks,
        List<ProjectShortDTO> madeProjects
) {
    public static AccountDTO fromEntity(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getEmail(),
            account.getName(),
            account.getPassword(),
            account.getRole(),
            account.getCommentList().stream().map(CommentShortDTO::fromEntity).toList(),
            account.getMadeTaskList().stream().map(TaskShortDTO::fromEntity).toList(),
            account.getMadeProjectList().stream().map(ProjectShortDTO::fromEntity).toList()
        );
    }
}
