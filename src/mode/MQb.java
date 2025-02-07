package mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MQb extends ModeBase{
	int t = 0;
	
	public MQb(){
		super();
		modeName = "qb";
		disPlayName = "Qb Event";
		isSecret = true;
	}
	
	public void tick(int time) {
		t++;
		if(t == 1) {
			for(LivingEntity e : ARSystem.RandomPlayer().getWorld().getLivingEntities()) {
				ARSystem.giveBuff(e, new TimeStop(e), 240);
			}
			Bgm.setBgm("no");
			ARSystem.playSoundAll("item27a");
		}
		if(t > 13) {
			if(t == 13) {
				Bgm.setForceBgm("wal");
			}
			
			if((t-14)%30 == 0) {
				Location lc = Map.getCenter();
				lc.setY(ARSystem.RandomPlayer().getLocation().getY());
				ARSystem.spellLocCast(NpcPlayer.npc(lc), lc,"item27e");
			}
			if(!Bgm.bgmcode.equals("wal")) {
				Bgm.setForceBgm("wal");
			}
		}
	}
}
