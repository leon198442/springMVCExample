package cn.com.titans.tcsmp.controller;

import java.io.UnsupportedEncodingException;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.titans.tcsmp.entity.ChargePile;
import cn.com.titans.tcsmp.entity.ChargeStation;
import cn.com.titans.tcsmp.service.ChargePileService;
import cn.com.titans.tcsmp.service.ChargeStationService;
import cn.com.titans.tcsmp.utils.Page;
import cn.com.titans.tcsmp.utils.StringUtils;


@Controller
public class ChargeStationController {
	@Autowired
	private ChargeStationService chargeStationService;
	@Autowired
	private ChargePileService chargePileService;
	
	@RequestMapping(value = "StationsList.do")
	public String stationsList_jsp(HttpServletRequest request, Model model) {
		Page<ChargeStation> page = new Page<ChargeStation>();

		page.setParameters(request.getParameterMap());

		page = chargeStationService.findPageWithParameters(page);
		model.addAttribute("pages", page);

		return "StationsList.jsp";
	}
	
	@RequestMapping(value = "StationLookUp")
	public String stationLookUp_jsp(HttpServletRequest request,Model model) {
		Page<ChargeStation> page = new Page<ChargeStation>();
		
		page.setParameters(request.getParameterMap());

		page = chargeStationService.findPageWithParameters(page);
		model.addAttribute("pages", page);

		return "StationLookUp.jsp";
	}
	
	@RequestMapping(value = "StationLookUp.search")
	public String stationLookUpSearch(HttpServletRequest request, Model model) {
		return stationLookUp_jsp(request,model);
	}
	
	@RequestMapping(value = "StationDelete.do")
	public String stationDelete(HttpServletRequest request) {
		String id = request.getParameter("sid");

		if (StringUtils.isEmpty(id)) {
			return "Done";
		}
		chargeStationService.deleteById(Long.decode(id));

		return "Done.jsp";
	}
	
	@RequestMapping(value = "StationUpdate.do")
	public String stationUpdate_jsp(HttpServletRequest request, Model model) {
		String id = request.getParameter("sid");
		if (!StringUtils.isEmpty(id)) {
			model.addAttribute("station", chargeStationService.findOne(Long.decode(id)));
		}
		return "StationUpdate.jsp";
	}
	

	@RequestMapping(value = "StationSave.do")
	public String stationSave(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		String pid = request.getParameter("id");
		ChargeStation cs = null;
		if (!StringUtils.isEmpty(pid)) {
			cs = chargeStationService.findOne(Long.decode(pid));
			
		}else{
			cs = new ChargeStation();
			cs.setStatus("3");
		}
		cs.setFullName( request.getParameter("name"));
		cs.setAddress( request.getParameter("address"));
		
		chargeStationService.save(cs);
		
		int i =0;
		while(true){
			
			String name = request.getParameter("items["+i+"].name");
			i++;
			if (StringUtils.isEmpty(name)){
				break;
			}else{
				ChargePile cp = new ChargePile();
				cp.setFullName(name);
				cp.setChargeStation(cs);
				cp.setStatus("3");
				chargePileService.save(cp);
			}
			
		}
		

		
		return "DoneClose.jsp";
	}


	
	
}
