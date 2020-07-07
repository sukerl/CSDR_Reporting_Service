package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.Document;
import li.bankfrick.informatik.reporting.csdr.entities.xml.dtos.head003_BizData;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head001.BusinessApplicationHeaderV01;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head003.BusinessDataHeaderV01;

@Service
public class XmlWriterService {

	private static final Logger logger = LogManager.getLogger(XmlWriterService.class);

	private static String SOURCE_FOLDER;
	@Value("${generic.source.directory}")
	public void setSourceFolder(String sourceFolder) {
		SOURCE_FOLDER = sourceFolder;
	}

	private static String RPTGDT_DATE;
	@Value("${generic.reporting.date}")
	public void setRptgDt(String rptgDt) {
		RPTGDT_DATE = rptgDt;
	}

	private static String RPTGDT_DATE_FORMAT;
	@Value("${technical.xml.auth.072.rpthdr.rptgdt.format}")
	public void setRptgDtFormat(String rptgDtFormat) {
		RPTGDT_DATE_FORMAT = rptgDtFormat;
	}

	private static String LEI;
	@Value("${generic.bank.frick.lei}")
	public void setLei(String lei) {
		LEI = lei;
	}

	private static String FROM_COUNTRY_CODE;
	@Value("${generic.reporting.country}")
	public void setFromCountryCode(String fromCountryCode) {
		FROM_COUNTRY_CODE = fromCountryCode;
	}

	// Momentane Zeit und Datum festlegen. Zeitangaben im XML bauen alle darauf auf.
	public static Calendar currentTime = Calendar.getInstance();

	public static void generateXML() {

		logger.info("Generieren der XML-Datei beginnt.");

		try {
			
			currentTime = Calendar.getInstance();
			
			StringWriter stringWriter = new StringWriter();

			// Root-Element erstellen und alle Subelemente generieren lassen.
			JAXBElement<BusinessDataHeaderV01> rootElementBizData = head003_BizData.createBizData();

			// JAXB Context festlegen (entweder auf Package- oder Klassenbasis)
			//JAXBContext jaxbContext = JAXBContext.newInstance("li.bankfrick.informatik.reporting.csdr.entities.xml.head003:li.bankfrick.informatik.reporting.csdr.entities.xml.head001:li.bankfrick.informatik.reporting.csdr.entities.xml.auth072");
			JAXBContext jaxbContext = JAXBContext.newInstance(BusinessDataHeaderV01.class, BusinessApplicationHeaderV01.class, Document.class);

			// Marshaller erzeugen.
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Pretty Print für XML durch Marshaller aktivieren.
			//jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// XML JAXB-Element als Datei ausgeben
			File outputFile = new File(SOURCE_FOLDER +"/" +generateOutputFilename());
			//jaxbMarshaller.marshal(rootElementBizData, outputFile);
			jaxbMarshaller.marshal(rootElementBizData, stringWriter);
			
			// Nochmaliger Pretty Print
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(new StringReader(stringWriter.toString())),new StreamResult(outputFile));

			logger.info("Datei " +outputFile +" wurde generiert.");
			
			createZipFile(outputFile);

		} catch (Exception e) {
			logger.error(e);
		}

		logger.info("Generieren der XML-Datei abgeschlossen.");
	}

	public static Calendar getCurrentTime() {
		return currentTime;
	}

	// Erstellen des Business Message Identifiers
	private static String generateOutputFilename() {		

		Date date = null;
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat(RPTGDT_DATE_FORMAT);

		// Wenn das Reportdatum im application.properties gesetzt ist, wird dieses verwendet.
		// Sonst wird das vorhergehende Quartal berechnet.
		if(RPTGDT_DATE.isEmpty() == false) {			
			try {
				date = format.parse(RPTGDT_DATE);
			} catch (ParseException e) {
				logger.error(e);
			}
			cal.setTime(date);
		} else {
			// 3 Monate vom jetztigen Datum abziehen
			cal.add(Calendar.MONTH, -3);

		}

		int quarter = (cal.get(Calendar.MONTH) / 3) + 1;
		int year = cal.get(Calendar.YEAR);		

		//NCALI_DATISR_CSDR9_LI-529900RQOBT3ZJMDRK43-2020-Q1.xml
		return "NCA" +FROM_COUNTRY_CODE + "_DATISR_CSDR9_" +FROM_COUNTRY_CODE +"-" + LEI + "-" + year + "-Q" +quarter + ".xml";
	}

	// ZIP-Datei erstellen
	public static void createZipFile(File outputFile) {
		String sourceFile = outputFile.getAbsolutePath();
		String targetFile = sourceFile.replace(".xml", ".zip");
		try {
			FileOutputStream fos = new FileOutputStream(targetFile);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			FileInputStream fis = new FileInputStream(outputFile);
			ZipEntry zipEntry = new ZipEntry(outputFile.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			zipOut.close();
			fis.close();
			fos.close();
		} catch (Exception e) {
			logger.error(e);
		}

		logger.info("ZIP-File " +targetFile +" wurde generiert.");
	}
}
