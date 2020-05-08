package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.Document;
import li.bankfrick.informatik.reporting.csdr.entities.xml.dtos.head003_BizData;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head001.BusinessApplicationHeaderV01;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head003.BusinessDataHeaderV01;

@Service
public class XmlWriterService {
	
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
	public static final Calendar currentTime = Calendar.getInstance();

	public static void generateXML() {
		try {

			// Root-Element erstellen und alle Subelemente generieren lassen.
			JAXBElement<BusinessDataHeaderV01> rootElementBizData = head003_BizData.createBizData();

			// JAXB Context festlegen (entweder auf Package- oder Klassenbasis)
			//JAXBContext jaxbContext = JAXBContext.newInstance("li.bankfrick.informatik.reporting.csdr.entities.xml.head003:li.bankfrick.informatik.reporting.csdr.entities.xml.head001:li.bankfrick.informatik.reporting.csdr.entities.xml.auth072");
			JAXBContext jaxbContext = JAXBContext.newInstance(BusinessDataHeaderV01.class, BusinessApplicationHeaderV01.class, Document.class);

			// Marshaller erzeugen.
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Pretty Print für XML aktivieren.
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// XML JAXB-Element als Datei ausgeben
			jaxbMarshaller.marshal(rootElementBizData, new File(SOURCE_FOLDER +"/" +generateOutputFilename()));

		} catch (JAXBException e) {
			e.printStackTrace();
		}
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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

}
