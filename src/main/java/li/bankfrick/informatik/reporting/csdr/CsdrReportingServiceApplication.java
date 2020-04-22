package li.bankfrick.informatik.reporting.csdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import li.bankfrick.informatik.reporting.csdr.services.ExcelToDbLoaderService;
import li.bankfrick.informatik.reporting.csdr.services.XmlWriterService;

@SpringBootApplication
public class CsdrReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsdrReportingServiceApplication.class, args);

		// Einlesen der Excel-Dateien starten
		ExcelToDbLoaderService.readExcelFiles();

		// XML generieren und in Datei schreiben
		XmlWriterService.generateXML();
		
	}
}
