package rest.autoservice.dto.auto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoRequestDto {
    private String brand;
    private String model;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "date format: dd.MM.yyyy")
    private LocalDate manufactureDate;
    private String number;
    private Long ownerId;
}
