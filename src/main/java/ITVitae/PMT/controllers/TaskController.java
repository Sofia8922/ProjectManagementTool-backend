package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> postTask(@Valid @RequestBody TaskCreateDTO createDTO)
    {
        TaskDTO created = taskService.createTask(createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks()
    {
        List<TaskDTO> taskDTOs = taskService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(taskDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id)
    {
        TaskDTO commentDTO = taskService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> putTask(@PathVariable Long id, @Valid @RequestBody TaskEditDTO editDTO)
    {
        TaskDTO created = taskService.editTask(id, editDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PutMapping("/{taskId}/addTag/{tagId}")
    public ResponseEntity<String> addTag(@PathVariable Long taskId, @PathVariable Long tagId)
    {
        return taskService.addTag(taskId, tagId);
    }

    @PutMapping("/{taskId}/removeTag/{tagId}")
    public ResponseEntity<String> removeTag(@PathVariable Long taskId, @PathVariable Long tagId)
    {
        return taskService.removeTag(taskId, tagId);
    }

    @PutMapping("/{taskId}/addAccount/{accountId}")
    public ResponseEntity<String> addDeveloper(@PathVariable Long taskId, @PathVariable Long accountId)
    {
        return taskService.addDeveloper(taskId, accountId);
    }

    @PutMapping("/{taskId}/removeAccount/{accountId}")
    public ResponseEntity<String> removeDeveloper(@PathVariable Long taskId, @PathVariable Long accountId)
    {
        return taskService.removeDeveloper(taskId, accountId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id)
    {
        return taskService.deleteTask(id);
    }
}