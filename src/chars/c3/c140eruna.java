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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import buff.Airborne;
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
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.c2.c69himi;
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

public class c140eruna extends c00main{

	int stack = 0;
	int sk2 = 0;
	boolean sk3 = false;
	
	float cooldowns[] = new float[3];
	
	public c140eruna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 140;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		stack = (int)f;
	}

	@Override
	public boolean skill1() {
		if(sk3) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.8f));
			delay(()->{
				skill("c140_s1-2");
			},4);
		} else {
			if(stack >= 200 && skillCooldown(0)) {
				Rule.playerinfo.get(player).tropy(140, 1);
				spskillon();
				spskillen();
				stack = 0;
				ARSystem.playSound((Entity)player, "c140sp");
				ARSystem.giveBuff(player, new Stun(player), 110);
				ARSystem.giveBuff(player, new Silence(player), 110);
				ARSystem.giveBuff(player, new Nodamage(player), 110);
				delay(()->{
					skill("c140_sp");
					delay(()->{
						stack = 0;
					},20);
				},100);
			} else {
				ARSystem.playSound((Entity)player, "c140s1");
				ARSystem.giveBuff(player, new Stun(player), 30);
				delay(()->{
					skill("c140_s1");
				},20);
			}
		}
		return true;
	}

	@Override
	public boolean skill2() {
		sk2 = 60;
		if(sk3) {
			player.setVelocity(player.getLocation().getDirection().multiply(2));
		} else {
			ARSystem.playSound((Entity)player, "c140s2");
			player.setVelocity(new Vector(0,2,0));
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(stack >= 5) {
			if(sk3) {
				sk3 = false;
			} else {
				ARSystem.playSound((Entity)player, "c140s3");
				sk3 = true;
			}
			float cd = cooldown[1];
			cooldown[1] = cooldowns[1];
			cooldowns[1] = cd;
			
			cd = cooldown[2];
			cooldown[2] = cooldowns[2];
			cooldowns[2] = cd;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(sk2 > 0) {
			sk2--;
			if(player.isSneaking()) {
				player.setFallDistance(-10);
				sk2 = 0;
				if(sk3) {
					player.setVelocity(player.getLocation().getDirection().multiply(2));
				} else {
					ARSystem.giveBuff(player, new Airborne(player), 60);
				}
			}
		}
		if(tk%20 == 0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c140:ps")+ "] : " + stack);
			if(sk3) scoreBoardText.add("&c ["+Main.GetText("c140:sk3")+ "]");
		}
		if(tk%4 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(10,8,10), box.TARGET)) {
				stack += 1;
			}
			if(ARSystem.isGameMode("lobotomy") && stack > 500) {
				stack = 500;
			}
		}
		if(sk3 && tk%4 == 0) {
			stack -= 1;
			if(stack <= 0) {
				sk3 = false;
				float cd = cooldown[1];
				cooldown[1] = cooldowns[1];
				cooldowns[1] = cd;
				
				cd = cooldown[2];
				cooldown[2] = cooldowns[2];
				cooldowns[2] = cd;
			}
		}
		if(isBattleTime() > 400 && stack > 0 && player.getHealth() < player.getMaxHealth()) {
			stack--;
			ARSystem.heal(player, 0.1f);
		}
		cooldowns[1] -= (skillmult + sskillmult)*0.05;
		cooldowns[2] -= (skillmult + sskillmult)*0.05;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() + (e.getDamage() * stack * 0.005));
			if(e.getDamage() <= 900) stack += ((int)e.getDamage()*2.5f);
		} else {
			stack -= e.getDamage();
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setVelocity(new Vector(0,-1,0));
			ARSystem.potion(target, 2, 60, 2);
		}
	}
	
	boolean himin = true;
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		Entity himi = null;
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				String s = Main.GetText("c"+Rule.c.get(e).getCode()+":tag");
				if(s.indexOf("tg1") != -1) {
					is = "g";
				}
				if((Rule.c.get(e) instanceof c69himi )&& himin){
					himin = false;
					is = "himi";
					himi = e;
					break;
				}
				if(Rule.c.get(e) instanceof c39sakuya){
					is = "md";
					break;
				}
			}
		}


		if(is.equals("md")) {
			ARSystem.playSound((Entity)player, "c140md");
		} else if(is.equals("himi")) {
			ARSystem.playSound((Entity)player, "c140himi");
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			ARSystem.giveBuff((LivingEntity)himi, new TimeStop((LivingEntity)himi), 200);
			ARSystem.giveBuff(player, new Nodamage(player), 80);
			stack += 100;
			cooldown[1] = cooldown[2] = cooldown[3] = cooldowns[1] = cooldowns[2] = cooldowns[3] = 0;
			Location loc = player.getLocation();
			
			loc = ULocal.lookAt(ULocal.offset(loc, new Vector(1,0.5f,0)), player.getLocation());
			himi.teleport(loc);
			Entity hi = himi;
			delay(()->{
				for(int i = 0; i <10; i++) {
					int j = i;
					delay(()->{
						hi.teleport(hi.getLocation().clone().add(0,-0.5 + j%2,0));
					},10*i);
				}
			},10);
			delay(()->{
				for(int i = 0; i < 36; i++) {
					int j = i;
					delay(()->{
						Location lc = player.getLocation();
						lc.setYaw(lc.getYaw()+20);
						player.teleport(lc);
						hi.teleport(ULocal.lookAt(ULocal.offset(player.getLocation(), new Vector(1,0.5f,0)), player.getLocation()));
					},2*i);
				}
				ARSystem.heal(player, 10);
			},110);
			cooldown[9] = 10;
		} else if(is.equals("g")) {
			ARSystem.playSound((Entity)player, "c140db3");
		} else {
			ARSystem.playSound((Entity)player, "c140db"+AMath.random(2));
		}
		
		return true;
	}

}
