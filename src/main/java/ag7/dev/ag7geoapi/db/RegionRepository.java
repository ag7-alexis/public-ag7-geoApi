package ag7.dev.ag7geoapi.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ag7.dev.ag7geoapi.model.Region;

/**
 * Region Repository : to create query
 * @ag7-alexis
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByNameIgnoreCase(String name);
}