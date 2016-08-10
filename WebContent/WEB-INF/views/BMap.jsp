<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="pageContent">
	<div class="row">
		<div class="col-md-6 col-sm-12">
	<div style="float:left;width:100%;height:700px;overflow:hidden;" id="baidu_map"></div></div>
	<div class="col-md-6 col-sm-12" layoutH="30">
				<div class="pageContent" >
				<form id="pagerForm" method="post" action="BMap.do">  
				    <input type="hidden" name="pageNum" value="1" />  
					<input type="hidden" name="numPerPage" value="${pages.everyPage}" />
				    <input type="hidden" name="orderField"       value="${param.orderField}" />  
				    <input type="hidden" name="orderDirection"  value="${param.orderDirection}" />  
				</form>  
				  
				  
				<div class="pageHeader">  
				    <form onsubmit="return navTabSearch(this);" action="BMap.do" method="post">  
				    <div class="searchBar">  
				    </div>  
				    </form>  
				</div>  
				<div class="pageContent">  
				    <div class="panelBar">  
				        <ul class="toolBar">  
				            <li class="line">line</li>  
				        </ul>  
				    </div>  
					    <table  class="table" width="100%">  
					        <thead>  
					            <tr>  
					                <th width="80">序号</th>  
					                <th width="120" orderField="fullName" <c:if test='${param.orderField == "fullName" }'> class="${param.orderDirection}"  </c:if>   >名称</th>  
					                <th width="120" orderField="address" <c:if test='${param.orderField == "address" }'> class="${param.orderDirection}"  </c:if> >地址</th>  
					                <th width="120">状态</th> 
					            </tr>  
					        </thead>  
					        <tbody>  
					         <c:forEach var="p" items="${pages.items}" varStatus="status" >   
					            <tr target="sid" rel="${p.id}" onMouseOver="changeFoucus('${p.id}')">  
					                <td>${p.id}</td>  
					                <td>${p.fullName}</td>  
					                <td>${p.address}</td>  
					                <td>${p.status}</td>  
					            </tr>  
					          </c:forEach>  
					        </tbody>  
					    </table>  
					    <div class="panelBar">  
					        <div class="pages">  
					            <span>显示</span>  
					            <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">  
					                <option value="1" <c:if test='${pages.everyPage == 1}'>selected="selected"</c:if>>1</option>  
					                <option value="2" <c:if test='${pages.everyPage == 2}'>selected="selected"</c:if>>2</option>  
					                <option value="100" <c:if test='${pages.everyPage == 100}'>selected="selected"</c:if>>100</option>   
					            </select>  
					            <span>条，共${pages.totalCount}条</span>  
					        </div>  
					          
					        <div class="pagination" targetType="navTab" totalCount="${pages.totalCount}" numPerPage="${pages.everyPage}" pageNumShown="10" currentPage="${pages.currentPage}"></div>  
					  
					    </div> 
				  </div>  
			</div>  
		</div>
	</div>
</div>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<script type="text/javascript">
	


//百度地图API功能

//默认参数配置
var params = {
	center : "兰州市",
	zoom : 5,
	searchSize : 11,
	clickMapSize : 14,
	isAnimation : false,
	allmapId : "baidu_map",
	searchId : "suggestId",
	searchResultId : "searchResultPanel",
	tipTitle: "详细信息"
}

//添加提示信息
var opts = {
	title : params.tipTitle, // 信息窗口标题
	enableMessage : true
};


// 创建地图 
var map;

function create(){
	createMap();
}

function changeFoucus(id){
	console.log(id);
}

function createMap() {
	map = new BMap.Map(params.allmapId,{enableMapClick:false});

	map.centerAndZoom(params.center,params.zoom);

	/* 鼠标操作 */
	map.enableScrollWheelZoom(true);
	map.enableContinuousZoom(true);
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);
}
/* 提示信息与按地址逆向查询经度纬度 */
myGeo = new BMap.Geocoder();
function geocodeSearch(address, content, tip) {
	myGeo.getPoint(address, function(point) {
		if (point) {
			var address = new BMap.Point(point.lng, point.lat);
			addMarker(address, content, tip);
		}
	},"全国");
}

// 编写自定义函数,创建标注
function addMarker(point, content, tip) {
	var marker;
	if (params.icon) {
		marker = new BMap.Marker(point, {
			icon : params.icon
		});
	} else {
		marker = new BMap.Marker(point);
	}
	if (tip) {
		marker.setTitle(tip);
	}
	map.addOverlay(marker);
	addClickHandler(content, marker, point);
	if (params.isAnimation) {
		marker.setAnimation(BMAP_ANIMATION_DROP); // 跳动的动画 BMAP_ANIMATION_DROP
	}
}
// 点击 弹出提示内容
function addClickHandler(content, marker, address) {
	marker.addEventListener("click", function(e) {
		if (address) {
			map.setCenter(address);
		}
		openInfo(content, e)
	});
}

// 打开内容提示信息
function openInfo(content, e) {
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts); // 创建信息窗口对象
	map.openInfoWindow(infoWindow, point); // 开启信息窗口
	map.centerAndZoom(point, params.clickMapSize);
	
	infoWindow.addEventListener("close", function(e) {
		map.centerAndZoom(params.center,params.zoom);
	});
}



</script>
<script type="text/javascript">


	create();
	//获取数据
	$(function() {
		var content_prefix = '<span style="margin-top:5px;color:green;">';
		var content_suffix = '</span><br>';
		var _fullName = '充电桩： ';
		var _dept = '地址： ';
		var _state = '状态： ';
		var _newline = '\n';
		var statName = ['故障','正常'];
		var cplist =  eval(${ChargePileList});
		
		for (var i=0;i<cplist.length;i++) {
			var pile = cplist[i]
			var content = "";
			var tip = "";
			content += content_prefix+ _fullName + pile.fullName + content_suffix;
			content += content_prefix+ _dept + pile.address + content_suffix;
			content += content_prefix+ _state + statName[pile.status] + content_suffix;
			
			tip += _fullName + pile.fullName + _newline;
			tip += _dept + pile.address + _newline;
			tip += _state + statName[pile.status] + _newline;
			geocodeSearch(pile.address, content,tip);

		}
	});

</script>


