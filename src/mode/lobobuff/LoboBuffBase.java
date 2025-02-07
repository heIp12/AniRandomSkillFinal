package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.MLoboTomy;
import mode.ModeBase;
import util.Text;

public class LoboBuffBase {
	MLoboTomy lobo;
	protected String name;
	protected boolean debuff = false;
	protected int lv;
	protected int id = 0;
	public LoboBuffBase(){
		id = 0;
	}
	public void onPlayerChar(Player p) {}
	public void onPlayerDie(Player p) {}
	public LivingEntity onEntityCreate(String name, int size) { return null; }
	public void onEntitySpawn(LivingEntity en) {}
	public void onEntityDie(LivingEntity en,LivingEntity killer) {}
	public void onNextLevel() {}
	public void onNextStage() {}
	public void onTime(int time) {}
	public void onTick() {}
	public void onRemove() {}
	
	public int getId() {return id;}
	
	public MLoboTomy getlobo() {
		if(lobo != null) return lobo;
		for(ModeBase m : ARSystem.AniRandomSkill.modes) {
			if(m instanceof MLoboTomy) {
				lobo = (MLoboTomy)m;
				return lobo;
			}
		}
		return null;
	}
	
	protected int stageTime() {
		if(ARSystem.AniRandomSkill != null) return ARSystem.AniRandomSkill.getTime();
		return 0;
	}
	
	public String getName() {
		if(name == null) {
			if(debuff) {
				lv = Text.getI("lobo:o"+id+"v");
				name = Text.get("lobo:lv"+lv)+ Text.get("lobo:o"+id);
			} else {
				lv = Text.getI("lobo:b"+id+"v");
				name = Text.get("lobo:lv"+lv)+ Text.get("lobo:b"+id);	
			}
		}
		return name;
	}
	
	public void delay(Runnable run,int time) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, run, time);
	}
}
