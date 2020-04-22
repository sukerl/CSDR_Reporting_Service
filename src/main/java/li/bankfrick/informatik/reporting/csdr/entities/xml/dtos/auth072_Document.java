package li.bankfrick.informatik.reporting.csdr.entities.xml.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.Document;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliser1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserReportHeader1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserReportV01;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.TransactionOperationType4Code;

@Component
public class auth072_Document {

	private static final Logger logger = LogManager.getLogger(auth072_Document.class);
	
	private static ObjectFactory objFactory = new ObjectFactory();

	private static String REPORT_CURRENCY;
	@Value("${auth.072.rpthdr.currency}")
	public void setReportCurrency(String reportCurrency) {
		REPORT_CURRENCY = reportCurrency;
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
		
		logger.debug("auth072 : Document generieren.");

		// Document-Element erstellen und dem Payload-Element zuweisen. 
		Document document = objFactory.createDocument();
		JAXBElement<Document> pyld = objFactory.createDocument(document);

		logger.debug("auth072 : Document->SttlmIntlrRpt generieren.");
		// SettlementInternaliserReportV01 anlegen
		SettlementInternaliserReportV01 sttlmIntlrRpt = objFactory.createSettlementInternaliserReportV01();
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->RptHdr generieren.");
		// Report Header generieren
		SettlementInternaliserReportHeader1 rptHdr = objFactory.createSettlementInternaliserReportHeader1();
		rptHdr.setCreDtTm(createCreDtTm(cal));
		rptHdr.setRptgDt(createRptgDt(cal));
		rptHdr.setCcy(REPORT_CURRENCY);
		rptHdr.setRptSts(TransactionOperationType4Code.NEWT);
		
		// Reportheader dem SettlementInternaliserReportV01 zuweisen
		sttlmIntlrRpt.setRptHdr(rptHdr);
		logger.debug("auth072 : Document->SttlmIntlrRpt->RptHdr generiert.");

		// Settlement Internaliser generieren und dem SettlementInternaliserReportV01 zuweisen
		SettlementInternaliser1 settlementInternaliser = auth072_SttlmIntlr.createSettlementInternaliser();
		sttlmIntlrRpt.setSttlmIntlr(settlementInternaliser);
		
		// Liste der IssuerCSDReports generieren und dem SettlementInternaliserReportV01 zuweisen
		sttlmIntlrRpt.getIssrCSD().addAll(auth072_IssrCSDs.createIssuerCSDReports());
		
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
