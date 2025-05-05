package com.ideas2it.training.patient.repository;

import com.ideas2it.training.patient.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    public Optional<Physician> findByLicenseNumber(String licenseNumber);
}
