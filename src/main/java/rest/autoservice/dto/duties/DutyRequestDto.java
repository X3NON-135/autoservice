package rest.autoservice.dto.duties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DutyRequestDto {
    private Long orderId;
    private Long masterId;
    private double price;
    private String typeOfDuty;
}
