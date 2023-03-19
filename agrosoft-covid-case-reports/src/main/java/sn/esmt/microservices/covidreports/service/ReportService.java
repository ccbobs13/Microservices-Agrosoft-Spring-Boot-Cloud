package sn.esmt.microservices.covidreports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.esmt.microservices.covidreports.bean.CovidReport;
import sn.esmt.microservices.covidreports.bean.ReportDetails;
import sn.esmt.microservices.covidreports.dto.ReportPayload;
import sn.esmt.microservices.covidreports.exception.ResourceAlreadyExistsException;
import sn.esmt.microservices.covidreports.exception.ResourceNotFoundException;
import sn.esmt.microservices.covidreports.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Transactional(readOnly = true)
	public CovidReport getReportByState(String state) {
		return reportRepository.findByState(state)
				.orElseThrow(() -> new ResourceNotFoundException("State", "Name", state));
	}

	@Transactional(readOnly = true)
	public List<CovidReport> getReports() {
		return reportRepository.findAll();
	}

	@Transactional
	public CovidReport create(ReportPayload reportPayload) {

		if (!reportRepository.existsByState(reportPayload.getState())) {
			String caseType = reportPayload.getCaseType().toUpperCase();

			if (reportPayload.getOldCaseType() != null && reportPayload.getOldState() != null) {

				String oldCaseType = reportPayload.getOldCaseType().toUpperCase();

				CovidReport oldCovidReport = reportRepository.findByState(reportPayload.getOldState())
						.orElseThrow(() -> new ResourceNotFoundException("State", "Name", reportPayload.getOldState()));

				switch (oldCaseType) {
				case "POSITIVE":
					oldCovidReport.getReport().setPositive(oldCovidReport.getReport().getPositive() - 1);
					oldCovidReport.getReport().setTotal(oldCovidReport.getReport().getTotal() - 1);

					break;
				case "NEGATIVE":
					oldCovidReport.getReport().setTotal(oldCovidReport.getReport().getTotal() - 1);

					break;
				case "VACCINATED":
					oldCovidReport.getReport().setVaccinated(oldCovidReport.getReport().getVaccinated() - 1);

					break;
				case "RECOVERED":
					oldCovidReport.getReport().setRecovered(oldCovidReport.getReport().getRecovered() - 1);

					break;
				case "DEATH":
					oldCovidReport.getReport().setDeath(oldCovidReport.getReport().getDeath() - 1);

					break;
				default:
					throw new IllegalArgumentException("Invalid value of CaseType");
				}

				reportRepository.save(oldCovidReport);
			}

			CovidReport covidReport = new CovidReport();
			covidReport.setState(reportPayload.getState().toUpperCase());
			ReportDetails reportDetails = new ReportDetails();
			covidReport.addReport(reportDetails);

			switch (caseType) {
			case "POSITIVE":
				covidReport.getReport().setPositive(covidReport.getReport().getPositive() + 1);
				covidReport.getReport().setTotal(covidReport.getReport().getTotal() + 1);
				break;
			case "NEGATIVE":
				covidReport.getReport().setTotal(covidReport.getReport().getTotal() + 1);
				break;
			case "VACCINATED":
				covidReport.getReport().setVaccinated(covidReport.getReport().getVaccinated() + 1);
				break;
			case "RECOVERED":
				covidReport.getReport().setRecovered(covidReport.getReport().getRecovered() + 1);
				break;
			case "DEATH":
				covidReport.getReport().setDeath(covidReport.getReport().getDeath() + 1);
				break;
			default:
				throw new IllegalArgumentException("Invalid value of CaseType");
			}

			return reportRepository.save(covidReport);
		}
		throw new ResourceAlreadyExistsException("State", "Name", reportPayload.getState());

	}

	@Transactional
	public CovidReport update(ReportPayload reportPayload) {

		CovidReport newCovidReport = reportRepository.findByState(reportPayload.getState())
				.orElseThrow(() -> new ResourceNotFoundException("State", "Name", reportPayload.getState()));

		String newCaseType = reportPayload.getCaseType().toUpperCase();
		// Vérifier si le state du report a changé

		if (reportPayload.getOldState() != null && reportPayload.getOldCaseType() != null) {

			CovidReport oldCovidReport = reportRepository.findByState(reportPayload.getOldState())
					.orElseThrow(() -> new ResourceNotFoundException("State", "Name", reportPayload.getOldState()));

			String oldCaseType = reportPayload.getOldCaseType().toUpperCase();

			switch (oldCaseType) {
			case "POSITIVE":
				oldCovidReport.getReport().setPositive(oldCovidReport.getReport().getPositive() - 1);
				oldCovidReport.getReport().setTotal(oldCovidReport.getReport().getTotal() - 1);

				break;
			case "NEGATIVE":
				oldCovidReport.getReport().setTotal(oldCovidReport.getReport().getTotal() - 1);

				break;
			case "VACCINATED":
				oldCovidReport.getReport().setVaccinated(oldCovidReport.getReport().getVaccinated() - 1);

				break;
			case "RECOVERED":
				oldCovidReport.getReport().setRecovered(oldCovidReport.getReport().getRecovered() - 1);

				break;
			case "DEATH":
				oldCovidReport.getReport().setDeath(oldCovidReport.getReport().getDeath() - 1);

				break;
			default:
				throw new IllegalArgumentException("Invalid value of CaseType");
			}

			reportRepository.save(oldCovidReport);
		}

		if (reportPayload.getState() != null && reportPayload.getCaseType() != null) {
			switch (newCaseType) {
			case "POSITIVE":
				newCovidReport.getReport().setPositive(newCovidReport.getReport().getPositive() + 1);
				newCovidReport.getReport().setTotal(newCovidReport.getReport().getTotal() + 1);
				break;
			case "NEGATIVE":
				newCovidReport.getReport().setTotal(newCovidReport.getReport().getTotal() + 1);
				break;
			case "VACCINATED":
				newCovidReport.getReport().setVaccinated(newCovidReport.getReport().getVaccinated() + 1);
				break;
			case "RECOVERED":
				newCovidReport.getReport().setRecovered(newCovidReport.getReport().getRecovered() + 1);
				break;
			case "DEATH":
				newCovidReport.getReport().setDeath(newCovidReport.getReport().getDeath() + 1);
				break;
			default:
				throw new IllegalArgumentException("Invalid value of CaseType");
			}

		}

		return reportRepository.save(newCovidReport);

	}

}
