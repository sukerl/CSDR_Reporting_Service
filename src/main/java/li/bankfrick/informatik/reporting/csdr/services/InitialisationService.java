package li.bankfrick.informatik.reporting.csdr.services;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ClntTp_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.FinInstrm_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.TxTp_Mapping;
import li.bankfrick.informatik.reporting.csdr.properties.MappingProperties;
import li.bankfrick.informatik.reporting.csdr.repositories.ClntTp_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.FinInstrm_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.TxTp_Mapping_Repository;

@Configuration
@EnableConfigurationProperties(MappingProperties.class)
public class InitialisationService {
	
	private static final Logger logger = LogManager.getLogger(InitialisationService.class);
	
	private static Map<String, String> FININSTR;
	private static Map<String, String> TXTYPE;
	private static Map<String, String> CLITYPE;
	
	private static FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY;
	private static TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY;
	private static ClntTp_Mapping_Repository CLNTTP_MAPPING_REPOSITORY;
	
	public InitialisationService(FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY, TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY, ClntTp_Mapping_Repository CLNTTP_MAPPING_REPOSITORY, MappingProperties MAPPING_PROPERTIES) {
		InitialisationService.FININSTRM_MAPPING_REPOSITORY = FININSTRM_MAPPING_REPOSITORY;
		InitialisationService.TXTP_MAPPING_REPOSITORY = TXTP_MAPPING_REPOSITORY;
		InitialisationService.CLNTTP_MAPPING_REPOSITORY = CLNTTP_MAPPING_REPOSITORY;
		
		InitialisationService.FININSTR = MAPPING_PROPERTIES.getFininstr();
		InitialisationService.TXTYPE = MAPPING_PROPERTIES.getTxtype();
		InitialisationService.CLITYPE = MAPPING_PROPERTIES.getClitype();
	}
	
	@PostConstruct
	public static void initializeMappings() {
		
		logger.debug("Hashmap der Finanzinstrumente: " +FININSTR);
		
		// Finanzinstumente aus Hashmap in DB speichern
		for (Map.Entry<String, String> entry : FININSTR.entrySet()) {
			
			String titelArten = entry.getValue();
			if (titelArten.isEmpty()) {
				titelArten = null;
			}
			
			FinInstrm_Mapping finInstrmMapping = new FinInstrm_Mapping();
			finInstrmMapping.setsI_FI_Type(entry.getKey());
			finInstrmMapping.setTitelArten(titelArten);

			FININSTRM_MAPPING_REPOSITORY.save(finInstrmMapping);			
		}
		logger.debug("Anzahl Mappings für Finanzinstrumente in DB: " +FININSTRM_MAPPING_REPOSITORY.count());
		
		logger.debug("Hashmap der Transaktionstypen: " +TXTYPE);
		
		// Transaktionstypen aus Hashmap in DB speichern 
		for (Map.Entry<String, String> entry : TXTYPE.entrySet()) {
			
			String txTypen = entry.getValue();
			if (txTypen.isEmpty()) {
				txTypen = null;
			}
			
			TxTp_Mapping txTpMapping = new TxTp_Mapping();
			txTpMapping.setsI_TT_Type(entry.getKey());
			txTpMapping.setTrc(txTypen);
			
			TXTP_MAPPING_REPOSITORY.save(txTpMapping);			
		}
		logger.debug("Anzahl Mappings für Transaktionstypen in DB: " +TXTP_MAPPING_REPOSITORY.count());
		
		logger.debug("Hashmap der Kundentypen: " +CLITYPE);
		
		// Kundentypen aus Hashmap in DB speichern 
		for (Map.Entry<String, String> entry : CLITYPE.entrySet()) {
			
			ClntTp_Mapping clntTpMapping = new ClntTp_Mapping();
			clntTpMapping.setClntTpXML(entry.getKey());
			clntTpMapping.setClntTpXLSX(entry.getValue());
						
			CLNTTP_MAPPING_REPOSITORY.save(clntTpMapping);			
		}
		logger.debug("Anzahl Mappings für Kundentypen in DB: " +CLNTTP_MAPPING_REPOSITORY.count());

	}

}
