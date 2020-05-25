package li.bankfrick.informatik.reporting.csdr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_4;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface Details_1_4_Repository extends JpaRepository<Details_1_4, Integer> {

	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d")
	VolValPair getOvrllTtlVolValPair();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.TRC IN :trc")
	VolValPair getTxTpVolValPairByTrc(@Param("trc") List<String> trc);
	
	@Query(value = "SELECT DISTINCT AnlegerTyp\n" + 
			"FROM Details_1_4", nativeQuery = true)
	List<String> getAnlegerTypen();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.anlegerTyp=:anlegerTyp")
	VolValPair getClntTpVolValPairByAnlegerTyp(@Param("anlegerTyp") String anlegerTyp);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.titelart IN :titelArten")
	VolValPair getVolValPairByTitelArten(@Param("titelArten") List<Integer> titelArten);
	
	@Query(value = "SELECT DISTINCT d.ISINland\n" +
			"FROM Details_1_4 d")
	public List<String> getDistinctISINland();
	
	@Query(value = "SELECT DISTINCT d.LEI\n" +
			"FROM Details_1_4 d\n" +
			"WHERE d.ISINland=:ISINland")
	public List<String> getDistinctLEIsByISINland(@Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR), SUM(d.betragInEUR))\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.ISINland=:ISINland")
	public VolValPair getVolValPairOvrllTtlByISINland(@Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR), SUM(d.betragInEUR))\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.titelart IN :titelArten AND d.ISINland=:ISINland")
	VolValPair getVolValPairByTitelArtenAndISINland(@Param("titelArten") List<Integer> titelArten, @Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.TRC IN :trc AND d.ISINland=:ISINland")
	VolValPair getTxTpVolValPairByTrcAndISINland(@Param("trc") List<String> trc, @Param("ISINland") String ISINland);
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d.betragInEUR) as volume, SUM(d.betragInEUR) as value)\n" + 
			"FROM Details_1_4 d\n" +
			"WHERE d.anlegerTyp IN :anlegerTyp AND d.ISINland=:ISINland")
	VolValPair getClntTpVolValPairByAnlegerTypAndISINland(@Param("anlegerTyp") String anlegerTyp, @Param("ISINland") String ISINland);
	
}
