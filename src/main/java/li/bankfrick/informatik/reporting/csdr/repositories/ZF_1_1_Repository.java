package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_1;

public interface ZF_1_1_Repository extends JpaRepository<ZF_1_1, Integer> {
	
	@Query(value = "SELECT SUM(Anzahl_Transaktionen)\n" + 
			"FROM ZF_1_1\n",
			nativeQuery = true)
	Integer getOverallTransactionCount();
	
	@Query(value = "SELECT SUM(GegenwertInEUR)\n" + 
			"FROM ZF_1_1\n",
			nativeQuery = true)
	BigDecimal getOverallTransactionVolume();
	
}
