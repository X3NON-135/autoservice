package rest.autoservice.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderResponseDto {
    private Long id;
    private Long autoId;
    private String description;
    private LocalDateTime acceptanceDate;
    private LocalDateTime finishedDate;
    private List<Long> dutyIds;
    private List<Long> productIds;
    private String status;
    private double totalPrice;
}
