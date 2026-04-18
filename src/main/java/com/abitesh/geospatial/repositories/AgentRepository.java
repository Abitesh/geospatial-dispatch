package com.abitesh.geospatial.repositories;

import com.abitesh.geospatial.models.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional; // <-- FIXED: Added this import
import java.util.UUID;

public interface AgentRepository extends JpaRepository<AgentEntity, UUID> {
    
    List<AgentEntity> findByStatus(String status);
    
    // FIXED: Changed returning type from Agent to AgentEntity
    @Query(value = "SELECT * FROM agents WHERE status = 'AVAILABLE' AND ST_DWithin(current_location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radiusInMeters)", nativeQuery = true)
    List<AgentEntity> findNearbyAvailableAgents(@Param("lat") double lat, @Param("lng") double lng, @Param("radiusInMeters") double radiusInMeters);

    // FIXED: Changed returning type from Agent to AgentEntity
    @Query(value = "SELECT * FROM agents WHERE status = 'AVAILABLE' ORDER BY current_location <-> ST_SetSRID(ST_MakePoint(:lng, :lat), 4326) LIMIT 1", nativeQuery = true)
    Optional<AgentEntity> findClosestAvailableAgent(@Param("lat") double lat, @Param("lng") double lng);
}