package com.ideas2it.training.patient.repository;

import com.ideas2it.training.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Patient entities.
 *
 * <p>This interface provides methods for performing CRUD operations on the
 * Patient entity. It extends {@link JpaRepository}, which provides
 * built-in methods for database interactions.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}