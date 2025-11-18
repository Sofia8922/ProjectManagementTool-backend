package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectShortDTO(
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Task must have a name")
    String name,
    String description,
    boolean finishedStatus,
    boolean scrappedStatus
    ){
    public static ProjectShortDTO fromEntity(Project project) {
        return new ProjectShortDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.isStatusFinished(),
                project.isStatusScrapped()
        );
    }
}
