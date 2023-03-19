package sn.esmt.microservices.covidcaseregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.esmt.microservices.covidcaseregistration.bean.CovidCase;
import sn.esmt.microservices.covidcaseregistration.exception.ResourceNotFoundException;
import sn.esmt.microservices.covidcaseregistration.repository.CovidCaseRepository;

@Service
public class CovidCaseService {

	@Autowired
	private CovidCaseRepository covidCaseRepository;

	@Transactional
	public CovidCase create(CovidCase covidCase) {
		covidCase.addAddress(covidCase.getAddress());
		return covidCaseRepository.save(covidCase);
	}

	@Transactional(readOnly = true)
	public CovidCase findOne(Long caseID) {
		return covidCaseRepository.findById(caseID)
				.orElseThrow(() -> new ResourceNotFoundException("Case", "Id", caseID));
	}

	@Transactional(readOnly = true)
	public List<CovidCase> findByNationalID(String nationalID) {
		return covidCaseRepository.findByNationalID(nationalID);
	}

	@Transactional(readOnly = true)
	public List<CovidCase> findAll() {
		return covidCaseRepository.findAll();
	}

	@Transactional
	public CovidCase update(CovidCase covidCase) {
		if (covidCase.getCaseID() != null) {
			return covidCaseRepository.findById(covidCase.getCaseID()).map(ca -> {
				ca.setCaseType(covidCase.getCaseType());

				ca.getAddress().setCity(covidCase.getAddress().getCity());
				ca.getAddress().setCountry(covidCase.getAddress().getCountry());
				ca.getAddress().setPostal(covidCase.getAddress().getPostal());
				ca.getAddress().setStreetAddress(covidCase.getAddress().getStreetAddress());
				ca.getAddress().setState(covidCase.getAddress().getState());

				ca.setDateOfBirth(covidCase.getDateOfBirth());
				ca.setEmail(covidCase.getEmail());
				ca.setFirstName(covidCase.getFirstName());
				ca.setLastName(covidCase.getLastName());
				ca.setPhone(covidCase.getPhone());
				ca.setSource(covidCase.getSource());
				ca.setNationalID(covidCase.getNationalID());
				ca.setNationalIDType(covidCase.getNationalIDType());
				return covidCaseRepository.save(ca);
			}).orElseThrow(() -> new ResourceNotFoundException("Case", "Id", covidCase.getCaseID()));
		}
		throw new IllegalArgumentException("Case ID is missing");
	}

	@Transactional
	public void delete(Long caseID) {
		CovidCase covidCase = covidCaseRepository.findById(caseID)
				.orElseThrow(() -> new ResourceNotFoundException("Case", "Id", caseID));
		covidCaseRepository.delete(covidCase);
	}
}
