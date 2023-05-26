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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import ars.gui.G_Satory;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c127jack extends c00main{
	Location target;
	int targettick = 0;
	int skd = 0;
	int sk2 = 0;
	boolean sk3 = false;
	List<Entity> targets;
	Location sk2l;
	
	int p = 0;
	
	boolean t = false;
	public c127jack(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 127;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c127s1");
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		skd = 20;
		Location loc = player.getLocation();
		delay(()->{
			player.setVelocity(new Vector(0,0.4,0));
			delay(()->{
				player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.2));
				
				delay(()->{
					ARSystem.playSound((Entity)player, "0katana",2);
					skill("c127_s1-1");
					ARSystem.playSound((Entity)player, "c127s12");
					for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
						damage(e,2);
						t = true;
					}
					delay(()->{
						ARSystem.playSound((Entity)player, "0katana2",2);
						skill("c127_s1-2");
						for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
							 damage(e,2);
							 t = true;
						}
						delay(()->{
							if(t) {
								player.setVelocity(new Vector(0,0.4,0));
								delay(()->{
									player.setVelocity(ULocal.lookAt(player.getLocation(), loc).getDirection().multiply(2).setY(0.2));
								},2);
							}
						},3);
					},10);
				},10);
			},2);
		},8);
		return true;
	}
	void damage(Entity e, double damage){
		float m = 1.4f;
		
		if(p > 0) damage *= m;
		if(player.getWorld().getTime()%36000 > 12500 && player.getWorld().getTime()%36000 < 22500) damage*= m;
		if(Rule.c.get(e) != null && Main.GetText("c"+Rule.c.get(e).getCode()+":tag").indexOf("tg1") != -1) damage *= m;
		
		if(sk3) {
			sk3 = false;
			if(((LivingEntity)e).getHealth() - damage < 1) {
				cooldown[1] = cooldown[2] = 0;
			} else {
				Curse cs = new Curse((LivingEntity) e);
				cs.setCaster(player);
				ARSystem.giveBuff((LivingEntity) e, cs, 60 , 1);
			}
		}
		((LivingEntity)e).setNoDamageTicks(0);
		((LivingEntity)e).damage(damage,player);
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c127s2");
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		sk2 = 20;
		skd = 20;
		targets = new ArrayList<>();
		sk2l = player.getLocation();
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c127s3");
		sk3 = true;
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0 && target!=null) {
			scoreBoardText.add("&c ["+Main.GetText("c127:ps")+ "] : &f" + AMath.round(player.getLocation().distance(target),2) +"[7~11]");
		}
		if(target != null && targettick > 0) {
			ARSystem.spellLocCast(player, target, "c127_p");
			if(skd <= 0  && player.getLocation().distance(target) >= 7 && player.getLocation().distance(target) <= 11) {
				p = 40;
				ARSystem.potion(player, 14, 20, 1);
			}
		}
		if(sk2 > 0) {
			if(sk2%4 == 0) {
				ARSystem.playSound((Entity)player, "0katana2",1.2f);
			}
			sk2--;
			skill("c127_s2");
			player.setVelocity(sk2l.getDirection().multiply(0.7));
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				if(!targets.contains(e)) {
					targets.add(e);
					damage(e,1);
					delay(()->{damage(e,1);},4);
					delay(()->{damage(e,1);},8);
				}
			}
		}
		
		if(p > 0) p--;
		if(skd > 0) skd--;
		if(targettick > 0) {
			targettick--;
			if(targettick == 0) {
				target = null;
			}
		}

		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = e.getEntity().getLocation();
			targettick = 160;
		} else {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				e.setDamage(e.getDamage()* 0.1);
			}
		}
		return true;
	}

}
