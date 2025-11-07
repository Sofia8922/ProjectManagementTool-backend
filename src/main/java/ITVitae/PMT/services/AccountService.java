package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Account.AccountCreateDTO;
import ITVitae.PMT.DTOs.Account.AccountDTO;
import ITVitae.PMT.DTOs.Account.AccountEditDTO;
import ITVitae.PMT.DTOs.Account.AccountLoginDTO;
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

    public Boolean attemptLogin(Long id, String password) {
        AccountLoginDTO accountDTO = accountRepository.findById(id)
                .map(AccountLoginDTO::fromEntity)
                .orElse(null);
        if(accountDTO == null) throw new RuntimeException("account not found");
        return password.equals(accountDTO.password());
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
