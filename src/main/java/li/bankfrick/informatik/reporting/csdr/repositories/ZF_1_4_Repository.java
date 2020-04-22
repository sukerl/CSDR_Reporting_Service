package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_4;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;

public interface ZF_1_4_Repository extends JpaRepository<ZF_1_4, Integer> {
	
	List<ZF_1_4> findByAnlegerTyp(String anlegerTyp);
	
	@Query(value = "SELECT SUM(Anzahl_Transaktionen)\n" + 
			"FROM ZF_1_4\n" +
			"WHERE AnlegerTyp=:anlegerTyp", nativeQuery = true)
	Integer getVolumeByAnlegerTyp(@Param("anlegerTyp") String anlegerTyp);

	@Query(value = "SELECT SUM(GegenwertInEUR)\n" + 
			"FROM ZF_1_4\n" +
			"WHERE AnlegerTyp=:anlegerTyp", nativeQuery = true)
	BigDecimal getValueByAnlegerTyp(@Param("anlegerTyp") String anlegerTyp);
	
	@Query(value = "SELECT DISTINCT AnlegerTyp\n" + 
			"FROM ZF_1_4", nativeQuery = true)
	List<String> getAnlegerTypen();
	
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(SUM(z.anzahlTransaktionen) as volume, SUM(z.gegenwertInEUR) as value)\n" + 
			"FROM ZF_1_4 z\n" +
			"WHERE z.anlegerTyp=:anlegerTyp")
	VolValPair getClntTpVolValPairByAnlegerTyp(@Param("anlegerTyp") String anlegerTyp);
	
}
