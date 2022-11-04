package com.burak.repository;

import com.burak.repository.entity.Online;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOnlineRepository extends MongoRepository<Online, String> {


    Optional<Online>  findOptionalByUserProfileId(String userProfileId);

    List<Online> findAllByOnline(Boolean online);
    List<Online> findByOnlineTrue();
    List<Online> findByOnlineFalse();





}
