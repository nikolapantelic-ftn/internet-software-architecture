package rs.ac.uns.ftn.isa.pharmacy.users.employee.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isa.pharmacy.auth.HttpRequestUtil;
import rs.ac.uns.ftn.isa.pharmacy.auth.IdentityProvider;
import rs.ac.uns.ftn.isa.pharmacy.auth.model.Role;
import rs.ac.uns.ftn.isa.pharmacy.users.employee.dtos.PharmacyInfoDto;
import rs.ac.uns.ftn.isa.pharmacy.users.employee.services.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employee-id/{personId}")
    public long getEmployeeId(@PathVariable long personId){
        return service.getEmployeeId(personId);
    }

    @Secured({Role.DERMATOLOGIST,Role.PHARMACIST})
    @GetMapping("/my-pharmacies")
    public List<PharmacyInfoDto> getMyPharmacies(HttpServletRequest request){
        IdentityProvider provider = HttpRequestUtil.getIdentity(request);
        return service.getMyPharmacies(provider.getRoleId())
                .stream()
                .map(PharmacyInfoDto::new)
                .collect(Collectors.toList());
    }
}
