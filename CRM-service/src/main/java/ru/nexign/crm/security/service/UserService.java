package ru.nexign.crm.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nexign.jpa.dao.ClientsRepository;
import ru.nexign.jpa.dao.UserRepository;
import ru.nexign.jpa.dto.Mapper;
import ru.nexign.jpa.dto.user.UserDto;
import ru.nexign.jpa.entity.UserEntity;
import ru.nexign.jpa.dto.user.UserRole;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ClientsRepository clientsRepository;
    private final Mapper mapper;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, ClientsRepository clientsRepository, Mapper mapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.clientsRepository = clientsRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public void register(UserDto user) {
        var entity = mapper.toEntity(user);
        entity.setPassword(encoder.encode(user.getPassword()));
        entity.setRole(UserRole.ROLE_ABONENT.toString());
        userRepository.save(entity);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
