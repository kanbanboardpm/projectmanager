package com.pm.projectmanager.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
	PINK("#F06292"),
	YELLOW("#F4B400"),
	CYAN("#02ACC1"),
	PURPLE("#AB47BC"),
	INDIGO("#5C6BC0"),
	ORANGE("#FF7043"),
	OLIVE("#9E9D24"),
	RED("#DB4437"),
	BLUE("#4285F4"),
	GREEN("#0D9D58");

	private final String hexCode;

	Color(String hexCode) {
		this.hexCode = hexCode;
	}

	// GET 요청 시 클라이언트에 Hex 코드 반환
	@JsonValue
	public String getHexCode() {
		return hexCode;
	}

	// POST 요청 시 색상 이름으로 Enum 매핑
	@JsonCreator
	public static Color fromValue(String value) {
		for (Color color : Color.values()) {
			if (color.name().equalsIgnoreCase(value)) {
				return color;
			}
		}
		throw new IllegalArgumentException("Unknown color: " + value);
	}

	public String getName() {
		return this.name();
	}
}
