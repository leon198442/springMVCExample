<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return dialogSearch(this);" action="StationLookUp.search">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${pages.pageSize}" /> <input type="hidden"
			name="orderField" value="${param.orderField}" /> <input
			type="hidden" name="orderDirection" value="${param.orderDirection}" />
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td><label>名称：</label><input type="text"
						name="FILTER_LIKE_fullName" value="${param.FILTER_LIKE_fullName}" /></td>
					<td><label>地址：</label><input type="text"
						name="FILTER_LIKE_address" value="${param.FILTER_LIKE_address}" /></td>
					<td><label>状态：</label><select class="combox"
						name="FILTER_EQ_status">
							<option
								<c:if test='${param.FILTER_EQ_status == ""}'>selected="selected"</c:if>
								value="">所有状态</option>
							<option
								<c:if test='${param.FILTER_EQ_status == "0"}'>selected="selected"</c:if>
								value="0">故障</option>
							<option
								<c:if test='${param.FILTER_EQ_status == "1"}'>selected="selected"</c:if>
								value="1">正常</option>
							<option
								<c:if test='${param.FILTER_EQ_status == "2"}'>selected="selected"</c:if>
								value="2">空闲</option>
							<option
								<c:if test='${param.FILTER_EQ_status == "3"}'>selected="selected"</c:if>
								value="3">未知</option>
					</select></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">搜索</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" layoutH="90px">

	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="80">序号</th>
				<th width="120" orderField="fullName"
					<c:if test='${param.orderField == "fullName" }'> class="${param.orderDirection}"  </c:if>>名称</th>
				<th width="120" orderField="address"
					<c:if test='${param.orderField == "address" }'> class="${param.orderDirection}"  </c:if>>地址</th>
				<th width="120">状态</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="p" items="${pages.items}" varStatus="status">
				<tr target="sid_station" rel="${p.id}">
					<td>${p.id}</td>
					<td>${p.fullName}</td>
					<td>${p.address}</td>
					<td><c:if test='${p.status == "0" }'>
							<font color="red">故障</font>
						</c:if> <c:if test='${p.status == "1" }'>正常</c:if> <c:if
							test='${p.status == "2" }'>空闲</c:if><c:if
							test='${p.status == "3" }'>未知</c:if>
							</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({id:'${p.id}',fullName:'${p.fullName}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="dialogPageBreak({numPerPage:this.value})">
			<option value="20"
				<c:if test='${pages.pageSize == 20}'>selected="selected"</c:if>>20</option>
			<option value="50"
				<c:if test='${pages.pageSize == 2}'>selected="selected"</c:if>>50</option>
			<option value="100"
				<c:if test='${pages.pageSize == 100}'>selected="selected"</c:if>>100</option>
		</select> <span>条，共${pages.totalCount}条</span>
	</div>

	<div class="pagination" targetType="dialog"
		totalCount="${pages.totalCount}" numPerPage="${pages.pageSize}"
		pageNumShown="10" currentPage="${pages.pageNum}"></div>

</div>

