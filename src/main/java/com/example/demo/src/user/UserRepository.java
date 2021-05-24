package com.example.demo.src.user;

import com.example.demo.src.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User save(User user);


    Optional<User> findByEmail(String email);


    @Query("select u from User u where u.id = :id")
    Optional<User> findById(@Param("id") Long id);

}
