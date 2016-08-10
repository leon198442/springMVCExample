
	


//百度地图API功能

//默认参数配置
var params = {
	zoom : 5,
	searchSize : 11,
	clickMapSize : 14,
	isAnimation : true,
	icon : null,
	tipTitle : "详细信息",
	allmapId : "allmap",
	searchId : "suggestId",
	searchResultId : "searchResultPanel"
}

//添加提示信息 width : 250, height: 80,
var opts = {
	title : params.tipTitle, // 信息窗口标题
	enableMessage : true
};

// 初始化参数
function setParams(cfg) {
	if (cfg == false) {
		return;
	}
	if (cfg.zoom) {
		params.zoom = cfg.zoom;
	}
	if (cfg.tip_title) {
		params.tip_title = cfg.tip_title;
	}
	if (cfg.id_allmap) {
		params.id_allmap = cfg.id_allmap;
	}
	if (cfg.searchId) {
		params.searchId = cfg.searchId;
	}
	if (cfg.searchResultId) {
		params.searchResultId = cfg.searchResultId;
	}
	if (cfg.isAnimation) {
		params.isAnimation = cfg.isAnimation;
	}
	if (cfg.icon) {
		params.icon = cfg.icon;
	}
}

// 创建地图 根据 ip 定位
var map;

function create(){
	createMap();
	addPanoramaControl();
	addNavigationControl();
	addMapTypeControl();
	addgeolocationControl();
}
function createMap() {
	map = new BMap.Map(params.allmapId);
	var point = new BMap.Point(114.061619, 22.546855);
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
	/* 鼠标操作 */
	map.enableScrollWheelZoom(true);
	map.enableContinuousZoom(true);
}
//定位城市
function myFun(result) {
	var cityName = result.name;
	map.setCenter(cityName);
	map.centerAndZoom(cityName, params.zoom);
	setTimeout(function(){
		map.centerAndZoom(cityName, params.searchSize);
	},7000);
}

//添加全景控件
function addPanoramaControl(x, y) {
	if (!x) {
		x = 8;
	}
	if (!y) {
		y = 220;
	}
	map.addTileLayer(new BMap.PanoramaCoverageLayer());
	var stCtrl = new BMap.PanoramaControl({
		anchor : BMAP_ANCHOR_TOP_LEFT
	});
	stCtrl.setOffset(new BMap.Size(x, y));
	map.addControl(stCtrl);
};

//左上角，添加默认缩放平移控件
function addNavigationControl(x, y) {
	if (!x) {
		x = 0;
	}
	if (!y) {
		y = 0;
	}
	top_left_navigation = new BMap.NavigationControl();
	top_right_navigation = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT
	}); // 右上角，仅包含平移和缩放按钮
	top_right_navigation.setOffset(new BMap.Size(x, y));
	/*
	 * 缩放控件type有四种类型:
	 * BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮
	 */
	// 添加控件和比例尺
	// map.addControl(top_left_control);
	map.addControl(top_left_navigation);
}
//左上角，添加比例尺
function addScaleControl(x, y) {
	if (!x) {
		x = 5;
	}
	if (!y) {
		y = 2;
	}
	top_left_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_TOP_LEFT
	});
	// {anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}
	top_right_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT
	});
	// 右上角，添加比例尺
	top_left_control.setOffset(new BMap.Size(x, y));
}

// 移除控件和比例尺
function delete_control() {
	map.removeControl(top_left_control);
	map.removeControl(top_left_navigation);
	map.removeControl(top_right_navigation);
}

//添加地图类型和缩略图
function addMapTypeControl(x, y) {
	if (!x) {
		x = 5;
	}
	if (!y) {
		y = 8;
	}
	var mapType1 = new BMap.MapTypeControl({
		mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
	});
	var mapType2 = new BMap.MapTypeControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT
	});
	mapType2.setOffset(new BMap.Size(x, y));

	var overView = new BMap.OverviewMapControl();
	var overViewOpen = new BMap.OverviewMapControl({
		isOpen : true,
		anchor : BMAP_ANCHOR_BOTTOM_RIGHT
	});
	map.addControl(mapType2); // 左上角，默认地图控件
	map.addControl(overView); // 添加默认缩略地图控件
	map.addControl(overViewOpen); // 右下角，打开
}

// 添加定位控件
function addgeolocationControl() {
	geolocationControl = new BMap.GeolocationControl();
	geolocationControl.addEventListener("locationSuccess", function(e) {
		// 定位成功事件
		var address = '';
		address += e.addressComponent.province;
		address += e.addressComponent.city;
		address += e.addressComponent.district;
		address += e.addressComponent.street;
		address += e.addressComponent.streetNumber;
	});
	geolocationControl.addEventListener("locationError", function(e) {
		// 定位失败事件
		alert(e.message);
	});
	map.addControl(geolocationControl);
}

/* 提示信息与按地址逆向查询经度纬度 */
myGeo = new BMap.Geocoder();
function geocodeSearch(address, content, tip) {
	myGeo.getPoint(address, function(point) {
		if (point) {
			var address = new BMap.Point(point.lng, point.lat);
			addMarker(address, content, tip);
			map.centerAndZoom(address, params.searchSize);
		}
	});
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
// 点击门店图标，弹出提示内容
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
	var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
	map.openInfoWindow(infoWindow, point); // 开启信息窗口
	map.centerAndZoom(point, params.clickMapSize);
}


//关键字检索
/*
function G(id) {
	return document.getElementById(id);
}
var ac = new BMap.Autocomplete( // 建立一个自动完成的对象
{
	"input" : params.searchId,
	"location" : map
});

ac.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
	var str = "";
	var _value = e.fromitem.value;
	var value = "";
	if (e.fromitem.index > -1) {
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str = "FromItem<br />index = " + e.fromitem.index + "<br />value = "
			+ value;

	value = "";
	if (e.toitem.index > -1) {
		_value = e.toitem.value;
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = "
			+ value;
	G(params.searchResultId).innerHTML = str;
});

var myValue;
ac.addEventListener("onconfirm", function(e) { // 鼠标点击下拉列表后的事件
	var _value = e.item.value;
	myValue = _value.province + _value.city + _value.district + _value.street
			+ _value.business;
	G(params.searchResultId).innerHTML = "onconfirm<br />index = "
			+ e.item.index + "<br />myValue = " + myValue;
	setPlace();
});

var prev_local = null;
function setPlace() {
	if (prev_local) {
		map.removeOverlay(prev_local); // 清除地图上覆盖物
		prev_local = null;
	}
	function _myFun() {
		var pp = local.getResults().getPoi(0).point; // 获取第一个智能搜索的结果
		var marker = new BMap.Marker(pp);
		map.setCenter(pp);
		map.centerAndZoom(pp, params.searchSize);
		prev_local = marker;
		map.addOverlay(marker); // 添加标注
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); // 跳动的动画 BMAP_ANIMATION_DROP
		marker.setTitle(myValue);
		addClickHandler(myValue, marker);
		marker.enableDragging();           // 可拖拽
	}
	var local = new BMap.LocalSearch(map, { // 智能搜索
		onSearchComplete : _myFun
	});
	local.search(myValue);
}
/**/
 
// 关键字检索
function G(id) {
	return document.getElementById(id);
}
var ac = new BMap.Autocomplete( // 建立一个自动完成的对象
{
	"input" : params.searchId,
	"location" : map
});

ac.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
	var str = "";
	var _value = e.fromitem.value;
	var value = "";
	if (e.fromitem.index > -1) {
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str = "FromItem<br />index = " + e.fromitem.index + "<br />value = "
			+ value;

	value = "";
	if (e.toitem.index > -1) {
		_value = e.toitem.value;
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = "
			+ value;
	G(params.searchResultId).innerHTML = str;
});

var myValue;
ac.addEventListener("onconfirm", function(e) { // 鼠标点击下拉列表后的事件
	var _value = e.item.value;
	myValue = _value.province + _value.city + _value.district + _value.street
			+ _value.business;
	G(params.searchResultId).innerHTML = "onconfirm<br />index = "
			+ e.item.index + "<br />myValue = " + myValue;
	setPlace();
});

var prev_local = null;
function setPlace() {
	if (prev_local) {
		map.removeOverlay(prev_local); // 清除地图上覆盖物
		prev_local = null;
	}
	function _myFun() {
		var pp = local.getResults().getPoi(0).point; // 获取第一个智能搜索的结果
		var marker = new BMap.Marker(pp);
		map.setCenter(pp);
		map.centerAndZoom(pp, params.searchSize);
		prev_local = marker;
		map.addOverlay(marker); // 添加标注
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); // 跳动的动画 BMAP_ANIMATION_DROP
		marker.setTitle(myValue);
		addClickHandler(myValue, marker);
		marker.enableDragging();           // 可拖拽
	}
	var local = new BMap.LocalSearch(map, { // 智能搜索
		onSearchComplete : _myFun
	});
	local.search(myValue);
}
