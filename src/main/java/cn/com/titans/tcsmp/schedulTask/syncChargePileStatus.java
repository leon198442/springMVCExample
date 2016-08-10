package cn.com.titans.tcsmp.schedulTask;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.titans.tcsmp.entity.ChargePile;
import cn.com.titans.tcsmp.entity.ChargePileInfo;
import cn.com.titans.tcsmp.entity.ChargePileStatus;
import cn.com.titans.tcsmp.service.ChargePileInfoService;
import cn.com.titans.tcsmp.service.ChargePileService;
import cn.com.titans.tcsmp.service.ChargePileStatusService;

@Component("syncChargePileStatus")
public class syncChargePileStatus {
	@Autowired
	private ChargePileService chargePileService;
	@Autowired
	private ChargePileInfoService chargePileInfoService;

	@Autowired
	private ChargePileStatusService chargePileStatusService;

	// @Scheduled(cron = "0 * * * * ?") // trigger every minute
	//
	// public void updateChargePileStatus() {
	//
	// chargePileService.updateChargePileStatus();
	//
	//
	//
	// }

	@Scheduled(cron = "0/15 * * * * ?") // trigger every 15sec

	public void updateChargePileInfo() {

		ChargePileInfo cpi = new ChargePileInfo();

		Random a = new Random();
		ChargePile cp = chargePileService.findOne(a.nextInt(100));

		if (cp != null) {
			cpi.setChargePile(cp);
			if (cp.getStatus().compareToIgnoreCase("1") != 0) {
				cpi.setChargedAmount(0);
				cpi.setCurrent(0);
				cpi.setVoltage(0);
			} else {
				
				float f = (float) (a.nextInt(100) * 0.01);

				cpi.setChargedAmount(a.nextInt(20) + f);

				
				f = (float) (a.nextInt(100) * 0.01);
				cpi.setCurrent(10 + a.nextInt(4) + f);
				f = (float) (a.nextInt(100) * 0.01);
				cpi.setVoltage(200 + a.nextInt(20) + f);
			}
			cpi.setChargeStartTime(new Date());
			chargePileInfoService.save(cpi);
		}

	}

	@Scheduled(cron = "0 * * * * ?") // trigger every 15sec

	public void updateChargePileStatus() {

		ChargePileStatus cps = new ChargePileStatus();

		Random a = new Random();
		ChargePile cp = chargePileService.findOne(a.nextInt(100));

		if (cp != null) {

			cps.setChargePile(cp);
			cps.setStatus(String.valueOf(a.nextInt(4)));
			if (cps.getStatus().compareToIgnoreCase("0") == 0) {
				if (a.nextBoolean()) {
					cps.setReasonCode(String.valueOf((101 + a.nextInt(3))));
				} else {
					cps.setReasonCode(String.valueOf((201 + a.nextInt(8))));
				}
			}

			chargePileStatusService.save(cps);
		}

	}

}
