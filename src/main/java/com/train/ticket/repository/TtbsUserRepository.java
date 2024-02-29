package com.train.ticket.repository;

import com.train.ticket.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TtbsUserRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findAll();
    UserInfo findByEmailAddress(String emailId);
}


