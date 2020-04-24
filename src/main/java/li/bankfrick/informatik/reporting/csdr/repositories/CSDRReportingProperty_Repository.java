package li.bankfrick.informatik.reporting.csdr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.CSDRReportingProperty;

public interface CSDRReportingProperty_Repository extends JpaRepository<CSDRReportingProperty, Integer> {
	
	String findByKey(String key);

}
