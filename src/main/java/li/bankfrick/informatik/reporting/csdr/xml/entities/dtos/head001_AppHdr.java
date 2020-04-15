package li.bankfrick.informatik.reporting.csdr.xml.entities.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.BusinessApplicationHeaderV01;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.GenericOrganisationIdentification1;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.OrganisationIdentification7;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.Party10Choice;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.Party9Choice;
import li.bankfrick.informatik.reporting.csdr.xml.entities.head001.PartyIdentification42;

@Component
public class head001_AppHdr {

	private static ObjectFactory objFactory = new ObjectFactory();

	// Variablen für BizMsgIdr
	private static String FROM_COUNTRY_CODE;
	@Value("${global.reporting.country}")
	public void setFromCountryCode(String fromCountryCode) {
		FROM_COUNTRY_CODE = fromCountryCode;
    }
	
	private static String TO_COUNTRY_CODE;
	@Value("${head.001.apphdr.to.country}")
	public void setToCountryCode(String toCountryCode) {
		TO_COUNTRY_CODE = toCountryCode;
    }

	private static String LEI;
	@Value("${global.bank.frick.lei}")
	public void setLei(String lei) {
		LEI = lei;
    }
	
	private static String LAUFNUMMER;
	@Value("${head.001.apphdr.laufnummer}")
	public void setLaufnummer(String laufnummer) {
		LAUFNUMMER = laufnummer;
    }
	
	// Variable für MsgDefIdr
	private static String MSG_DEF_IDR;
	@Value("${head.001.apphdr.msgdefidr}")
	public void setMsgDefIdr(String msgDefIdr) {
		MSG_DEF_IDR = msgDefIdr;
    }
	
	private static String CREDT_DATE_FORMAT;
	@Value("${head.001.apphdr.credt.date.format}")
	public void setCredtDateFormat(String credtDateFormat) {
		CREDT_DATE_FORMAT = credtDateFormat;
    }
	
	public static JAXBElement<BusinessApplicationHeaderV01> createAppHdr(Calendar cal) {

		// appHdr generieren
		BusinessApplicationHeaderV01 businessApplicationHeaderV01 = objFactory.createBusinessApplicationHeaderV01();
		JAXBElement<BusinessApplicationHeaderV01> appHdr = objFactory.createAppHdr(businessApplicationHeaderV01);

		// "Fr"-Objektbaum generieren 
		Party9Choice frOrgId = objFactory.createParty9Choice();
		PartyIdentification42 frOrgIdId = objFactory.createPartyIdentification42();
		Party10Choice frOrgIdIdOrgId = objFactory.createParty10Choice();
		OrganisationIdentification7 frOthr = objFactory.createOrganisationIdentification7();
		GenericOrganisationIdentification1 frOtherId = objFactory.createGenericOrganisationIdentification1();
		frOtherId.setId(FROM_COUNTRY_CODE);

		frOthr.getOthr().add(frOtherId);
		frOrgIdIdOrgId.setOrgId(frOthr);
		frOrgIdId.setId(frOrgIdIdOrgId);
		frOrgId.setOrgId(frOrgIdId);

		// "Fr"-Objektbaum dem Header zuweisen
		businessApplicationHeaderV01.setFr(frOrgId);

		// "To"-Objektbaum generieren 
		Party9Choice toOrgId = objFactory.createParty9Choice();
		PartyIdentification42 toOrgIdId = objFactory.createPartyIdentification42();
		Party10Choice toOrgIdIdOrgId = objFactory.createParty10Choice();
		OrganisationIdentification7 toOthr = objFactory.createOrganisationIdentification7();
		GenericOrganisationIdentification1 toOtherId = objFactory.createGenericOrganisationIdentification1();
		toOtherId.setId(TO_COUNTRY_CODE);

		toOthr.getOthr().add(toOtherId);
		toOrgIdIdOrgId.setOrgId(toOthr);
		toOrgIdId.setId(toOrgIdIdOrgId);
		toOrgId.setOrgId(toOrgIdId);

		// "To"-Objektbaum dem Header zuweisen
		businessApplicationHeaderV01.setTo(toOrgId);
		
		// "BizMsgIdr" generieren und dem Header zuweisen
		businessApplicationHeaderV01.setBizMsgIdr(getBizMsgIdr(cal));
		
		// "MsgDefIdr" dem Header zuweisen
		businessApplicationHeaderV01.setMsgDefIdr(MSG_DEF_IDR);

		// "CreDt" generieren und dem Header zuweisen
		businessApplicationHeaderV01.setCreDt(getCreDt(cal));
		
		// businessApplicationHeaderV01 dem appHdr zuweisen 
		appHdr.setValue(businessApplicationHeaderV01);

		return appHdr;
	}

	private static String getBizMsgIdr(Calendar cal) {		
		
		Calendar tmpCal = (Calendar) cal.clone();
		
		// 3 Monate vom jetztigen Datum abziehen
		tmpCal.add(Calendar.MONTH, -3);
		
		int quarter = (tmpCal.get(Calendar.MONTH) / 3) + 1;
		int year = tmpCal.get(Calendar.YEAR);
		
		return FROM_COUNTRY_CODE + "-" + LEI + "-" + year + "-Q" +quarter + "_" + LAUFNUMMER;
	}
	
	private static XMLGregorianCalendar getCreDt(Calendar cal) {

		Calendar tmpCal = (Calendar) cal.clone();
		DateFormat format = new SimpleDateFormat(CREDT_DATE_FORMAT);
				
		Date date = tmpCal.getTime();
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xmlGregorianCalendar;
	}
}
