package com.feng.common.utils;

public class Constant {
	public static final int SUPER_ADMIN = 1;

	public static final String PAGE = "page";
	public static final String LIMIT = "limit";
	public static final String ORDER_FIELD = "sidx";
	public static final String ORDER = "order";
	public static final String ASC = "asc";

	public enum MenuType {
		CATALOG(0), MENU(1), BUTTON(2);

		private final int value;
		MenuType(int value) { this.value = value; }
		public int getValue() { return value; }
	}

	public enum ScheduleStatus {
		NORMAL(0), PAUSE(1);

		private final int value;
		ScheduleStatus(int value) { this.value = value; }
		public int getValue() { return value; }
	}
}
