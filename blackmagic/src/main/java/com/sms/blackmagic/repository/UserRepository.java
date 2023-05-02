package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByAccountName(String username);
}
