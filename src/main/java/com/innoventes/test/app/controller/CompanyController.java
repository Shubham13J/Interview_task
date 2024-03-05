package com.innoventes.test.app.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.mapper.CompanyMapper;
import com.innoventes.test.app.service.CompanyService;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
		List<Company> companyList = companyService.getAllCompanies();
		
		List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
		
		for (Company entity : companyList) {
			companyDTOList.add(companyMapper.getCompanyDTO(entity));
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(companyDTOList);
	}

	@PostMapping("/companies")
	public ResponseEntity<CompanyDTO> addCompany(@Valid @RequestBody CompanyDTO companyDTO)
			throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company newCompany = companyService.addCompany(company);
		CompanyDTO newCompanyDTO = companyMapper.getCompanyDTO(newCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCompany.getId())
				.toUri();
		return ResponseEntity.created(location).body(newCompanyDTO);
	}

	@PutMapping(value = "/companies/{id}")
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CompanyDTO companyDTO) throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company updatedCompany = companyService.updateCompany(id, company);
		CompanyDTO updatedCompanyDTO = companyMapper.getCompanyDTO(updatedCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedCompanyDTO);
	}

	@DeleteMapping(value = "/companies/{id}")
	public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable(value = "id") Long id) {
		companyService.deleteCompany(id);
		return ResponseEntity.noContent().build();
	}

	public String getMessage(String exceptionCode) {
		return messageSource.getMessage(exceptionCode, null, LocaleContextHolder.getLocale());
	}

//	Task 3: Add the following API:
//	Add an API to retrieve the Company record by id
	@GetMapping("/{companyId}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId) {
		CompanyDTO companyDTO = companyService.getCompanyById(companyId);

		if (companyDTO != null) {
			return ResponseEntity.ok(companyDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	Task 4: Add the following API:
//	Add an API to retrieve the Company record by companyCode

	@GetMapping("/byCode/{companyCode}")
	public ResponseEntity<CompanyDTO> getCompanyByCode(@PathVariable String companyCode) {
		CompanyDTO companyDTO = companyService.getCompanyByCode(companyCode);

		if (companyDTO != null) {
			return ResponseEntity.ok(companyDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{companyId}")
	public ResponseEntity<CompanyDTO> partialUpdateCompany(
			@PathVariable Long companyId,
			@RequestBody @Valid CompanyDTO partialUpdateDTO
	) {
		CompanyDTO existingCompanyDTO = companyService.getCompanyById(companyId);

		if (existingCompanyDTO != null) {

			BeanUtils.copyProperties(partialUpdateDTO, existingCompanyDTO, "id");

			CompanyDTO updatedCompanyDTO = companyService.updateCompany(existingCompanyDTO);
			return ResponseEntity.ok(updatedCompanyDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
