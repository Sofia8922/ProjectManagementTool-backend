package ITVitae.PMT.controllers;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Account.AccountEditDTO;
import ITVitae.PMT.DTOs.Account.AccountLoginReturnDTO;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<AccountDTO> postAccount(@Valid @RequestBody AccountCreateDTO createDTO)
    {
        AccountDTO created = accountService.createAccount(createDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAllAccounts()
    {
        List<AccountDTO> accountDTOs = accountService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(accountDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id)
    {
        AccountDTO accountDTO = accountService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
    }

    @GetMapping("/find/{role}/")
    public ResponseEntity<List<AccountDTO>> searchAccountShort(@PathVariable Constants.UserRole role)
    {
        return searchAccount(role, "");
    }

    @GetMapping("/find/{role}/{email}")
    public ResponseEntity<List<AccountDTO>> searchAccount(@PathVariable Constants.UserRole role, @PathVariable String email)
    {
        List<AccountDTO> accountDTOs = accountService.findByRoleAndEmail(role, email);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> putAccount(@PathVariable Long id, @Valid @RequestBody AccountEditDTO editDTO)
    {
        AccountDTO created = accountService.editAccount(id, editDTO);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PostMapping("/{email}/{password}")
    public ResponseEntity<AccountLoginReturnDTO> loginAccount(@PathVariable String email, @PathVariable String password)
    {
        AccountLoginReturnDTO account = accountService.attemptLogin(email, password);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
