package ITVitae.PMT.DTOs.Task;

import ITVitae.PMT.models.Task;
import ITVitae.PMT.miscellaneous.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateDTO(
        @NotBlank(message = "Task must have a name")
        String name,
        String content,
        @NotNull(message = "Task must have an creator")
        Long creatorId,
        @NotNull(message = "Task must have an project")
        Long projectId
) {
    public Task toEntity() {
        Task task = new Task();
        task.setName(this.name);
        task.setContent(this.content);
        task.setTaskStatus(Constants.TaskStatus.PENDING);
        return task;
    }
}
