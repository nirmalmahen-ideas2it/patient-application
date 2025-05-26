package com.ideas2it.training.patient.repository;

import com.ideas2it.training.patient.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Physician entities.
 *
 * <p>This interface provides methods for performing CRUD operations on the
 * Physician entity. It extends {@link JpaRepository}, which provides
 * built-in methods for database interactions. Additionally, it includes
 * a custom method for finding a physician by their license number.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    /**
     * Finds a physician by their license number.
     *
     * <p>This method retrieves an optional {@link Physician} entity based on
     * the provided license number. If no physician is found, the optional
     * will be empty.</p>
     *
     * @param licenseNumber the license number of the physician
     * @return an {@link Optional} containing the physician if found, or empty if not
     */
    Optional<Physician> findByLicenseNumber(String licenseNumber);
}