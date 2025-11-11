package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.DTOs.Tag.TagDTO;
import ITVitae.PMT.DTOs.Task.TaskShortDTO;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.models.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectDTO (
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Project must have a name")
    String name,
    String description,
    List<TagDTO> tags,
    List<TaskShortDTO> tasks,
    boolean scrappedStatus,
    @NotBlank(message =  "Project must have a creator")
    AccountShortDTO projectCreator,
    List<AccountShortDTO> developers,
    List<AccountShortDTO> customers
    ){
    public static ProjectDTO fromEntity(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTags().stream().map(TagDTO::fromEntity).toList(),
                project.getTasks().stream().map(TaskShortDTO::fromEntity).toList(),
                project.isStatusScrapped(),
                AccountShortDTO.fromEntity(project.getCreator()),
                project.getDevelopers().stream().map(AccountShortDTO::fromEntity).toList(),
                project.getCustomers().stream().map(AccountShortDTO::fromEntity).toList()
        );
    }
}
