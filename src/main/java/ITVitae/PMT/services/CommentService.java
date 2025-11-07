package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Comment;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    final private CommentRepository commentRepository;
    final private AccountRepository accountRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
    }

    public CommentDTO createComment(CommentCreateDTO createDTO) {
        Comment comment = createDTO.toEntity();
        Account author = accountRepository.findById(createDTO.authorId())
                .orElseThrow(() -> new RuntimeException("Author id not found"));

        if(author.getRole() == Account.UserRole.CUSTOMER) new RuntimeException("Customers cannot comment");

        comment.setAuthor(author);

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
}
