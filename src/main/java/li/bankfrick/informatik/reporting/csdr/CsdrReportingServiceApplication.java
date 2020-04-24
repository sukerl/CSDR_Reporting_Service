package li.bankfrick.informatik.reporting.csdr;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import li.bankfrick.informatik.reporting.csdr.services.ExcelToDbLoaderService;
import li.bankfrick.informatik.reporting.csdr.services.XmlWriterService;

@SpringBootApplication
public class CsdrReportingServiceApplication implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(CsdrReportingServiceApplication.class);
	
	File propertyFile;
	
	public static void main(String[] args) {
		SpringApplication.run(CsdrReportingServiceApplication.class, args);
	}
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()){
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        boolean containsOption = args.containsOption("properties.excel.file");
        logger.info("Contains properties.excel.file: " + containsOption);
   
        // Prüfen ob das Argument das für das Einlesen des Property Files benötigt wird gesetzt wurde, wenn nein versuchen Default-File zu laden
        if (containsOption) {
        	String propertyFileName = args.getOptionValues("properties.excel.file").get(0);
        	propertyFile = new File(propertyFileName);
        	// Prüfen ob das angegebene Property-File auch tatsächlich existiert, wenn ein, Verarbeitung abbrechen
        	if (propertyFile.exists()==false) {
        		logger.error("Die angegebene Property-Datei " +propertyFileName +" konnte nicht gefunden werden.");
        		System.exit(1);
        	}
        	logger.info("Property-Datei " +propertyFile.getName() +" gefunden und wird verwendet.");
        } else {
        	logger.warn("Es wurde keine Property-Datei angegeben, versuche die Standard Datei CSDR_Reporting_Properties.xlsx zu laden.");
        	propertyFile = new File("./CSDR_Reporting_Properties.xlsx");
        	// Wenn die Standard-Property-Datei auch nciht gefunden wird, Verarbeitung abbrechen.
        	if (propertyFile.exists()==false) {
        		logger.error("Die Standard-Property-Datei CSDR_Reporting_Properties.xlsx konnte nicht gefunden werden.");
        		System.exit(1);
        	}
        	logger.info("Die Standard-Property-Datei CSDR_Reporting_Properties.xlsx wurde gefunden und wird verwendet.");
        }
        
 		// Einlesen der Excel-Dateien starten
		ExcelToDbLoaderService.readExcelFiles(propertyFile);

		// XML generieren und in Datei schreiben
		XmlWriterService.generateXML();

    }
}
