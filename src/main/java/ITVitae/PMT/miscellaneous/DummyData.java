package ITVitae.PMT.miscellaneous;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.DTOs.Project.ProjectCreateDTO;
import ITVitae.PMT.DTOs.Tag.TagCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskCreateDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.services.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ITVitae.PMT.miscellaneous.Constants.UserRole.*;

@Component
public class DummyData {
    private final AccountService accountService;
    private final CommentService commentService;
    private final ProjectService projectService;
    private final TagService tagService;
    private final TaskService taskService;

    @Autowired
    public DummyData(AccountService accountService, CommentService commentService, ProjectService projectService, TagService tagService, TaskService taskService) {
        this.accountService = accountService;
        this.commentService = commentService;
        this.projectService = projectService;
        this.tagService = tagService;
        this.taskService = taskService;
    }

    @PostConstruct
    public void createDummyData() {
        //account
        AccountCreateDTO[] dummyAccounts = {
            new AccountCreateDTO("Amanda@email.com", "Amanda", "1234", OWNER),
            new AccountCreateDTO("Brandon@email.com", "Brandon", "1234", DEVELOPER),
            new AccountCreateDTO( "Chris@email.com", "Chris","1234", DEVELOPER),
            new AccountCreateDTO("Dara@email.com", "Dara", "1234", DEVELOPER),
            new AccountCreateDTO( "Emil@email.com","Emil", "1234", CUSTOMER)
        };
        for (AccountCreateDTO acd : dummyAccounts) accountService.createAccount(acd);
        //projects
        ProjectCreateDTO[] dummyProjects = {
                new ProjectCreateDTO("Pretzel store", "revamp the website", 1L),
                new ProjectCreateDTO("Arne's game store", "might delete this later idk", 1L)
        };
        for (ProjectCreateDTO pcd : dummyProjects) projectService.createProject(pcd);
        projectService.addAccount(1L, 2L, Constants.ignoreVerification);
        projectService.addAccount(1L, 3L, Constants.ignoreVerification);
        projectService.addAccount(1L, 5L, Constants.ignoreVerification);
        projectService.addAccount(2L, 4L, Constants.ignoreVerification);
        //tags
        TagCreateDTO[] dummytags = {
                new TagCreateDTO("Difficult", Constants.Colour.ORANGE, 1L),
                new TagCreateDTO("Easy", Constants.Colour.GREEN, 1L),
                new TagCreateDTO("Jojo", Constants.Colour.BLUE, 2L),
                new TagCreateDTO("Jojojo", Constants.Colour.WHITE, 2L)
        };
        for(TagCreateDTO tcd : dummytags) tagService.createTag(tcd, Constants.ignoreVerification);
        //tasks
        TaskCreateDTO[] dummyTasks = {
            new TaskCreateDTO("logo", "redesign the logo", 1L, 1L),
            new TaskCreateDTO("main page", "", 3L, 1L),
            new TaskCreateDTO("sell form", "don't forget the discount", 4L, 1L),
            new TaskCreateDTO("FAQ", "just a basic FAQ page", 3L, 1L)
        };
        for (TaskCreateDTO tcd : dummyTasks) taskService.createTask(tcd, Constants.ignoreVerification);
        TaskEditDTO[] editDummyTasks = {
            new TaskEditDTO("#", "#", "COMPLETED"),
            new TaskEditDTO("#", "#", "IN_PROGRESS")
        };
        Long index = 1L;
        for (TaskEditDTO ted : editDummyTasks) {
            taskService.editTask(index, ted, Constants.ignoreVerification);
            index++;
        }
        taskService.addTag(1L, 1L, Constants.ignoreVerification);
        taskService.addTag(2L, 1L, Constants.ignoreVerification);
        taskService.addTag(2L, 2L, Constants.ignoreVerification);
        //comments
        CommentCreateDTO[] dummyComments = {
            new CommentCreateDTO("Good morning", 1L, 2L),
            new CommentCreateDTO("Hello", 3L, 1L),
            new CommentCreateDTO("Good day", 4L, 1L),
            new CommentCreateDTO("How is everybody?", 4L, 1L)
        };
        for (CommentCreateDTO ccd : dummyComments) commentService.createComment(ccd, Constants.ignoreVerification);
    }

}
