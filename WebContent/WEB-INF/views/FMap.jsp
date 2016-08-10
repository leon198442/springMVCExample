<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,java.util.*, javax.servlet.*,java.text.SimpleDateFormat"%>

<div class="pageContent" layoutH="0">

	<div style="width: 100%; height: 100%;" id="f_map"></div>

</div>
<script type="text/javascript">

//百度地图API功能

//默认参数配置
var params = {
	center : "武汉市",
	zoom : 6,
	searchSize : 11,
	clickMapSize : 14,
	isAnimation : false,
	allmapId : "f_map",
}

//添加提示信息
var opts = {
	title : params.tipTitle, // 信息窗口标题
	enableMessage : true
};


// 创建地图 
var map;
// 定义一个控件类,即function
function detailControl(){
  // 默认停靠位置和偏移量
  this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
  this.defaultOffset = new BMap.Size(200, 20);
}



function create() {
	map = new BMap.Map(params.allmapId,{enableMapClick:false});

	map.centerAndZoom(params.center,params.zoom);

	/* 鼠标操作 */
	map.enableScrollWheelZoom(true);
	map.enableContinuousZoom(true);
	map.enableInertialDragging();
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);
	

	// 通过JavaScript的prototype属性继承于BMap.Control
	detailControl.prototype = new BMap.Control();

	// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	detailControl.prototype.initialize = function(map){
	  // 创建一个DOM元素
	  var div = document.createElement("div");
	  var imgStation = document.createElement("img");
	  imgStation.setAttribute("src","images/station.png");
	  imgStation.setAttribute("title","站点数");
	  var aStation = document.createElement("a");
	  aStation.setAttribute("id","StationNum");
	  aStation.setAttribute("href","StationsList.do");
	  aStation.setAttribute("target","navTab");
	  aStation.setAttribute("title","充电站列表");
	  aStation.setAttribute("rel","StationsList");
	  aStation.appendChild(document.createTextNode(": "+ ${StationNum}));
	  div.appendChild(imgStation);
	  div.appendChild(aStation);
	  
	  var imgPile = document.createElement("img");
	  imgPile.setAttribute("src","images/pile.png");
	  imgPile.setAttribute("title","电桩数");
	  var aPile = document.createElement("a");
	  aPile.setAttribute("id","PileNum");
	  aPile.setAttribute("href","PilesList.do");
	  aPile.setAttribute("target","navTab");
	  aPile.setAttribute("title","充电桩列表");
	  aPile.setAttribute("rel","PilesList");
	  aPile.appendChild(document.createTextNode(": "+ ${PileNum}));
	  div.appendChild(imgPile);
	  div.appendChild(aPile);
	  

	  var imgWarn = document.createElement("img");
	  imgWarn.setAttribute("src","images/warning.png");
	  imgWarn.setAttribute("title","故障电桩数");
	  var aWarn = document.createElement("a");
	  aWarn.setAttribute("id","WarnNum");
	  aWarn.setAttribute("href","PilesList.do?FILTER_EQ_status=0");
	  aWarn.setAttribute("target","navTab");
	  aWarn.setAttribute("title","充电桩列表");
	  aWarn.setAttribute("rel","PilesList");
	  aWarn.appendChild(document.createTextNode(": "+ ${WarnNum}));
	  div.appendChild(imgWarn);
	  div.appendChild(aWarn);
	  
	  // 设置样式
	  div.style.cursor = "pointer";
	  div.style.border = "1px solid gray";
	  div.style.backgroundColor = "white";
	  div.style.width = "200";
	  div.style.height = "30";
	  

	  map.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
	}
	// 创建控件
	var myDetailControl = new detailControl();
	// 添加到地图当中
	map.addControl(myDetailControl);

}
/* 提示信息与按地址逆向查询经度纬度 */
myGeo = new BMap.Geocoder();
function geocodeSearch(station, content, tip) {
	var address = station.address;
	myGeo.getPoint(address, function(point) {
		if (point) {
			var addr = new BMap.Point(point.lng, point.lat);
			addMarker(station,addr, content, tip);
		}
	},"全国");
}

// 编写自定义函数,创建标注
function addMarker(station,point, content, tip) {
	
	var icon;
	if (station.status==0) {
		 icon = new BMap.Icon('images/bubble-pink.png', new BMap.Size(36, 36),{anchor: new BMap.Size(18, 30)});//故障

	} else if (station.status==1){
		icon = new BMap.Icon('images/bubble-green.png', new BMap.Size(36, 36),{anchor: new BMap.Size(18, 30)});//正常

	} else if (station.status==2){
		icon = new BMap.Icon('images/bubble-green.png', new BMap.Size(36, 36),{anchor: new BMap.Size(18, 30)});//空闲

	} else if (station.status==3){
		icon = new BMap.Icon('images/bubble-azure.png', new BMap.Size(36, 36),{anchor: new BMap.Size(18, 30)});//未知

	}
	var marker = new BMap.Marker(point, {icon:icon});
	if (tip) {
		marker.setTitle(tip);
	}
	map.addOverlay(marker);
	addClickHandler(station,content, marker, point);
	if (params.isAnimation) {
		marker.setAnimation(BMAP_ANIMATION_DROP); // 跳动的动画 BMAP_ANIMATION_DROP
	}
}
// 点击 弹出提示内容
function addClickHandler(station,content, marker, address) {
	marker.addEventListener("click", function(e) {
		if (address) {
			map.setCenter(address);
		}
		openInfo(station,content, e)
	});
}

// 打开内容提示信息
function openInfo(station,content, e) {
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,{title : station.fullName,offset:new BMap.Size(0, -30)}); // 创建信息窗口对象
	map.openInfoWindow(infoWindow, point); // 开启信息窗口
	map.centerAndZoom(point, params.clickMapSize);
	
	//infoWindow.addEventListener("close", function(e) {
	//	map.centerAndZoom(params.center,params.zoom);
	//});
}
	
	//获取数据
	function createMarkers(cplist) {
		var content_prefix = '<span style="margin-top:5px;color:green;">';
		var content_suffix = '</span><br>';
		var _fullName = '充电站： ';
		var _dept = '地址： ';
		var _state = '状态： ';
		var _newline = '\n';
		var statName = ['故障','正常','空闲','未知'];
		//var cplist =  eval(${ChargeStationList});
		
		for (var i=0;i<cplist.length;i++) {
			var station = cplist[i]
			var content = "";
			var tip = "";
			content += content_prefix+ _fullName + station.fullName + content_suffix;
			content += content_prefix+ _dept + station.address + content_suffix;
			content += content_prefix+ _state + statName[station.status] + content_suffix;
			content += '<a class="button" onclick="show('+station.id+')"><span>详情</span></a>';
			tip += _fullName + station.fullName + _newline;
			tip += _dept + station.address + _newline;
			tip += _state + statName[station.status] + _newline;
			geocodeSearch(station, content,tip);

		}
	}
	
	function show(id) {
		 navTab.openTab("PilesList","PilesList.do?FILTER_EQ_chargeStation_id="+id,{title:"充电桩列表"});
	
	}

</script>
<script type="text/javascript">
create();

createMarkers(eval(${ChargeStationList}));
</script>


<script type="text/javascript">


//定时刷新故障数
function getUpdate() 
{
    $.ajax({
        type : "get",
        url : "getUpdate.do",
        dataType : "json",
        global : false,
        async : true,
        success : function(result){
            document.getElementById("StationNum").innerHTML = ": "+result.stationNum;
            document.getElementById("WarnNum").innerHTML = ": "+result.warnNum;
            document.getElementById("PileNum").innerHTML = ": "+result.pileNum;
            createMarkers(result.jcsList);
        },
        error :function(XMLHttpRequest, textStatus, errorThrown){     
        	console.log(errorThrown+" : "+textStatus);     
        }
	});

}



window.setInterval("getUpdate();", 50000);
</script>


