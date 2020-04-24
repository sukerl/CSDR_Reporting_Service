package li.bankfrick.informatik.reporting.csdr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.FinInstrm_Mapping;

public interface FinInstrm_Mapping_Repository extends JpaRepository<FinInstrm_Mapping, Integer> {
	

}
