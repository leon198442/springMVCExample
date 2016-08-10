package cn.com.titans.tcsmp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.titans.tcsmp.entity.ChargePile;
import cn.com.titans.tcsmp.entity.ChargePileInfo;
import cn.com.titans.tcsmp.entity.ChargeStation;
import cn.com.titans.tcsmp.service.ChargePileInfoService;
import cn.com.titans.tcsmp.service.ChargeStationService;
import cn.com.titans.tcsmp.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class ChargePileInfoController {
	@Autowired
	private ChargeStationService chargeStationService;
	@Autowired
	private ChargePileInfoService chargePileInfoService;

//	@RequestMapping(value = "PileChart.do")
//	public String pileChart_jsp(HttpServletRequest request, Model model) {
//		List<ChargeStation> cslist = chargeStationService.findAll();
//
//		model.addAttribute("cslist", cslist);
//		String sid = request.getParameter("ChargeStation_id");
//		if (!StringUtils.isEmpty(sid)) {
//
//			ChargeStation s = chargeStationService.findOne(Long.decode(sid));
//			model.addAttribute("cplist", s.getChargePileList());
//		}
//
//		String pid = request.getParameter("ChargePile_id");
//		if (!StringUtils.isEmpty(pid)) {
//			Date date = new Date();
//			String d = request.getParameter("date");
//			if (StringUtils.isEmpty(d)) {
//				SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
//				d = dateFmt.format(date);
//			}
//
//			DateFormat df = DateFormat.getDateInstance();
//			try {
//				date = df.parse(d);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			List<ChargePileInfo> cpiList = chargePileInfoService.findByChargePileIdAndStartDate(Long.decode(pid), date);
//			if (cpiList.size() <= 1) {// no data to show in chart
//				return "PileChart.jsp";
//			}
//			JSONObject jobj = new JSONObject();
//
//			long[] xArray = new long[cpiList.size()];
//			String[][] yValue = new String[3][cpiList.size()];
//			int i = 0;
//			String[] axisxlables = new String[cpiList.size()];
//			SimpleDateFormat dateFmt = new SimpleDateFormat("HH:mm");
//			for (ChargePileInfo cpi : cpiList) {
//
//				xArray[i] = cpi.getUpdateDate().getTime();
//				axisxlables[i] = dateFmt.format(cpi.getUpdateDate());
//				yValue[0][i] = String.valueOf(cpi.getVoltage());
//				yValue[1][i] = String.valueOf(cpi.getCurrent());
//				yValue[2][i] = String.valueOf(cpi.getChargedAmount());
//
//				i++;
//
//			}
//
//			jobj.element("axisxlables", JSONArray.fromObject(axisxlables).toString());
//			jobj.element("xArray", JSONArray.fromObject(xArray).toString());
//			jobj.element("yValue", JSONArray.fromObject(yValue).toString());
//
//			model.addAttribute("data", jobj.toString());
//		}
//
//		return "PileChart.jsp";
//	}

	@RequestMapping(value = "getPileList.do")
	public void getPileList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sid = request.getParameter("sid");
		ChargeStation cs = chargeStationService.findOne(Long.decode(sid));

		List<ChargePile> cpList = cs.getChargePileList();

		String jcpList = null;
		
		try {
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { // 只要设置这个数组，指定过滤哪些字段。
					"chargePileList" });
			jcpList = JSONArray.fromObject(cpList, config).toString();
		} catch (Exception e) {
			System.out.println(this.getClass() + ",异常：" + e.getMessage());
		}

		response.setContentType("text/html;charaet=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jcpList);
		out.flush();
		out.close();

	}
	
	@RequestMapping(value = "PileChart.do")
	public String pileChart_jsp(HttpServletRequest request, Model model) {
		List<ChargeStation> cslist = chargeStationService.findAll();

		model.addAttribute("cslist", cslist);
		String sid = request.getParameter("ChargeStation_id");
		if (!StringUtils.isEmpty(sid)) {

			ChargeStation s = chargeStationService.findOne(Long.decode(sid));
			model.addAttribute("cplist", s.getChargePileList());
		}

		String pid = request.getParameter("ChargePile_id");
		if (!StringUtils.isEmpty(pid)) {
			Date date = new Date();
			String d = request.getParameter("date");
			if (StringUtils.isEmpty(d)) {
				SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
				d = dateFmt.format(date);
			}

			DateFormat df = DateFormat.getDateInstance();
			try {
				date = df.parse(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<ChargePileInfo> cpiList = chargePileInfoService.findByChargePileIdAndStartDate(Long.decode(pid), date);
			if (cpiList.size() <= 1) {// no data to show in chart
				return "PileChart.jsp";
			}
			
			JSONArray jarray = new JSONArray();

			for (ChargePileInfo cpi : cpiList) {
				JSONObject jobj = new JSONObject();
				
				jobj.element("time", cpi.getUpdateDate().getTime());

				jobj.element("vol", cpi.getVoltage());
				jobj.element("cur", cpi.getCurrent());
				jobj.element("amo", cpi.getChargedAmount());

				jarray.element(jobj.toString());

			}

			model.addAttribute("data", jarray.toString());
		}

		return "PileChart.jsp";
	}
	

}
