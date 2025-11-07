package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectCreateDTO(
    @NotBlank(message = "Task must have a name")
    String name,
    String description,
    @NotNull(message = "Task must have an creator")
    Long creatorId
){
    public Project toEntity() {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStatusScrapped(false);
        return project;
    }
}
