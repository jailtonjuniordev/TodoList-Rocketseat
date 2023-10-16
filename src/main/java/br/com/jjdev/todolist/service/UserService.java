package br.com.jjdev.todolist.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.domain.user.UserType;
import br.com.jjdev.todolist.dto.UpdateUserDTO;
import br.com.jjdev.todolist.dto.UserDTO;
import br.com.jjdev.todolist.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;


    public void createUser(UserDTO user) throws Exception {

        userRepository.save(User.builder()
                .name(user.name())
                .email(user.email())
                .password(BCrypt
                        .withDefaults()
                        .hashToString(12, user
                                .password()
                                .toCharArray()))
                .userType(user.userType() == null ? UserType.USER : user.userType())
                .build());
    }

    public User getUserById(UUID id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("Could not find user"));
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public void deleteUser(UUID id) throws Exception {
        userRepository.deleteById(getUserById(id).getId());
    }

    public void updateUser(UUID id, UpdateUserDTO user) throws Exception {
        User editedUser = this.getUserById(id);

        updateSelected(editedUser, user);

        this.userRepository.save(editedUser);
    }

    private void updateSelected(User editedUser, UpdateUserDTO user) {
        if (user.name() != null) {
            editedUser.setName(user.name());
        }

        if (user.email() != null) {
            editedUser.setEmail(user.email());
        }

        if (user.password() != null) {
            editedUser.setPassword(user.password());
        }

        if (user.userType() != null) {
            editedUser.setUserType(user.userType());
        }

    }

    public Page<User> searchProducts(
            UUID id,
            String name,
            String email,
            UserType userType,
            Pageable pageable
    ) throws Exception {
        Map<String, Object> filters = this.createFiltersMap(
                id,
                name,
                email,
                userType);

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        List<User> users = this.searchForUsers(filters, pageable, cb);
        Long totalItems = this.countUsers(filters, cb);

        if (users.isEmpty()) {
            throw new Exception("No users found");
        }
        return new PageImpl<>(users, pageable, totalItems);
    }

    private Map<String, Object> createFiltersMap(
            UUID id,
            String name,
            String email,
            UserType userType
    ) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("id", id);
        filters.put("name", name);
        filters.put("email", email);
        filters.put("userType", userType);

        return filters;
    }

    private List<Predicate> generatePredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<User> root) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((key, value) -> {
            if (value != null) {
                predicates.add(cb.like(root.get(key), "%" + value + "%"));
            }
        });
        return predicates;
    }

    private List<User> searchForUsers(Map<String, Object> filters, Pageable pageable, CriteriaBuilder cb) {
        CriteriaQuery<User> userQuery = cb.createQuery(User.class);
        Root<User> userRoot = userQuery.from(User.class);
        List<Predicate> productPredicates = generatePredicates(filters, cb, userRoot);
        userQuery.where(productPredicates.toArray(new Predicate[0]));
        TypedQuery<User> query = this.entityManager.createQuery(userQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    private Long countUsers(Map<String, Object> filters, CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        List<Predicate> countPredicates = generatePredicates(filters, cb, countRoot);
        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        return this.entityManager.createQuery(countQuery).getSingleResult();
    }
}
