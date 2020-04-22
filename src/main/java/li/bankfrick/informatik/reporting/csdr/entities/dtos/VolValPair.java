package li.bankfrick.informatik.reporting.csdr.entities.dtos;

import java.math.BigDecimal;

public class VolValPair {
	
	public BigDecimal volume;
	public BigDecimal value;
	
	public VolValPair() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VolValPair(long volume, BigDecimal value) {
		super();
		this.volume = new BigDecimal(volume);
		this.value = value;
	}
	
	public VolValPair(Integer volume, BigDecimal value) {
		super();
		this.volume = new BigDecimal(volume);
		this.value = value;
	}
	
	public VolValPair(BigDecimal volume, BigDecimal value) {
		super();
		this.volume = volume;
		this.value = value;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "VolValPair [volume=" + volume + ", value=" + value + "]";
	}
}
