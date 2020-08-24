package ag7.dev.ag7geoapi.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ag7.dev.ag7geoapi.model.Department;

/**
 * Department Repository : to create query
 * @ag7-alexis
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCase(String name);

    Optional<Department> findByNumIgnoreCase(String num);
}