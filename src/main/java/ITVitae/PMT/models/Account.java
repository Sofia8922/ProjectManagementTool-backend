package ITVitae.PMT.models;

import ITVitae.PMT.miscellaneous.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @ManyToMany(mappedBy = "developers")
    private List<Project> developers = new ArrayList<>();
    @ManyToMany(mappedBy = "customers")
    private List<Project> customers= new ArrayList<>();
    @NotNull
    private Constants.UserRole role;
    @ManyToMany(mappedBy = "assignedDevelopers")
    private List<Task> assignedTasks = new ArrayList<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "taskCreator")
    private List<Task> madeTaskList = new ArrayList<>();
    @OneToMany(mappedBy = "projectCreator")
    private List<Project> madeProjectList = new ArrayList<>();

    public Account() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Constants.UserRole getRole() {
        return role;
    }

    public void setRole(Constants.UserRole role) {
        this.role = role;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public List<Task> getMadeTaskList() {
        return madeTaskList;
    }

    public List<Task> getAssignedTasks() { return assignedTasks; }

    public List<Project> getMadeProjectList() {
        return madeProjectList;
    }
}
