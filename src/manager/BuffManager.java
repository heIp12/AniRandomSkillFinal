package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PlusHp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.JSONMessage;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import net.minecraft.server.v1_12_R1.PacketPlayInRecipeDisplayed;
import types.BuffType;


public class BuffManager {
	HashMap<LivingEntity,EntityBuffManager> buff;
	
	public BuffManager(){
		buff = new HashMap<LivingEntity,EntityBuffManager>();
	}
	
	public EntityBuffManager getBuffs(LivingEntity e) {
		return buff.get(e);
	}
	
	public HashMap<LivingEntity, EntityBuffManager> getHashMap() {
		return buff;
	}
	
	public void clear() {
		for(EntityBuffManager e : buff.values()) {
			e.clear();
		}
		buff.clear();
	}
	
	public void run() {
		for(LivingEntity e : buff.keySet()) {
			if(buff.get(e) != null) buff.get(e).run();
		}
	}
	
	public Set<LivingEntity> getTarget() {
		if(buff == null) {
			return null;
		}
		return buff.keySet();
	}
	
	public List<Buff> selectBuffType(LivingEntity target, BuffType bufftype) {
		List<Buff> bf = new ArrayList<Buff>();
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs.istype(bufftype)) {
					bf.add(buffs);
				}
			}
		}
		return bf;
	}
	
	public Buff selectBuff(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null && buffs.getName() != null && buffs.getName().toUpperCase().equals(name.toUpperCase())) {
					return buffs;
				}
			}
		}
		return null;
	}
	public void selectBuffTime(LivingEntity target, String name,int time) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
					buffs.setTime(time);
				}
			}
		}
	}
	public void selectBuffValue(LivingEntity target, String name,float value) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
					buffs.setValue(value);
				}
			}
		}
	}
	public void selectBuffAddTime(LivingEntity target, String name,int time) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
					buffs.addTime(time);
				}
			}
		}
	}
	public void selectBuffAddValue(LivingEntity target, String name,float value) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
					buffs.addValue(value);
				}
			}
		}
	}
	public boolean isBuff(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null) {
					if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean OnBuffValue(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null) {
					if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
						if(buffs.getValue() > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public boolean OnBuffTime(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null) {
					if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
						if(buffs.getTime() > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public double GetBuffValue(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null) {
					if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
						return buffs.getValue();
					}
				}
			}
		}
		return 0;
	}
	public int GetBuffTime(LivingEntity target, String name) {
		if(buff.get(target) != null && buff.get(target).getBuff() != null) {
			for(Buff buffs : buff.get(target).getBuff()) {
				if(buffs != null) {
					if(buffs.getName().toUpperCase().equals(name.toUpperCase())) {
						return buffs.getTime();
					}
				}
			}
		}
		return 0;
	}
	public Buff buffs(LivingEntity e ,String name) {
		Buff buff = null;
		if(name.equals("airborne")) buff = new Airborne(e);
		if(name.equals("barrier")) buff = new Barrier(e);
		if(name.equals("noattack")) buff = new Noattack(e);
		if(name.equals("nodamage")) buff = new Nodamage(e);
		if(name.equals("panic")) buff = new Panic(e);
		if(name.equals("plushp")) buff = new PlusHp(e);
		if(name.equals("silence")) buff = new Silence(e);
		if(name.equals("stun")) buff = new Stun(e);
		if(name.equals("timestop")) buff = new TimeStop(e);
		
		return buff;
	}
	public Buff buffs(LivingEntity e ,String name,int tick) {
		Buff buff = buffs(e,name);
		if(buff != null) buff.setTime(tick);
		return buff;
	}
	public Buff buffs(LivingEntity e ,String name,int tick,int value) {
		Buff buff = buffs(e,name,tick);
		if(buff != null) buff.setValue(value);
		return buff;
	}
}

