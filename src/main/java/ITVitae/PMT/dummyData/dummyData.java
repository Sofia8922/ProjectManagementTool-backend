package ITVitae.PMT.dummyData;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Comment.CommentCreateDTO;
import ITVitae.PMT.services.AccountService;
import ITVitae.PMT.services.CommentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ITVitae.PMT.models.Account.UserRole.*;

@Component
public class dummyData {
    private final AccountService accountService;
    private final CommentService commentService;

    @Autowired
    public dummyData(AccountService accountService, CommentService commentService) {
        this.accountService = accountService;
        this.commentService = commentService;
    }

    @PostConstruct
    public void createDummyData() {
        //account
        AccountCreateDTO[] dummyAccounts = {
            new AccountCreateDTO("Amanda", "Amanda@email.com", "1234", OWNER),
            new AccountCreateDTO("Brandon", "Brandon@email.com", "1234", DEVELOPER),
            new AccountCreateDTO("Chris", "Chris@email.com", "1234", DEVELOPER),
            new AccountCreateDTO("Dara", "Dara@email.com", "1234", CUSTOMER)
        };
        for (AccountCreateDTO acd : dummyAccounts) accountService.createAccount(acd);
        //comments
        CommentCreateDTO[] dummyComments = {
            new CommentCreateDTO(1L, "Good morning"),
            new CommentCreateDTO(2L, "Good day")
        };
        for (CommentCreateDTO ccd : dummyComments) commentService.createComment(ccd);
    }
}
