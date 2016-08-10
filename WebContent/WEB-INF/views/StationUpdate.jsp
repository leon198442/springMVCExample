<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="pageContent">
	<form method="post" action="StationSave.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>充电站名称：</label>
				<input type="hidden" name="id" value="${station.id}"/>
				<input name="name" class="required" type="text" size="30" value="${station.fullName}" alt="请输入充电站名称"/>
			</p>

			<p>
				<label>充电站地址：</label>
				<input type="text" class="required" name="address" value="${station.address}" />
			</p>
<div class="divider"></div>
<h3 class="contentTitle">批量添加充电桩</h3>			
				<div>
					<table class="list nowrap itemDetail" addButton="新建充电桩" width="100%">
						<thead>
							<tr>
								<th type="text" name="items[#index#].name" size="12" fieldClass="required" ">充电桩名称</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

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