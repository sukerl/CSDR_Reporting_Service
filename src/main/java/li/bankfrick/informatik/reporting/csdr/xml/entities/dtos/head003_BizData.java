package li.bankfrick.informatik.reporting.csdr.xml.entities.dtos;

import java.util.Calendar;

import javax.xml.bind.JAXBElement;

import li.bankfrick.informatik.reporting.csdr.xml.entities.head003.BusinessApplicationHeaderEnvelope;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head003.BusinessDataHeaderV01;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head003.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head003.StrictPayload;

public class head003_BizData {

	public static JAXBElement<BusinessDataHeaderV01> createBizData(Calendar cal) {

		// ObjectFactory für xsd:head.003.001.01 initialisieren
		ObjectFactory objFactory = new ObjectFactory();

		// Neues Root Element BizData erstellen
		BusinessDataHeaderV01 businessDataHeaderV01 = objFactory.createBusinessDataHeaderV01();
		JAXBElement<BusinessDataHeaderV01> rootElementBizData = objFactory.createBizData(businessDataHeaderV01);

		// Application Header erstellen
		BusinessApplicationHeaderEnvelope businessApplicationHeaderEnvelope = objFactory.createBusinessApplicationHeaderEnvelope();
		businessDataHeaderV01.setHdr(businessApplicationHeaderEnvelope);
		businessApplicationHeaderEnvelope.setAny(head001_AppHdr.createAppHdr(cal));

		// Payload erstellen
		StrictPayload strictPayload = objFactory.createStrictPayload();
		strictPayload.setAny(auth072_Document.createDocument(cal));
		businessDataHeaderV01.setPyld(strictPayload);

		return rootElementBizData;
	}

}
