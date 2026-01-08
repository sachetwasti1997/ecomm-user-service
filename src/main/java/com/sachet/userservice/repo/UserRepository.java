package com.sachet.userservice.repo;

import com.sachet.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    User getUsersByEmail(String email);
}
