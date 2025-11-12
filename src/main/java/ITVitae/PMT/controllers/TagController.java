package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Tag.TagCreateDTO;
import ITVitae.PMT.DTOs.Tag.TagDTO;
import ITVitae.PMT.DTOs.Tag.TagEditDTO;
import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.services.TagService;
import ITVitae.PMT.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping()
    public ResponseEntity<TagDTO> postTag(@Valid @RequestBody TagCreateDTO createDTO)
    {
        TagDTO created = tagService.createTag(createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<TagDTO>> getAllTags()
    {
        List<TagDTO> tagDTOs = tagService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tagDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable Long id)
    {
        TagDTO commentDTO = tagService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> putTag(@PathVariable Long id, @Valid @RequestBody TagEditDTO editDTO)
    {
        TagDTO created = tagService.editTag(id, editDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id)
    {
        return tagService.deleteTag(id);
    }
}