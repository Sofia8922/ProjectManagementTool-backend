package ITVitae.PMT.DTOs.Task;

import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskShortDTO(
    @NotNull(message =  "Id is required")
    Long id,
    @NotBlank(message = "Task must have a name")
    String name,
    String content,
    @NotNull(message = "Task must have an status")
    Constants.TaskStatus status
) {
    public static TaskShortDTO fromEntity(Task task) {
        return new TaskShortDTO(
                task.getId(),
                task.getName(),
                task.getContent(),
                task.getTaskStatus()
        );
    }
}
