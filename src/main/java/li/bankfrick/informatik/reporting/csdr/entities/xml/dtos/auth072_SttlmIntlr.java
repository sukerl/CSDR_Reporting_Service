package li.bankfrick.informatik.reporting.csdr.entities.xml.dtos;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import li.bankfrick.informatik.reporting.csdr.entities.db.mapping.SttlmIntlr_FinInstrm_Mapping;
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
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.Details_1_2_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.SttlmIntlr_FinInstrm_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.SttlmIntlr_TxTp_Mapping_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_1_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_3_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_4_Repository;
import li.bankfrick.informatik.reporting.csdr.repositories.ZF_1_5_Repository;

@Component
public class auth072_SttlmIntlr {

	private static final Logger logger = LogManager.getLogger(auth072_SttlmIntlr.class);

	private static Details_1_1_Repository DETAILS_1_1_REPOSITORY;
	private static Details_1_2_Repository DETAILS_1_2_REPOSITORY;
	private static ZF_1_1_Repository ZF_1_1_REPOSITORY;
	private static ZF_1_3_Repository ZF_1_3_REPOSITORY;
	private static ZF_1_4_Repository ZF_1_4_REPOSITORY;
	private static ZF_1_5_Repository ZF_1_5_REPOSITORY;
	private static SttlmIntlr_FinInstrm_Mapping_Repository STTLMINTLR_FININSTRM_MAPPING_REPOSITORY;
	private static SttlmIntlr_TxTp_Mapping_Repository STTLMINTLR_TXTP_MAPPING_REPOSITORY;

	public auth072_SttlmIntlr(Details_1_1_Repository DETAILS_1_1_REPOSITORY,
			Details_1_2_Repository DETAILS_1_2_REPOSITORY, ZF_1_1_Repository ZF_1_1_REPOSITORY,
			ZF_1_3_Repository ZF_1_3_REPOSITORY, ZF_1_4_Repository ZF_1_4_REPOSITORY,
			ZF_1_5_Repository ZF_1_5_REPOSITORY, SttlmIntlr_FinInstrm_Mapping_Repository STTLMINTLR_FININSTRM_MAPPING_REPOSITORY,
			SttlmIntlr_TxTp_Mapping_Repository STTLMINTLR_TXTP_MAPPING_REPOSITORY) {
		auth072_SttlmIntlr.DETAILS_1_1_REPOSITORY = DETAILS_1_1_REPOSITORY;
		auth072_SttlmIntlr.DETAILS_1_2_REPOSITORY = DETAILS_1_2_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_1_REPOSITORY = ZF_1_1_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_3_REPOSITORY = ZF_1_3_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_4_REPOSITORY = ZF_1_4_REPOSITORY;
		auth072_SttlmIntlr.ZF_1_5_REPOSITORY = ZF_1_5_REPOSITORY;
		auth072_SttlmIntlr.STTLMINTLR_FININSTRM_MAPPING_REPOSITORY = STTLMINTLR_FININSTRM_MAPPING_REPOSITORY;
		auth072_SttlmIntlr.STTLMINTLR_TXTP_MAPPING_REPOSITORY = STTLMINTLR_TXTP_MAPPING_REPOSITORY;
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

	public static SettlementInternaliser1 createSettlementInternaliser() {

		// Settlement Internaliser generieren
		SettlementInternaliser1 settlementInternaliser = objFactory.createSettlementInternaliser1();

		settlementInternaliser.setId(createID());
		settlementInternaliser.setOvrllTtl(createOvrllTtl());
		settlementInternaliser.setFinInstrm(createFinInstrm());
		settlementInternaliser.setTxTp(createTxTp());
		settlementInternaliser.setClntTp(createClntTp());
		settlementInternaliser.setTtlCshTrf(createTtlCshTrf());

		return settlementInternaliser;
	}

	private static SettlementInternaliserIdentification1 createID() {

		SettlementInternaliserIdentification1 settlementInternaliserIdentification = objFactory
				.createSettlementInternaliserIdentification1();
		ContactDetails4 responsiblePerson = objFactory.createContactDetails4();
		responsiblePerson.setNm(RP_NAME);
		responsiblePerson.setPhneNb(RP_PHONE);
		responsiblePerson.setEmailAdr(RP_EMAIL);
		responsiblePerson.setFctn(RP_FUNCTION);

		settlementInternaliserIdentification.setLEI(LEI);
		settlementInternaliserIdentification.setRspnsblPrsn(responsiblePerson);
		settlementInternaliserIdentification.setCtry(FROM_COUNTRY_CODE);

		return settlementInternaliserIdentification;

	}

	// <p:OvrllTtl>
	private static InternalisationData1 createOvrllTtl() {

		InternalisationData1 ovrllTtl = objFactory.createInternalisationData1();

		BigDecimal overallTransactionCount = new BigDecimal(ZF_1_1_REPOSITORY.getOverallTransactionCount());
		BigDecimal overallTransactionVolume = ZF_1_1_REPOSITORY.getOverallTransactionVolume();

		InternalisationData2 aggt = objFactory.createInternalisationData2();
		InternalisationDataVolume1 aggtSttld = objFactory.createInternalisationDataVolume1();
		aggtSttld.setVol(overallTransactionCount);
		aggtSttld.setVal(overallTransactionVolume);
		aggt.setSttld(aggtSttld);

		InternalisationDataVolume1 aggtFaild = objFactory.createInternalisationDataVolume1();
		aggtFaild.setVol(new BigDecimal(0));
		aggtFaild.setVal(new BigDecimal(0));
		aggt.setFaild(aggtFaild);

		InternalisationDataVolume1 aggtTtl = aggtSttld;
		aggt.setTtl(aggtTtl);

		InternalisationDataRate1 faildRate = objFactory.createInternalisationDataRate1();
		faildRate.setVolPctg(new BigDecimal(0));
		faildRate.setVal(new BigDecimal(0));

		ovrllTtl.setAggt(aggt);
		ovrllTtl.setFaildRate(faildRate);

		return ovrllTtl;
	}

	// <p:FinInstrm>
	private static SettlementInternaliserFinancialInstrument1 createFinInstrm() {

		SettlementInternaliserFinancialInstrument1 finInstrm = objFactory.createSettlementInternaliserFinancialInstrument1();
		
		List<SttlmIntlr_FinInstrm_Mapping> finInstrmMappings = STTLMINTLR_FININSTRM_MAPPING_REPOSITORY.findAll();
		
		for (SttlmIntlr_FinInstrm_Mapping finInstrmMapping : finInstrmMappings) {
			
			String sI_FI_Type = finInstrmMapping.getsI_FI_Type();
			String titelArten = finInstrmMapping.getTitelArten();
			
			VolValPair sumData = new VolValPair(0,new BigDecimal(0));
			
			if (titelArten != null) {
				List<String> titelArtenList = Arrays.asList(titelArten.split("\\s*,\\s*"));

				sumData.setVolume(new BigDecimal(DETAILS_1_2_REPOSITORY.getVolumeByTitelArten(titelArtenList)));
				sumData.setValue(DETAILS_1_2_REPOSITORY.getValueByTitelArten(titelArtenList));
			}

			InternalisationData1 internalisationData = objFactory.createInternalisationData1();
			InternalisationData2 aggt = objFactory.createInternalisationData2();
			InternalisationDataVolume1 aggtSttld = objFactory.createInternalisationDataVolume1();
			aggtSttld.setVol(sumData.getVolume());
			aggtSttld.setVal(sumData.getValue());
			aggt.setSttld(aggtSttld);

			InternalisationDataVolume1 aggtFaild = objFactory.createInternalisationDataVolume1();
			aggtFaild.setVol(new BigDecimal(0));
			aggtFaild.setVal(new BigDecimal(0));
			aggt.setFaild(aggtFaild);

			InternalisationDataVolume1 aggtTtl = aggtSttld;
			aggt.setTtl(aggtTtl);

			InternalisationDataRate1 faildRate = objFactory.createInternalisationDataRate1();
			faildRate.setVolPctg(new BigDecimal(0));
			faildRate.setVal(new BigDecimal(0));

			internalisationData.setAggt(aggt);
			internalisationData.setFaildRate(faildRate);
		    
		    /*
		    Method method = WhateverYourClassIs.class.getDeclaredMethod("Method" + MyVar);
			method.invoke(internalisationData);
		     */
			
			try {
				Method method = SettlementInternaliserFinancialInstrument1.class.getDeclaredMethod("set" +sI_FI_Type, InternalisationData1.class);
				method.invoke(finInstrm, internalisationData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		return finInstrm;
	}

	// <p:TxTp>
	private static SettlementInternaliserTransactionType1 createTxTp() {

		SettlementInternaliserTransactionType1 txTp = objFactory.createSettlementInternaliserTransactionType1();

		return txTp;
	}

	// <p:ClntTp>
	private static SettlementInternaliserClientType1 createClntTp() {

		SettlementInternaliserClientType1 clntTp = objFactory.createSettlementInternaliserClientType1();

		return clntTp;
	}

	// <p:TtlCshTrf>
	private static InternalisationData1 createTtlCshTrf() {

		InternalisationData1 ttlCshTrf = objFactory.createInternalisationData1();

		return ttlCshTrf;
	}
}
