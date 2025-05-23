package com.clebermarcolino.project.services;

import com.clebermarcolino.project.entities.User;
import com.clebermarcolino.project.repositories.UserRepository;
import com.clebermarcolino.project.services.exceptions.DataBaseException;
import com.clebermarcolino.project.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> object = userRepository.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User object) {
        return userRepository.save(object);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public User update(Long id, User object) {
        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, object);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User object) {
        entity.setName(object.getName());
        entity.setEmail(object.getEmail());
        entity.setPhone(object.getPhone());
    }
}
