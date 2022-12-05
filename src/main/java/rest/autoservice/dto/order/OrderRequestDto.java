package rest.autoservice.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.model.Order;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    private Long autoId;
    private String description;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "date format: dd.MM.yyyy HH:mm")
    private LocalDateTime acceptanceDate;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "date format: dd.MM.yyyy HH:mm")
    private LocalDateTime completeDate;
    private Order.Status status;
    private double totalPrice;
    private List<Long> dutiesIds;
    private List<Long> productsIds;
}
