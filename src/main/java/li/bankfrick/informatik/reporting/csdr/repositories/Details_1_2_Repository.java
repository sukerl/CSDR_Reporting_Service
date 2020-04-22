package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_2;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface Details_1_2_Repository extends JpaRepository<Details_1_2, Integer> {

	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d), SUM(d.betragInEUR))\n" + 
			"FROM Details_1_2 d\n" +
			"WHERE d.titelart IN :titelArten")
	VolValPair getVolValPairByTitelArten(@Param("titelArten") List<Integer> titelArten);
	
	@Query(value = "SELECT COUNT(*)\n" + 
			"FROM Details_1_2\n" +
			"WHERE Titelart IN :titelArten", nativeQuery = true)
	Integer getVolumeByTitelArten(@Param("titelArten") List<String> titelArten);

	@Query(value = "SELECT SUM(BetragInEUR)\n" + 
			"FROM Details_1_2\n" +
			"WHERE Titelart IN :titelArten", nativeQuery = true)
	BigDecimal getValueByTitelArten(@Param("titelArten") List<String> titelArten);

}
