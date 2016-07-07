package org.biac.trace.service;

import java.math.BigDecimal;
import java.util.Map;

public interface AirconditionService {

	Map<String, Float> getWorkValue(BigDecimal workMode);
}
