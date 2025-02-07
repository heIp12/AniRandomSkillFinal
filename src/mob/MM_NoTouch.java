package mob;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.Barrier;
import buff.Bload;
import buff.Fascination;
import buff.Follow;
import buff.NoCC;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import event.Skill;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import manager.Bgm;
import mode.MLoboTomy;
import mode.ModeBase;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class MM_NoTouch extends MobMMBase {
	String last = "noclick_e";
	int i = 0;
	public MM_NoTouch(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		if(AMath.random(200) <= 1) {
			int rep = AMath.random(100);
			String next = last;
			if(rep <= 50) {
				last = "noclick_e";
				Rule.buffmanager.selectBuffTime(entity, "follow", 0);
			} else if(rep <= 55) {
				last = "human";
			} else if(rep <= 57) {
				last = "sado";
			} else if(rep <= 59) {
				last = "nul";
			} else if(rep <= 61) {
				last = "magic1";
			} else if(rep <= 66) {
				if(AMath.random(2) <= 1) {
					last = "food2";
				} else {
					last = "food";
				}
			} else if(rep <= 70) {
				last = "bunny";
			} else if(rep <= 75) {
				last = "dango";
			} else if(rep <= 80) {
				last = "bard";
			} else if(rep <= 85) {
				last = "sn";
			} else if(rep <= 90) {
				last = "nabi";
			} else {
				last = "noclick_e";
				//작은새
			}
			if(!next.equals(last)) {
				removeparts(next);
				addparts(last);
			}
		}
		if(!last.equals("noclick_e")) {
			if(AMath.random(100) <= 3) {
				Entity p = ARSystem.boxSPlayerOne(entity, new Vector(30,30,30), box.ALL);
				if(p != null) {
					ARSystem.giveBuff(entity, new Follow(caster, (LivingEntity)p), 60, 0.3);
				}
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(Rule.c.get(attaker) != null && isCooldown("1", 5, false)) {
			i++;
			Player p = (Player)attaker;
			ARSystem.playSoundAll("touchout");
			entity.teleport(Map.randomLoc());
			if(i == 1) {
				if(ARSystem.isGameMode("lobotomy")) for(int i =0; i<2; i++) getlobo().adddebuff();
				ARSystem.playSoundAll("touchout1");
				ARSystem.addBuff(p, new Panic(p), 200);
				for(Player pl : Rule.c.keySet()) {
					Rule.c.get(pl).hpCost(20, false);
				}
				for(int i = 0; i< 10; i++) Rule.c.get(p).cooldown[i] += 20;
			}
			if(i == 2) {
				ARSystem.playSoundAll("touchout2");
				if(ARSystem.isGameMode("lobotomy")) for(int i =0; i<20; i++) getlobo().adddebuff();
				if(ARSystem.isGameMode("lobotomy") && AMath.random(10) <= 5){
					int level = getlobo().level;
					int count = getlobo().count;
					String txt = Text.get("lobo:"+level+"-"+(count-1));
					if(txt == null) txt = Text.get("lobo:"+level+"-"+count);
					for(String s : txt.split(",")) {
						String[] mob = s.split(":");
						getlobo().spawn(mob[0] , Integer.parseInt(mob[1])*2,true);
					}
					for(Player pl : Rule.c.keySet()) {
						Rule.c.get(pl).hpCost(5, false);
						ARSystem.addBuff(pl, new Panic(pl), 100);
					}
					for(int i = 0; i< 10; i++) Rule.c.get(p).cooldown[i] += 20;
				} else {
					Rule.c.put(p, new c000humen(p,Rule.gamerule,null));
					for(Player pl : Rule.c.keySet()) {
						ARSystem.addBuff(pl, new Panic(pl), 400);	
						pl.setHealth(1);
						for(int i = 0; i< 10; i++) Rule.c.get(p).cooldown[i] += 10;
					}
				}
			}
			if(i == 3) {
				ARSystem.playSoundAll("touchout3");
				delay(()->{
					ARSystem.GameStop();
				},100);
			}
		}
	}
	
	MLoboTomy lobo = null;
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
}
