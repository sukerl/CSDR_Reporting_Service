package li.bankfrick.informatik.reporting.csdr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_5;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface Details_1_5_Repository extends JpaRepository<Details_1_5, Integer> {
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_5 d")
	VolValPair getTtlCshTrfVolValPair();

	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_5 d\n" +
			"WHERE d.land=:land")
	VolValPair getTtlCshTrfVolValPairByLand(@Param("land") String land);
	
}
