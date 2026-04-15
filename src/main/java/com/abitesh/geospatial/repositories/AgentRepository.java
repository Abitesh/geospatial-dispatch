package com.abitesh.geospatial.repositories;

import com.abitesh.geospatial.models.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AgentRepository extends JpaRepository<AgentEntity, UUID> {
}