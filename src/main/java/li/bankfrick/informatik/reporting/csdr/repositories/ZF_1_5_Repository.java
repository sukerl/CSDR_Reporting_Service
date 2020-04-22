package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_5;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface ZF_1_5_Repository extends JpaRepository<ZF_1_5, Integer> {
	
	@Query(value = "SELECT SUM(Anzahl_Transaktionen)\n" + 
			"FROM ZF_1_5", nativeQuery = true)
	Integer getVolume();

	@Query(value = "SELECT SUM(UebertragInEUR)\n" + 
			"FROM ZF_1_5", nativeQuery = true)
	BigDecimal getValue();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(SUM(z.anzahlTransaktionen) as volume, SUM(z.uebertragInEUR) as value)\n" + 
			"FROM ZF_1_5 z")
	VolValPair getTtlCshTrfVolValPair();
	
}
