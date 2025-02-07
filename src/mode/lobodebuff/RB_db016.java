package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import types.MobBuffs;
import util.AMath;
import util.Map;
import util.Text;

public class RB_db016 extends LoboBuffBase{
	public RB_db016() {
		id = 16;
		debuff = true;
	}

	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			p.setCustomName(Text.get("lobo:o16-1"));
			p.setDisplayName(Text.get("lobo:o16-1"));
			p.setPlayerListName(Text.get("lobo:o16-1"));
			Rule.team.teamQuit("H", p);
		}
	}
	
	@Override
	public void onPlayerChar(Player p) {
	}
	
	@Override
	public LivingEntity onEntityCreate(String name, int size) {
		LivingEntity en = Map.spawnMM(name, Map.randomLoc());
		getlobo().addMob(en);
		en.setCustomName(Text.get("lobo:o16-1"));
		en.setMaxHealth(AMath.round(en.getMaxHealth()* (AMath.random(13)*0.1+0.7),0));
		en.setHealth(en.getMaxHealth());
		int rd = getlobo().level*3;
		rd *= (getlobo().difficulty-5);
		if(AMath.random(200) <= rd) MobBuffs.get("lbhp", en);
		if(AMath.random(250) <= rd) MobBuffs.get("lbmaxhp", en);
		if(AMath.random(400) <= rd) MobBuffs.get("lbdefence", en);
		delay(()->{
			if(en != null) {
				en.setCustomName(Text.get("lobo:o16-1"));
			}
		},10);
		return en;
	}
	
	@Override
	public void onRemove() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setCustomName(p.getName());
			p.setDisplayName(p.getName());
			p.setPlayerListName(p.getName());
			Rule.team.teamJoin("H", p);
		}
	}
}
