package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Project.ProjectCreateDTO;
import ITVitae.PMT.DTOs.Project.ProjectDTO;
import ITVitae.PMT.DTOs.Project.ProjectEditDTO;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.ProjectRepository;
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
            .orElseThrow(() -> new RuntimeException("Creator id not found"));

        if(creator.getRole() != Constants.UserRole.OWNER)
            throw new RuntimeException("Only owners can create projects");

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
        //put developerlist in DTO. ProjectDTO might be null!
        List<AccountDTO> developerDTOs = new ArrayList<>();
        return developerDTOs;
    }

    public List<AccountDTO> findProjectClients(Long id) {
        ProjectDTO projectDTO = findById(id);
        //put clientlist in DTO. ProjectDTO might be null!
        List<AccountDTO> clientDTOs = new ArrayList<>();
        return clientDTOs;
    }

    public ProjectDTO editProject(Long id, ProjectEditDTO editDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project id not found"));

        if(!editDTO.name().equals(Constants.noEdit))
            project.setName(editDTO.name());
        if(!editDTO.description().equals(Constants.noEdit))
            project.setDescription(editDTO.description());
        projectRepository.save(project);
        return ProjectDTO.fromEntity(project);
    }

    public ResponseEntity<String> deleteProject(Long id)
    {
        if(projectRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        projectRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
