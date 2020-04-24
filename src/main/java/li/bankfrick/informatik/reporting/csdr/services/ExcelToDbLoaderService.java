package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.CSDRReportingProperty;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_1;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.Details_1_2;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_1;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_3;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_4;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.ZF_1_5;
import li.bankfrick.informatik.reporting.csdr.repositories.CSDRReportingProperty_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_2_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_3_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_4_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_5_Repository;

@Service
public class ExcelToDbLoaderService {

	private static final Logger logger = LogManager.getLogger(ExcelToDbLoaderService.class);

	private static CSDRReportingProperty_Repository PROPERTIES_REPOSITORY;
	private static Details_1_1_Repository DETAILS_1_1_REPOSITORY;
	private static Details_1_2_Repository DETAILS_1_2_REPOSITORY;	
	private static ZF_1_1_Repository ZF_1_1_REPOSITORY;
	private static ZF_1_3_Repository ZF_1_3_REPOSITORY;
	private static ZF_1_4_Repository ZF_1_4_REPOSITORY;
	private static ZF_1_5_Repository ZF_1_5_REPOSITORY;

	public ExcelToDbLoaderService(CSDRReportingProperty_Repository PROPERTIES_REPOSITORY, Details_1_1_Repository DETAILS_1_1_REPOSITORY, Details_1_2_Repository DETAILS_1_2_REPOSITORY, ZF_1_1_Repository ZF_1_1_REPOSITORY, ZF_1_3_Repository ZF_1_3_REPOSITORY, ZF_1_4_Repository ZF_1_4_REPOSITORY, ZF_1_5_Repository ZF_1_5_REPOSITORY) {
		ExcelToDbLoaderService.PROPERTIES_REPOSITORY = PROPERTIES_REPOSITORY;
		ExcelToDbLoaderService.DETAILS_1_1_REPOSITORY = DETAILS_1_1_REPOSITORY;
		ExcelToDbLoaderService.DETAILS_1_2_REPOSITORY = DETAILS_1_2_REPOSITORY;
		ExcelToDbLoaderService.ZF_1_1_REPOSITORY = ZF_1_1_REPOSITORY;
		ExcelToDbLoaderService.ZF_1_3_REPOSITORY = ZF_1_3_REPOSITORY;
		ExcelToDbLoaderService.ZF_1_4_REPOSITORY = ZF_1_4_REPOSITORY;
		ExcelToDbLoaderService.ZF_1_5_REPOSITORY = ZF_1_5_REPOSITORY;
	}

	private static String SOURCE_FOLDER;
	@Value("${excel.files.source.folder}")
	public void setSourceFolder(String sourceFolder) {
		SOURCE_FOLDER = sourceFolder;
	}

	private static String PATTERN_1_1;
	@Value("${excel.pattern.1.1.file}")
	public void setPattern_1_1(String pattern_1_1) {
		PATTERN_1_1 = pattern_1_1;
	}
	
	private static String PATTERN_1_2;
	@Value("${excel.pattern.1.2.file}")
	public void setPattern_1_2(String pattern_1_2) {
		PATTERN_1_2 = pattern_1_2;
	}

	private static String PATTERN_1_3;
	@Value("${excel.pattern.1.3.file}")
	public void setPattern_1_3(String pattern_1_3) {
		PATTERN_1_3 = pattern_1_3;
	}
	
	private static String PATTERN_1_4;
	@Value("${excel.pattern.1.4.file}")
	public void setPattern_1_4(String pattern_1_4) {
		PATTERN_1_4 = pattern_1_4;
	}
	
	private static String PATTERN_1_5;
	@Value("${excel.pattern.1.5.file}")
	public void setPattern_1_5(String pattern_1_5) {
		PATTERN_1_5 = pattern_1_5;
	}
		
	private static String DETAIL_SHEET_1_1;
	@Value("${excel.pattern.1.1.file.details.sheet}")
	public void setDetailSheet_1_1(String detailSheet_1_1) {
		DETAIL_SHEET_1_1 = detailSheet_1_1;
	}
	
	private static String ZF_SHEET_1_1;
	@Value("${excel.pattern.1.1.file.zf.sheet}")
	public void setZF_1_1(String zf_1_1) {
		ZF_SHEET_1_1 = zf_1_1;
	}
	
	private static String DETAIL_SHEET_1_2;
	@Value("${excel.pattern.1.2.file.details.sheet}")
	public void setDetailSheet_1_2(String detailSheet_1_2) {
		DETAIL_SHEET_1_2 = detailSheet_1_2;
	}
	
	private static String ZF_SHEET_1_3;
	@Value("${excel.pattern.1.3.file.zf.sheet}")
	public void setZF_1_3(String zf_1_3) {
		ZF_SHEET_1_3 = zf_1_3;
	}
	
	private static String ZF_SHEET_1_4_PROF;
	@Value("${excel.pattern.1.4.file.zf.prof.sheet}")
	public void setZF_1_4_Prfssnl(String zf_1_4_Prfssnl) {
		ZF_SHEET_1_4_PROF = zf_1_4_Prfssnl;
	}
	
	private static String ZF_SHEET_1_4_KLEIN;
	@Value("${excel.pattern.1.4.file.zf.klein.sheet}")
	public void setZF_1_4_Rtl(String zf_1_4_Rtl) {
		ZF_SHEET_1_4_KLEIN = zf_1_4_Rtl;
	}
	
	private static String ZF_SHEET_1_5;
	@Value("${excel.pattern.1.5.file.zf.sheet}")
	public void setZF_1_5(String zf_1_5) {
		ZF_SHEET_1_5 = zf_1_5;
	}

	public static void readExcelFiles(File propertyFile) {

		load_Properties(propertyFile);
		load_Details_1_1();
		load_Details_1_2();
		load_ZF_1_1();
		load_ZF_1_3();
		load_ZF_1_4_Prfssnl();
		load_ZF_1_4_Rtl();
		load_ZF_1_5();
	}
	
	private static void load_Properties(File propertyFile) {

		Workbook workbook;
		int startingRow = 2;
		
		try {
		workbook = WorkbookFactory.create(propertyFile);
		Sheet sheet = workbook.getSheet("Properties");
		
		// Schleife über alle Reihen in Excel-Sheet
					Iterator<Row> rows = sheet.rowIterator();
					while (rows.hasNext ())	{
						
						Row currentRow = rows.next();

						// Die ersten X-Reihen auslassen
						if(currentRow.getRowNum()<startingRow-1) {
							continue;
						}
						
						CSDRReportingProperty CSDRReportingProperty = new CSDRReportingProperty();
						
						CSDRReportingProperty.setKey(currentRow.getCell(0).getStringCellValue());
						CSDRReportingProperty.setValue(currentRow.getCell(1).getStringCellValue());
						
						PROPERTIES_REPOSITORY.save(CSDRReportingProperty);				
					}
					
					workbook.close();
					
					logger.debug("Anzahl Properties in DB: " +PROPERTIES_REPOSITORY.count());
		
		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
		
	}

	private static void load_Details_1_1() {

		Workbook workbook;
		int startingRow = 2;
		
		File[] files = findFilesForId(PATTERN_1_1);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_1 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(DETAIL_SHEET_1_1);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				Details_1_1 details_1_1 = new Details_1_1();
				
				details_1_1.setGegenseite((currentRow.getCell(0)).getStringCellValue());
				details_1_1.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				details_1_1.setTRC((currentRow.getCell(2)).getStringCellValue());
				details_1_1.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				details_1_1.setDepotstelle((currentRow.getCell(4)).getStringCellValue());
				details_1_1.setBezeichnungDepotstelle((currentRow.getCell(5)).getStringCellValue());
				details_1_1.setLEI((currentRow.getCell(6)).getStringCellValue());
				BigDecimal transaktion = new BigDecimal((currentRow.getCell(7)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_1.setTransaktion(transaktion.intValue());
				details_1_1.setISINland((currentRow.getCell(8)).getStringCellValue());
				details_1_1.setValor((currentRow.getCell(9)).getStringCellValue());
				details_1_1.setKurzbezeichnung((currentRow.getCell(10)).getStringCellValue());
				BigDecimal titelart = new BigDecimal((currentRow.getCell(11)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_1.setTitelart(titelart.intValue());
				//details_1_1.setLiefercode((currentRow.getCell(12)).getStringCellValue());
				details_1_1.setLiefercode("");
				BigDecimal systemdatum = new BigDecimal((currentRow.getCell(13)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_1.setSystemdatum(systemdatum.toString());
				BigDecimal betragCHF = new BigDecimal((currentRow.getCell(14)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_1.setBetragInCHF(betragCHF);
				BigDecimal betragEUR = new BigDecimal((currentRow.getCell(15)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_1.setBetragInEUR(betragEUR.setScale(2, RoundingMode.HALF_UP));
				
				DETAILS_1_1_REPOSITORY.save(details_1_1);				
			}
			
			workbook.close();
			
			logger.debug("Anzahl " +PATTERN_1_1 +" Detail Datensätze in DB: " +DETAILS_1_1_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_Details_1_2() {

		Workbook workbook;
		int startingRow = 2;

		File[] files = findFilesForId(PATTERN_1_2);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_2 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(DETAIL_SHEET_1_2);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				Details_1_2 details_1_2 = new Details_1_2();
				
				details_1_2.setGegenseite((currentRow.getCell(0)).getStringCellValue());
				details_1_2.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				details_1_2.setTRC((currentRow.getCell(2)).getStringCellValue());
				details_1_2.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				details_1_2.setDepotstelle((currentRow.getCell(4)).getStringCellValue());
				details_1_2.setBezeichnungDepotstelle((currentRow.getCell(5)).getStringCellValue());
				details_1_2.setLEI((currentRow.getCell(6)).getStringCellValue());
				BigDecimal transaktion = new BigDecimal((currentRow.getCell(7)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_2.setTransaktion(transaktion.intValue());
				details_1_2.setISINland((currentRow.getCell(8)).getStringCellValue());
				details_1_2.setValor((currentRow.getCell(9)).getStringCellValue());
				details_1_2.setKurzbezeichnung((currentRow.getCell(10)).getStringCellValue());
				BigDecimal titelart = new BigDecimal((currentRow.getCell(11)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_2.setTitelart(titelart.intValue());
				//details_1_2.setLiefercode((currentRow.getCell(12)).getStringCellValue());
				details_1_2.setLiefercode("");
				BigDecimal systemdatum = new BigDecimal((currentRow.getCell(13)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_2.setSystemdatum(systemdatum.toString());
				BigDecimal betragCHF = new BigDecimal((currentRow.getCell(14)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_2.setBetragInCHF(betragCHF);
				BigDecimal betragEUR = new BigDecimal((currentRow.getCell(15)).getNumericCellValue(), MathContext.DECIMAL64);
				details_1_2.setBetragInEUR(betragEUR.setScale(2, RoundingMode.HALF_UP));
				
				DETAILS_1_2_REPOSITORY.save(details_1_2);				
			}
			
			workbook.close();
			
			logger.debug("Anzahl " +PATTERN_1_2 +" Detail Datensätze in DB: " +DETAILS_1_2_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_ZF_1_1() {

		Workbook workbook;
		int startingRow = 2;

		File[] files = findFilesForId(PATTERN_1_1);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_1 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(ZF_SHEET_1_1);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				ZF_1_1 zf_1_1 = new ZF_1_1();
				
				BigDecimal gegenseite = new BigDecimal((currentRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_1.setGegenseite(gegenseite.intValue());
				zf_1_1.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				zf_1_1.setTRC((currentRow.getCell(2)).getStringCellValue());
				zf_1_1.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				BigDecimal anzahlTransaktionen = new BigDecimal((currentRow.getCell(4)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_1.setAnzahlTransaktionen(anzahlTransaktionen.intValue());
				BigDecimal gegenwertCHF = new BigDecimal((currentRow.getCell(5)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_1.setGegenwertInCHF(gegenwertCHF.setScale(2, RoundingMode.HALF_UP));
				BigDecimal gegenwertEUR = new BigDecimal((currentRow.getCell(6)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_1.setGegenwertInEUR(gegenwertEUR.setScale(2, RoundingMode.HALF_UP));
				
				ZF_1_1_REPOSITORY.save(zf_1_1);				
			}
			
			workbook.close();
			
			logger.debug("Anzahl " +PATTERN_1_1 +" ZF Datensätze in DB: " +ZF_1_1_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_ZF_1_3() {

		Workbook workbook;
		int startingRow = 2;

		File[] files = findFilesForId(PATTERN_1_3);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_3 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(ZF_SHEET_1_3);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				ZF_1_3 zf_1_3 = new ZF_1_3();
				
				BigDecimal gegenseite = new BigDecimal((currentRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_3.setGegenseite(gegenseite.intValue());
				zf_1_3.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				zf_1_3.setTRC((currentRow.getCell(2)).getStringCellValue());
				zf_1_3.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				BigDecimal anzahlTransaktionen = new BigDecimal((currentRow.getCell(4)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_3.setAnzahlTransaktionen(anzahlTransaktionen.intValue());
				BigDecimal gegenwertCHF = new BigDecimal((currentRow.getCell(5)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_3.setGegenwertInCHF(gegenwertCHF.setScale(2, RoundingMode.HALF_UP));
				BigDecimal gegenwertEUR = new BigDecimal((currentRow.getCell(6)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_3.setGegenwertInEUR(gegenwertEUR.setScale(2, RoundingMode.HALF_UP));
				
				ZF_1_3_REPOSITORY.save(zf_1_3);				
			}
			
			workbook.close();
			
			logger.debug("Anzahl " +PATTERN_1_3 +" ZF Datensätze in DB: " +ZF_1_3_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_ZF_1_4_Prfssnl() {

		Workbook workbook;
		int startingRow = 2;
		String anlegerTyp = "Prfssnl";

		File[] files = findFilesForId(PATTERN_1_4);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_4 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(ZF_SHEET_1_4_PROF);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				ZF_1_4 zf_1_4 = new ZF_1_4();
				
				zf_1_4.setAnlegerTyp(anlegerTyp);
				BigDecimal gegenseite = new BigDecimal((currentRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenseite(gegenseite.intValue());
				zf_1_4.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				zf_1_4.setTRC((currentRow.getCell(2)).getStringCellValue());
				zf_1_4.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				BigDecimal anzahlTransaktionen = new BigDecimal((currentRow.getCell(4)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setAnzahlTransaktionen(anzahlTransaktionen.intValue());
				BigDecimal gegenwertCHF = new BigDecimal((currentRow.getCell(5)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenwertInCHF(gegenwertCHF.setScale(2, RoundingMode.HALF_UP));
				BigDecimal gegenwertEUR = new BigDecimal((currentRow.getCell(6)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenwertInEUR(gegenwertEUR.setScale(2, RoundingMode.HALF_UP));
				
				ZF_1_4_REPOSITORY.save(zf_1_4);				
			}
			
			workbook.close();
			
			List<ZF_1_4> investorsByType = ZF_1_4_REPOSITORY.findByAnlegerTyp(anlegerTyp);
			
			logger.debug("Anzahl " +PATTERN_1_4 +" ZF Datensätze vom Typ " +anlegerTyp +" in DB: " +investorsByType.size());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_ZF_1_4_Rtl() {

		Workbook workbook;
		int startingRow = 2;
		String anlegerTyp = "Rtl";

		File[] files = findFilesForId(PATTERN_1_4);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_4 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(ZF_SHEET_1_4_KLEIN);
			
			// Schleife über alle Reihen in Excel-Sheet
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext ())	{
				
				Row currentRow = rows.next();

				// Die ersten X-Reihen auslassen
				if(currentRow.getRowNum()<startingRow-1) {
					continue;
				}
				
				ZF_1_4 zf_1_4 = new ZF_1_4();
				
				zf_1_4.setAnlegerTyp(anlegerTyp);
				BigDecimal gegenseite = new BigDecimal((currentRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenseite(gegenseite.intValue());
				zf_1_4.setBezeichnungGegenseite((currentRow.getCell(1)).getStringCellValue());
				zf_1_4.setTRC((currentRow.getCell(2)).getStringCellValue());
				zf_1_4.setBezeichnungTRC((currentRow.getCell(3)).getStringCellValue());
				BigDecimal anzahlTransaktionen = new BigDecimal((currentRow.getCell(4)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setAnzahlTransaktionen(anzahlTransaktionen.intValue());
				BigDecimal gegenwertCHF = new BigDecimal((currentRow.getCell(5)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenwertInCHF(gegenwertCHF.setScale(2, RoundingMode.HALF_UP));
				BigDecimal gegenwertEUR = new BigDecimal((currentRow.getCell(6)).getNumericCellValue(), MathContext.DECIMAL64);
				zf_1_4.setGegenwertInEUR(gegenwertEUR.setScale(2, RoundingMode.HALF_UP));
				
				ZF_1_4_REPOSITORY.save(zf_1_4);				
			}
			
			workbook.close();
			
			List<ZF_1_4> investorsByType = ZF_1_4_REPOSITORY.findByAnlegerTyp(anlegerTyp);
			
			logger.debug("Anzahl " +PATTERN_1_4 +" ZF Datensätze vom Typ " +anlegerTyp +" in DB: " +investorsByType.size());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}
	
	private static void load_ZF_1_5() {

		Workbook workbook;

		File[] files = findFilesForId(PATTERN_1_5);

		if (files.length != 1) {
			logger.error("Ungültige Anzahl Dateien für Pattern: " + PATTERN_1_5 + " ; Anzahl gefundene Dateien: "
					+ files.length + " ; Beende Verarbeitung.");
			System.exit(1);
		}

		try {
			workbook = WorkbookFactory.create(files[0]);
			Sheet sheet = workbook.getSheet(ZF_SHEET_1_5);
			
			Row negativeRow = sheet.getRow(1);
			ZF_1_5 zf_1_5_neg = new ZF_1_5();
			
			zf_1_5_neg.setUebertragsArt("negativ");
			BigDecimal anzahlNegTransaktionen = new BigDecimal((negativeRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_neg.setAnzahlTransaktionen(anzahlNegTransaktionen.intValue());
			BigDecimal uebertragNegCHF = new BigDecimal((negativeRow.getCell(1)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_neg.setUebertragInCHF(uebertragNegCHF.setScale(2, RoundingMode.HALF_UP));
			BigDecimal uebertragNegEUR = new BigDecimal((negativeRow.getCell(2)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_neg.setUebertragInEUR(uebertragNegEUR.setScale(2, RoundingMode.HALF_UP));
			
			ZF_1_5_REPOSITORY.save(zf_1_5_neg);
			
			Row positiveRow = sheet.getRow(4);
			ZF_1_5 zf_1_5_pos = new ZF_1_5();
			
			zf_1_5_pos.setUebertragsArt("positiv");
			BigDecimal anzahlPosTransaktionen = new BigDecimal((positiveRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_pos.setAnzahlTransaktionen(anzahlPosTransaktionen.intValue());
			BigDecimal uebertragPosCHF = new BigDecimal((positiveRow.getCell(1)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_pos.setUebertragInCHF(uebertragPosCHF.setScale(2, RoundingMode.HALF_UP));
			BigDecimal uebertragPosEUR = new BigDecimal((positiveRow.getCell(2)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_pos.setUebertragInEUR(uebertragPosEUR.setScale(2, RoundingMode.HALF_UP));
			
			ZF_1_5_REPOSITORY.save(zf_1_5_pos);
			
			Row barOutRow = sheet.getRow(9);
			ZF_1_5 zf_1_5_barout = new ZF_1_5();
			
			zf_1_5_barout.setUebertragsArt("barbezuege");
			BigDecimal anzahlBarbezuegeTransaktionen = new BigDecimal((barOutRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barout.setAnzahlTransaktionen(anzahlBarbezuegeTransaktionen.intValue());
			BigDecimal BarbezuegeCHF = new BigDecimal((barOutRow.getCell(1)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barout.setUebertragInCHF(BarbezuegeCHF.setScale(2, RoundingMode.HALF_UP));
			BigDecimal BarbezuegeEUR = new BigDecimal((barOutRow.getCell(2)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barout.setUebertragInEUR(BarbezuegeEUR.setScale(2, RoundingMode.HALF_UP));
			
			ZF_1_5_REPOSITORY.save(zf_1_5_barout);
			
			Row barInRow = sheet.getRow(13);
			ZF_1_5 zf_1_5_barin = new ZF_1_5();
			
			zf_1_5_barin.setUebertragsArt("bareinzahlungen");
			BigDecimal anzahlBareinzahungenTransaktionen = new BigDecimal((barInRow.getCell(0)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barin.setAnzahlTransaktionen(anzahlBareinzahungenTransaktionen.intValue());
			BigDecimal BareinzahungenCHF = new BigDecimal((barInRow.getCell(1)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barin.setUebertragInCHF(BareinzahungenCHF.setScale(2, RoundingMode.HALF_UP));
			BigDecimal BareinzahungenEUR = new BigDecimal((barInRow.getCell(2)).getNumericCellValue(), MathContext.DECIMAL64);
			zf_1_5_barin.setUebertragInEUR(BareinzahungenEUR.setScale(2, RoundingMode.HALF_UP));
			
			ZF_1_5_REPOSITORY.save(zf_1_5_barin);
			
			workbook.close();
			
			logger.debug("Anzahl " +PATTERN_1_5 +" ZF Datensätze in DB: " +ZF_1_5_REPOSITORY.count());

		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
	}

	private static File[] findFilesForId(String id) {

		File sourceFolderFile = new File(SOURCE_FOLDER);

		return sourceFolderFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().contains(id);
			}
		});
	}

}
