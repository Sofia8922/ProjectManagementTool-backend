package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.DTOs.Task.TaskShortDTO;
import ITVitae.PMT.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectDTO (
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Task must have a name")
    String name,
    String description,
    List<TaskShortDTO> tasks,
    boolean scrappedStatus
    ){
    public static ProjectDTO fromEntity(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTasks().stream().map(TaskShortDTO::fromEntity).toList(),
                project.isStatusScrapped()
        );
    }
}
