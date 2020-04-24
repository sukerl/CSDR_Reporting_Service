package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TxTp_Mapping")
public class TxTp_Mapping {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "TxTp_Mapping_id")
		private Integer sttlmIntlr_TxTp_Mapping_id;
		
		@Column(name = "SI_TT_Type")
		private String sI_TT_Type;
		
		@Column(name = "TRC")
		private String trc;

		public TxTp_Mapping() {
			super();
			// TODO Auto-generated constructor stub
		}

		public TxTp_Mapping(Integer sttlmIntlr_TxTp_Mapping_id, String sI_TT_Type, String trc) {
			super();
			this.sttlmIntlr_TxTp_Mapping_id = sttlmIntlr_TxTp_Mapping_id;
			this.sI_TT_Type = sI_TT_Type;
			this.trc = trc;
		}

		public Integer getTxTp_Mapping_id() {
			return sttlmIntlr_TxTp_Mapping_id;
		}

		public void setTxTp_Mapping_id(Integer sttlmIntlr_TxTp_Mapping_id) {
			this.sttlmIntlr_TxTp_Mapping_id = sttlmIntlr_TxTp_Mapping_id;
		}

		public String getsI_TT_Type() {
			return sI_TT_Type;
		}

		public void setsI_TT_Type(String sI_TT_Type) {
			this.sI_TT_Type = sI_TT_Type;
		}

		public String getTrc() {
			return trc;
		}

		public void setTrc(String trc) {
			this.trc = trc;
		}

		@Override
		public String toString() {
			return "TxTp_Mapping [sttlmIntlr_TxTp_Mapping_id=" + sttlmIntlr_TxTp_Mapping_id + ", sI_TT_Type="
					+ sI_TT_Type + ", trc=" + trc + "]";
		}			
}

