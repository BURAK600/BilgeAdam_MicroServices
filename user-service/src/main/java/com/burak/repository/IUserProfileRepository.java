package com.burak.repository;

import com.burak.repository.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);

}