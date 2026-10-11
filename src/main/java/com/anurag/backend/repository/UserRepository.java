package com.anurag.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.anurag.backend.entity.User;

/**
 * Repository Interface: UserRepository
 * Provides database operations for User entity using Spring Data JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(TRIM(u.username)) = LOWER(TRIM(:identifier)) " +
           "OR LOWER(TRIM(u.email)) = LOWER(TRIM(:identifier)) ORDER BY u.id DESC")
    java.util.List<User> findByUsernameOrEmailIgnoreCase(@Param("identifier") String identifier);

    @Query("SELECT u FROM User u WHERE LOWER(TRIM(u.username)) = LOWER(TRIM(:identifier)) " +
           "OR LOWER(TRIM(u.email)) = LOWER(TRIM(:identifier)) " +
           "OR LOWER(TRIM(u.fullName)) = LOWER(TRIM(:identifier)) ORDER BY u.id DESC")
    java.util.List<User> findByIdentifierIgnoreCase(@Param("identifier") String identifier);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
