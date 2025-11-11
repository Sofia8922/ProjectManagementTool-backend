package ITVitae.PMT.models;

import ITVitae.PMT.miscellaneous.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private String content;
    private Constants.TaskStatus taskStatus;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "taskCreator_id")
    private Account taskCreator;
    @ManyToMany
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "task_assignedDeveloper",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "assignedDeveloper_id")
    )
    private List<Account> assignedDevelopers = new ArrayList<>();

    public Task() {

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

    public Account getTaskCreator() {
        return taskCreator;
    }

    public void setTaskCreator(Account taskCreator) {
        this.taskCreator = taskCreator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Constants.TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Constants.TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<Account> getAssignedDevelopers() {
        return assignedDevelopers;
    }

    public void setAssignedDevelopers(List<Account> assignedDevelopers) {
        this.assignedDevelopers = assignedDevelopers;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
