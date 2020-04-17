package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZF_1_5")
public class ZF_1_5 {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ZF_1_5_ID")
		private Integer zf_1_5_ID;
		
		@Column(name = "UebertragsArt")
		private String uebertragsArt;
		
		@Column(name = "Anzahl_Transaktionen")
		private Integer anzahlTransaktionen;
		
		@Column(name = "UebertragInCHF")
		private BigDecimal uebertragInCHF;
		
		@Column(name = "UebertragInEUR")
		private BigDecimal uebertragInEUR;

		public ZF_1_5() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ZF_1_5(Integer zf_1_5_ID, String uebertragsArt, Integer anzahlTransaktionen, BigDecimal uebertragInCHF,
				BigDecimal uebertragInEUR) {
			super();
			this.zf_1_5_ID = zf_1_5_ID;
			this.uebertragsArt = uebertragsArt;
			this.anzahlTransaktionen = anzahlTransaktionen;
			this.uebertragInCHF = uebertragInCHF;
			this.uebertragInEUR = uebertragInEUR;
		}

		public Integer getZf_1_5_ID() {
			return zf_1_5_ID;
		}

		public void setZf_1_5_ID(Integer zf_1_5_ID) {
			this.zf_1_5_ID = zf_1_5_ID;
		}

		public String getUebertragsArt() {
			return uebertragsArt;
		}

		public void setUebertragsArt(String uebertragsArt) {
			this.uebertragsArt = uebertragsArt;
		}

		public Integer getAnzahlTransaktionen() {
			return anzahlTransaktionen;
		}

		public void setAnzahlTransaktionen(Integer anzahlTransaktionen) {
			this.anzahlTransaktionen = anzahlTransaktionen;
		}

		public BigDecimal getUebertragInCHF() {
			return uebertragInCHF;
		}

		public void setUebertragInCHF(BigDecimal uebertragInCHF) {
			this.uebertragInCHF = uebertragInCHF;
		}

		public BigDecimal getUebertragInEUR() {
			return uebertragInEUR;
		}

		public void setUebertragInEUR(BigDecimal uebertragInEUR) {
			this.uebertragInEUR = uebertragInEUR;
		}

		@Override
		public String toString() {
			return "ZF_1_5 [zf_1_5_ID=" + zf_1_5_ID + ", uebertragsArt=" + uebertragsArt + ", anzahlTransaktionen="
					+ anzahlTransaktionen + ", uebertragInCHF=" + uebertragInCHF + ", uebertragInEUR=" + uebertragInEUR
					+ "]";
		}
}
