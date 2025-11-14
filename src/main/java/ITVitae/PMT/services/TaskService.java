package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.miscellaneous.ErrorHandler;
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
                .orElseGet(() -> ErrorHandler.throwError("Creator Id", Constants.Errors.NOT_FOUND));
        if(creator.getRole() == Constants.UserRole.CUSTOMER)
            ErrorHandler.throwError("Customers cannot create tasks", Constants.Errors.CUSTOM);
        Project project = projectRepository.findById(createDTO.projectId())
                .orElseGet(() -> ErrorHandler.throwError("Project Id", Constants.Errors.NOT_FOUND));
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
                .orElseGet(() -> ErrorHandler.throwError("Task Id", Constants.Errors.NOT_FOUND));
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
                .orElseGet(() -> ErrorHandler.throwError("Comment Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Transactional
    public ResponseEntity<String> addTag(Long taskId, Long tagId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseGet(() -> ErrorHandler.throwError("Task Id", Constants.Errors.NOT_FOUND));
        Tag tag = tagRepository.findById(tagId)
                .orElseGet(() -> ErrorHandler.throwError("Tag Id", Constants.Errors.NOT_FOUND));
        List<Tag> existingTags = task.getTags();
        if(existingTags.contains(tag))
            ErrorHandler.throwError("Tag", Constants.Errors.ALREAD_EXISTS);
        List<Tag> allowedTags = task.getProject().getTags();
        if(!allowedTags.contains(tag))
            ErrorHandler.throwError("Tag", Constants.Errors.WRONG_PROJECT);
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.addTag(tag);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> removeTag(Long taskId, Long tagId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseGet(() -> ErrorHandler.throwError("Task Id", Constants.Errors.NOT_FOUND));
        Tag tag = tagRepository.findById(tagId)
                .orElseGet(() -> ErrorHandler.throwError("Tag Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.removeTag(tag);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Transactional
    public ResponseEntity<String> addDeveloper(Long taskId, Long accountId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseGet(() -> ErrorHandler.throwError("Tag Id", Constants.Errors.NOT_FOUND));
        Account account = accountRepository.findById(accountId)
                .orElseGet(() -> ErrorHandler.throwError("Account Id", Constants.Errors.NOT_FOUND));
        if(account.getRole() != Constants.UserRole.DEVELOPER)
            ErrorHandler.throwError("Account must be a developer!", Constants.Errors.CUSTOM);
        List<Account> existingDevs = task.getAssignedDevelopers();
        for(Account dev : existingDevs)
            if (dev.equals(account))
                ErrorHandler.throwError("Account", Constants.Errors.ALREAD_EXISTS);
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.addAssignedDeveloper(account);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> removeDeveloper(Long taskId, Long accountId, Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseGet(() -> ErrorHandler.throwError("Task Id", Constants.Errors.NOT_FOUND));
        Account account = accountRepository.findById(accountId)
                .orElseGet(() -> ErrorHandler.throwError("Account Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithProject(userId, task.getProject().getId(), true);

        task.removeAssignedDeveloper(account);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
