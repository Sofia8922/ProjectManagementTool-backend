package ITVitae.PMT.DTOs.Tag;

import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagCreateDTO(
        @NotBlank(message = "Tag must have a name")
        String name,
        @NotNull(message = "Tag must have a colour")
        Constants.Colour colour,
        @NotNull(message = "Tag must have an project id")
        Long projectId
) {
    public Tag toEntity() {
        Tag tag = new Tag();
        tag.setName(this.name);
        tag.setColour(this.colour);
        return tag;
    }
}
