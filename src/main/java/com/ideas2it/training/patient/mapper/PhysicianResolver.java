package com.ideas2it.training.patient.mapper;

import com.ideas2it.training.patient.entity.Physician;
import com.ideas2it.training.patient.repository.PhysicianRepository;
import com.ideas2it.training.patient.util.exceptions.PhysicianNotFoundException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * Resolver for mapping physician details.
 *
 * <p>This component is responsible for resolving a physician entity based on the provided
 * license number. It interacts with the {@link PhysicianRepository} to fetch the physician
 * details from the database. If the physician is not found, it throws a
 * {@link PhysicianNotFoundException}.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * PhysicianResolver resolver = new PhysicianResolver(repository);
 * Physician physician = resolver.resolvePhysician("LICENSE123");
 * </pre>
 *
 * @author Alagu Nirmal Mahendran
 * @version 1.0
 * @since 06/05/2025
 */
@Component
public class PhysicianResolver {

    private final PhysicianRepository repository;

    /**
     * Constructs an instance of {@link PhysicianResolver}.
     *
     * @param repository the repository for accessing physician data
     */
    public PhysicianResolver(PhysicianRepository repository) {
        this.repository = repository;
    }

    /**
     * Resolves a physician by license number.
     *
     * <p>This method retrieves a physician entity from the database based on the provided
     * license number. If the license number is null or the physician is not found, it
     * returns null or throws an exception, respectively.</p>
     *
     * @param licenseNumber the license number of the physician
     * @return the resolved physician entity
     * @throws PhysicianNotFoundException if no physician is found with the given license number
     */
    @Named("resolvePhysician")
    public Physician resolvePhysician(String licenseNumber) {
        return licenseNumber != null
            ? repository.findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new PhysicianNotFoundException("Physician not found with license number: " + licenseNumber))
            : null;
    }
}
