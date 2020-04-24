package li.bankfrick.informatik.reporting.csdr.entities.xml.dtos;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.entities.db.excel.FinInstrm_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.db.excel.TxTp_Mapping;
import li.bankfrick.informatik.reporting.csdr.entities.dtos.VolValPair;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationData1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationData2;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationDataRate1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.InternalisationDataVolume1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.IssuerCSDIdentification1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.IssuerCSDReport1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.ObjectFactory;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserClientType1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserFinancialInstrument1;
import li.bankfrick.informatik.reporting.csdr.entities.xml.auth072.SettlementInternaliserTransactionType1;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.FinInstrm_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.TxTp_Mapping_Repository;

@Component
public class auth072_IssrCSDs {

	private static final Logger logger = LogManager.getLogger(auth072_IssrCSDs.class);

	private static Details_1_1_Repository DETAILS_1_1_REPOSITORY;
	private static FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY;
	private static TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY;

	public auth072_IssrCSDs(Details_1_1_Repository DETAILS_1_1_REPOSITORY,
			FinInstrm_Mapping_Repository FININSTRM_MAPPING_REPOSITORY, TxTp_Mapping_Repository TXTP_MAPPING_REPOSITORY) {
		auth072_IssrCSDs.DETAILS_1_1_REPOSITORY = DETAILS_1_1_REPOSITORY;
		auth072_IssrCSDs.FININSTRM_MAPPING_REPOSITORY = FININSTRM_MAPPING_REPOSITORY;
		auth072_IssrCSDs.TXTP_MAPPING_REPOSITORY = TXTP_MAPPING_REPOSITORY;
	}

	private static ObjectFactory objFactory = new ObjectFactory();

	// Methode die die gesammte Liste von IssuerCSDReport1 anhand der ersten beiden Buchstaben der ISINs generiert
	public static List<IssuerCSDReport1> createIssuerCSDReports() {

		List<IssuerCSDReport1> issrCSDs = new ArrayList<IssuerCSDReport1>();

		List<String> ISINlands = DETAILS_1_1_REPOSITORY.getDistinctISINland();

		for (String ISINland : ISINlands) {

			issrCSDs.add(createIssuerCSDReport(ISINland));

		}

		return issrCSDs;

	}

	// Methode die einen einzelnen IssuerCSDReport anhand der ISIN generiert
	private static IssuerCSDReport1 createIssuerCSDReport(String ISINland) {

		IssuerCSDReport1 issrCSD = objFactory.createIssuerCSDReport1();

		issrCSD.setId(createID(ISINland));
		issrCSD.setOvrllTtl(createOvrllTtl(ISINland));
		issrCSD.setFinInstrm(createFinInstrm(ISINland));
		issrCSD.setTxTp(createTxTp(ISINland));
		issrCSD.setClntTp(createClntTp(ISINland));
		issrCSD.setTtlCshTrf(createTtlCshTrf(ISINland));

		return issrCSD;		
	}

	// <Id>: ID des Issuer CSD generieren
	private static IssuerCSDIdentification1 createID(String ISINland) {

		IssuerCSDIdentification1 issrCSDid = objFactory.createIssuerCSDIdentification1();
		issrCSDid.setFrstTwoCharsInstrmId(ISINland);

		// LEI Nummer setzen. Wenn keine oder mehrere LEIs zurückgegeben werden, LEI nicht setzen, ist kein Pflichtfeld.
		List<String> LEIs = DETAILS_1_1_REPOSITORY.getDistinctLEIsByISINland(ISINland);
		if (LEIs.size() == 1) {
			issrCSDid.setLEI(LEIs.get(0));
		}

		return issrCSDid;
	}

	// <OvrllTtl> : Die Summen für das Overall Total werden anhand der ISIN aus der DB extrahiert
	private static InternalisationData1 createOvrllTtl(String ISINland) {

		VolValPair ovrllTtlVolValPair = DETAILS_1_1_REPOSITORY.getVolValPairOvrllTtlByISINland(ISINland);

		InternalisationData1 ovrllTtl = createInternalisationData1(ovrllTtlVolValPair);

		return ovrllTtl;
	}

	// <FinInstrm>: Die Daten für die Financial Instruments bzw. die einzelnen Subkategorien werden anhand der definierten Mappings und der entsprechenden Titelarten (201, 250,...) berechnet 
	private static SettlementInternaliserFinancialInstrument1 createFinInstrm(String ISINland) {

		logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->FinInstrm generieren.");

		SettlementInternaliserFinancialInstrument1 finInstrm = objFactory.createSettlementInternaliserFinancialInstrument1();

		// Alle FinInstrm-Mappings aus der DB laden
		List<FinInstrm_Mapping> finInstrmMappings = FININSTRM_MAPPING_REPOSITORY.findAll();

		// Für jedes Financial Instrument die Mappings und die dazugehörigen Werte aus der DB lesen, den Objekten zuweisen und am Schluss über Reflection die Elemente setzen 
		for (FinInstrm_Mapping finInstrmMapping : finInstrmMappings) {

			String sI_FI_Type = finInstrmMapping.getsI_FI_Type();
			String titelArten = finInstrmMapping.getTitelArten();

			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->FinInstrm->" +sI_FI_Type +" generieren.");

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
				volValPair = DETAILS_1_1_REPOSITORY.getVolValPairByTitelArtenAndISINland(titelArtenIntegerList, ISINland);
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
			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->FinInstrm->" +sI_FI_Type +" generiert.");
		}

		logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->FinInstrm generiert.");

		return finInstrm;
	}
	
	// <TxTp>: Die Daten für die Transaktionstypen bzw. die einzelnen Subkategorien werden gemäss Massimo Raminelli alle auf 0 gesetzt, es findet keine Berechnung statt. 
		private static SettlementInternaliserTransactionType1 createTxTp(String ISINland) {

			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TxTp generieren.");
			
			SettlementInternaliserTransactionType1 txTp = objFactory.createSettlementInternaliserTransactionType1();

			// Alle TxTp-Mappings aus der DB laden
			List<TxTp_Mapping> txTpMappings = TXTP_MAPPING_REPOSITORY.findAll();

			// Für alle TxTp-Mappings die InternalisationData1 Objekte erzeugen und dem SettlementInternaliserTransactionType1 Objekt zuweisen
			for (TxTp_Mapping txTpMapping : txTpMappings) {

				String sI_TT_Type = txTpMapping.getsI_TT_Type();
				
				logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TxTp->" +sI_TT_Type +" generieren.");

				// Das Volume-Value-Pair zuerst mit leeren Werten befüllen, falls kein Mapping existiert sind diese Werte immer 0.
				VolValPair volValPair = new VolValPair(0,new BigDecimal(0));

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
				
				logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TxTp->" +sI_TT_Type +" generiert.");
			}
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TxTp generiert.");

			return txTp;
		}
		
		// <ClntTp>: Professionelle und Retail Kunden. Gemäss Massimo Raminelli alle auf 0 gesetzt, es findet keine Berechnung statt. 
		private static SettlementInternaliserClientType1 createClntTp(String ISINland) {
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->ClntTp generieren.");

			SettlementInternaliserClientType1 clntTp = objFactory.createSettlementInternaliserClientType1();

			// Anlegertypen für "Professionell" und "Retail" setzen 
			List<String> anlegerTypen = new ArrayList<String>();
			anlegerTypen.add("Prfssnl");
			anlegerTypen.add("Rtl");

			// Für alle Anlagetypen die InternalisationData1 Objekte erzeugen und dem SettlementInternaliserClientType1 Objekt zuweisen
			for (String anlegerTyp : anlegerTypen) {
				
				logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->ClntTp->" +anlegerTyp +" generieren");

				VolValPair volValPair = new VolValPair(0,new BigDecimal(0));
				
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
				
				logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->ClntTp->" +anlegerTyp +" generiert");

			}
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->ClntTp generiert.");

			return clntTp;
		}
		
		// <TtlCshTrf> Gemäss Massimo Raminelli alle auf 0 gesetzt, es findet keine Berechnung statt.
		private static InternalisationData1 createTtlCshTrf(String ISINland) {
			
			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TtlCshTrf generieren.");

			// Das Volume-Value-Pair mit den Daten aus der DB befüllen
			VolValPair volValPair = new VolValPair(0,new BigDecimal(0));

			// InternalisationData für ttlCshTrf erstellen
			InternalisationData1 ttlCshTrf = createInternalisationData1(volValPair);	

			logger.debug("auth072 : Document->SttlmIntlrRpt->IssrCSD(" +ISINland +")->TtlCshTrf generiert.");
			
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
