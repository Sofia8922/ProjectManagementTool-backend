package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentDTO;
import ITVitae.PMT.DTOs.Project.ProjectCreateDTO;
import ITVitae.PMT.DTOs.Project.ProjectDTO;
import ITVitae.PMT.DTOs.Project.ProjectEditDTO;
import ITVitae.PMT.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> postProject(@Valid @RequestBody ProjectCreateDTO createDTO)
    {
        ProjectDTO created = projectService.createProject(createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getAllProjects()
    {
        List<ProjectDTO> projectDTOS = projectService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(projectDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id)
    {
        ProjectDTO projectDTO = projectService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> putProject(@PathVariable Long id, @Valid @RequestBody ProjectEditDTO createDTO)
    {
        ProjectDTO created = projectService.editProject(id, createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id)
    {
        return projectService.deleteProject(id);
    }
}
