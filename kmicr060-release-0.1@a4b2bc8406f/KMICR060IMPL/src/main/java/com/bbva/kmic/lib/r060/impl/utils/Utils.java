package com.bbva.kmic.lib.r060.impl.utils;

import java.util.HashMap;
import java.util.Map;

import com.bbva.kmic.dto.movementmodel.MicroloanMovementFilter;

public class Utils {
	private Utils() {
		// sonar
	}

	public static Map<String, Object> buildListParams(final MicroloanMovementFilter filter) {
		final Map<String, Object> params = new HashMap<>();
		params.put("initialDate", filter.getPeriod().getInitialDate());
		params.put("finalDate", filter.getPeriod().getFinalDate());
		params.put("type", filter.getMovementType());
		return params;
	}
}
