package cn.com.titans.tcsmp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
public class ChargePileController {

	@Autowired
	private ChargePileService chargePileService;
	@Autowired
	private ChargeStationService chargeStationService;



	@RequestMapping(value = "PilesList.do")
	public String pileList_jsp(HttpServletRequest request, Model model) {
		Page<ChargePile> page = new Page<ChargePile>();

		page.setParameters(request.getParameterMap());

		page = chargePileService.findPageWithParameters(page);
		model.addAttribute("pages", page);
		
		List<ChargeStation> cslist = chargeStationService.findAll();
		
		model.addAttribute("cslist", cslist);

		return "PilesList.jsp";
	}

	@RequestMapping(value = "PileUpdate.do")
	public String pileUpdate_jsp(HttpServletRequest request, Model model) {
		String id = request.getParameter("pid");
		if (!StringUtils.isEmpty(id)) {
			model.addAttribute("pile", chargePileService.findOne(Long.decode(id)));
		}
		return "PileUpdate.jsp";
	}
	
	@RequestMapping(value = "PileSave.do")
	public String pileSave(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		String pid = request.getParameter("id");
		ChargePile cp = null;
		if (!StringUtils.isEmpty(pid)) {
			cp = chargePileService.findOne(Long.decode(pid));
			
		}else{
			cp = new ChargePile();
			cp.setStatus("3");
		}
		String id = request.getParameter("ChargeStation.id");
		if (!StringUtils.isEmpty(id)) {
			ChargeStation cs = chargeStationService.findOne(Long.decode(id));
			cp.setChargeStation(cs);
		}
		
		cp.setFullName( request.getParameter("name"));
		
		
		
		chargePileService.save(cp);
		
		return "DoneClose.jsp";
	}

	@RequestMapping(value = "PileDelete.do")
	public String pileDelete(HttpServletRequest request) {
		String id = request.getParameter("pid");

		if (StringUtils.isEmpty(id)) {
			return "Done.jsp";
		}
		chargePileService.deleteById(Long.decode(id));

		return "Done.jsp";
	}

}
