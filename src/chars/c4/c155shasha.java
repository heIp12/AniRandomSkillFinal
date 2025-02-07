package chars.c4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
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
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c155shasha extends c00main{
	int attack = 0;
	List<Location> water = new ArrayList<>();
	int s2t = 0;
	int s2l = 0;
	
	Entity sp;

	public c155shasha(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 155;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(player.isSneaking()) {
			for(Location lc : water) {
				if(lc.distance(player.getLocation()) < 5) {
					for(int i = 0; i < 10; i++) {
						delay(()->{
							lc.add(player.getLocation().getDirection().multiply(0.3f));
						},i);
					}
				}
			}
			cooldown[1] *= 0.5;
			ARSystem.playSound((Entity)player, "c155s12");
		} else {
			if(water.size() < 20) {
				ARSystem.playSound((Entity)player, "c155s1");
				if(isps) {
					water.add(ULocal.offset(player.getLocation().clone(), new Vector(0,0,2)));
					water.add(ULocal.offset(player.getLocation().clone(), new Vector(0,0,-2)));
					ARSystem.playSound((Entity)player, "c155s12");
				} else {
					ARSystem.playSound((Entity)player, "c155s12");
					water.add(ULocal.offset(player.getLocation().clone(), new Vector(2,0,0)));	
				}
			} else {
				cooldown[1] = 0;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c155s2");
		s2t = 6;
		s2l = 0;
		return true;
	}
	
	@Override
	public boolean skill3() {
		boolean cast = false;
		Collections.sort(water,new locCompare());
		for(int i =0; i<3; i++) {
			for(Location lc : water) {
				if(lc.distance(player.getLocation()) < 8) {
					ARSystem.spellLocCast(player, lc, "c155_s3e");
					delay(()->{skill("c155_s3");},i*8);
					water.remove(lc);
					cast = true;
					break;
				}
			}
		}
		if(cast) {
			ARSystem.playSound((Entity)player, "c155s3");
			ARSystem.playSound((Entity)player, "c155s32");
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean skill4() {
		List<Location> lc = new ArrayList<>();
		Collections.sort(water,new locCompare());
		for(Location loc : water) {
			if(loc.distance(player.getLocation()) > 8) {
				lc.add(loc);
			}
		}
		if(lc.size() > 0) {
			ARSystem.playSound((Entity)player, "c155s4");
			ARSystem.playSound((Entity)player, "c155s42");
			for(Location loc : lc) {
				water.remove(loc);
				ARSystem.spellLocCast(player, loc, "c155_s4e");	
			}
			Location lcc = player.getLocation();
			lcc.setPitch(0);
			for(int i = 0; i < lc.size(); i++) {
				int j = i;
				delay(()->{
					ARSystem.spellLocCast(player, ULocal.offset(lcc.clone(), new Vector(1+(j*2),0,0)), "c155_s4");	
				},2*i);
			}
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(s2t > 0) {
			if(e.getNewSlot() == 0) {
				s2l = 1;
			} else if(e.getNewSlot() == 1) {
				s2l = 2;
			} else if(e.getNewSlot() == 2) {
				s2l = 3;
			}
			player.getInventory().setHeldItemSlot(7);
			e.setCancelled(true);
			return false;
		}
		return super.key(e);
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(2,player);
			if(AMath.random(2) == 1) {
				if(water.size() < 20) {
					ARSystem.playSound(target, "c155s12");
					water.add(target.getLocation());
				}
			}
		}
	}
	

	@Override
	public boolean tick() {
		tropy++;
		int id = player.getLocation().getBlock().getTypeId();
		if((id == 8 || id == 9 ) && tk%10 == 0 && AMath.random(10) <= 4) {
			if(water.size() < 20) water.add(ULocal.offset(player.getLocation().clone(), new Vector(2-AMath.random(20)*0.2,AMath.random(20)*0.2,2-AMath.random(20)*0.2)));
		}
		if(sp == null) sp = ARSystem.RandomPlayer(player);
		if(!isps && Rule.c.get(sp) == null) sp = ARSystem.RandomPlayer(player);
		if(!isps && Rule.c.size() > 2 && sp == player) sp = ARSystem.RandomPlayer(player);
		for(Location lc : water) {
			ARSystem.spellLocCast(player, lc, "c155p");
		}
		if(water.size() <= 5) {
			for(int i = 0; i < 10; i ++) if(cooldown[i] > 0) cooldown[i] -= 0.005;
			if(water.size() <= 3) for(int i = 0; i < 10; i ++) if(cooldown[i] > 0) cooldown[i] -= 0.015;
		}
		if(s2t > 0) {
			s2t--;
			if(s2t <= 0) {
				ARSystem.playSound((Entity)player, "c155s22");
				float size = 0.8f;
				if(s2l == 0) {
					for(Location lc : water) {
						ARSystem.spellLocCast(player, lc, "c155_s2");
					}
					size = 0.1f;
				} else if(s2l == 1) {
					for(Location lc : water) {
						List<Entity> en = ARSystem.boxS(ARSystem.box(lc, player, new Vector(20,20,20), box.TARGET), lc);
						if(en.size() > 0) {
							ARSystem.spellLocCast(player, ULocal.lookAt(lc.clone(), en.get(0).getLocation()), "c155_s2");
						} else {
							ARSystem.spellLocCast(player, lc, "c155_s2");
						}
						ARSystem.spellLocCast(player, lc, "c155_s2");
					}
					size = 0.8f;
				} else if(s2l == 2) {
					for(Location lc : water) {
						ARSystem.spellLocCast(player, ULocal.lookAt(lc.clone(), player.getLocation()), "c155_s2");
					}
					size = 0.4f;
				} else if(s2l == 3) {
					for(Location lc : water) {
						Location lcc = lc.clone();
						lcc.setPitch(player.getLocation().getPitch());
						lcc.setYaw(player.getLocation().getYaw());
						ARSystem.spellLocCast(player, lcc, "c155_s2");
					}

					size = 0.2f;
				}
				for(int i = (int)(Math.max(1,water.size()*size)); i > 0; i--) water.remove(AMath.random(water.size())-1);
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c155:sk1")+ "] : " + water.size());
		}
		return true;
	}
	int tropy = 0;
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			if(tropy <= 60 && s_kill >= 2) {
				Rule.playerinfo.get(this.player).tropy(155,1);
			}
			tropy = 0;
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			attack++;
			if(attack%3 == 0) {
				for(int i = 0; i < 10; i++) if(cooldown[i] > 0) cooldown[i] -= 0.5;
			}
			if(attack%15 == 0) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier", 4);
			}
			if(isps && attack%4 == 0) {
				ARSystem.playSound(e.getEntity(), "c155s12");
				water.add(e.getEntity().getLocation());
			}
		} else {
			if(!isps && e.getDamager() == sp && skillCooldown(0)) {
				setcooldown[1] *= 0.5;
				spskillon();
				spskillen();
				ARSystem.playSound((Entity)player, "c155sp");
			}
		}
		return true;
	}
	
	class locCompare implements Comparator<Location> {
		@Override
		public int compare(Location o1, Location o2) {
			 if(o1.distance(player.getLocation()) > o2.distance(player.getLocation())) {
		         return 1;
		     } else{
		         return -1;
		     }
		}
	}	
}