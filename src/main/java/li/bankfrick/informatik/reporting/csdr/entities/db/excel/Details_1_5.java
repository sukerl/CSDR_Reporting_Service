package li.bankfrick.informatik.reporting.csdr.entities.db.excel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Details_1_5")
public class Details_1_5 {
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "Detail_1_5_ID")
		private Integer detail_1_5_ID;
		
		@Column(name = "TRC")
		private String TRC;
		
		@Column(name = "BetragInCHF")
		private BigDecimal betragInCHF;
		
		@Column(name = "BetragInEUR")
		private BigDecimal betragInEUR;
				
		@Column(name = "Referenz")
		private String referenz;
		
		@Column(name = "Stamm")
		private String stamm;
		
		@Column(name = "Land")
		private String land;
		
		public Details_1_5() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Details_1_5(Integer detail_1_5_ID, String tRC, BigDecimal betragInCHF, BigDecimal betragInEUR,
				String referenz, String stamm, String land) {
			super();
			this.detail_1_5_ID = detail_1_5_ID;
			TRC = tRC;
			this.betragInCHF = betragInCHF;
			this.betragInEUR = betragInEUR;
			this.referenz = referenz;
			this.stamm = stamm;
			this.land = land;
		}

		public Integer getDetail_1_5_ID() {
			return detail_1_5_ID;
		}

		public void setDetail_1_5_ID(Integer detail_1_5_ID) {
			this.detail_1_5_ID = detail_1_5_ID;
		}

		public String getTRC() {
			return TRC;
		}

		public void setTRC(String tRC) {
			TRC = tRC;
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

		public String getReferenz() {
			return referenz;
		}

		public void setReferenz(String referenz) {
			this.referenz = referenz;
		}

		public String getStamm() {
			return stamm;
		}

		public void setStamm(String stamm) {
			this.stamm = stamm;
		}

		public String getLand() {
			return land;
		}

		public void setLand(String land) {
			this.land = land;
		}

		@Override
		public String toString() {
			return "Details_1_5 [detail_1_5_ID=" + detail_1_5_ID + ", TRC=" + TRC + ", betragInCHF=" + betragInCHF
					+ ", betragInEUR=" + betragInEUR + ", referenz=" + referenz + ", stamm=" + stamm + ", land=" + land
					+ "]";
		}		
}
