package cn.com.titans.tcsmp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.com.titans.tcsmp.entity.ChargeStation;
import cn.com.titans.tcsmp.service.ChargePileService;
import cn.com.titans.tcsmp.service.ChargeStationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class controller {

	@Autowired
	private ChargePileService chargePileService;

	@Autowired
	private ChargeStationService chargeStationService;

	@RequestMapping(value = "getUpdate.do")
	public void getUpdate(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		Date date = new Date();
		date.setTime(date.getTime() - 60000);// update changes in 1 min
		List<ChargeStation> csList = chargeStationService.findByUpdateDate(date);

		String jcsList = null;

		try {
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { // 只要设置这个数组，指定过滤哪些字段。
					"chargePileList" });
			jcsList = JSONArray.fromObject(csList, config).toString();
		} catch (Exception e) {
			System.out.println(this.getClass() + ",异常：" + e.getMessage());
		}


		JSONObject jobj = new JSONObject();
		jobj.put("stationNum", chargeStationService.countAll());
		jobj.put("pileNum", chargePileService.countAll());
		jobj.put("warnNum", chargePileService.countByStatus("0"));
		jobj.put("jcsList", jcsList);

		response.setContentType("text/html;charaet=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jobj);
		out.flush();
		out.close();
	}

	@RequestMapping(value = "FMap.do")
	public String map_jsp(Model model) {
		List<ChargeStation> csList = chargeStationService.findAll();
		String jcsList = null;

		try {
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { // 只要设置这个数组，指定过滤哪些字段。
					"chargePileList" });
			jcsList = JSONArray.fromObject(csList, config).toString();
		} catch (Exception e) {
			System.out.println(this.getClass() + ",异常：" + e.getMessage());
		}

		model.addAttribute("ChargeStationList", jcsList);

		model.addAttribute("StationNum", chargeStationService.countAll());
		model.addAttribute("PileNum", chargePileService.countAll());
		model.addAttribute("WarnNum", chargePileService.countByStatus("0"));
		return "FMap.jsp";
	}

//	@RequestMapping(value = "BMap.do")
//	public void Bmap_jsp(HttpServletRequest request, Model model) {
//		List<ChargePile> cpList = chargePileService.findAll();
//
//		String jcpList = JSONArray.fromObject(cpList).toString();
//		model.addAttribute("ChargePileList", jcpList);
//
//		Page<ChargePile> page = new Page<ChargePile>();
//		String currentPage = (String) request.getParameter("pageNum");
//		String everyPage = (String) request.getParameter("numPerPage");
//		if (currentPage != null && !currentPage.isEmpty()) {
//			page.setPageNum(Integer.valueOf(currentPage));
//		}
//		if (everyPage != null && !everyPage.isEmpty()) {
//			page.setPageSize(Integer.valueOf(everyPage));
//		}
//
//		page = chargePileService.findAllByPage(page);
//		model.addAttribute("pages", page);
//		return;
//	}

}