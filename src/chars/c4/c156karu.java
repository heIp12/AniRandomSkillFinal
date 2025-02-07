package chars.c4;

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
import org.bukkit.inventory.ItemStack;
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
import ars.TeamInfo;
import ars.gui.G_Lvup;
import buff.Airborne;
import buff.ArmorUp;
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import buffs.BuffBase;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
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
import util.ItemCreate;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c156karu extends c00main{
	ItemStack[] karucon = null;
	
	float s2r = 0;
	int s2t = 0;
	Location lc;
	List<Location> loc = new ArrayList<>();
	
	int ps = 0;
	
	int dbf = 0;
	
	int gs = 1;
	int gs2 = 1;
	
	public c156karu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 156;
		load();
		text();
		c = this;
		battleTime = 140;
	}

	@Override
	public boolean skill1() {
		skill("c156_s1");
		ARSystem.playSound((Entity)player, "aram1", AMath.random(5)*0.1f+0.5f);
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(s2t <= 0) {
			loc.clear();
			s2r = 0;
			s2t = 200;
			lc = player.getLocation();
			cooldown[2] = 0;
			gs = Text.getI("c156:p");
			gs2 = Text.getI("c156:p2");
		} else {
			if(Rule.buffmanager.GetBuffTime(player, "powerup") > 0 && Rule.buffmanager.GetBuffValue(player, "powerup") > 0 && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				ARSystem.giveBuff(player, new Stun(player), 60);
				ARSystem.giveBuff(player, new Silence(player), 60);
				ARSystem.spellLocCast(player, lc, "c156_sp");
				s2t = 0;
				Rule.buffmanager.selectBuffTime(player, "powerup", 0);
				ARSystem.playSoundAll("c156sp");
				return true;
			}
			s2t = 0;
			ARSystem.playSound((Entity)player, "c156s1"+AMath.random(2));
			loc.add(lc);
			if(loc.size() > 0) {
				if(loc.size() > 1) cooldown[3] = setcooldown[3] * (loc.size()-1);
				for(Location l : loc) {
					ARSystem.spellLocCast(player, l, "c156_s2");
					
					Location ll = l;
					for(int j = 0; j < 30; j++) {
						delay(()->{
							List<Entity> p = new ArrayList<Entity>();
							for(Entity e : ARSystem.box(ll, player, new Vector(7,7,7), box.TARGET)) {
								Vector v = e.getVelocity().add(ULocal.lookAt(e.getLocation().clone(), ll).getDirection().multiply(gs*0.001));
								if(Rule.buffmanager.GetBuffTime((LivingEntity)e, "airborne") > 0) {
									v = v.clone().multiply(gs2*0.01);
								}
								if(e.getLocation().distance(l) <= 1.5) {
									if(p.contains(e)) {
										LivingEntity en = (LivingEntity)e;
										if(e.getLocation().distance(l) <= 1) {
											ARSystem.giveBuff(en, new Stun(en), 5);
										} else {
											ARSystem.giveBuff(en, new Stun(en), 3);	
										}
										v = v.multiply(0);
										v.setY(0.01);
									}
									p.add(e);
								}
								e.setVelocity(v);
							}
						},10+j);
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(s2t > 0 && loc.size() < 5) {
			double rg = 100;
			for(Location l : loc) {
				if(lc.distance(l) < rg) {
					rg = lc.distance(l);
				}
			}
			if(rg < 2) {
				player.sendTitle("",Text.get("c156:t1"),0,20,20);
				cooldown[3] = 0;
				return true;
			} else {
				if(loc.size() == 0) ARSystem.playSound((Entity)player, "c156s2");
				loc.add(lc);
			}
			cooldown[2] = 0.5f;
			cooldown[3] = 0.5f;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	
	@Override
	public boolean skill4() {
		List<Entity> tg = ARSystem.PlayerBeamBox(player, 15, 3, box.TARGET);
		
		if(tg.size() > 0) {
			ARSystem.playSound((Entity)player, "c156s3"+AMath.random(2));
			LivingEntity en = (LivingEntity)tg.get(0);
			ARSystem.giveBuff(en, new ArmorUp(en), 100, -0.5f);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(player.isSneaking()) {
			return false;
		}
		if(karucon == null) {
			karucon = new ItemStack[9];
			for(int i = 0; i <9; i++) {
				karucon[i] = ItemCreate.Name(ItemCreate.Item(293,1494-i), "imgk"+(i+1));
			}
		}
		new G_Lvup(this, karucon);
		return true;
	}

	@Override
	public void select(String i) {
		skill(""+i);
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(cooldown[3] > 0) cooldown[3] -= 2;
			if(cooldown[2] > 0) cooldown[2] -= 1;
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.giveBuff(target, new Airborne(target), 6);
			delay(()->{target.setVelocity(new Vector(0,0,0));},0);
			delay(()->{target.setVelocity(new Vector(0,0,0));},1);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(8,player);
			ARSystem.addBuff(target, new Panic(target), 40);
		}
		if(n.equals("4")) {
			ARSystem.giveBuff(target, new Silence(target), 40);
			ARSystem.giveBuff(target, new Stun(target), 80);
		}
	}
	

	@Override
	public boolean tick() {
		if(Rule.buffmanager.GetBuffTime(player, "panic") > 120) {
			if(dbf <= 0) {
				dbf = 140;
				ARSystem.playSound((Entity)player, "c156pn");
			}
		}
		if(dbf > 0) dbf--;
		
		if(AMath.random(500) <= 1 && ARSystem.winstop <= 0 && Rule.team.getTeam(player) != null && Rule.team.getTeam(player).size() > 0) {
			ARSystem.playSoundAll("c156e");
			spskillen(Text.get("c156:t2"));
			for(TeamInfo tm : Rule.team.getTeam(player)) {
				Rule.team.teamQuit(tm.getTeamName(), player);
			}
			Rule.playerinfo.get(this.player).tropy(156,1);
		}
		if(s2t > 0) {
			if(!player.isSneaking()) {
				if(s2r < 15) {
					s2r+= 0.4;
				}
			}
			s2t--;
			lc = ULocal.offset(player.getLocation().clone(), new Vector(s2r,0,0));
			ARSystem.spellLocCast(player, lc, "c156_s2e");

			for(Location l : loc) {
				ARSystem.spellLocCast(player, l, "c156_s2e2");
			}
		}
		if(tk%20 == 0) {
			if(ps > 0) scoreBoardText.add("&c ["+Main.GetText("c156:ps")+ "] : "+ AMath.round(ps*0.05, 1));
		}

		if(ps > 0) ps--;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && e == player) {
			ARSystem.giveBuff(player, new PowerUp(player), 300, 0.2f);
		}
		if(p == player && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.player == Rule.c.size()) {
			ARSystem.playSound((Entity)player, "c156fd");
			Holo.create(player.getLocation(), Text.get("c156:t3"), 100, new Vector(0,0,0));
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(battleTime >= 140) {
				ps = 60;
			}
			if(ps > 0) e.setDamage(e.getDamage() * 1.7);
		} else {
			
		}
		return true;
	}

	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player,"c156db"+AMath.random(4));
		return true;
	}
}
