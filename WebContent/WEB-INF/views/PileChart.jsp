<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(function () {
	var d = eval(${data});
	console.log(d);
	
	if(d==null){
		document.getElementById("chartHolder").innerHTML = "无可用数据！";
		return;
	}
	
    Morris.Line({
      element: 'chartHolder',
      data: d,
      xkey: 'time',
      ykeys: ['vol', 'cur','amo'],
      labels: ['电压(V)', '电流(A)','电量(kwh)'],
      smooth:false,
      dateFormat: function (x) {     	  
    	  return new Date(x).toLocaleTimeString(); }
    });
});
</script>

<div class="pageHeader">
	<form id="pagerForm" onsubmit="return dialogSearch(this);"
		action="PileChart.do" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td><label>所属充电站：</label> <select 
						name="ChargeStation_id" id="cs" onchange="getPileList(this.value,'cp')">
							<option value="">请选择...</option>
							<c:forEach var="s" items="${cslist}">
								<option value="${s.id}"
									<c:if test='${param.ChargeStation_id == s.id}'>selected="selected"</c:if>>${s.fullName}</option>
							</c:forEach>
					</select></td>
					<td><label>充电桩：</label> <select 
						name="ChargePile_id" id="cp">
							<option value="">请选择...</option>
							<c:forEach var="p" items="${cplist}">
								<option value="${p.id}"
									<c:if test='${param.ChargePile_id == p.id}'>selected="selected"</c:if>>${p.fullName}</option>
							</c:forEach>
					</select></td>
					<td>
					<label>日期：</label>
						<input type="text" name="date" class="date" readonly="true" value="${param.date}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">选择</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div id="chartHolder" style="width: 650px;height: 450px"></div>
