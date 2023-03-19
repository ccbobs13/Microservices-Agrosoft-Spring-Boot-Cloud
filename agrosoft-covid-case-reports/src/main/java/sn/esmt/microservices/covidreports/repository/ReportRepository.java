package sn.esmt.microservices.covidreports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.esmt.microservices.covidreports.bean.CovidReport;

public interface ReportRepository extends JpaRepository<CovidReport, String> {

	Optional<CovidReport> findByState(String state);

	boolean existsByState(String state);

}
