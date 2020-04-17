package li.bankfrick.informatik.reporting.csdr;

import java.io.StringWriter;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import li.bankfrick.informatik.reporting.csdr.entities.xml.dtos.head003_BizData;
import li.bankfrick.informatik.reporting.csdr.services.ExcelToDbLoader;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head003.BusinessDataHeaderV01;

@SpringBootApplication
public class CsdrReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsdrReportingServiceApplication.class, args);
		
		final Calendar cal = Calendar.getInstance();
		
		ExcelToDbLoader.readExcelFiles();
		
		JAXBElement<BusinessDataHeaderV01> rootElementBizData = head003_BizData.createBizData(cal);
		
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(
					"li.bankfrick.informatik.reporting.csdr.entities.xml.head003:li.bankfrick.informatik.reporting.csdr.entities.xml.head001:li.bankfrick.informatik.reporting.csdr.entities.xml.auth072");

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Print XML String to Console
			StringWriter sw = new StringWriter();

			// Write XML to StringWriter
			jaxbMarshaller.marshal(rootElementBizData, sw);

			// Verify XML Content
			String xmlContent = sw.toString();
			System.out.println(xmlContent);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
