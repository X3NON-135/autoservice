package rest.autoservice.dto.duties;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DutyRequestDto {
    private Long orderId;
    private Long masterId;
    private BigDecimal price;
    private String typeOfDuty;
}
