package rest.autoservice.dto.auto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoRequestDto {
    private String brand;
    private String model;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate manufactureDate;
    private String number;
    private Long ownerId;
}
