package cn.com.titans.tcsmp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.com.titans.tcsmp.entity.ChargePileStatus;
import cn.com.titans.tcsmp.entity.ChargeStation;
import cn.com.titans.tcsmp.service.ChargePileStatusService;
import cn.com.titans.tcsmp.service.ChargeStationService;
import cn.com.titans.tcsmp.service.ReasonCount;
import cn.com.titans.tcsmp.utils.Page;
import cn.com.titans.tcsmp.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ChargePileStatusController {
	@Autowired
	private ChargeStationService chargeStationService;

	@Autowired
	private ChargePileStatusService chargePileStatusService;
	
	@RequestMapping(value = "PileStatus.do")
	public String pileStatus_jsp(HttpServletRequest request, Model model) {
		
		String sid = request.getParameter("ChargeStation_id");
		if (!StringUtils.isEmpty(sid)) {

			ChargeStation s = chargeStationService.findOne(Long.decode(sid));
			model.addAttribute("cplist", s.getChargePileList());
		}
		Page<ChargePileStatus> page = new Page<ChargePileStatus>();
		
		String pid = request.getParameter("ChargePile_id");
		if (!StringUtils.isEmpty(pid)) {
			page.addParameter("FILTER_EQ_chargePile.id", pid);
		}else{
			pid="0";
		}
		page.setOrderField("updateDate");
		page.setOrderDirection("desc");
		
		page.setParameters(request.getParameterMap());

		page = chargePileStatusService.findPageWithParameters(page);
		model.addAttribute("pages", page);
		List<ChargeStation> cslist = chargeStationService.findAll();
		
		model.addAttribute("cslist", cslist);
		
		JSONObject jobj = new JSONObject();
		List<ReasonCount> rcList = chargePileStatusService.getReasonCount(Long.decode(pid));

		int[] values = new int[rcList.size()];
		int i = 0;
		String[] lables = new String[rcList.size()];
		//String[] href = new String[rcList.size()];
		for (ReasonCount rc : rcList) {

			values[i] = rc.getCounts();
			lables[i] = ChargePileStatus.parseCode(rc.getReasonCode()) + " %%.%";
			//href[i] = "PileStatus.do?ChargeStation_id="+sid+"&ChargePile_id="+pid+"&FILTER_EQ_reasonCode="+rc.getReasonCode();

			i++;

		}

		jobj.element("values", JSONArray.fromObject(values).toString());
		jobj.element("lables", JSONArray.fromObject(lables).toString());
		//jobj.element("href", JSONArray.fromObject(href).toString());

		model.addAttribute("reasons", jobj.toString());
		
		
		return "PileStatus.jsp";
	}
	
	

}
