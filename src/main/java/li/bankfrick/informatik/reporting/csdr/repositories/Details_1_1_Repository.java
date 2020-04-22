package li.bankfrick.informatik.reporting.csdr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.dtos.IssrCSDRecord;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_1;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface Details_1_1_Repository extends JpaRepository<Details_1_1, Integer> {

	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.db.dtos.IssrCSDRecord(d.ISINland, d.LEI, d.titelart, count(d.ISINland) as anzahl, SUM(d.betragInEUR) as summeInEUR)\n" +
			"FROM Details_1_1 d\n" +
			"GROUP BY d.ISINland, d.LEI, d.titelart")
	public List<IssrCSDRecord> getAllIssrCSDRecordsInclTitelart();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.db.dtos.IssrCSDRecord(d.ISINland, d.LEI, 0 as titelart, count(d.ISINland) as anzahl, SUM(d.betragInEUR) as summeInEUR)\n" +
			"FROM Details_1_1 d\n" +
			"GROUP BY d.ISINland, d.LEI, titelart")
	public List<IssrCSDRecord> getAllIssrCSDRecordsExclTitelart();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.db.dtos.IssrCSDRecord(d.ISINland, d.LEI, d.titelart, count(d.ISINland) as anzahl, SUM(d.betragInEUR) as summeInEUR)\n" +
			"FROM Details_1_1 d\n" +
			"WHERE d.ISINland=:ISINland\n" +
			"GROUP BY d.ISINland, d.LEI, d.titelart")
	public List<IssrCSDRecord> getIssrCSDRecordDetailsByISINland(@Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d), SUM(d.betragInEUR))\n" + 
			"FROM Details_1_1 d\n" +
			"WHERE d.ISINland=:ISINland")
	public VolValPair getVolValPairOvrllTtlByISINland(@Param("ISINland") String ISINland);
	
	@Query(value = "SELECT DISTINCT d.ISINland\n" +
			"FROM Details_1_1 d")
	public List<String> getDistinctISINland();
	
	@Query(value = "SELECT DISTINCT d.LEI\n" +
			"FROM Details_1_1 d\n" +
			"WHERE d.ISINland=:ISINland")
	public List<String> getDistinctLEIsByISINland(@Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d), SUM(d.betragInEUR))\n" + 
			"FROM Details_1_1 d\n" +
			"WHERE d.titelart IN :titelArten AND d.ISINland=:ISINland")
	VolValPair getVolValPairByTitelArtenAndISINland(@Param("titelArten") List<Integer> titelArten, @Param("ISINland") String ISINland);

}
