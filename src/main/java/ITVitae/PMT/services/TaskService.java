package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.models.*;
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

    public TaskDTO createTask(TaskCreateDTO createDTO, Long userId) {
        Task task = createDTO.toEntity();
        Account creator = accountRepository.findById(createDTO.creatorId())
                .orElseThrow(() -> new RuntimeException("Creator id not found"));
        if(creator.getRole() == Constants.UserRole.CUSTOMER) throw new RuntimeException("Customers cannot create tasks");
        Project project = projectRepository.findById(createDTO.projectId())
                .orElseThrow(() -> new RuntimeException("Project id not found"));
        CheckCredentials.checkWithProject(userId, createDTO.projectId(), true);

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
    public TaskDTO editTask(Long id, TaskEditDTO editDTO, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        if(!editDTO.name().equals(Constants.noEdit))
            task.setName(editDTO.name());
        if(!editDTO.content().equals(Constants.noEdit))
            task.setContent(editDTO.content());
        if(!editDTO.status().isEmpty())
            task.setTaskStatus(editDTO.status().get());
        taskRepository.save(task);
        return TaskDTO.fromEntity(task);
    }

    public ResponseEntity<String> deleteTask(Long id, Long userId)
    {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment id not found"));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Transactional
    public ResponseEntity<String> addTag(Long taskId, Long tagId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag id not found"));
        List<Tag> existingTags = task.getTags();
        if(existingTags.contains(tag)) throw new RuntimeException("Tag already added!");
        List<Tag> allowedTags = task.getProject().getTags();
        if(!allowedTags.contains(tag)) throw new RuntimeException("Tag not part of project");
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.addTag(tag);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> removeTag(Long taskId, Long tagId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag id not found"));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.removeTag(tag);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Transactional
    public ResponseEntity<String> addDeveloper(Long taskId, Long accountId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account id not found"));
        if(account.getRole() != Constants.UserRole.DEVELOPER)
            throw new RuntimeException("Account must be a developer!");
        List<Account> existingDevs = task.getAssignedDevelopers();
        for(Account dev : existingDevs)
            if (dev.equals(account))
                throw new RuntimeException("Account already added!");
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.addAssignedDeveloper(account);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> removeDeveloper(Long taskId, Long accountId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task id not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account id not found"));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.removeAssignedDeveloper(account);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
