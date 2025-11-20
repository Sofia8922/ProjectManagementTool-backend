package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Account.AccountShortDTO;
import ITVitae.PMT.DTOs.Project.ProjectCreateDTO;
import ITVitae.PMT.DTOs.Project.ProjectDTO;
import ITVitae.PMT.DTOs.Project.ProjectEditDTO;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.ErrorHandler;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    final private ProjectRepository projectRepository;
    final private AccountRepository accountRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, AccountRepository accountRepository) {
        this.projectRepository = projectRepository;
        this.accountRepository = accountRepository;
    }

    public ProjectDTO createProject(ProjectCreateDTO createDTO) {
        Project project = createDTO.toEntity();
        Account creator = accountRepository.findById(createDTO.creatorId())
                .orElseGet(() -> ErrorHandler.throwError("Creator Id", Constants.Errors.NOT_FOUND));

        if(creator.getRole() != Constants.UserRole.OWNER)
            ErrorHandler.throwError("Only owners can create projects", Constants.Errors.CUSTOM);

        project.setCreator(creator);

        Project savedProject = projectRepository.save(project);
        return ProjectDTO.fromEntity(savedProject);
    }

    public List<ProjectDTO> findAll() {
        return projectRepository.findAll()
            .stream()
            .map(ProjectDTO::fromEntity)
            .toList();
    }

    public ProjectDTO findById(Long id) {
        return projectRepository.findById(id)
            .map(ProjectDTO::fromEntity)
            .orElse(null);
    }

    public List<ProjectDTO> findByAccountId(Long id) {
        List<ProjectDTO> allTaskDTOs = findAll();
        List<ProjectDTO> outputTaskDTOs = new ArrayList<>();
        for(ProjectDTO tdto : allTaskDTOs) if (tdto.projectCreator().id().equals(id)) outputTaskDTOs.add(tdto);
        return outputTaskDTOs;
    }

    public List<AccountDTO> findProjectDevelopers(Long id) {
        ProjectDTO projectDTO = findById(id);
        List<AccountDTO> developers = new ArrayList<>();
        for(AccountShortDTO developerDTO : projectDTO.developers()) {
            Account developer = accountRepository.findById(developerDTO.id()).get();
            developers.add(AccountDTO.fromEntity(developer));
        }
        return developers;
    }

    public List<AccountDTO> findProjectCustomers(Long id) {
        ProjectDTO projectDTO = findById(id);
        List<AccountDTO> customers = new ArrayList<>();
        for(AccountShortDTO developerDTO : projectDTO.customers()) {
            Account customer = accountRepository.findById(developerDTO.id()).get();
            customers.add(AccountDTO.fromEntity(customer));
        }
        return customers;
    }

    @Transactional
    public ProjectDTO editProject(Long id, ProjectEditDTO editDTO, Long userId) {
        Project project = projectRepository.findById(id)
                .orElseGet(() -> ErrorHandler.throwError("Project Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithId(userId, project.getCreator().getId());

        if(!editDTO.name().equals(Constants.noEdit))
            project.setName(editDTO.name());
        if(!editDTO.description().equals(Constants.noEdit))
            project.setDescription(editDTO.description());
        if(!editDTO.scrapped().isEmpty())
            project.setStatusScrapped(editDTO.scrapped().get());
        projectRepository.save(project);
        return ProjectDTO.fromEntity(project);
    }

    public ResponseEntity<String> deleteProject(Long id, Long userId)
    {
        if(projectRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        CheckCredentials.checkWithProject(userId, id, false);
        projectRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Transactional
    public ResponseEntity<String> addAccount(Long projectId, Long accountId, Long userId)
    {
        CheckCredentials.checkWithProject(userId, projectId, false);
        Project project = projectRepository.findById(projectId)
                .orElseGet(() -> ErrorHandler.throwError("Project Id", Constants.Errors.NOT_FOUND));
        Account account = accountRepository.findById(accountId)
                .orElseGet(() -> ErrorHandler.throwError("Account Id", Constants.Errors.NOT_FOUND));
        List<Account> existingDevs = project.getDevelopers();
        for(Account dev : existingDevs)
            if (dev.equals(account))
                ErrorHandler.throwError("Developer", Constants.Errors.ALREADY_EXISTS);
        List<Account> existingCustomer = project.getCustomers();
        for(Account dev : existingCustomer)
            if (dev.equals(account))
                ErrorHandler.throwError("Customer", Constants.Errors.ALREADY_EXISTS);

        if(account.getRole() == Constants.UserRole.OWNER)
            ErrorHandler.throwError("Account must be a developer or customer!", Constants.Errors.CUSTOM);

        if(account.getRole() == Constants.UserRole.DEVELOPER) {
            project.addDeveloper(account);
        }
        else
        {
            project.addCustomer(account);
        }
        projectRepository.save(project);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity<String> removeAccount(Long projectId, Long tagId, Long userId)
    {
        CheckCredentials.checkWithProject(userId, projectId, false);
        Project project = projectRepository.findById(projectId)
                .orElseGet(() -> ErrorHandler.throwError("Project Id", Constants.Errors.NOT_FOUND));
        Account account = accountRepository.findById(tagId)
                .orElseGet(() -> ErrorHandler.throwError("Account Id", Constants.Errors.NOT_FOUND));

        project.removeDeveloper(account);
        project.removeCustomer(account);
        projectRepository.save(project);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
