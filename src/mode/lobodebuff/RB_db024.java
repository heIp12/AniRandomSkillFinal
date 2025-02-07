package mode.lobodebuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import mode.lobobuff.LoboBuffBase;
import types.TargetMap;
import types.box;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db024 extends LoboBuffBase{
	public List<LivingEntity> love = new ArrayList<>();
	List<Entity> removes = new ArrayList<Entity>();
	List<Entity> loveadd = new ArrayList<Entity>();
	TargetMap<LivingEntity, Double> damage = new TargetMap<>();
	
	public RB_db024() {
		id = 24;
		debuff = true;
	}
	
	@Override
	public void onNextStage() {
		love.clear();
	}
	
	@Override
	public void onTime(int time) {
		if(time%3 == 0) {
			
			if(love.size() <= 3) {
				Entity e = getlobo().vilager.get(AMath.random(getlobo().vilager.size())-1);
				int i = 0;
				while(love.contains(e)) {
					if(AMath.random(3) <= 2) {
						e = getlobo().vilager.get(AMath.random(getlobo().vilager.size())-1);
					} else if(AMath.random(3) <= 2) {
						e = getlobo().mobs.get(AMath.random(getlobo().mobs.size())-1);
					} else {
						e = ARSystem.RandomPlayer();
					}
					i++;
					if(i > 1000) break;
				}
				love.add((LivingEntity)e);
			}
			
			for(Entity e : loveadd) if(!love.contains(e)) love.add((LivingEntity)e);
			loveadd.clear();
			
			for(LivingEntity e : love) {
				if(getlobo().vilager.contains(e)) {
					if(AMath.random(10) <= 1) {
						Entity t = ARSystem.boxSOne(e, new Vector(30, 8, 30), box.TARGET);
						if(t != null) ARSystem.giveBuff(e, new Fascination(e, (LivingEntity)t), 40, 1);
					}
				}
				ARSystem.playSound(e, "slimegirl",1 ,0.1f);
				if(love.size() < 20) ARSystem.spellCast(NpcPlayer.npc(Map.randomLoc()), e, "lobo_slime");
				else ARSystem.spellCast(NpcPlayer.npc(Map.randomLoc()), e, "lobo_slime3");
				for(Entity en : ARSystem.box(e, new Vector(5,5,5), box.ALL)) {
					if(!love.contains(en) && AMath.random(10) <= 2) {
						loveadd.add((LivingEntity)en);
					}
					if(damage.get((LivingEntity)en) == null) damage.add((LivingEntity)en,0);
					if(damage.get((LivingEntity)en) <= 0) {
						damage.add((LivingEntity)en, 3);
						((LivingEntity)en).damage(5 + getlobo().cr.size()/2, NpcPlayer.npc(e.getLocation()));	
					}
				}
			}
			
			boolean all = true;
			for(Player p : Rule.c.keySet()) {
				if(!love.contains(p)) {
					all = false;
				}
			}
			if(all && getlobo().cr.size() >= 3) {
				ARSystem.playSoundAll("slimegirl");
				for(Player p : Rule.c.keySet()) {
					for(int i =0; i< 20; i++) {
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
							ARSystem.spellCast(p ,p, "lobo_slime");
							Rule.c.get(p).hpCost(0.5, false);
						},i);
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
						ARSystem.Death(p, NpcPlayer.npc(p.getLocation()));
					},20);
				}
			}
		}
		for(Entity e : removes) {
			love.remove(e);
		}
		damage.addAll(-1);
		damage.removes();
		removes.clear();
	}
	
	@Override
	public void onEntityDie(LivingEntity en, LivingEntity killer) {
		if(MythicMobs.inst().getMobManager().isActiveMob(en.getUniqueId())) {
			ActiveMob ams = MythicMobs.inst().getMobManager().getActiveMob(en.getUniqueId()).get();
			String targetType = ams.getType().getInternalName();
			if(targetType.contains("heart")) {
				ARSystem.playSound(en, "slimegirl", 0.5f ,1f);
				ARSystem.spellCast(NpcPlayer.npc(Map.randomLoc()), en, "lobo_slime");
				for(Entity ens : ARSystem.box(en, new Vector(8,8,8), box.ALL)) {
					if(!love.contains(ens)) {
						loveadd.add((LivingEntity)ens);
					}
				}
			} else {
				if(love.contains(en)) {
					ARSystem.playSound(en, "slimespawn");
					LivingEntity entity = Map.spawnMM("heart", en.getLocation());
					getlobo().addMob(entity);
					loveadd.add(entity);
					if(en.getCustomName() != null) entity.setCustomName(en.getCustomName());
					else entity.setCustomName(en.getName());
					
					removes.add(en);
				}
			}
		} else {
			if(love.contains(en)) {
				ARSystem.playSound(en, "slimespawn");
				LivingEntity entity = Map.spawnMM("heart", en.getLocation());
				getlobo().addMob(entity);
				loveadd.add(entity);
				if(en.getCustomName() != null) entity.setCustomName(en.getCustomName());
				else entity.setCustomName(en.getName());
				removes.add(en);
			}
		}
	}
}
