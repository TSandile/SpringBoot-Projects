package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUsername(String username);
}
