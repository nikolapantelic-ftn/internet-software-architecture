package rs.ac.uns.ftn.isa.pharmacy.pharma.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.pharmacy.auth.HttpRequestUtil;
import rs.ac.uns.ftn.isa.pharmacy.auth.IdentityProvider;
import rs.ac.uns.ftn.isa.pharmacy.auth.model.Role;
import rs.ac.uns.ftn.isa.pharmacy.pharma.domain.Drug;
import rs.ac.uns.ftn.isa.pharmacy.pharma.dtos.DrugSearchDto;
import rs.ac.uns.ftn.isa.pharmacy.pharma.dtos.StoredDrugDto;
import rs.ac.uns.ftn.isa.pharmacy.pharma.dtos.DrugReservationDto;
import rs.ac.uns.ftn.isa.pharmacy.pharma.mappers.DrugReservationMapper;
import rs.ac.uns.ftn.isa.pharmacy.pharma.mappers.DrugSearchMapper;
import rs.ac.uns.ftn.isa.pharmacy.pharma.mappers.StoredDrugMapper;
import rs.ac.uns.ftn.isa.pharmacy.pharma.services.DrugService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/drugs")
public class DrugController {

    private final DrugService drugService;

    DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping
    public List<Drug> getAll() {
        return drugService.findAll();
    }

    @GetMapping("{id}")
    public Drug get(@PathVariable Long id) {
        return drugService.findById(id);
    }

    @GetMapping("/search/{drugName}")
    public ResponseEntity<?> search(HttpServletRequest request, @PathVariable String drugName) {
        IdentityProvider identityProvider = HttpRequestUtil.getIdentity(request);
        // This will block Role.Supplier from accessing this. Ugly but works.
        if (
            identityProvider != null
            && !identityProvider.getAuthorities().isEmpty()
            && ((SimpleGrantedAuthority)identityProvider.getAuthorities().toArray()[0]).getAuthority().equals(Role.SUPPLIER)
        ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<StoredDrugDto> searchResult = drugService.searchByName(drugName).stream()
                                                        .map(StoredDrugMapper::objectToDto)
                                                        .collect(Collectors.toList());
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/patient-search/{drugName}")
    @Secured(Role.PATIENT)
    public List<DrugSearchDto> patientSearch(@PathVariable String drugName) {
        return drugService.searchByName(drugName).stream()
                .map(DrugSearchMapper::objectToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    Drug create(@RequestBody Drug drug) {
        return drugService.create(drug);
    }

    @PutMapping("{id}")
    public Drug update(@RequestBody Drug drug, @PathVariable Long id) {
        return drugService.update(drug, id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        drugService.deleteById(id);
    }


}