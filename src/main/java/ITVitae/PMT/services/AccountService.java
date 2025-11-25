package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.*;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.ErrorHandler;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    final private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(AccountCreateDTO createDTO) {
        List<Account> existingAccounts = accountRepository.findAll();
        for(Account existingAccount : existingAccounts)
            if(existingAccount.getEmail().equalsIgnoreCase(createDTO.email()))
                ErrorHandler.throwError("Email", Constants.Errors.ALREADY_EXISTS);
        Account account = createDTO.toEntity();
        Account savedAccount = accountRepository.save(account);
        return AccountDTO.fromEntity(savedAccount);
    }

    public List<AccountDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDTO::fromEntity)
                .toList();
    }

    public AccountDTO findById(Long id) {
        return accountRepository.findById(id)
                .map(AccountDTO::fromEntity)
                .orElse(null);
    }

    public List<AccountDTO> findByRoleAndEmail(Constants.UserRole role, String name) {
        List<AccountDTO> allByName =
            name.isEmpty() ?
                findAll() :
                accountRepository.findByEmailContainingIgnoreCase(name).stream().map(AccountDTO::fromEntity).toList();

        List<AccountDTO> output = new ArrayList<>();
        for(AccountDTO accountDTO : allByName)
            if(accountDTO.role() == role) output.add(accountDTO);

        return output;
    }

    public AccountLoginReturnDTO attemptLogin(String email, String password) {
        Account account = accountRepository.findByEmailIgnoreCase(email);
        if(account == null) ErrorHandler.throwError("Email", Constants.Errors.NOT_FOUND);
        AccountPasswordDTO accountPasswordDTO = AccountPasswordDTO.fromEntity(account);
        if(password.equals(accountPasswordDTO.password()))
            return AccountLoginReturnDTO.fromEntity(account);
        ErrorHandler.throwError(Constants.Errors.WRONG_PASSWORD);
        return null;
    }

    public AccountDTO editAccount(Long id, AccountEditDTO editDTO, Long userId) {
        Account account = accountRepository.findById(id)
                .orElseGet(() -> ErrorHandler.throwError("Account Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithId(userId, id);

        if(!editDTO.name().equals(Constants.noEdit))
            account.setName(editDTO.name());
        if(!editDTO.password().equals(Constants.noEdit))
            account.setPassword(editDTO.password());
        accountRepository.save(account);
        return AccountDTO.fromEntity(account);
    }
}
