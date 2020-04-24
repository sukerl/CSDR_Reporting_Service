package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FinInstrm_Mapping")
public class FinInstrm_Mapping {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "FinInstrm_Mapping_id")
		private Integer sttlmIntlr_FinInstrm_Mapping_id;
		
		@Column(name = "SI_FI_Type")
		private String sI_FI_Type;
		
		@Column(name = "Titel_Arten")
		private String titelArten;

		public FinInstrm_Mapping() {
			super();
			// TODO Auto-generated constructor stub
		}

		public FinInstrm_Mapping(Integer sttlmIntlr_Mapping_id, String sI_FI_Type, String titelArten) {
			super();
			this.sttlmIntlr_FinInstrm_Mapping_id = sttlmIntlr_Mapping_id;
			this.sI_FI_Type = sI_FI_Type;
			this.titelArten = titelArten;
		}

		public Integer getSttlmIntlr_Mapping_id() {
			return sttlmIntlr_FinInstrm_Mapping_id;
		}

		public void setSttlmIntlr_Mapping_id(Integer sttlmIntlr_Mapping_id) {
			this.sttlmIntlr_FinInstrm_Mapping_id = sttlmIntlr_Mapping_id;
		}

		public String getsI_FI_Type() {
			return sI_FI_Type;
		}

		public void setsI_FI_Type(String sI_FI_Type) {
			this.sI_FI_Type = sI_FI_Type;
		}

		public String getTitelArten() {
			return titelArten;
		}

		public void setTitelArten(String titelArten) {
			this.titelArten = titelArten;
		}

		@Override
		public String toString() {
			return "FinInstrm_Mapping [sttlmIntlr_Mapping_id=" + sttlmIntlr_FinInstrm_Mapping_id + ", sI_FI_Type="
					+ sI_FI_Type + ", titelArten=" + titelArten + "]";
		}			
}

