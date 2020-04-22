package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.Document;
import li.bankfrick.informatik.reporting.csdr.entities.xml.dtos.head003_BizData;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head001.BusinessApplicationHeaderV01;
import li.bankfrick.informatik.reporting.csdr.entities.xml.head003.BusinessDataHeaderV01;

@Service
public class XmlWriterService {

	static String outputFilename = "NCALI_DATISR_CSDR9_LI-529900RQOBT3ZJMDRK43-2020-Q1.xml";

	public static void generateXML() {
		try {
			// Momentane Zeit und Datum festlegen. Zeitangaben im XML bauen alle darauf auf.
			final Calendar cal = Calendar.getInstance();

			// Root-Element erstellen und alle Subelemente generieren lassen.
			JAXBElement<BusinessDataHeaderV01> rootElementBizData = head003_BizData.createBizData(cal);

			// JAXB Context festlegen (entweder auf Package- oder Klassenbasis)
			//JAXBContext jaxbContext = JAXBContext.newInstance("li.bankfrick.informatik.reporting.csdr.entities.xml.head003:li.bankfrick.informatik.reporting.csdr.entities.xml.head001:li.bankfrick.informatik.reporting.csdr.entities.xml.auth072");
			JAXBContext jaxbContext = JAXBContext.newInstance(BusinessDataHeaderV01.class, BusinessApplicationHeaderV01.class, Document.class);

			// Marshaller erzeugen.
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Pretty Print für XML aktivieren.
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// XML JAXB-Element als Datei ausgeben
			jaxbMarshaller.marshal(rootElementBizData, new File("C:/TEMP/CSDR/" + outputFilename));

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
