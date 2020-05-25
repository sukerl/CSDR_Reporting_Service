package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ClntTp_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_4;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_5;
import li.bankfrick.informatik.reporting.csdr.properties.ExcelPatternProperties;
import li.bankfrick.informatik.reporting.csdr.repositories.ClntTp_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_4_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_5_Repository;

@Component
@Configuration
@EnableConfigurationProperties(ExcelPatternProperties.class)
public class ExcelToDbLoaderService {

	private static final Logger logger = LogManager.getLogger(ExcelToDbLoaderService.class);

	private static Details_1_4_Repository DETAILS_1_4_REPOSITORY;	
	private static Details_1_5_Repository DETAILS_1_5_REPOSITORY;
	private static ClntTp_Mapping_Repository CLNTTP_MAPPING_REPOSITORY;

	private static Map<String, String> EXCEL_FILE_PATTERNS;

	private static String SOURCE_FOLDER;
	@Value("${generic.source.directory}")
	public void setSourceFolder(String sourceFolder) {
		SOURCE_FOLDER = sourceFolder;
	}

	private static String PATTERN_1_4;
	@Value("${technical.excel.pattern.1_4}")
	public void setPattern_1_4(String pattern_1_4) {
		PATTERN_1_4 = pattern_1_4;
	}

	private static String PATTERN_1_5;
	@Value("${technical.excel.pattern.1_5}")
	public void setPattern_1_5(String pattern_1_5) {
		PATTERN_1_5 = pattern_1_5;
	}

	private static String DETAIL_SHEETS_PATTERN;
	@Value("${technical.excel.detail.sheet.pattern}")
	public void setDetailSheetsPattern(String detailSheetsPattern) {
		DETAIL_SHEETS_PATTERN = detailSheetsPattern;
	}	

	public ExcelToDbLoaderService(Details_1_4_Repository DETAILS_1_4_REPOSITORY, Details_1_5_Repository DETAILS_1_5_REPOSITORY, ClntTp_Mapping_Repository CLNTTP_MAPPING_REPOSITORY, ExcelPatternProperties EXCEL_PATTERN_PROPERTIES) {
		ExcelToDbLoaderService.DETAILS_1_4_REPOSITORY = DETAILS_1_4_REPOSITORY;
		ExcelToDbLoaderService.DETAILS_1_5_REPOSITORY = DETAILS_1_5_REPOSITORY;
		ExcelToDbLoaderService.CLNTTP_MAPPING_REPOSITORY = CLNTTP_MAPPING_REPOSITORY;

		ExcelToDbLoaderService.EXCEL_FILE_PATTERNS = EXCEL_PATTERN_PROPERTIES.getPattern();
	}

	// Hauptfunktion zum Einlesen der Excel Dateien
	public static Boolean readExcelFiles() {

		logger.info("Verarbeitung der Excel-Files beginnt.");

		Boolean excelFilesSuccessful = false;

		// Wenn alle Dateien vorhanden sind, Dateiinhalte in DB einlesen.
		if(checkExcelFilesPresent()) {

			//load_Details_1_4(PATTERN_1_4, DETAIL_SHEET_1_4_KLEIN, "Kleinsparer");
			//load_Details_1_4(PATTERN_1_4, DETAIL_SHEET_1_4_PROF, "Professionell");
			load_Details_1_4();
			load_Details_1_5();

			// Dateien in archive-Ordner verschieben
			moveFilesToDoneFolder();

			excelFilesSuccessful = true;			
		}
		return excelFilesSuccessful;
	}

	// Funktion die überprüft, ob alle benötigten Dateien für die Verarbeitung vorhanden sind.
	public static Boolean checkExcelFilesPresent() {

		logger.debug("Hashmap der Excel File Patterns: " +EXCEL_FILE_PATTERNS);

		Boolean allFilesPresent = true;

		for (Map.Entry<String, String> entry : EXCEL_FILE_PATTERNS.entrySet()) {

			File[] files = findFilesForId(entry.getValue());

			if (files.length != 1) {
				logger.error("Ungültige Anzahl Dateien für Pattern: " + entry.getValue() + " ; Anzahl gefundene Dateien: "
						+ files.length + " ; Beende Verarbeitung.");

				allFilesPresent = false;
				break;
			}
		}

		return allFilesPresent;		
	}

	//private static void load_Details_1_4(String filePattern, String sheetName, String anlegerTyp) {
	private static void load_Details_1_4() {

		Workbook workbook;
		int startingRow = 2;

		File[] files = findFilesForId(PATTERN_1_4);

		logger.debug("Für Pattern " +PATTERN_1_4 + " wird Datei " +files[0] +" verwendet.");

		try {
			workbook = WorkbookFactory.create(files[0]);

			// Alle Sheets die das DETAIL_SHEETS_PATTERN beinhalten in eine Liste eintragen
			List<String> sheetNames = new ArrayList<String>();
			for (int i=0; i<workbook.getNumberOfSheets(); i++) {
				if ((workbook.getSheetName(i).contains(DETAIL_SHEETS_PATTERN))) {
					sheetNames.add( workbook.getSheetName(i) );	
				}			    
			}
			// Alle relevanten Sheets verarbeiten
			for (String sheetName : sheetNames ) {
				
				logger.debug("Sheet \"" +sheetName +"\" wird verarbeitet.");
				
				Sheet sheet = workbook.getSheet(sheetName);

				// Schleife über alle Reihen in Excel-Sheet
				Iterator<Row> rows = sheet.rowIterator();
				while (rows.hasNext ())	{

					Row currentRow = rows.next();

					// Die ersten X-Reihen auslassen
					if(currentRow.getRowNum()<startingRow-1) {
						continue;
					}

					Details_1_4 details_1_4 = new Details_1_4();

					details_1_4.setGegenseite((currentRow.getCell(0)).getStringCellValue());
					details_1_4.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
					details_1_4.setTRC((currentRow.getCell(2)).getStringCellValue());
					details_1_4.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
					details_1_4.setDepotstelle((currentRow.getCell(4)).getStringCellValue());
					details_1_4.setBezeichnungDepotstelle((currentRow.getCell(5)).getStringCellValue());
					details_1_4.setLEI((currentRow.getCell(6)).getStringCellValue());
					BigDecimal transaktion = new BigDecimal((currentRow.getCell(7)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_4.setTransaktion(transaktion.intValue());
					details_1_4.setISINland((currentRow.getCell(8)).getStringCellValue());
					details_1_4.setValor((currentRow.getCell(9)).getStringCellValue());
					details_1_4.setKurzbezeichnung((currentRow.getCell(10)).getStringCellValue());
					BigDecimal titelart = new BigDecimal((currentRow.getCell(11)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_4.setTitelart(titelart.intValue());
					//details_1_4.setLiefercode((currentRow.getCell(12)).getStringCellValue());
					details_1_4.setLiefercode("");
					BigDecimal systemdatum = new BigDecimal((currentRow.getCell(13)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_4.setSystemdatum(systemdatum.toString());
					BigDecimal betragCHF = new BigDecimal((currentRow.getCell(14)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_4.setBetragInCHF(betragCHF);
					BigDecimal betragEUR = new BigDecimal((currentRow.getCell(15)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_4.setBetragInEUR(betragEUR.setScale(2, RoundingMode.HALF_UP));
					String anlegerTyp=sheetName.substring((sheetName.lastIndexOf(' '))+1);
					
					// Alle Kundentypen-Mappings aus der DB laden
					List<ClntTp_Mapping> clntTpMappings = CLNTTP_MAPPING_REPOSITORY.findAll();

					// Kundentyp finden der mit Wert aus Name des Excel-Sheets übereinstimmt und entsprechend setzen. 
					for (ClntTp_Mapping clntTpMapping : clntTpMappings) {
						if (anlegerTyp.equals(clntTpMapping.getClntTpXLSX())) {
							details_1_4.setAnlegerTyp(clntTpMapping.getClntTpXML());
						}					
					}

					logger.trace(details_1_4);
					
					DETAILS_1_4_REPOSITORY.save(details_1_4);				
				}
			}

			workbook.close();

			logger.debug("Anzahl " +PATTERN_1_4 +" Detail Datensätze in DB: " +DETAILS_1_4_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static void load_Details_1_5() {

		Workbook workbook;
		int startingRow = 2;

		File[] files = findFilesForId(PATTERN_1_5);

		logger.debug("Für Pattern " +PATTERN_1_5 + " wird Datei " +files[0] +" verwendet.");

		try {
			workbook = WorkbookFactory.create(files[0]);

			// Alle Sheets die das DETAIL_SHEETS_PATTERN beinhalten in eine Liste eintragen
			List<String> sheetNames = new ArrayList<String>();
			for (int i=0; i<workbook.getNumberOfSheets(); i++) {
				if ((workbook.getSheetName(i).contains(DETAIL_SHEETS_PATTERN))) {
					sheetNames.add( workbook.getSheetName(i) );	
				}			    
			}

			// Alle relevanten Sheets verarbeiten
			for (String sheetName : sheetNames ) {
				
				logger.debug("Sheet \"" +sheetName +"\" wird verarbeitet.");
				
				Sheet sheet = workbook.getSheet(sheetName);

				// Schleife über alle Reihen in Excel-Sheet
				Iterator<Row> rows = sheet.rowIterator();
				while (rows.hasNext ())	{

					Row currentRow = rows.next();

					// Die ersten X-Reihen auslassen
					if(currentRow.getRowNum()<startingRow-1) {
						continue;
					}

					Details_1_5 details_1_5 = new Details_1_5();

					BigDecimal trc = new BigDecimal((currentRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_5.setTRC(trc.toString());
					BigDecimal betragCHF = new BigDecimal((currentRow.getCell(1)).getNumericCellValue(), MathContext.DECIMAL64);
					// Absoluten Wert auf 2 Nachkommastellen nach oben gerundet setzen
					details_1_5.setBetragInCHF(betragCHF.abs().setScale(2, RoundingMode.HALF_UP));					
					BigDecimal betragEUR = new BigDecimal((currentRow.getCell(2)).getNumericCellValue(), MathContext.DECIMAL64);
					// Absoluten Wert auf 2 Nachkommastellen nach oben gerundet setzen
					details_1_5.setBetragInEUR(betragEUR.abs().setScale(2, RoundingMode.HALF_UP));
					BigDecimal referenz = new BigDecimal((currentRow.getCell(3)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_5.setReferenz(referenz.toString());
					BigDecimal stamm = new BigDecimal((currentRow.getCell(4)).getNumericCellValue(), MathContext.DECIMAL64);
					details_1_5.setStamm(stamm.toString());
					details_1_5.setLand((currentRow.getCell(5)).getStringCellValue());
					
					logger.trace(details_1_5);

					DETAILS_1_5_REPOSITORY.save(details_1_5);				
				}
			}
			workbook.close();

			logger.debug("Anzahl " +PATTERN_1_5 +" Detail Datensätze in DB: " +DETAILS_1_5_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
		}
	}	

	private static File[] findFilesForId(String id) {

		// Ordnername für Excel-Dateien aus Properties lesen, falls Wert in Properties-File leer, wird Root-Ordner angenommen
		if (SOURCE_FOLDER.isEmpty()) {
			SOURCE_FOLDER = "./";
		}

		File[] foundFiles = new File[0];

		File sourceFolderFile = new File(SOURCE_FOLDER);

		if (sourceFolderFile.exists()) {
			foundFiles = sourceFolderFile.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().contains(id);
				}
			});
		}

		return foundFiles;
	}

	// Verarbeitete Dateien in archive-Folder verschieben.
	private static void moveFilesToDoneFolder() {

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss");
		Date date = new Date();

		// archive-Folder besteht aus Originalordner plus "archive" plus aktuellem Zeitstempel
		File archiveFolder = new File(SOURCE_FOLDER + "/archive" +"/" +dateFormat.format(date));

		// archive-Verzeichnis anlegen, falls es noch nicht existiert.
		if (! archiveFolder.exists()) {
			archiveFolder.mkdirs();
		}

		// Alle Dateien die dem Pattern entsprechen verschieben. Sollten dieseblen sein wie die, die verarbeitet wurden.
		for (Map.Entry<String, String> entry : EXCEL_FILE_PATTERNS.entrySet()) {

			File[] files = findFilesForId(entry.getValue());

			files[0].renameTo(new File(archiveFolder + "/" +files[0].getName()));
		}
	}
}
