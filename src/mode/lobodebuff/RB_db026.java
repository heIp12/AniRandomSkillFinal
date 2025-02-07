package mode.lobodebuff;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Nodie;
import buff.PowerUp;
import buff.Silence;
import manager.Bgm;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Text;

public class RB_db026 extends LoboBuffBase{
	int delay = 10;
	public RB_db026() {
		id = 26;
		debuff = true;

		delay = 1;
		if(AMath.random(10) <= 1) {
			getlobo().nobgmList.add("m7");
			delay(()->{Bgm.setBgm("m7");},10);
		} else {
			getlobo().nobgmList.add("m6");
			delay(()->{Bgm.setBgm("m6");},10);
		}
	}
	
	@Override
	public void onTime(int time) {
		if(delay > 0) {
			delay--;
			return;
		}
		if(Bgm.bgmNameCode.equals("m7")) {
			for(Player p : Rule.c.keySet()) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0.5);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.1);
				power.setTime(40);
			}
			for(LivingEntity p : getlobo().mobs) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0.5);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.2);
				power.setTime(40);
				if(AMath.random(5) <= 1 && !Rule.buffmanager.isBuff(p, "nodie")) ARSystem.giveBuff(p, new Nodie(p), 40);
			}
			for(LivingEntity p : getlobo().etcmobs) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0.4);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.15);
				power.setTime(40);
				if(AMath.random(5) <= 1 && !Rule.buffmanager.isBuff(p, "nodie")) ARSystem.giveBuff(p, new Nodie(p), 40);
			}
		} else if(Bgm.bgmNameCode.equals("m6")) {
			for(Player p : Rule.c.keySet()) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.05);
				power.setTime(40);
			}
			for(LivingEntity p : getlobo().mobs) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0.3);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.1);
				power.setTime(40);
			}
			for(LivingEntity p : getlobo().etcmobs) {
				if(!Rule.buffmanager.isBuff(p, "powerup")) ARSystem.giveBuff(p, new PowerUp(p), 40, 0.2);
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.08);
				power.setTime(40);
			}
		} else {
			List<String> list = getlobo().nobgmList;
			if(list.contains("m7")) {
				list.remove("m7");
			}
			if(list.contains("m6")) {
				list.remove("m6");
			}
			getlobo().text(393,getName() + Text.get("lobo:ot2"));
			delay(()->{
				getlobo().debuff.remove(this);
			},5);
		}
	}
	
	@Override
	public void onRemove() {
		List<String> list = getlobo().nobgmList;
		if(list.contains("m7")) {
			list.remove("m7");
		}
		if(list.contains("m6")) {
			list.remove("m6");
		}
		delay(()->{
			getlobo().debuff.remove(this);
		},5);
	}
}
