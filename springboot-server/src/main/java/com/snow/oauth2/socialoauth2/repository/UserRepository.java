package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailContainingIgnoreCase(String email);
}
