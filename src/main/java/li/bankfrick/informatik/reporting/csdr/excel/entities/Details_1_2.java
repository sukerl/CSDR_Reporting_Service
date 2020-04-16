package li.bankfrick.informatik.reporting.csdr.excel.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Details_1_2")
public class Details_1_2 {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "Detail_1_2_ID")
		private Integer detail_1_2_ID;
		
		@Column(name = "Gegenseite")
		private String gegenseite;
		
		@Column(name = "Bezeichnung_Gegenseite")
		private String bezeichnungGegenseite;
		
		@Column(name = "TRC")
		private String TRC;
		
		@Column(name = "Bezeichnung_TRC")
		private String bezeichnungTRC;
		
		@Column(name = "Depotstelle")
		private String depotstelle;
		
		@Column(name = "Bezeichnung_Depotstelle")
		private String bezeichnungDepotstelle;
		
		@Column(name = "LEI")
		private String LEI;
		
		@Column(name = "Transaktion")
		private Integer transaktion;
		
		@Column(name = "ISIN_Land")
		private String ISINland;
		
		@Column(name = "Valor")
		private String valor;
		
		@Column(name = "Kurzbezeichnung")
		private String kurzbezeichnung;
		
		@Column(name = "Titelart")
		private Integer titelart;
		
		@Column(name = "Liefercode")
		private String liefercode;
		
		@Column(name = "Systemdatum")
		private String systemdatum;
		
		@Column(name = "BetragInCHF")
		private BigDecimal betragInCHF;
		
		@Column(name = "BetragInEUR")
		private BigDecimal betragInEUR;

		public Details_1_2() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Details_1_2(Integer detail_1_2_ID, String gegenseite, String bezeichnungGegenseite, String tRC,
				String bezeichnungTRC, String depotstelle, String bezeichnungDepotstelle, String lEI,
				Integer transaktion, String iSINland, String valor, String kurzbezeichnung, Integer titelart,
				String liefercode, String systemdatum, BigDecimal betragInCHF, BigDecimal betragInEUR) {
			super();
			this.detail_1_2_ID = detail_1_2_ID;
			this.gegenseite = gegenseite;
			this.bezeichnungGegenseite = bezeichnungGegenseite;
			TRC = tRC;
			this.bezeichnungTRC = bezeichnungTRC;
			this.depotstelle = depotstelle;
			this.bezeichnungDepotstelle = bezeichnungDepotstelle;
			LEI = lEI;
			this.transaktion = transaktion;
			ISINland = iSINland;
			this.valor = valor;
			this.kurzbezeichnung = kurzbezeichnung;
			this.titelart = titelart;
			this.liefercode = liefercode;
			this.systemdatum = systemdatum;			
			this.betragInCHF = betragInCHF;
			this.betragInEUR = betragInEUR;
		}

		public Integer getDetail_1_2_ID() {
			return detail_1_2_ID;
		}

		public void setDetail_1_2_ID(Integer detail_1_2_ID) {
			this.detail_1_2_ID = detail_1_2_ID;
		}

		public String getGegenseite() {
			return gegenseite;
		}

		public void setGegenseite(String gegenseite) {
			this.gegenseite = gegenseite;
		}

		public String getBezeichnungGegenseite() {
			return bezeichnungGegenseite;
		}

		public void setBezeichnungGegenseite(String bezeichnungGegenseite) {
			this.bezeichnungGegenseite = bezeichnungGegenseite;
		}

		public String getTRC() {
			return TRC;
		}

		public void setTRC(String tRC) {
			TRC = tRC;
		}

		public String getBezeichnungTRC() {
			return bezeichnungTRC;
		}

		public void setBezeichnungTRC(String bezeichnungTRC) {
			this.bezeichnungTRC = bezeichnungTRC;
		}

		public String getDepotstelle() {
			return depotstelle;
		}

		public void setDepotstelle(String depotstelle) {
			this.depotstelle = depotstelle;
		}

		public String getBezeichnungDepotstelle() {
			return bezeichnungDepotstelle;
		}

		public void setBezeichnungDepotstelle(String bezeichnungDepotstelle) {
			this.bezeichnungDepotstelle = bezeichnungDepotstelle;
		}

		public String getLEI() {
			return LEI;
		}

		public void setLEI(String lEI) {
			LEI = lEI;
		}

		public Integer getTransaktion() {
			return transaktion;
		}

		public void setTransaktion(Integer transaktion) {
			this.transaktion = transaktion;
		}

		public String getISINland() {
			return ISINland;
		}

		public void setISINland(String iSINland) {
			ISINland = iSINland;
		}

		public String getValor() {
			return valor;
		}

		public void setValor(String valor) {
			this.valor = valor;
		}

		public String getKurzbezeichnung() {
			return kurzbezeichnung;
		}

		public void setKurzbezeichnung(String kurzbezeichnung) {
			this.kurzbezeichnung = kurzbezeichnung;
		}

		public Integer getTitelart() {
			return titelart;
		}

		public void setTitelart(Integer titelart) {
			this.titelart = titelart;
		}

		public String getLiefercode() {
			return liefercode;
		}

		public void setLiefercode(String liefercode) {
			this.liefercode = liefercode;
		}

		public String getSystemdatum() {
			return systemdatum;
		}

		public void setSystemdatum(String systemdatum) {
			this.systemdatum = systemdatum;
		}

		public BigDecimal getBetragInCHF() {
			return betragInCHF;
		}

		public void setBetragInCHF(BigDecimal betragInCHF) {
			this.betragInCHF = betragInCHF;
		}

		public BigDecimal getBetragInEUR() {
			return betragInEUR;
		}

		public void setBetragInEUR(BigDecimal betragInEUR) {
			this.betragInEUR = betragInEUR;
		}

		@Override
		public String toString() {
			return "Details_1_2 [detail_1_2_ID=" + detail_1_2_ID + ", gegenseite=" + gegenseite
					+ ", bezeichnungGegenseite=" + bezeichnungGegenseite + ", TRC=" + TRC + ", bezeichnungTRC="
					+ bezeichnungTRC + ", depotstelle=" + depotstelle + ", bezeichnungDepotstelle="
					+ bezeichnungDepotstelle + ", LEI=" + LEI + ", transaktion=" + transaktion + ", ISINland="
					+ ISINland + ", valor=" + valor + ", kurzbezeichnung=" + kurzbezeichnung + ", titelart=" + titelart
					+ ", liefercode=" + liefercode + ", systemdatum=" + systemdatum + ", betragInCHF=" + betragInCHF
					+ ", betragInEUR=" + betragInEUR + "]";
		}

}
