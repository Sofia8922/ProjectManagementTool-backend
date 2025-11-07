package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.DTOs.Comment.CommentEditDTO;
import ITVitae.PMT.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> postComment(@Valid @RequestBody CommentCreateDTO createDTO)
    {
        CommentDTO created = commentService.createComment(createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAllComments()
    {
        List<CommentDTO> commentDTOs = commentService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(commentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id)
    {
        CommentDTO commentDTO = commentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> putComment(@PathVariable Long id, @Valid @RequestBody CommentEditDTO createDTO)
    {
        CommentDTO created = commentService.editComment(id, createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id)
    {
        return commentService.deleteComment(id);
    }
}
