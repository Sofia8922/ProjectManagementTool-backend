package ITVitae.PMT.DTOs.Project;

import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.miscellaneous.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public record ProjectEditDTO(
    @NotBlank(message = "Task must have a name")
    String name,
    String description,
    Optional<Boolean> scrapped,
    List<AccountShortDTO>projectDevelopers,
    List<AccountShortDTO>projectCustomers
){
    @JsonCreator
    public ProjectEditDTO(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("scrapped") String scrappedStr,
        @JsonProperty("projectDevelopers") List<AccountShortDTO> projectDevelopers,
        @JsonProperty("projectCustomers") List<AccountShortDTO> projectCustomers
    ) {
        this(name, description, parseBool(scrappedStr), projectDevelopers, projectCustomers);
    }

    private static Optional<Boolean> parseBool(String scrappedStr) {
        return scrappedStr.equals(Constants.noEdit) ?
                Optional.empty() :
                Optional.of(Boolean.valueOf(scrappedStr));
    }
}
