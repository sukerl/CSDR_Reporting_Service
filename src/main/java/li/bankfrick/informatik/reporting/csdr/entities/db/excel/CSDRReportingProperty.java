package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reporting_Properties")
public class CSDRReportingProperty {
				
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "Reporting_Property_ID")
			private Integer reporting_Property_ID;
			
			@Column(name = "Key")
			private String key;
			
			@Column(name = "Value")
			private String value;

			public CSDRReportingProperty() {
				super();
				// TODO Auto-generated constructor stub
			}

			public CSDRReportingProperty(Integer reporting_Property_ID, String key, String value) {
				super();
				this.reporting_Property_ID = reporting_Property_ID;
				this.key = key;
				this.value = value;
			}

			public Integer getReporting_Property_ID() {
				return reporting_Property_ID;
			}

			public void setReporting_Property_ID(Integer reporting_Property_ID) {
				this.reporting_Property_ID = reporting_Property_ID;
			}

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}

			@Override
			public String toString() {
				return "CSDRReportingProperty [reporting_Property_ID=" + reporting_Property_ID + ", key=" + key
						+ ", value=" + value + "]";
			}

}
