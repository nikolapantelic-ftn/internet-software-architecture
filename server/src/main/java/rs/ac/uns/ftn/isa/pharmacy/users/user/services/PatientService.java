package rs.ac.uns.ftn.isa.pharmacy.users.user.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.pharmacy.pharma.domain.Drug;
import rs.ac.uns.ftn.isa.pharmacy.users.user.Patient;
import rs.ac.uns.ftn.isa.pharmacy.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.isa.pharmacy.users.user.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAppointedBy(long employeeId){
        return repository.getAppointedBy(employeeId);
    }

    public Patient get(long patientId){
        return repository.getOne(patientId);
    }

    public Patient update(Patient patient, long id) {
        if (repository.existsById(id)) {
            patient.setId(id);
            return repository.save(patient);
        }
        throw new EntityNotFoundException(Patient.class.getSimpleName(), id);
    }

    public boolean isAllergic(long patientId, long drugId) {
        return repository.getOne(patientId)
                .getAllergicTo()
                .stream()
                .anyMatch(d -> d.getId() == drugId);
    }

    public Patient findByPersonId(long id) {
        return repository.findByPersonId(id);
    }

    public void updateAllergies(List<Drug> drugs, long patientId) {
        Patient patient = repository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException(Patient.class.getSimpleName(), patientId));
        patient.setAllergicTo(drugs);
        repository.save(patient);
    }

    @Transactional
    public void eraseAllPenalties() {
        repository.eraseAllPenalties();
    }
}