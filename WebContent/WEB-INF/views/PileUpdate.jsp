<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="pageContent">
	<form method="post" action="PileSave.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>充电桩名称：</label>
				<input type="hidden" name="id" value="${pile.id}"/>
				<input name="name" class="required" type="text" size="30" value="${pile.fullName}" alt="请输入充电桩名称"/>
			</p>

			<p>
				<label>充电站名称：</label>
				<input type="hidden" name="ChargeStation.id" value="${ChargeStation.id}"/>
				<input type="text" class="required" readonly="readonly" name="ChargeStation.fullName" value="${pile.chargeStation.fullName}" lookupGroup="ChargeStation" />
				<a class="btnLook" href="StationLookUp" lookupGroup="ChargeStation" rel="lookUpCS">查找带回</a>
				<!--  <input type="text" class="required" name="ChargeStation.fullName" value="${pile.chargeStation.fullName}" />-->
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
</div>