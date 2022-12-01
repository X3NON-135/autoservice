package rest.autoservice.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime acceptanceDate;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime completeDate;
}
