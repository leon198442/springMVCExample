<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="PileStatus.do" method="post">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${pages.pageSize}" /> <input type="hidden"
			name="orderField" value="${param.orderField}" /> <input
			type="hidden" name="orderDirection" value="${param.orderDirection}" />
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td><label>所属充电站：</label> <select name="ChargeStation_id"
						id="cs" onchange="getPileList(this.value,'cps')">
							<option value="">请选择...</option>
							<c:forEach var="s" items="${cslist}">
								<option value="${s.id}"
									<c:if test='${param.ChargeStation_id == s.id}'>selected="selected"</c:if>>${s.fullName}</option>
							</c:forEach>
					</select></td>
					<td><label>充电桩：</label> <select name="ChargePile_id" id="cps">
							<option value="">请选择...</option>
							<c:forEach var="p" items="${cplist}">
								<option value="${p.id}"
									<c:if test='${param.ChargePile_id == p.id}'>selected="selected"</c:if>>${p.fullName}</option>
							</c:forEach>
					</select></td>
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
								<button type="submit">检索</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="row">
	<div class="col-md-6 col-sm-12">
		<div class="pageContent" layoutH="90px">

			<table class="table" width="100%">
				<thead>
					<tr>
						<th width="80">序号</th>
						<th width="120" orderField="chargePile.fullName"
							<c:if test='${param.orderField == "chargePile.fullName" }'> class="${param.orderDirection}"  </c:if>>充电桩名称</th>
						<th width="120" orderField="chargePile.chargeStation.Id"
							<c:if test='${param.orderField == "chargePile.chargeStation.Id" }'> class="${param.orderDirection}"  </c:if>>所属充电站</th>
						<th width="120">状态</th>
						<th width="120" orderField="reasonCode"
							<c:if test='${param.orderField == "reasonCode" }'> class="${param.orderDirection}"  </c:if>>信息</th>
						<th width="120" orderField="updateDate"
							<c:if test='${param.orderField == "updateDate" }'> class="${param.orderDirection}"  </c:if>>更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${pages.items}" varStatus="status">
						<tr>
							<td>${p.id}</td>
							<td>${p.chargePile.fullName}</td>
							<td>${p.chargePile.chargeStation.fullName}</td>
							<td><c:if test='${p.status == "0" }'>
									<font color="red">故障</font>
								</c:if> <c:if test='${p.status == "1" }'>正常</c:if> <c:if
									test='${p.status == "2" }'>空闲</c:if> <c:if
									test='${p.status == "3" }'>未知</c:if></td>
							<td>${p.reasonMsg}</td>
							<td>${p.updateDate}</td>
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
	</div>
	<div class="col-md-6 col-sm-12">
			<div><h1>故障分布图</h1></div>
			<div id="pieHolder" style="width: 650px; height: 450px"></div>
		</div>
	</div>

<script>
	$(function () {
		var r = Raphael("pieHolder");  
		var d = eval(${reasons});
		if(d==null){
			document.getElementById("pieHolder").innerHTML = "无可用数据！";
			return;
		}
		
		var pie = r.piechart(200, 225, 150, d.values,{stroke:"#fff",legend:d.lables,legendothers:"其他 %%.%"});
		pie.hover(function() {
		    this.sector.stop();
		    this.sector.scale(1.1, 1.1, this.cx, this.cy);
		 
		    if(this.label) {
		        this.label[0].stop();
		        this.label[0].attr({
		            r: 7.5
		        });
		        this.label[1].attr({
		            "font-weight": 800
		        });
		    }
		}, function() {
		    this.sector.animate({
		        transform: 's1 1 ' + this.cx + ' ' + this.cy
		    }, 500, "bounce");
		        // 添加动画效果
		    if(this.label) {
		        this.label[0].animate({
		            r: 5
		        }, 500, "bounce");
		        this.label[1].attr({
		            "font-weight": 400
		        });
		    }
		});
	});
</script>
