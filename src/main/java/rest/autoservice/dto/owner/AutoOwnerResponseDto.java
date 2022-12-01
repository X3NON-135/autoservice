package rest.autoservice.dto.owner;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.dto.auto.AutoResponseDto;
import rest.autoservice.dto.order.OrderResponseDto;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoOwnerResponseDto {
    private Long id;
    private String fullName;
    private List<AutoResponseDto> autos;
    private List<OrderResponseDto> orders;
}
