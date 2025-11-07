package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.DTOs.Comment.CommentEditDTO;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import ITVitae.PMT.models.Task;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.CommentRepository;
import ITVitae.PMT.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    final private CommentRepository commentRepository;
    final private AccountRepository accountRepository;
    final private TaskRepository taskRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, AccountRepository accountRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
        this.taskRepository = taskRepository;
    }

    public CommentDTO createComment(CommentCreateDTO createDTO) {
        Comment comment = createDTO.toEntity();
        Account author = accountRepository.findById(createDTO.authorId())
                .orElseThrow(() -> new RuntimeException("Author id not found"));
        if(author.getRole() == Constants.UserRole.CUSTOMER) new RuntimeException("Customers cannot comment");
        Task task = taskRepository.findById(createDTO.taskId())
                .orElseThrow(() -> new RuntimeException("Task id not found"));

        comment.setAuthor(author);
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.fromEntity(savedComment);
    }

    public List<CommentDTO> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentDTO::fromEntity)
                .toList();
    }

    public CommentDTO findById(Long id) {
        return commentRepository.findById(id)
                .map(CommentDTO::fromEntity)
                .orElse(null);
    }

    public CommentDTO editComment(Long id, CommentEditDTO editDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment id not found"));

        if(!editDTO.content().equals(Constants.noEdit))
            comment.setContent(editDTO.content());
        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    public ResponseEntity<String> deleteComment(Long id)
    {
        if(commentRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
