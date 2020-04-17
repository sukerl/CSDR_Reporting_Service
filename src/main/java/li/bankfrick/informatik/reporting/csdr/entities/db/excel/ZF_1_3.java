package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZF_1_3")
public class ZF_1_3 {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ZF_1_3_ID")
		private Integer zf_1_3_ID;
		
		@Column(name = "Gegenseite")
		private Integer gegenseite;
		
		@Column(name = "Bezeichnung_Gegenseite")
		private String bezeichnungGegenseite;
		
		@Column(name = "TRC")
		private String TRC;
		
		@Column(name = "Bezeichnung_TRC")
		private String bezeichnungTRC;
		
		@Column(name = "Anzahl_Transaktionen")
		private Integer anzahlTransaktionen;
		
		@Column(name = "GegenwertInCHF")
		private BigDecimal gegenwertInCHF;
		
		@Column(name = "GegenwertInEUR")
		private BigDecimal gegenwertInEUR;

		public ZF_1_3() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ZF_1_3(Integer zf_1_3_ID, Integer gegenseite, String bezeichnungGegenseite, String tRC,
				String bezeichnungTRC, Integer anzahlTransaktionen, BigDecimal gegenwertInCHF,
				BigDecimal gegenwertInEUR) {
			super();
			this.zf_1_3_ID = zf_1_3_ID;
			this.gegenseite = gegenseite;
			this.bezeichnungGegenseite = bezeichnungGegenseite;
			TRC = tRC;
			this.bezeichnungTRC = bezeichnungTRC;
			this.anzahlTransaktionen = anzahlTransaktionen;
			this.gegenwertInCHF = gegenwertInCHF;
			this.gegenwertInEUR = gegenwertInEUR;
		}

		public Integer getZf_1_3_ID() {
			return zf_1_3_ID;
		}

		public void setZf_1_3_ID(Integer zf_1_3_ID) {
			this.zf_1_3_ID = zf_1_3_ID;
		}

		public Integer getGegenseite() {
			return gegenseite;
		}

		public void setGegenseite(Integer gegenseite) {
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

		public Integer getAnzahlTransaktionen() {
			return anzahlTransaktionen;
		}

		public void setAnzahlTransaktionen(Integer anzahlTransaktionen) {
			this.anzahlTransaktionen = anzahlTransaktionen;
		}

		public BigDecimal getGegenwertInCHF() {
			return gegenwertInCHF;
		}

		public void setGegenwertInCHF(BigDecimal gegenwertInCHF) {
			this.gegenwertInCHF = gegenwertInCHF;
		}

		public BigDecimal getGegenwertInEUR() {
			return gegenwertInEUR;
		}

		public void setGegenwertInEUR(BigDecimal gegenwertInEUR) {
			this.gegenwertInEUR = gegenwertInEUR;
		}

		@Override
		public String toString() {
			return "ZF_1_3 [zf_1_3_ID=" + zf_1_3_ID + ", gegenseite=" + gegenseite + ", bezeichnungGegenseite="
					+ bezeichnungGegenseite + ", TRC=" + TRC + ", bezeichnungTRC=" + bezeichnungTRC
					+ ", anzahlTransaktionen=" + anzahlTransaktionen + ", gegenwertInCHF=" + gegenwertInCHF
					+ ", gegenwertInEUR=" + gegenwertInEUR + "]";
		}
}
