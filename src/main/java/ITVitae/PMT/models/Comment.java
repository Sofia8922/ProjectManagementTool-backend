package ITVitae.PMT.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "comment must have content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Task getTask() { return task; }

    public void setTask(Task task) {
        this.task = task;
    }
}
