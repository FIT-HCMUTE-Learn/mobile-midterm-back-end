package com.mobile.api.service;

import com.mobile.api.constant.BaseConstant;
import com.mobile.api.enumeration.ErrorCode;
import com.mobile.api.exception.BusinessException;
import com.mobile.api.exception.ResourceNotFoundException;
import com.mobile.api.form.RegistrationForm;
import com.mobile.api.model.entity.Account;
import com.mobile.api.model.entity.Group;
import com.mobile.api.model.entity.User;
import com.mobile.api.repository.AccountRepository;
import com.mobile.api.repository.GroupRepository;
import com.mobile.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void register(RegistrationForm registrationForm) {
        // Create ACCOUNT
        if (accountRepository.existsByEmail(registrationForm.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_EMAIL_EXISTED);
        }

        Account account = new Account();
        account.setUsername(registrationForm.getEmail());
        account.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        account.setEmail(registrationForm.getEmail());

        Group group = groupRepository.findFirstByKind(BaseConstant.GROUP_KIND_USER)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.GROUP_NOT_FOUND));
        account.setGroup(group);
        Account savedAccount = accountRepository.save(account);

        // Create USER
        User user = new User();
        user.setAccount(savedAccount);
        userRepository.save(user);
    }
}
