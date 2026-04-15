package com.abitesh.geospatial.repositories;

import com.abitesh.geospatial.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}