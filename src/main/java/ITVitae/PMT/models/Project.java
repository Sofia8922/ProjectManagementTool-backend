package ITVitae.PMT.models;

import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.miscellaneous.DummyData;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
    private boolean scrappedStatus;

    @ManyToOne
    @JoinColumn(name = "projectCreator_id")
    private Account projectCreator;

    @ManyToMany()
    @JoinTable(
            name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private List<Account> developers = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "project_customer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Account> customers = new ArrayList<>();

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

    public List<Tag> getTags() {
        return tags;
    }

    @Transactional
    public boolean isStatusFinished() {
        if(DummyData.starting) return false;
        if (tasks.isEmpty()) return false;
        for (Task task : tasks) {
            if (!(task.getTaskStatus().equals(Constants.TaskStatus.COMPLETED) ||
                    task.getTaskStatus().equals(Constants.TaskStatus.SCRAPPED)))
                return false;
        }
        return true;
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

    public List<Account> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Account> developers) {
        this.developers = developers;
    }

    public void setCustomers(List<Account> customers) {
        this.customers = customers;
    }

    public void addDeveloper(Account account) { developers.add(account); }

    public void removeDeveloper(Account account) { developers.remove(account); }

    public List<Account> getCustomers() {
        return customers;
    }

    public void addCustomer(Account account) { customers.add(account); }

    public void removeCustomer(Account account) { customers.remove(account); }
}
