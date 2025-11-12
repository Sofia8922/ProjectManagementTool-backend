package ITVitae.PMT.miscellaneous;

import ITVitae.PMT.models.Account;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.models.Task;
import ITVitae.PMT.repositories.AccountRepository;
import ITVitae.PMT.repositories.ProjectRepository;
import ITVitae.PMT.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckCredentials {
    static AccountRepository accountRepository;
    static ProjectRepository projectRepository;
    static TaskRepository taskRepository;

    @Autowired
    CheckCredentials(AccountRepository accountRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public static void checkWithAccount(Long verificationId, Account account)
    {
        if(verificationId.equals(Constants.ignoreVerification)) return;
        Account loggedInUser = accountRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        if(account != loggedInUser) throw new RuntimeException("Denied");
    }

    @Transactional
    public static void checkWithProject(Long verificationId, Long projectId, boolean developersAllowed)
    {
        if(verificationId.equals(Constants.ignoreVerification)) return;
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("project id not found"));
        Account loggedInUser = accountRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        if(project.getCreator() == loggedInUser) return;
        if(developersAllowed)
            for(Account developer : project.getDevelopers())
                if(developer.equals(loggedInUser)) return;

        throw new RuntimeException("Denied");
    }

    @Transactional
    public static void checkWithTask(Long verificationId, Long taskId, boolean developersAllowed)
    {
        if(verificationId.equals(Constants.ignoreVerification)) return;
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Caught nullpointer exception"));
        checkWithProject(verificationId, task.getProject().getId(), developersAllowed);
    }

    @Transactional
    public static void checkWithId(Long verificationId, Long otherId)
    {
        if(verificationId.equals(Constants.ignoreVerification)) return;
        if(!verificationId.equals(otherId)) throw new RuntimeException("Denied");
    }
}
