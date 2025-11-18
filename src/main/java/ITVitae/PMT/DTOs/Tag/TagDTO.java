package ITVitae.PMT.DTOs.Tag;

import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagDTO(
        @NotNull(message =  "Id is required")
        Long id,
        @NotBlank(message = "Tag must have a name")
        String name,
        @NotNull(message = "Tag must have a colour")
        Constants.Colour colour
) {
    public static TagDTO fromEntity(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getName(),
                tag.getColour()
        );
    }
}
