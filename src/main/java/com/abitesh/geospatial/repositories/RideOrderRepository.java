package com.abitesh.geospatial.repositories;

import com.abitesh.geospatial.models.RideOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RideOrderRepository extends JpaRepository<RideOrderEntity, UUID> {
}