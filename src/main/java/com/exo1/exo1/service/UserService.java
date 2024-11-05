package com.exo1.exo1.service;

import com.exo1.exo1.dto.UserDto;
import com.exo1.exo1.entity.User;
import com.exo1.exo1.mapper.UserMapper;
import com.exo1.exo1.repository.ProjetRepository;
import com.exo1.exo1.repository.TaskRepository;
import com.exo1.exo1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

// PRISE DE NOTES :
//! LE CACHING se fait uniquement dans les services
//! On a une differenciation entre le cache d'une liste et le cache d'éléments individuels

@Service
@AllArgsConstructor
@Cacheable(value = "usersList")
// On ne peux pas avoir plusieurs caches (un pour la liste et un pour la valeur seule) ?
//@Cacheable(value = "users", key = "#id")
public class UserService {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private ProjetRepository projetRepository;
    private UserMapper userMapper;
    
    public List<UserDto> findAll(Pageable pageable) {
        return userMapper.toDtos(userRepository.findAll());
    }

    public UserDto findById(long id) {
        return userMapper.toDto(userRepository.findByIdWithTask(id).orElse(null));
    }

    //@CachePut(value = "usersList", key = "userDto.id")
    //@CacheEvict(value = "usersList", allEntries = true)
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.getProjets().stream().forEach(projet -> {
                    projet.getTasks().stream().forEach(task -> {task.setProjet(projet);});
                });
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        userDto.setId(existingUser.getId());
        User userUpdated = userMapper.toEntity(userDto);
        userUpdated.getProjets().stream().forEach(projet -> {
            if(projetRepository.findById(projet.getId()).isPresent()) {
                projet.setUsers(new HashSet<>(Collections.singleton(userUpdated)));
                projet.getTasks().stream().forEach(
                        task -> {
                            if(taskRepository.findById(task.getId()).isPresent()) {
                                task.setProjet(projet);
                            }
                        });
            }
        });
        return userMapper.toDto(userRepository.save(userUpdated));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
