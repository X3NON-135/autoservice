package rest.autoservice.dto.order;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    private Long autoId;
    private String description;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate acceptanceDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate completeDate;
}
