package li.bankfrick.informatik.reporting.csdr.entities.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SttlmIntlr_TxTp_Mapping")
public class SttlmIntlr_TxTp_Mapping {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "SttlmIntlr_TxTp_Mapping_id")
		private Integer sttlmIntlr_TxTp_Mapping_id;
		
		@Column(name = "SI_TT_Type")
		private String sI_TT_Type;
		
		@Column(name = "TRC")
		private String trc;

		public SttlmIntlr_TxTp_Mapping() {
			super();
			// TODO Auto-generated constructor stub
		}

		public SttlmIntlr_TxTp_Mapping(Integer sttlmIntlr_Mapping_id, String sI_FI_Type, String titelArten) {
			super();
			this.sttlmIntlr_TxTp_Mapping_id = sttlmIntlr_Mapping_id;
			this.sI_TT_Type = sI_FI_Type;
			this.trc = titelArten;
		}

		public Integer getSttlmIntlr_Mapping_id() {
			return sttlmIntlr_TxTp_Mapping_id;
		}

		public void setSttlmIntlr_Mapping_id(Integer sttlmIntlr_Mapping_id) {
			this.sttlmIntlr_TxTp_Mapping_id = sttlmIntlr_Mapping_id;
		}

		public String getsI_FI_Type() {
			return sI_TT_Type;
		}

		public void setsI_FI_Type(String sI_FI_Type) {
			this.sI_TT_Type = sI_FI_Type;
		}

		public String getTitelArten() {
			return trc;
		}

		public void setTitelArten(String titelArten) {
			this.trc = titelArten;
		}

		@Override
		public String toString() {
			return "SttlmIntlr_FinInstrm_Mapping [sttlmIntlr_Mapping_id=" + sttlmIntlr_TxTp_Mapping_id + ", sI_FI_Type="
					+ sI_TT_Type + ", titelArten=" + trc + "]";
		}			
}

