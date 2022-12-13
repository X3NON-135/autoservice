package rest.autoservice.dto.master;

import java.util.List;

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
public class MasterRequestDto {
    private String fullName;
    private List<Long> finishedOrdersIds;
}

