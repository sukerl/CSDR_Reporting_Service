package li.bankfrick.informatik.reporting.csdr.entities.xml.dtos;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.FinInstrm_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.TxTp_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.ContactDetails4;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationData1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationData2;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationDataRate1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationDataVolume1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliser1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserClientType1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserFinancialInstrument1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserIdentification1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserTransactionType1;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_2_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.FinInstrm_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.TxTp_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_3_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_4_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_5_Repository;

@Component
public class auth072_SttlmIntlr {

	private static final Logger logger = LogManager.getLogger(auth072_SttlmIntlr.class);

	private static Details_1_2_Repository DETAILS_1_2_REPOSITORY;
	private static ZF_1_1_Repository ZF_1_1_REPOSITORY;
	private static ZF_1_3_Repository ZF_1_3_REPOSITORY;
	private static ZF_1_4_Repository ZF_1_4_REPOSITORY;
	private static ZF_1_5_Repository ZF_1_5_REPOSITORY;
	private static FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY;
	private static TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY;

	public auth072_SttlmIntlr(Details_1_2_Repository DETAILS_1_2_REPOSITORY, ZF_1_1_Repository ZF_1_1_REPOSITORY,
			ZF_1_3_Repository ZF_1_3_REPOSITORY, ZF_1_4_Repository ZF_1_4_REPOSITORY,
			ZF_1_5_Repository ZF_1_5_REPOSITORY, FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY,
			TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY) {
		auth072_SttlmIntlr.DETAILS_1_2_REPOSITORY = DETAILS_1_2_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_1_REPOSITORY = ZF_1_1_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_3_REPOSITORY = ZF_1_3_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_4_REPOSITORY = ZF_1_4_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_5_REPOSITORY = ZF_1_5_REPOSITORY;
		auth072_SttlmIntlr.FININSTRM_MAPPING_REPOSITORY = FININSTRM_MAPPING_REPOSITORY;
		auth072_SttlmIntlr.TXTP_MAPPING_REPOSITORY = TXTP_MAPPING_REPOSITORY;
	}

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

	// <SttlmIntlr>: Generieren des kompletten Settlement Internalisers
	public static SettlementInternaliser1 createSettlementInternaliser() {

		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr generieren.");
		
		// Settlement Internaliser generieren
		SettlementInternaliser1 settlementInternaliser = objFactory.createSettlementInternaliser1();

		settlementInternaliser.setId(createID());
		settlementInternaliser.setOvrllTtl(createOvrllTtl());
		settlementInternaliser.setFinInstrm(createFinInstrm());
		settlementInternaliser.setTxTp(createTxTp());
		settlementInternaliser.setClntTp(createClntTp());
		settlementInternaliser.setTtlCshTrf(createTtlCshTrf());

		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr generiert.");
		
		return settlementInternaliser;
	}

	// <Id>: ID des Settlement Internalizers generieren
	private static SettlementInternaliserIdentification1 createID() {
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->Id generieren.");

		SettlementInternaliserIdentification1 settlementInternaliserIdentification = objFactory.createSettlementInternaliserIdentification1();
		ContactDetails4 responsiblePerson = objFactory.createContactDetails4();
		responsiblePerson.setNm(RP_NAME);
		responsiblePerson.setPhneNb(RP_PHONE);
		responsiblePerson.setEmailAdr(RP_EMAIL);
		responsiblePerson.setFctn(RP_FUNCTION);

		settlementInternaliserIdentification.setLEI(LEI);
		settlementInternaliserIdentification.setRspnsblPrsn(responsiblePerson);
		settlementInternaliserIdentification.setCtry(FROM_COUNTRY_CODE);
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->Id generiert.");

		return settlementInternaliserIdentification;

	}

	// <OvrllTtl> : Die Summen für das Overall Total können direkt aus dem Zusammenfassungssheet übernommen werden.
	private static InternalisationData1 createOvrllTtl() {
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->OvrllTtl generieren.");

		// Das Volume-Value-Pair mit den Daten aus der DB befüllen
		VolValPair volValPair = ZF_1_1_REPOSITORY.getOvrllTtlVolValPair();

		// InternalisationData für ovrllTtl erstellen
		InternalisationData1 ovrllTtl = createInternalisationData1(volValPair);		

		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->OvrllTtl generiert.");
		
		return ovrllTtl;
	}

	// <FinInstrm>: Die Daten für die Financial Instruments bzw. die einzelnen Subkategorien werden anhand der definierten Mappings und der entsprechenden Titelarten (201, 250,...) berechnet 
	private static SettlementInternaliserFinancialInstrument1 createFinInstrm() {
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->FinInstrm generieren.");

		SettlementInternaliserFinancialInstrument1 finInstrm = objFactory.createSettlementInternaliserFinancialInstrument1();

		// Alle FinInstrm-Mappings aus der DB laden
		List<FinInstrm_Mapping> finInstrmMappings = FININSTRM_MAPPING_REPOSITORY.findAll();

		// Für jedes Financial Instrument die Mappings und die dazugehörigen Werte aus der DB lesen, den Objekten zuweisen und am Schluss über Reflection die Elemente setzen 
		for (FinInstrm_Mapping finInstrmMapping : finInstrmMappings) {

			String sI_FI_Type = finInstrmMapping.getsI_FI_Type();
			String titelArten = finInstrmMapping.getTitelArten();
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->FinInstrm->" +sI_FI_Type +" generieren.");

			// Das Volume-Value-Pair zuerst mit leeren Werten befüllen, falls kein Mapping existiert sind diese Werte immer 0.
			VolValPair volValPair = new VolValPair(0,new BigDecimal(0));

			if (titelArten != null) {
				// String mit Mappings in Liste von Integern umwandeln, wird für JPA-Repository bzw. Hibernate so benötigt. 
				List<String> titelArtenStringList = Arrays.asList(titelArten.split("\\s*,\\s*"));
				List<Integer> titelArtenIntegerList = new ArrayList<Integer>();
				for(String titelArt : titelArtenStringList) {
					titelArtenIntegerList.add(Integer.valueOf(titelArt));
				}

				// Daten aus den entsprechenden Tabellen anhand der Titelarten errechnen.
				volValPair = DETAILS_1_2_REPOSITORY.getVolValPairByTitelArten(titelArtenIntegerList);
			}

			InternalisationData1 internalisationData = createInternalisationData1(volValPair);

			// Über Reflection werden die einzelnen Elemente über die entsprechenden Funktionen befüllt.
			// ACHTUNG, WICHTIG: Damit das funktioniert, müssen die Namen im Mapping mit den Namen im XML übereinstimmen!!!
			try {
				Method method = SettlementInternaliserFinancialInstrument1.class.getDeclaredMethod("set" +sI_FI_Type, InternalisationData1.class);
				method.invoke(finInstrm, internalisationData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->FinInstrm->" +sI_FI_Type +" generiert.");
		}
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->FinInstrm generiert.");

		return finInstrm;
	}

	// <TxTp>: Die Daten für die Transaktionstypen bzw. die einzelnen Subkategorien werden anhand der definierten Mappings und der entsprechenden Transaktionstypen (ACT, VCT,...) berechnet 
	private static SettlementInternaliserTransactionType1 createTxTp() {

		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TxTp generieren.");
		
		SettlementInternaliserTransactionType1 txTp = objFactory.createSettlementInternaliserTransactionType1();

		// Alle TxTp-Mappings aus der DB laden
		List<TxTp_Mapping> txTpMappings = TXTP_MAPPING_REPOSITORY.findAll();

		// Für alle TxTp-Mappings die InternalisationData1 Objekte erzeugen und dem SettlementInternaliserTransactionType1 Objekt zuweisen
		for (TxTp_Mapping txTpMapping : txTpMappings) {

			String sI_TT_Type = txTpMapping.getsI_TT_Type();
			String trc = txTpMapping.getTrc();
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TxTp->" +sI_TT_Type +" generieren.");

			// Das Volume-Value-Pair zuerst mit leeren Werten befüllen, falls kein Mapping existiert sind diese Werte immer 0.
			VolValPair volValPair = new VolValPair(0,new BigDecimal(0));

			if (trc != null) {
				// String mit Mappings in Liste umwandeln, wird für JPA-Repository bzw. Hibernate so benötigt. 
				List<String> trcList = Arrays.asList(trc.split("\\s*,\\s*"));

				// Daten aus den entsprechenden Tabellen anhand der Titelarten errechnen.
				volValPair = ZF_1_3_REPOSITORY.getTxTpVolValPairByTrc(trcList);
			}

			InternalisationData1 internalisationData = createInternalisationData1(volValPair);

			// Über Reflection werden die einzelnen Elemente über die entsprechenden Funktionen befüllt.
			// ACHTUNG, WICHTIG: Damit das funktioniert, müssen die Namen im Mapping mit den Namen im XML übereinstimmen!!!
			try {
				Method method = SettlementInternaliserTransactionType1.class.getDeclaredMethod("set" +sI_TT_Type, InternalisationData1.class);
				method.invoke(txTp, internalisationData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TxTp->" +sI_TT_Type +" generiert.");
		}
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TxTp generiert.");

		return txTp;
	}

	// <ClntTp>: Professionelle und Retail Kunden 
	private static SettlementInternaliserClientType1 createClntTp() {
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->ClntTp generieren.");

		SettlementInternaliserClientType1 clntTp = objFactory.createSettlementInternaliserClientType1();

		// Anlegertypen für "Professionell" und "Retail" aus der DB laden 
		List<String> anlegerTypen = ZF_1_4_REPOSITORY.getAnlegerTypen();

		// Für alle Anlagetypen die InternalisationData1 Objekte erzeugen und dem SettlementInternaliserClientType1 Objekt zuweisen
		for (String anlegerTyp : anlegerTypen) {
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->ClntTp->" +anlegerTyp +" generieren");

			VolValPair volValPair = ZF_1_4_REPOSITORY.getClntTpVolValPairByAnlegerTyp(anlegerTyp);

			InternalisationData1 internalisationData = createInternalisationData1(volValPair);

			// Über Reflection werden die einzelnen Elemente über die entsprechenden Funktionen befüllt.
			// ACHTUNG, WICHTIG: Damit das funktioniert, müssen die Namen in der DB mit den Namen im XML übereinstimmen!!!
			try {
				Method method = SettlementInternaliserClientType1.class.getDeclaredMethod("set" +anlegerTyp, InternalisationData1.class);
				method.invoke(clntTp, internalisationData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->ClntTp->" +anlegerTyp +" generiert");

		}
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->ClntTp generiert.");

		return clntTp;
	}

	// <TtlCshTrf>
	private static InternalisationData1 createTtlCshTrf() {
		
		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TtlCshTrf generieren.");

		// Das Volume-Value-Pair mit den Daten aus der DB befüllen
		VolValPair volValPair = ZF_1_5_REPOSITORY.getTtlCshTrfVolValPair();

		// InternalisationData für ttlCshTrf erstellen
		InternalisationData1 ttlCshTrf = createInternalisationData1(volValPair);	

		logger.debug("auth072 : Document->SttlmIntlrRpt->SttlmIntlr->TtlCshTrf generiert.");
		
		return ttlCshTrf;
	}

	// Generische Funktion für die Generierung von InternalisationData1 Objekten anhand übergebenem Volume-Value-Paar
	// Die Werte für fehlgeschlagene Überträge sind immer 0, es gibt keine fehlgeschlagenen Überträge.
	private static InternalisationData1 createInternalisationData1(VolValPair volValPair) {
		
		if (volValPair.getVolume() == null) {
			volValPair.setVolume(new BigDecimal(0));
		}
		
		if (volValPair.getValue() == null) {
			volValPair.setValue(new BigDecimal(0));
		}

		InternalisationData1 internalisationData = objFactory.createInternalisationData1();

		InternalisationData2 aggt = objFactory.createInternalisationData2();
		InternalisationDataVolume1 aggtSttld = objFactory.createInternalisationDataVolume1();
		aggtSttld.setVol(volValPair.getVolume());
		aggtSttld.setVal(volValPair.getValue());
		aggt.setSttld(aggtSttld);

		// Es gibt keine fehlgeschlagenen Überträge -> immer 0 
		InternalisationDataVolume1 aggtFaild = objFactory.createInternalisationDataVolume1();
		aggtFaild.setVol(new BigDecimal(0));
		aggtFaild.setVal(new BigDecimal(0));
		aggt.setFaild(aggtFaild);

		// Da die fehlgeschlagenen Elemente immer 0 sind, entspricht das Total immer den gesettelten Überträgen
		InternalisationDataVolume1 aggtTtl = aggtSttld;
		aggt.setTtl(aggtTtl);

		// Die failed Rate ist immer 0, da es keine fehlgeschlagenen Überträge gibt.
		InternalisationDataRate1 faildRate = objFactory.createInternalisationDataRate1();
		faildRate.setVolPctg(new BigDecimal(0));
		faildRate.setVal(new BigDecimal(0));

		internalisationData.setAggt(aggt);
		internalisationData.setFaildRate(faildRate);

		return internalisationData;

	}
}
