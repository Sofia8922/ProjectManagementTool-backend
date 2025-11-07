package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectEditDTO(
    @NotBlank(message = "Task must have a name")
    String name,
    String description
){
    public Project toEntity() {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        return project;
    }
}
