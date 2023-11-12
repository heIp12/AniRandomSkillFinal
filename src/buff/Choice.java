package buff;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import chars.c.c000humen;
import chars.c2.c61tyana;
import chars.c2.c90arabe;
import event.Skill;
import event.WinEvent;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;

public class Choice extends Buff{
	Player caster;
	int select = 6;
	int lv = 0;
	int type = 1;
	int type2 = 1;
	int maxselect = 0;
	boolean selects = false;
	double count = 0;
	Location start_loc;
	
	public Choice(Player player, int i) {
		super(player);
		caster = player;
		lv = i;
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);

		buffName = "choice";
		onlyone = true;
		color = "§a";
		isScore = true;
		select = 4+(i*3);
		if(select >= 22) {
			select = 21;
			if(Rule.c.get(player) instanceof c90arabe) {
				select = 22;
			}
		}
		
		while(type < AMath.random(i*4) || type2 < AMath.random(i*4)) {
			type = AMath.random(select);
			type2 = AMath.random(select);
			while(type2 == type) type2 = AMath.random(select);
		}
		count = lv*3+AMath.random(lv*2);
		
		ARSystem.playSound(player, "c90a"+AMath.random(5));
		ARSystem.addBuff(player, new TimeStop(player), 60);
		player.sendTitle(Main.GetText("c90:t0"), 
						 count(type)+Main.GetText("c90:t"+type) + " | "+ 
						 count(type2)+Main.GetText("c90:t"+type2));
		start_loc = player.getLocation();
		if(Rule.c.get(player) != null && Rule.c.get(player) instanceof c61tyana) {
			ARSystem.playSound((Entity)player, "c61kami");
		}
	}
	
	public double count(int type) {
		double value = count;
		if(type == 1) value*=  2;
		if(type == 2) value*=  0.3;
		if(type == 3) value*=  0.5;
		if(type == 4) value*=  20;
		if(type == 5) value*=  0.3;
		if(type == 6) value*=  0.3;
		if(type == 7) value*=  0.2;
		if(type == 8) value*=  0.3;
		if(type == 9) value*=  0.2;
		if(type == 11) value*= 0.3;
		if(type == 12) value*= 0.2;
		if(type == 13) value=  1;
		if(type == 14) value*= 0.1;
		if(type == 15) value*= 0.5;
		if(type == 16) value*= 0.3;
		if(type == 17) value*= 0.3;
		if(type == 18) value*= 0.2;
		if(type == 19) value= 1;
		if(type == 20) value= 1;
		if(type == 21) value= 1;
		if(type == 22) value= 1;
		value = AMath.round(value, 1);
		return value;
	}
	
	public boolean onTicks() {
		if(selects == false) {
			if(AMath.random(2)==1) type = type2;
			value = count(type);
			caster.sendTitle(Main.GetText("c90:ps"), value+Main.GetText("c90:t"+type));
			selects = true;
		}
		if(type == 10) {
			for(int i=0;i<10;i++)Rule.c.get(caster).cooldown[i] = (float) value;
			value = 0;
			stop();
		}
		if(type == 13) {
			caster.teleport(ARSystem.RandomPlayer());
			value = 0;
			stop();
		}
		if(type == 15) {
			caster.damage(value,caster);
			value = 0;
			stop();
		}
		if(type == 16) {
			ARSystem.giveBuff(caster, new Stun(caster), (int) (value*20));
			value = 0;
			type = 0;
			stop();
		}
		if(type == 17) {
			ARSystem.giveBuff(caster, new Rampage(caster), (int) (value*20));
			type = 0;
			value = 0;
			stop();
		}
		if(type == 18) {
			if(caster.getMaxHealth() - value > 1) {
				caster.setMaxHealth(caster.getMaxHealth() - value);
				value = 0;
			}
			stop();
		}
		if(type == 20) {
			value = 0;
			Rule.c.put(caster, GetChar.get(caster, Rule.gamerule, ""+AMath.random(GetChar.getCount())));
			stop();
		}
		if(type == 21) {
			value = 0;
			Rule.c.put(caster, new c000humen(caster, Rule.gamerule, null));
			stop();
		}
		
		if(type == 22) {
			value = 0;
			WinEvent event = new WinEvent(caster);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()) {
				Rule.playerinfo.get(caster).tropy(90,1);
				for(Player p : Rule.c.keySet()) {
					if(!(Rule.c.get(p) instanceof c90arabe)) {
						Rule.c.put(p, new c000humen(p, Rule.gamerule, null));
						Skill.remove(p, p);
					}
				}
				stop();
			}
		}
		if(value <= 0) {
			stop();
		}
		if(type == 3) {
			if(caster.isSneaking()) {
				caster.setSneaking(false);
				value-=1;
			}
		}
		if(type == 6 && tick%5 == 0) {
			if(caster.getLocation().getPitch() > 80) {
				value-=0.25;
			}
		}
		if(type == 7 && tick%2 == 0) {
			value-=0.1;
			caster.setVelocity(new Vector(0,0.3,0));
		}
		if(type == 8) {
			value-=0.1;
			Location loc = caster.getLocation();
			loc.setYaw(loc.getYaw()+36);
			caster.teleport(loc);
		}
		if(type == 9 && tick%2 == 0) {
			value-=0.1;
			caster.setVelocity(start_loc.getDirection().multiply(0.4));
		}
		if(type == 11 && tick%2 == 0 && caster.isOnGround()) {
			value-=1;
			caster.setVelocity(new Vector(0,0.5,0));
		}
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(e.getDamager() == caster) {
			if(type == 12) value-=1;
		}
		return true;
	}
	
	@Override
	public void last() {
		if(value > 0 && value > -100) {
			value = -1000;
			if(Rule.c.get(caster) != null) {
				Skill.remove(caster, caster);
			}
		}
		if(type == 8) ARSystem.playSound((Entity)caster, "h");
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(selects == false) {
			if(e.getNewSlot() == 0) {
				value = count(type);
				caster.sendTitle(Main.GetText("c90:ps"), value+Main.GetText("c90:t"+type));
				ARSystem.giveBuff(caster, new TimeStop(caster), 0);
				selects = true;
			}
			if(e.getNewSlot() == 1) {
				type = type2;
				value = count(type);
				caster.sendTitle(Main.GetText("c90:ps"), value+Main.GetText("c90:t"+type));
				ARSystem.giveBuff(caster, new TimeStop(caster), 0);
				selects = true;
			}
		}
		if(e.getNewSlot() == 8) {
			if(Rule.c.get(e.getPlayer()).cooldown[9] <= 0){
				if(type == 2) {
					value-=1;
				}
				if(type == 14 && ARSystem.boxSOne(caster, new Vector(12,12,12), box.TARGET) != null) {
					value-=1;
				}
			}
		}
		if(e.getNewSlot() == 6) {
			if(caster.isSneaking() && type==19){
				value = 0;
			}
		}
		return true;
	}
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(type == 1) {
			value -= e.getFrom().distance(e.getTo())/2;
		}
		if(type == 4) {
			value -= Math.abs(e.getFrom().getYaw() - e.getTo().getYaw());
		}
		if(type == 5) {
			double pm = e.getFrom().getY() - e.getTo().getY();
			if(pm > 0) value -= pm/2;
		}
		return super.onMove(e);
	}
}
