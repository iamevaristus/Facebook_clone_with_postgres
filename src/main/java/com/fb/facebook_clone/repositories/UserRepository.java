package com.fb.facebook_clone.repositories;

import com.fb.facebook_clone.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    @Query("SELECT user FROM User user WHERE user.username = ?1")
    User checkIfUsernameExists(String username);

    @Query("SELECT user FROM User user WHERE user.emailAddress = :emailAddress")
    User checkIfEmailExists(@Param("emailAddress") String emailAddress);

    @Query("SELECT user FROM User user WHERE user.emailAddress = :emailAddress AND user.password = :password")
    User loginWithEmailAndPassword(@Param("password") String password, @Param("emailAddress") String emailAddress);

    @Query("SELECT user FROM User user WHERE user.username = :username AND user.password = :password")
    User loginWithUsernameAndPassword(@Param("password") String password, @Param("username") String username);
}
