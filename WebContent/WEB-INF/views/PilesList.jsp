<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="PilesList.do" method="post">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${pages.pageSize}" /> <input type="hidden"
			name="orderField" value="${param.orderField}" /> <input
			type="hidden" name="orderDirection" value="${param.orderDirection}" />
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td><label>名称：</label><input type="text"
						name="FILTER_LIKE_fullName" value="${param.FILTER_LIKE_fullName}" /></td>
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
					<td><label>所属充电站：</label> <select class="combox"
						name="FILTER_EQ_chargeStation_id">
							<option
								<c:if test='${param.FILTER_EQ_chargeStation_id == ""}'>selected="selected"</c:if>
								value="">所有电站</option>
							<c:forEach var="p" items="${cslist}" varStatus="status">
								<option value="${p.id}"
									<c:if test='${param.FILTER_EQ_chargeStation_id == p.id}'>selected="selected"</c:if>>${p.fullName}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" layoutH="90px">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="PileUpdate.do" target="dialog"
				rel="addPile"><span>添加</span></a></li>
			<li><a class="delete" href="PileDelete.do?pid={sid_pile}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="PileUpdate.do?pid={sid_pile}"
				target="dialog" rel="editPile"><span>修改</span></a></li>
		</ul>
	</div>

	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="80">序号</th>
				<th width="120" orderField="fullName"
					<c:if test='${param.orderField == "fullName" }'> class="${param.orderDirection}"  </c:if>>名称</th>
				<th width="120" orderField="chargeStation.Id"
					<c:if test='${param.orderField == "chargeStation.Id" }'> class="${param.orderDirection}"  </c:if>>所属充电站</th>
				<th width="120">状态</th>
				<th width="120">操作</th>
			</tr>
		</thead>
		<tbody>
			<fmt:formatDate var="d" value="<%=new Date()%>" pattern="yyyy-MM-dd" />
			<c:forEach var="p" items="${pages.items}" varStatus="status">
				<tr target="sid_pile" rel="${p.id}" data="${p.chargeStation.id}_${p.id}">
					<td>${p.id}</td>
					<td>${p.fullName}</td>
					<td>${p.chargeStation.fullName}</td>
					<td><c:if test='${p.status == "0" }'>
							<font color="red">故障</font>
						</c:if> <c:if test='${p.status == "1" }'>正常</c:if> <c:if
							test='${p.status == "2" }'>空闲</c:if>
						<c:if test='${p.status == "3" }'>未知</c:if></td>
					<td><a class="button"
						href="PileChart.do?ChargeStation_id=${p.chargeStation.id}&ChargePile_id=${p.id}&date=${d}"
						target="dialog" width="680" height="580" rel="demo_barchart" title="充电桩每日曲线图"><span>每日曲线</span></a>
						<a class="button"
						href="PileStatus.do?ChargeStation_id=${p.chargeStation.id}&ChargePile_id=${p.id}"
						target="navTab" rel="PileStatus" title="充电桩状态"><span>状态信息</span></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value})">
			<option value="20"
				<c:if test='${pages.pageSize == 20}'>selected="selected"</c:if>>20</option>
			<option value="50"
				<c:if test='${pages.pageSize == 50}'>selected="selected"</c:if>>50</option>
			<option value="100"
				<c:if test='${pages.pageSize == 100}'>selected="selected"</c:if>>100</option>
		</select> <span>条，共${pages.totalCount}条</span>
	</div>

	<div class="pagination" targetType="navTab"
		totalCount="${pages.totalCount}" numPerPage="${pages.pageSize}"
		pageNumShown="10" currentPage="${pages.pageNum}"></div>

</div>
<script type="text/javascript">
function dbltable(target,data){
	if( target == "sid_pile"){//identify this table
		
		var date =new Date();
		var d = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		var i = data.indexOf("_");
		
		var cs = data.substring(0, i);
		var cp = data.substring(i+1);		
		 navTab.openTab("demo_barchart","PileChart.do?ChargeStation_id="+cs+"&ChargePile_id="+cp+"&date="+d,{title:"充电桩每日曲线图"});
	} 
}
</script>
