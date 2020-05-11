package li.bankfrick.informatik.reporting.csdr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import li.bankfrick.informatik.reporting.csdr.services.ExcelToDbLoaderService;
import li.bankfrick.informatik.reporting.csdr.services.XmlWriterService;

@SpringBootApplication
@EnableScheduling
public class CsdrReportingServiceApplication {

	private static final Logger logger = LogManager.getLogger(CsdrReportingServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CsdrReportingServiceApplication.class, args);
	}
	
	@Scheduled(cron = "${technical.application.schedule}")
	public static void launchApplication() {
		
		logger.info("Verarbeitung beginnt.");
		
 		// Einlesen der Excel-Dateien starten und wenn erfolgreich XML generieren
		if(ExcelToDbLoaderService.readExcelFiles()) {
			
			logger.info("Verarbeitung der Excel-Files erfolgreich abgeschlossen.");
			
			// XML generieren und in Datei schreiben
			XmlWriterService.generateXML();
		}
		
		logger.info("Verarbeitung abgeschlossen.");

	}
}
