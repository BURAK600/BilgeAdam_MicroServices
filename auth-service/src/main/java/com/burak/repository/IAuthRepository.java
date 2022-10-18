package com.burak.repository;

import com.burak.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findOptionalByUserNameAndPassword(String userName, String password);
}
