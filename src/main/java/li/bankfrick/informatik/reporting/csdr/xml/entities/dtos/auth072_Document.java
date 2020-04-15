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

import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.ContactDetails4;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.Document;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.SettlementInternaliser1;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.SettlementInternaliserIdentification1;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.SettlementInternaliserReportHeader1;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.SettlementInternaliserReportV01;
import li.bankfrick.informatik.reporting.csdr.xml.entities.auth072.TransactionOperationType4Code;

@Component
public class auth072_Document {

	private static ObjectFactory objFactory = new ObjectFactory();
	
	private static String FROM_COUNTRY_CODE;
	@Value("${global.reporting.country}")
	public void setFromCountryCode(String fromCountryCode) {
		FROM_COUNTRY_CODE = fromCountryCode;
    }
	
	private static String LEI;
	@Value("${global.bank.frick.lei}")
	public void setLei(String lei) {
		LEI = lei;
    }
	
	private static String REPORT_CURRENCY;
	@Value("${auth.072.rpthdr.currency}")
	public void setReportCurrency(String reportCurrency) {
		REPORT_CURRENCY = reportCurrency;
    }
	
	private static String RP_NAME;
	@Value("${auth.072.responsible.person.name}")
	public void setRpName(String rpName) {
		RP_NAME = rpName;
    }
	
	private static String RP_PHONE;
	@Value("${auth.072.responsible.person.phone}")
	public void setRpPhone(String rpPhone) {
		RP_PHONE = rpPhone;
    }
	
	private static String RP_EMAIL;
	@Value("${auth.072.responsible.person.email}")
	public void setRpEmail(String rpEmail) {
		RP_EMAIL = rpEmail;
    }
	
	private static String RP_FUNCTION;
	@Value("${auth.072.responsible.person.function}")
	public void setRpFunction(String rpFunction) {
		RP_FUNCTION = rpFunction;
    }
	
	private static String CREDTTM_DATE_FORMAT;
	@Value("${auth.072.rpthdr.credttm.format}")
	public void setCredttmFormat(String creddttmFormat) {
		CREDTTM_DATE_FORMAT = creddttmFormat;
    }
	
	private static String RPTGDT_DATE_FORMAT;
	@Value("${auth.072.rpthdr.rptgdt.format}")
	public void setRptgDt(String rptgDt) {
		RPTGDT_DATE_FORMAT = rptgDt;
    }

	public static JAXBElement<Document> createDocument(Calendar cal) {

		// Document-Element erstellen und dem Payload-Element zuweisen. 
		Document document = objFactory.createDocument();
		JAXBElement<Document> pyld = objFactory.createDocument(document);

		// SettlementInternaliserReportV01 anlegen
		SettlementInternaliserReportV01 sttlmIntlrRpt = objFactory.createSettlementInternaliserReportV01();
		
		// Report Header generieren
		SettlementInternaliserReportHeader1 rptHdr = objFactory.createSettlementInternaliserReportHeader1();
		rptHdr.setCreDtTm(createCreDtTm(cal));
		rptHdr.setRptgDt(createRptgDt(cal));
		rptHdr.setCcy(REPORT_CURRENCY);
		rptHdr.setRptSts(TransactionOperationType4Code.NEWT);
		
		// Reportheader dem SettlementInternaliserReportV01 zuweisen
		sttlmIntlrRpt.setRptHdr(rptHdr);
		
		// Settlement Internaliser generieren
		SettlementInternaliser1 settlementInternaliser = objFactory.createSettlementInternaliser1();
		SettlementInternaliserIdentification1 settlementInternaliserIdentification = objFactory.createSettlementInternaliserIdentification1();
		ContactDetails4 responsiblePerson = objFactory.createContactDetails4();
		responsiblePerson.setNm(RP_NAME);
		responsiblePerson.setPhneNb(RP_PHONE);
		responsiblePerson.setEmailAdr(RP_EMAIL);
		responsiblePerson.setFctn(RP_FUNCTION);
		
		settlementInternaliserIdentification.setLEI(LEI);
		settlementInternaliserIdentification.setRspnsblPrsn(responsiblePerson);
		settlementInternaliserIdentification.setCtry(FROM_COUNTRY_CODE);
		
		settlementInternaliser.setId(settlementInternaliserIdentification);
		
		// Settlement Internaliser dem SettlementInternaliserReportV01 zuweisen
		sttlmIntlrRpt.setSttlmIntlr(settlementInternaliser);
		
		// SettlementInternaliserReportV01 dem Document zuweisen
		document.setSttlmIntlrRpt(sttlmIntlrRpt);

		return pyld;

	}

	// Datum des übergebenen Kalenderobjektes für CreDtTm formatieren
	private static XMLGregorianCalendar createCreDtTm(Calendar cal) {

		Calendar tmpCal = (Calendar) cal.clone();
		DateFormat format = new SimpleDateFormat(CREDTTM_DATE_FORMAT);

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

	// Datum des letzen Quartalstages für RptgDt ermitteln
	private static XMLGregorianCalendar createRptgDt(Calendar cal) {

		Calendar tmpCal = (Calendar) cal.clone();
		DateFormat format = new SimpleDateFormat(RPTGDT_DATE_FORMAT);

		// 3 Monate vom jetztigen Datum abziehen
		tmpCal.add(Calendar.MONTH, -3);

		int quarter = (tmpCal.get(Calendar.MONTH) / 3) + 1;
		switch (quarter) {
		case 3:
			// 30. September
			tmpCal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			tmpCal.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 2:
			// 30. Juni
			tmpCal.set(Calendar.MONTH, Calendar.JUNE);
			tmpCal.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 1:
			// 31. März
			tmpCal.set(Calendar.MONTH, Calendar.MARCH);
			tmpCal.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 0:	default:
			// 31. Dezember des letzten Jahres
			tmpCal.set(Calendar.MONTH, Calendar.DECEMBER);
			tmpCal.set(Calendar.DAY_OF_MONTH, 31);
			tmpCal.set(Calendar.YEAR, (tmpCal.get(Calendar.YEAR)-1));
		}
		
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
