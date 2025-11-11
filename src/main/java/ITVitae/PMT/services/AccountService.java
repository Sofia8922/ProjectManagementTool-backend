package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.*;
import ITVitae.PMT.models.Account;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    final private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(AccountCreateDTO createDTO) {
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

    public AccountLoginReturnDTO attemptLogin(String email, String password) {
        Account account = accountRepository.findByEmailIgnoreCase(email);
        if(account == null) throw new RuntimeException("email not found");
        AccountPasswordDTO accountPasswordDTO = AccountPasswordDTO.fromEntity(account);
        if(password.equals(accountPasswordDTO.password()))
            return AccountLoginReturnDTO.fromEntity(account);
        throw new RuntimeException("password doesn't match");
    }

    public AccountDTO editAccount(Long id, AccountEditDTO editDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account id not found"));

        if(!editDTO.email().equals(Constants.noEdit))
            account.setEmail(editDTO.email());
        if(!editDTO.name().equals(Constants.noEdit))
            account.setName(editDTO.name());
        if(!editDTO.password().equals(Constants.noEdit))
            account.setPassword(editDTO.password());
        accountRepository.save(account);
        return AccountDTO.fromEntity(account);
    }
}
