package ITVitae.PMT.DTOs.Task;

import ITVitae.PMT.miscellaneous.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record TaskEditDTO(
        @NotBlank(message = "Task must have a name")
        String name,
        String content,
        Optional<Constants.TaskStatus> status
) {
    @JsonCreator
    public TaskEditDTO(
            @JsonProperty("name") String name,
            @JsonProperty("content") String content,
            @JsonProperty("status") String statusStr
    ) {
        this(name, content, parseStatus(statusStr));
    }

    private static Optional<Constants.TaskStatus> parseStatus(String statusStr) {
         return statusStr.equals(Constants.noEdit) ?
            Optional.empty() :
            Optional.of(Constants.TaskStatus.valueOf(statusStr));
    }
}
