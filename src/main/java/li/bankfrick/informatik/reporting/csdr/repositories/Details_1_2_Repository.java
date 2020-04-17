package li.bankfrick.informatik.reporting.csdr.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_2;

public interface Details_1_2_Repository extends JpaRepository<Details_1_2, Integer> {
	
	/*
	@Query(value = "SELECT new li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair(COUNT(d), SUM(d.BetragInEUR))\n" + 
			"FROM Details_1_2 d\n" +
			"WHERE d.Titelart IN :titelArten")
	VolValPair getVolValPairByTitelArten(@Param("titelArten") List<String> titelArten);
	*/
	
	@Query(value = "SELECT COUNT(*)\n" + 
			"FROM Details_1_2\n" +
			"WHERE Titelart IN :titelArten", nativeQuery = true)
	Integer getVolumeByTitelArten(@Param("titelArten") List<String> titelArten);

	@Query(value = "SELECT SUM(BetragInEUR)\n" + 
			"FROM Details_1_2\n" +
			"WHERE Titelart IN :titelArten", nativeQuery = true)
	BigDecimal getValueByTitelArten(@Param("titelArten") List<String> titelArten);

	/*
	@Query(value = "SELECT instruments.BaseCurrency, instruments.ReferenceCurrency, crossrates.MidPrice, extractions.SnapTime\r\n"
			+ "FROM crossrates\r\n" + "JOIN extractions ON crossrates.Extraction_ID=extractions.Extraction_ID\r\n"
			+ "JOIN instruments ON crossrates.Instrument_ID=instruments.Instrument_ID\r\n"
			+ "WHERE extractions.Faultless = true\r\n" + "AND instruments.BaseCurrency IN :baseCurrencies\r\n"
			+ "AND extractions.SnapTime = \r\n" + "(SELECT MAX(extractions.SnapTime) FROM extractions \r\n"
			+ "JOIN crossrates ON crossrates.Extraction_ID=extractions.Extraction_ID\r\n"
			+ "JOIN instruments ON crossrates.Instrument_ID=instruments.Instrument_ID\r\n"
			+ "WHERE extractions.Faultless = true\r\n"
			+ "AND instruments.BaseCurrency IN :baseCurrencies)", nativeQuery = true)
	List<MidPriceCrossRate> getMidPriceCrossRates(@Param("baseCurrencies") List<String> baseCurrencies);
	*/
}
