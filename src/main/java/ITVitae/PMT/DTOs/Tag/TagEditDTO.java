package ITVitae.PMT.DTOs.Tag;

import ITVitae.PMT.miscellaneous.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record TagEditDTO(
        @NotBlank(message = "Tag must have a name")
        String name,
        @NotNull(message = "Tag must have a colour")
        Optional<Constants.Colour> colour
) {
    @JsonCreator
    public TagEditDTO(
            @JsonProperty("name") String name,
            @JsonProperty("colour") String colourStr
    ) {
        this(name, parseStatus(colourStr));
    }

    private static Optional<Constants.Colour> parseStatus(String statusStr) {
         return statusStr.equals(Constants.noEdit) ?
            Optional.empty() :
            Optional.of(Constants.Colour.valueOf(statusStr));
    }
}
