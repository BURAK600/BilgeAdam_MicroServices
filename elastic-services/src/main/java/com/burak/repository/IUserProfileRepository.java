package com.burak.repository;

import com.burak.repository.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.Optional;


public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);


}
