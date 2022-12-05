package rest.autoservice.service;

import java.math.BigDecimal;

public interface SalaryService {
    BigDecimal getMasterSalaryByMasterIdAndOrderId(Long masterId, Long orderId);
}
