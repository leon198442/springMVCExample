package cn.com.titans.tcsmp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.titans.tcsmp.utils.Criterion.Operator;

public class Page<T> {
	private String orderField = "id";
	private String orderDirection = "ASC";

	// 当前页数
	private int pageNum = 1;

	// 每页显示数量
	private int pageSize = 20;

	// 总页数
	private int totalPage;
	// 总数量
	private long totalCount;

	private List<T> items = new ArrayList<T>();

	private Criteria<T> parameters = new Criteria<T>();

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	// 设置总数量的同时，设置总页数
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public Criteria<T> getParameters() {
		return parameters;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameters(Map mP) {
		Map<String, String[]> mParameters = (Map<String, String[]>)mP;

		String[] pageNum = mParameters.get("pageNum");
		String[] numPerPage = mParameters.get("numPerPage");
		String[] orderField = mParameters.get("orderField");
		String[] orderDirection = mParameters.get("orderDirection");
		if (pageNum != null && pageNum.length != 0) {
			if (!StringUtils.isEmpty(pageNum[0]))
				this.setPageNum(Integer.valueOf(pageNum[0]));
		}
		if (numPerPage != null && numPerPage.length != 0) {
			if (!StringUtils.isEmpty(numPerPage[0]))
				this.setPageSize(Integer.valueOf(numPerPage[0]));
		}
		if (orderField != null && orderField.length != 0) {
			if (!StringUtils.isEmpty(orderField[0]))
				this.setOrderField(orderField[0]);
		}
		if (orderDirection != null && orderDirection.length != 0) {
			if (!StringUtils.isEmpty(orderDirection[0]))
				this.setOrderDirection(orderDirection[0]);
		}

	
		for (String p : mParameters.keySet()) {
				String value = mParameters.get(p)[0];
				parameters.add(parse(p,value));

		}

	}
	
	public void addParameter(String name,String value) {
		
		parameters.add(parse(name,value));
		
	}

	private SimpleExpression parse(String name, String value) {
		if(StringUtils.isEmpty(value)){
			return null;//if value is empty means for all
		}
		// FILTER_<Operator>_<fieldName>
		SimpleExpression se = null;
		if (name.startsWith("FILTER")) {
			String[] splitedPara = StringUtils.split(name, "_",3);
			if (splitedPara.length < 3) {// Unformatted parameter
				return null;
			}
			String operator = splitedPara[1];
			String fieldName = splitedPara[2].replace('_', '.');
			
			switch (operator) {
			case "EQ":
				se = new SimpleExpression(fieldName, value, Operator.EQ);
				break;
			case "NE":
				se = new SimpleExpression(fieldName, value, Operator.NE);
				break;
			case "LIKE":
				se = new SimpleExpression(fieldName, value, Operator.LIKE);
				break;
			case "GT":
				se = new SimpleExpression(fieldName, value, Operator.GT);
				break;
			case "LT":
				se = new SimpleExpression(fieldName, value, Operator.LT);
				break;
			case "GTE":
				se = new SimpleExpression(fieldName, value, Operator.GTE);
				break;
			case "LTE":
				se = new SimpleExpression(fieldName, value, Operator.LTE);
				break;
			default:
				break;

			}

		}
		return se;

	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

}
