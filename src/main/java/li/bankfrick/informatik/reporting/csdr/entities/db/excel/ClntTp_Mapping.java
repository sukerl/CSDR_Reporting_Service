package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ClntTp_Mapping")
public class ClntTp_Mapping {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ClntTp_Mapping_id")
		private Integer sttlmIntlr_ClntTp_Mapping_id;
		
		@Column(name = "ClntTp_XML")
		private String clntTpXML;
		
		@Column(name = "ClntTp_XLSX")
		private String clntTpXLSX;

		public ClntTp_Mapping() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ClntTp_Mapping(Integer sttlmIntlr_ClntTp_Mapping_id, String clntTpXML, String clntTpXLSX) {
			super();
			this.sttlmIntlr_ClntTp_Mapping_id = sttlmIntlr_ClntTp_Mapping_id;
			this.clntTpXML = clntTpXML;
			this.clntTpXLSX = clntTpXLSX;
		}

		public Integer getSttlmIntlr_ClntTp_Mapping_id() {
			return sttlmIntlr_ClntTp_Mapping_id;
		}

		public void setSttlmIntlr_ClntTp_Mapping_id(Integer sttlmIntlr_ClntTp_Mapping_id) {
			this.sttlmIntlr_ClntTp_Mapping_id = sttlmIntlr_ClntTp_Mapping_id;
		}

		public String getClntTpXML() {
			return clntTpXML;
		}

		public void setClntTpXML(String clntTpXML) {
			this.clntTpXML = clntTpXML;
		}

		public String getClntTpXLSX() {
			return clntTpXLSX;
		}

		public void setClntTpXLSX(String clntTpXLSX) {
			this.clntTpXLSX = clntTpXLSX;
		}

		@Override
		public String toString() {
			return "ClntTp_Mapping [sttlmIntlr_ClntTp_Mapping_id=" + sttlmIntlr_ClntTp_Mapping_id + ", clntTpXML="
					+ clntTpXML + ", clntTpXLSX=" + clntTpXLSX + "]";
		}		
}

