package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.models.Tag;
import ITVitae.PMT.models.Task;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.ProjectRepository;
import ITVitae.PMT.repositories.TagRepository;
import ITVitae.PMT.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    final private TaskRepository taskRepository;
    final private AccountRepository accountRepository;
    final private ProjectRepository projectRepository;
    final private TagRepository tagRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, AccountRepository accountRepository, ProjectRepository projectRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
    }

    public TaskDTO createTask(TaskCreateDTO createDTO) {
        Task task = createDTO.toEntity();
        Account creator = accountRepository.findById(createDTO.creatorId())
                .orElseThrow(() -> new RuntimeException("Creator id not found"));
        if(creator.getRole() == Constants.UserRole.CUSTOMER) new RuntimeException("Customers cannot create tasks");
        Project project = projectRepository.findById(createDTO.projectId())
                .orElseThrow(() -> new RuntimeException("Project id not found"));

        task.setTaskCreator(creator);
        task.setProject(project);

        Task savedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(savedTask);
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskDTO::fromEntity)
                .toList();
    }

    public TaskDTO findById(Long id) {
        return taskRepository.findById(id)
                .map(TaskDTO::fromEntity)
                .orElse(null);
    }

    @Transactional
    public TaskDTO editTask(Long id, TaskEditDTO editDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task id not found"));

        if(!editDTO.name().equals(Constants.noEdit))
            task.setName(editDTO.name());
        if(!editDTO.content().equals(Constants.noEdit))
            task.setContent(editDTO.content());
        if(!editDTO.status().isEmpty())
            task.setTaskStatus(editDTO.status().get());
        taskRepository.save(task);
        return TaskDTO.fromEntity(task);
    }

    public ResponseEntity<String> deleteTask(Long id)
    {
        if(taskRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> addTag(Long taskId, long tagId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        Tag tag = tagRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tag id not found"));

        task.addTag(tag);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
