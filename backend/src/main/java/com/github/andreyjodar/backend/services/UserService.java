package com.github.andreyjodar.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.exceptions.NotFoundException;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public User create(User user) {
        User userInsert = userRepository.save(user);
        sendSuccessEmail(userInsert);
        return userInsert;
    }

    private void sendSuccessEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        emailService.emailTemplate(user.getEmail(), "Cadastro Sucesso", context, "cadastroSucesso");
    }

    public User update(User user) {
        User userDb = findById(user.getId());
        userDb.setName(user.getName());
        userDb.setEmail(user.getEmail());
        return userRepository.save(userDb);
    }

    public void delete(Long id) {
        User userBanco = findById(id);
        userRepository.delete(userBanco);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException {
            return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        }
}