package ms.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class KpiClientDto {

	@Getter
	@Setter
	String ageAverage;

	@Getter
	@Setter
	String standarDeviation;
	

}
