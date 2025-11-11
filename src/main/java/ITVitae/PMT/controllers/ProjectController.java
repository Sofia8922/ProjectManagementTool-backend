package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.DTOs.Project.ProjectCreateDTO;
import ITVitae.PMT.DTOs.Project.ProjectDTO;
import ITVitae.PMT.DTOs.Project.ProjectEditDTO;
import ITVitae.PMT.miscellaneous.Constants;
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

    @GetMapping("/accounts/{id}")
    public ResponseEntity<List<ProjectDTO>> getProjectByAccount(@PathVariable Long id)
    {
        List<ProjectDTO> commentDTO = projectService.findByAccountId(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }

    @GetMapping("/{id}/developers")
    public ResponseEntity<List<AccountDTO>> getProjectDevelopers(@PathVariable Long id)
    {
        List<AccountDTO> accountDTOs = projectService.findProjectDevelopers(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTOs);
    }

    @GetMapping("/{id}/customers")
    public ResponseEntity<List<AccountDTO>> getProjectCustomers(@PathVariable Long id)
    {
        List<AccountDTO> accountDTOs = projectService.findProjectCustomers(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> putProject(@PathVariable Long id, @Valid @RequestBody ProjectEditDTO createDTO)
    {
        ProjectDTO created = projectService.editProject(id, createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PutMapping("/{taskId}/addAccount/{accountId}")
    public ResponseEntity<String> addAccount(@PathVariable Long taskId, @PathVariable Long accountId)
    {
        return projectService.addAccount(taskId, accountId);
    }

    @PutMapping("/{taskId}/removeAccount/{accountId}")
    public ResponseEntity<String> removeAccount(@PathVariable Long taskId, @PathVariable Long accountId)
    {
        return projectService.removeAccount(taskId, accountId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id)
    {
        return projectService.deleteProject(id);
    }
}
