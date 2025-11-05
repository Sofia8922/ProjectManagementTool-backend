package ITVitae.PMT.models;

import ITVitae.PMT.repositories.AccountRepository;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;
    private String content;

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
}
