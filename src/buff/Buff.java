package buff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import Main.Main;
import ars.Rule;
import types.BuffType;
import util.AMath;

public class Buff {
	public int order = 100;
	float tick = 0;
	double value;
	boolean alltime = false;
	boolean onlyone = false;
	boolean isText = true;
	boolean isScore = false;
	boolean isValueP = false;
	String buffName = "";
	String color = "§7";
	
	LivingEntity target;
	List<BuffType> bufftype = new ArrayList<BuffType>();
	
	public Buff(LivingEntity target){
		this.target = target;
		start(100000);
	}
	public Buff(LivingEntity target,int tick){
		this.target = target;
		start(tick);
	}
	public boolean OnlyOne() {
		return onlyone;
	}
	
	public boolean istype(BuffType buff) {
		if(bufftype.contains(buff)) {
			return true;
		}
		return false;
	}
	
	public String getName() { return buffName;}
	
	public void start(int tick) {
		first();
		this.tick = tick;
	}
	
	public void stop() {
		tick = 0;
		last();
		Rule.buffmanager.getBuffs(target).removeBuff(this);
		
	}
	
	public void setTime(int tick) {
		this.tick = tick;
	}
	public int getTime() {
		return (int) AMath.round(tick,0);
	}
	
	public void last() {}
	public void first() {};
	public void setValue(double value) {this.value = value;}
	public double getValue() {return value;}
	public void addTime(int time) { tick+=time; }
	public void addValue(double val) { value+=val; }
	public boolean getAllTime() {return alltime;}
	public String getText() {
		String s = color+" [" +buffName +"] :";
		if(tick > 0) {
			s += " &7"+AMath.round(tick*0.05,1)+"(s)";
		}
		if(value > 0){
			if(isValueP) {
				s += " &7"+AMath.round(value*100,0) + "%";
			} else {
				s += " &7"+AMath.round(value,2);
			}
		}
		return s;
	}
	
	public boolean onTick(){
		if(target instanceof ArmorStand) {
			stop();
		}
		else if(buffName.equals("timestop") || !Rule.buffmanager.OnBuffTime(target, "timestop")) {
			if(!alltime) {
				if(Rule.c.get(target) != null && tick%2 == 0 ) {
					if(!Rule.c.get(target).buffText.contains(color+" ["+buffName +"§f:") && isText) {
						Rule.c.get(target).buffText += color+" ["+buffName +"§f:"+ AMath.round(tick*0.05,1)+color+"]";
						if(bufftype.contains(BuffType.HEADCC)){
							Rule.c.get(target).buffHardCC = true;
						} else {
							Rule.c.get(target).buffHardCC = false;
						}
					}
				}
				if(tick > 0) tick-= 1 + Rule.buffmanager.GetBuffValue(target, "buffac");
				if(tick <=0) stop();
				onTicks();
			}
			if(alltime) {
				tick = -2000;
				onTicks();
			}
		}
		if(isScore) {
			boolean score = true;
			if(Rule.c.get(target) != null && Rule.c.get(target).buffTexts != null) {
				for(Buff b : Rule.c.get(target).buffTexts) {
					if(b == this) {
						score = false;
						break;
					}
				}
			}
			if(score && Rule.c.get(target) != null) {
				String s = getText();
				if(tick > 0 || value > 0) {
					if(Rule.c.get(target).buffTexts != this) {
						Rule.c.get(target).buffTexts.add(this);
					}
				}
			}
		}
		return true;
	}
	public boolean onTicks(){
		return true;
	}
	
	public boolean onHitNext(EntityDamageByEntityEvent e){ return true; }
	public boolean onHit(EntityDamageByEntityEvent e){ return true; }
	public boolean onAttack(EntityDamageByEntityEvent e){ return true; }
	public boolean onDeath(PlayerDeathEvent e){ return true; }
	public boolean onRemove(){ return true; }
	public boolean onSkill(PlayerItemHeldEvent e){ return true; }
	public boolean onMove(PlayerMoveEvent e){ return true; }
	
}
