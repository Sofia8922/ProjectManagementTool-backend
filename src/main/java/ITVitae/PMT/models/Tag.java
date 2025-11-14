package ITVitae.PMT.models;

import ITVitae.PMT.miscellaneous.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Constants.Colour colour;
    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Tag() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constants.Colour getColour() {
        return colour;
    }

    public void setColour(Constants.Colour colour) {
        this.colour = colour;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
