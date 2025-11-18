package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.DTOs.Comment.CommentEditDTO;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.miscellaneous.ErrorHandler;
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

    public CommentDTO createComment(CommentCreateDTO createDTO, Long userId) {
        Comment comment = createDTO.toEntity();
        Account author = accountRepository.findById(createDTO.authorId())
                .orElseGet(() -> ErrorHandler.throwError("Author id", Constants.Errors.NOT_FOUND));
        if(author.getRole() == Constants.UserRole.CUSTOMER)
                ErrorHandler.throwError("Customers cannot comment", Constants.Errors.CUSTOM);
        Task task = taskRepository.findById(createDTO.taskId())
                .orElseGet(() -> ErrorHandler.throwError("Task id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithId(userId, createDTO.authorId());
        CheckCredentials.checkWithTask(userId, createDTO.taskId(), true);

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

    public CommentDTO editComment(Long id, CommentEditDTO editDTO, Long userId) {
        Comment comment = commentRepository.findById(id)
            .orElseGet(() -> ErrorHandler.throwError("Comment id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithAccount(userId, comment.getAuthor());

        if(!editDTO.content().equals(Constants.noEdit))
            comment.setContent(editDTO.content());
        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    public ResponseEntity<String> deleteComment(Long id, Long userId)
    {
        Comment comment = commentRepository.findById(id)
            .orElseGet(() -> ErrorHandler.throwError("Comment id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithAccount(userId, comment.getAuthor());

        commentRepository.delete(comment);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
