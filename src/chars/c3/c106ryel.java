package chars.c3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c106ryel extends c00main{
	float mp = 6500;
	int stack = 0;
	int s1time = 0;
	Entity target;
	
	@Override
	public void setStack(float f) {
		stack = (int) f;
	}
	
	public c106ryel(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 106;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		List<Entity> entitys = ARSystem.box(player, new Vector(6,6,6), box.TARGET);
		if(entitys.size() > 0 && mp > 1200) {
			ARSystem.playSound((Entity)player, "c106s1");
			mp -=1200;
			skill("c106_s1-1");
		} else if(mp > 2000) {
			ARSystem.playSound((Entity)player, "c106s1");
			mp-=2000;
			skill("c106_s1-2");
			
		} else {
			cooldown[1] = 0;
		}
		
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			this.target = target;
			s1time = 40;
		}
	}
	@Override
	public boolean skill2() {
		if(target != null) {
			cooldown[1] = 0;
			s1time= 0;
			if(target.getLocation().distance(player.getLocation()) > 5 && mp > 600) {
				ARSystem.playSound((Entity)player, "c106s2");
				mp -=600;
				ARSystem.spellCast(player, target, "c106_move");
				skill("c106_s2-2");
			} else if(mp > 1200) {
				ARSystem.playSound((Entity)player, "c106s2");
				mp -=1200;
				skill("c106_s2-1");
			}
			target = null;
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(mp > 1200 && stack < 5) {
			mp-=1200;
			ARSystem.playSound((Entity)player, "c106s3");
			ARSystem.giveBuff(player, new Stun(player), 70);
			ARSystem.giveBuff(player, new Silence(player), 70);
			stack++;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(tk%20==0) {
			mp+= 180* (skillmult+sskillmult);
			if(mp > 6500) mp =6500;
			
			scoreBoardText.add("&c [Mp]&f : "+ mp + "/6500");
			scoreBoardText.add("&c ["+Main.GetText("c106:sk3")+ "] : &f" + stack );
			if(target != null) scoreBoardText.add("&c ["+Main.GetText("c106:sk2")+ "] : &f" + target.getName() );
			
			if(ARSystem.box(player, new Vector(6,4,6), box.TARGET).size() >= 3 && !isps) {
				spskillon();
				spskillen();
				Rule.playerinfo.get(player).tropy(106, 1);
				skill("c106_sp");
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				player.setMaxHealth(player.getMaxHealth()*2);
				ARSystem.heal(player,player.getMaxHealth()/2);
				ARSystem.playSound((Entity)player, "c106sp");
			}
		}
		if(isps && tk%10 == 0) {
			Entity target = ARSystem.boxSOne(player, new Vector(6,2,6), box.TARGET);
			if(target != null) {
				ARSystem.playSound(target, "0sword", 1.4f, 0.2f);
				ARSystem.spellLocCast(player, target.getLocation(), "c106_p");
				for(int j=0;j<10;j++) {
					if(cooldown[j] > 0) cooldown[j] -=1;
				}
			}
		}
		if(s1time > 0) {
			s1time--;
			if(s1time == 0) target = null;
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.5);
			if(stack > 0) {
				for(int i=0;i<stack;i++) {
					delay(()->{
						ARSystem.playSound(e.getDamager(), "0sword", 1.4f);
						ARSystem.spellLocCast(player, e.getDamager().getLocation(), "c106_p");
						for(int j=0;j<10;j++) {
							if(cooldown[j] > 0) cooldown[j] -=1;
						}
					},2*i);
				}
				stack = 0;
			}
			if(isps) e.setDamage(e.getDamage()*0.8);
		}
		return true;
	}
	
}
