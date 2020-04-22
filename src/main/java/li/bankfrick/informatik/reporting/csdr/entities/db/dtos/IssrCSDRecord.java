package li.bankfrick.informatik.reporting.csdr.entities.db.dtos;

import java.math.BigDecimal;

public class IssrCSDRecord {

	private String ISINland;
	private String LEI;
	private int titelart;
	private long anzahl;
	private BigDecimal summeInEUR;

	public IssrCSDRecord(String iSINland, String lEI, int titelart, long anzahl, BigDecimal summeInEUR) {
		super();
		ISINland = iSINland;
		LEI = lEI;
		this.titelart = titelart;
		this.anzahl = anzahl;
		this.summeInEUR = summeInEUR;
	}

	public String getISINland() {
		return ISINland;
	}

	public void setISINland(String iSINland) {
		ISINland = iSINland;
	}

	public String getLEI() {
		return LEI;
	}

	public void setLEI(String lEI) {
		LEI = lEI;
	}

	public int getTitelart() {
		return titelart;
	}

	public void setTitelart(int titelart) {
		this.titelart = titelart;
	}

	public long getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(long anzahl) {
		this.anzahl = anzahl;
	}

	public BigDecimal getSummeInEUR() {
		return summeInEUR;
	}

	public void setSummeInEUR(BigDecimal summeInEUR) {
		this.summeInEUR = summeInEUR;
	}

	@Override
	public String toString() {
		return "IssrCSDRecord [ISINland=" + ISINland + ", LEI=" + LEI + ", titelart=" + titelart
				+ ", anzahl=" + anzahl + ", summeInEUR=" + summeInEUR + "]";
	}

}
