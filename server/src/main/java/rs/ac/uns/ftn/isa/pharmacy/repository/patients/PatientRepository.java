package rs.ac.uns.ftn.isa.pharmacy.repository.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.isa.pharmacy.domain.users.user.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Modifying
    @Query(value = "select distinct patient from Patient patient " +
            "left join Appointment appointment on appointment.patient.id=patient.id " +
            "left join Shift shift on shift.id=appointment.shift.id " +
            "where shift.employee.id= ?1")
    List<Patient> getAppointedBy(long employeeId);

    Patient findByPersonId(long personId);
}
