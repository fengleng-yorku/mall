package com.feng.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

import java.util.Map;

public class Query<T> {

	public IPage<T> getPage(Map<String, Object> params) {
		return this.getPage(params, null, false);
	}

	public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
		long curPage = 1;
		long limit = 10;

		if (params.get(Constant.PAGE) != null) {
			curPage = Long.parseLong((String) params.get(Constant.PAGE));
		}
		if (params.get(Constant.LIMIT) != null) {
			limit = Long.parseLong((String) params.get(Constant.LIMIT));
		}

		Page<T> page = new Page<>(curPage, limit);
		params.put(Constant.PAGE, page);

		// only allow word characters to prevent SQL injection
		String orderField = sanitize((String) params.get(Constant.ORDER_FIELD));
		String order = (String) params.get(Constant.ORDER);

		if (StringUtils.hasText(orderField) && StringUtils.hasText(order)) {
			if (Constant.ASC.equalsIgnoreCase(order)) {
				return page.addOrder(OrderItem.asc(orderField));
			} else {
				return page.addOrder(OrderItem.desc(orderField));
			}
		}

		if (!StringUtils.hasText(defaultOrderField)) {
			return page;
		}

		if (isAsc) {
			page.addOrder(OrderItem.asc(defaultOrderField));
		} else {
			page.addOrder(OrderItem.desc(defaultOrderField));
		}

		return page;
	}

	private String sanitize(String str) {
		if (str == null) return null;
		return str.replaceAll("[^\\w]", "");
	}
}
