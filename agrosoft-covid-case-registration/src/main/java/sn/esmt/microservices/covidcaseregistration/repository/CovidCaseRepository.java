package sn.esmt.microservices.covidcaseregistration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.esmt.microservices.covidcaseregistration.bean.CovidCase;

public interface CovidCaseRepository extends JpaRepository<CovidCase, Long> {

	List<CovidCase> findByNationalID(String nationalID);
}
