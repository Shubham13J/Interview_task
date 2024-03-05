package com.innoventes.test.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.innoventes.test.app.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.error.ApplicationErrorCodes;
import com.innoventes.test.app.exception.ResourceNotFoundException;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.repository.CompanyRepository;
import com.innoventes.test.app.service.CompanyService;
import com.innoventes.test.app.util.ServiceHelper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceHelper serviceHelper;

	@Override
	public List<Company> getAllCompanies() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyRepository.findAll().forEach(companyList::add);
		return companyList;
	}

	@Override
	public Company addCompany(Company company) throws ValidationException {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) throws ValidationException {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		company.setId(existingCompanyRecord.getId());
		return companyRepository.save(company);
	}

	@Override
	public void deleteCompany(Long id) {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		companyRepository.deleteById(existingCompanyRecord.getId());
	}

	@Override
	public CompanyDTO getCompanyById(Long companyId) {
		Company company = companyRepository.findById(companyId).orElse(null);


		return (company != null) ? convertToDTO(company) : null;
	}

	@Override
	public CompanyDTO getCompanyByCode(String companyCode) {
		Company company = companyRepository.findByCompanyCodeIgnoreCase(companyCode);

		return (company != null) ? convertToDTO(company) : null;
	}

	@Override
	public CompanyDTO updateCompany(CompanyDTO existingCompanyDTO) {
		Company updatedCompany = convertToEntity(existingCompanyDTO);
		Company savedCompany = companyRepository.save(updatedCompany);
		return convertToDTO(savedCompany);
	}

	private Company convertToEntity(CompanyDTO companyDTO) {
		Company company = new Company();
		company.setId(companyDTO.getId());
		company.setCompanyName(companyDTO.getCompanyName());
		company.setEmail(companyDTO.getEmail());
		company.setStrength(companyDTO.getStrength());
		company.setWebSiteURL(companyDTO.getWebSiteURL());
		company.setCompanyCode(companyDTO.getCompanyCode());


		return company;
	}


	private CompanyDTO convertToDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setCompanyName(company.getCompanyName());
		companyDTO.setEmail(company.getEmail());
		companyDTO.setStrength(company.getStrength());
		companyDTO.setWebSiteURL(company.getWebSiteURL());
		companyDTO.setCompanyCode(company.getCompanyCode());

		return companyDTO;
	}
}
