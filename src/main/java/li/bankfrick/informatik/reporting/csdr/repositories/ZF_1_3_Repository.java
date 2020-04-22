package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_3;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface ZF_1_3_Repository extends JpaRepository<ZF_1_3, Integer> {
	
	@Query(value = "SELECT SUM(Anzahl_Transaktionen)\n" + 
			"FROM ZF_1_3\n" +
			"WHERE TRC IN :trc", nativeQuery = true)
	Integer getVolumeByTrc(@Param("trc") List<String> trc);

	@Query(value = "SELECT SUM(GegenwertInEUR)\n" + 
			"FROM ZF_1_3\n" +
			"WHERE TRC IN :trc", nativeQuery = true)
	BigDecimal getValueByTrc(@Param("trc") List<String> trc);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(SUM(z.anzahlTransaktionen) as volume, SUM(z.gegenwertInEUR) as value)\n" + 
			"FROM ZF_1_3 z\n" +
			"WHERE z.TRC IN :trc")
	VolValPair getTxTpVolValPairByTrc(@Param("trc") List<String> trc);
	
}
