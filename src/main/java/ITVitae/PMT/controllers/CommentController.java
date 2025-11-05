package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.services.CommentService;
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
}
