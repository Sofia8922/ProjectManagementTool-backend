package ITVitae.PMT.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
    //tags
    private boolean scrappedStatus;
    @ManyToOne
    @JoinColumn(name = "projectCreator_id")
    private Account projectCreator;

    public Project() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean isStatusScrapped() {
        return scrappedStatus;
    }

    public void setStatusScrapped(boolean scrappedStatus) {
        this.scrappedStatus = scrappedStatus;
    }

    public Account getCreator() {
        return projectCreator;
    }

    public void setCreator(Account creator) {
        this.projectCreator = creator;
    }
}
