package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    List<User> findByEmailContainingIgnoreCase(String email);

    @Query("{ $or: [ { $expr: { $regexMatch: " +
           "{ input: '$email', regex: ?0, options: 'i' } } }, { 'id': ?0 } ] }")
        // $or toán tử logic hoặc, $expr để sử dụng biểu thức aggregation, $regexMatch để so khớp chuỗi
    Page<User> searchUsersByEmailOrId(String query, Pageable pageable);


}
