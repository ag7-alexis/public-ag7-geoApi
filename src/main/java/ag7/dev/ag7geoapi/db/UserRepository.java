package ag7.dev.ag7geoapi.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ag7.dev.ag7geoapi.model.User;

/**
 * User Repository : to create query
 * @ag7-alexis
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}