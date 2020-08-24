package ag7.dev.ag7geoapi.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import ag7.dev.ag7geoapi.model.City;

/**
 * City Repository : to create query
 * @ag7-alexis
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

    List<City> findAllByNameContainingIgnoreCase(String potential_name);

    List<City> findAllByPostalCodesContainingIgnoreCase(String postal_code);
}