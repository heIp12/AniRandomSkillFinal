package mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.NpcPlayer;
import armorstand.BaseArmorStand;
import ars.ARSystem;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import util.AMath;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;

public class MobMMBase extends MobBase{
	protected Player caster;
	
	public MobMMBase(LivingEntity mob){
		super(mob);
	}
	
	public void signal(String name,int time) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(a.castPlayer == NpcPlayer.npc(entity.getLocation()) && a.caster.getUniqueId().equals(entity.getUniqueId())) {
				a.sign.put(name,time);
			}
		}
	}
	public void signalPart(String name,int time) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(a.castPlayer == NpcPlayer.npc(entity.getLocation()) && a.caster.getUniqueId().equals(entity.getUniqueId())) {
				a.sign.put(name,time);
				parts(a,name,time);
			}
		}
	}
	void parts(BaseArmorStand b,String name,int time){
		if(b.parts != null && !b.parts.isEmpty()) {
			for(BaseArmorStand parts : b.parts) {
				parts.sign.put(name,time);
				parts(parts,name,time);
			}
		}
	}
	public void addparts(String name) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(a.castPlayer == NpcPlayer.npc(entity.getLocation()) && a.caster.getUniqueId().equals(entity.getUniqueId())) {
				a.addParts(name);
			}
		}
	}
	public void removeparts(String name) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(a.castPlayer == NpcPlayer.npc(entity.getLocation()) && a.caster.getUniqueId().equals(entity.getUniqueId())) {
				a.removeParts(name);
			}
		}
	}
	
	public void setTarget(LivingEntity target) {
		if(entity!= null && mmm().isActiveMob(entity.getUniqueId())) {
			ActiveMob mob = mmm().getActiveMob(entity.getUniqueId()).get();
			mob.resetTarget();
			mob.setTarget(BukkitAdapter.adapt(target));
		}
	}
	
	
	public List<LivingEntity> box(Entity et, Vector vt,types.box box) {
		List<LivingEntity> entity = new ArrayList<LivingEntity>();
		for(Entity e : et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ())) {
			if(e instanceof LivingEntity && !(e instanceof ArmorStand)) {
				entity.add((LivingEntity)e);
			}
		}

		if(entity == null || entity.size() <= 0) return entity;
		
		List<LivingEntity> en = new ArrayList<LivingEntity>();
		
		String faction = "";
		if(mmm().isActiveMob(this.entity.getUniqueId())) {
			ActiveMob am = mmm().getActiveMob(this.entity.getUniqueId()).get();
			if(am.hasFaction()) {
				faction = am.getFaction();
			} else {
				faction = "none";
			}
		}
		for(LivingEntity e : entity) {
			if(!ARSystem.isTarget(e, et, box)) {
				en.add(e);
			}
			if(e instanceof Player && ((Player) e).getGameMode() != GameMode.ADVENTURE) {
				en.add(e);
			}
			if(box == types.box.PLAYER) {
				if(!(e instanceof Player)) {
					en.add(e);
				}
			}
			if(box == types.box.TARGET) {
				if(mmm().isActiveMob(e.getUniqueId()) && faction.equals(mmm().getActiveMob(e.getUniqueId()).get().getFaction())) {
					en.add(e);
				}
			}
			if(box == types.box.TEAM) {
				if(!mmm().isActiveMob(e.getUniqueId()) && faction.equals(mmm().getActiveMob(e.getUniqueId()).get().getFaction())) {
					en.add(e);
				}
			}
		} 
		for(LivingEntity e : en) {
			entity.remove(e);
		}
		return entity;
	}
	 public LivingEntity boxRandom(Entity et, Vector vt,types.box box) {
			List<LivingEntity> entity = box(et, vt, box);
			if(entity == null || entity.size() < 1) return null;
			return entity.get(AMath.random(entity.size())-1);
		}
	 
	public List<LivingEntity> boxS(Entity et, Vector vt,types.box box) {
		List<LivingEntity> entity = box(et,vt,box);
		LivingEntity[] p = new LivingEntity[entity.size()];
		for(int i =0; i < p.length; i++) p[i] = entity.get(i);
		
		if(p.length == 0) return new ArrayList<LivingEntity>();
		if(p[0] == null) return new ArrayList<LivingEntity>();
		if(entity.size() == 1) return entity;
		
		for(int i = 0; i < entity.size(); i++) {
			for(int j = 0; j < i; j++) {
				if(p[i].getLocation().distance(et.getLocation()) < p[j].getLocation().distance(et.getLocation())) {
					LivingEntity ps;
					ps = p[i];
					p[i] = p[j];
					p[j] = ps;
				}
			}
		}
		
		entity.clear();
		for(LivingEntity e : p) entity.add(e);
		return entity;
	}
	
	public LivingEntity boxSOne(Entity et, Vector vt,types.box box) {
		List<LivingEntity> ens = boxS(et,vt,box);
		if(ens != null && ens.size() >= 1) {
			return ens.get(0);
		}
		return null;
	}
	
	public MobManager mmm(){
		return MythicMobs.inst().getMobManager();
	}
	
}
