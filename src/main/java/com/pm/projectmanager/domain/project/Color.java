package com.pm.projectmanager.domain.project;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum Color {
	BLUE("#4285F4"),
	RED("#DB4437"),
	YELLOW("#F4B400"),
	GREEN("#0D9D58"),
	PURPLE("#AB47BC"),
	CYAN("#02ACC1"),
	ORANGE("#FF7043"),
	KHAKI("#9E9D24"),
	INDIGO("#5C6BC0"),
	PINK("#F06292"),
    DEFAULT("#E1E3E5");

	private final String hexCode;

	Color(String hexCode) {
		this.hexCode = hexCode;
	}

	@Override
	public String toString() {
		return hexCode;
	}

	@JsonValue
	public String getHexCode() {
		return hexCode;
	}
}
