package chars.c3;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Lvup;
import buff.Curse;
import buff.NoHeal;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.Wound;
import chars.c.c00main;
import chars.ca.c1134siro;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class c134siro extends c00main{
	Spider unit;
	boolean start = true;
	boolean start2 = true;
	float speed = -14;
	int motion = 0;
	
	int lv = 1;
	int exp = 0;
	int maxexp = 5;
	int p = 0;
	int y = 5;
	
	String[][] t = new String[][]{{"a1","b1","o"+AMath.random(5)},{" "," "," "},{"c1","c2","c3"},{"d1","d2","d3"},{"e1","e2","e3"},{"f1","f2","f3"}};
	List<String> ts;
	public c134siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 134;
		load();
		text();
		c = this;
		Rule.playerinfo.get(player).tropy(134, 1);
	}
	
	@Override
	public void setStack(float f) {
		xp((int)f);
	}
	
	void xp(int i) {
		boolean lvup = false;
		exp += i;
		while(exp >= maxexp) {
			if(lv >= 14 && skillCooldown(0)) {
				spskillon();
				spskillen();
				Rule.c.put(player, new c1134siro(player, plugin, this));
			}
			lvup = true;
			exp -= maxexp;
			maxexp += lv;
			if(lv < 9) {
				maxexp *= 1.1f;
			} else {
				maxexp -= lv*0.4;
			}
			lv++;
			speed -= 1;
			addhp(3);
			ARSystem.heal(unit, 999);
			ARSystem.heal(player, 999);
		}
		if(lvup) {
			ARSystem.playSound(player, "c134lvup3");
			delay(()->{
				ARSystem.playSound(unit, "c134lvup1");
			},20);
		}
	}
	
	boolean is(String str) {
		return ts.contains(str);
	}

	@Override
	public boolean skill1() {
		LivingEntity g = null;
		for(Entity e : ARSystem.boxS(unit, new Vector(3,2,3), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			if(en != player) {
				g = en;
				break;
			}
		}
		LivingEntity tag = g;
		if(tag == null) {
			cooldown[1] = 0;
			return false;
		}
		ARSystem.playSound(unit, "c134sk1");
		unit.teleport(ULocal.lookAt(unit.getLocation(), tag.getLocation()));
		motion = 10;
		for(int i =0;i<5;i++)
		delay(()->{
			unit.teleport(unit.getLocation().add(unit.getLocation().getDirection().add(new Vector(0,0.05,0)).multiply(0.7)));
		},i);
		delay(()->{
			tag.setNoDamageTicks(0);
			tag.damage(2+lv,player);
			ARSystem.heal(unit, 2);
			ARSystem.heal(player, 2);
			if(is("o4")) {
				ARSystem.heal(unit, lv*0.5);
				ARSystem.heal(player, lv*0.5);
			}
			xp(2+lv);
			if(is("o3")) {
				ARSystem.giveBuff(tag, new Curse(tag), 100, 0.2f);
			}
			if(is("a2")) {
				xp((int)(tag.getMaxHealth()*(0.1f+(0.01*lv))));
				tag.setNoDamageTicks(0);
				tag.damage(tag.getMaxHealth()*(0.1f+(0.01*lv)),player);
			} else if(is("a3")) {
				ARSystem.giveBuff(tag, new Silence(tag), 40 + lv*6);
			} else if(is("a1")) {
				Wound w = new Wound(tag);
				w.setValue(1);
				w.setDelay(player,40,0);
				ARSystem.giveBuff(tag, w, 100 + (lv*10));
			}
		},3);
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(is("b1")) {
			ARSystem.spellLocCast(player, unit.getLocation(), "c134_s2-2");
			motion = 5;
			ARSystem.playSound(unit, "c134home");
		} else {
			LivingEntity g = null;
			for(Entity e : ARSystem.boxS(unit, new Vector(8,4,8), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				if(en != player) {
					g = en;
					break;
				}
			}
			if(g == null) {
				cooldown[1] = 0;
				return false;
			}
			unit.teleport(ULocal.lookAt(unit.getLocation(), g.getLocation()));
			motion = 10;
			ARSystem.spellLocCast(player, unit.getLocation(), "c134_s2");
		}
		return true;
	}

	@Override
	public boolean skill3() {
		if(is("c1")) {
			ARSystem.potion(unit, 14, 60+ (lv*2), 1);
		} else if(is("c2")) {
			Location lc = unit.getLocation().clone();
			lc.setPitch(0);
			unit.teleport(lc);
			motion = 5;
			for(int i =0;i<5;i++)
			delay(()->{
				unit.teleport(unit.getLocation().add(unit.getLocation().getDirection().multiply(0.8 + (-speed*0.07))));
			},i);
		} else if(is("c3")) {
			Location lc = unit.getLocation().clone();
			lc.setPitch(0);
			unit.teleport(lc);
			motion = 3;
			for(int i =0;i<3;i++)
			delay(()->{
				unit.teleport(unit.getLocation().add(unit.getLocation().getDirection().multiply(-0.5)));
			},i);
			ARSystem.giveBuff(unit, new Nodamage(unit), 10+lv);
		}
		return true;
	}
	
	ItemStack[] is(String[] str) {
		ItemStack[] is = new ItemStack[3];
		is[0] = ItemCreate.Lore(ItemCreate.Item(339),str[0],Text.getLine(str[0]+"_lore", 1));
		is[1] = ItemCreate.Lore(ItemCreate.Item(339),str[1],Text.getLine(str[1]+"_lore", 1));
		is[2] = ItemCreate.Lore(ItemCreate.Item(339),str[2],Text.getLine(str[2]+"_lore", 1));
		return is;
	}
	
	@Override
	public boolean skill4() {
		if(Math.max(3,(p+1)*3) <= lv) {
			if(p == 1) {
				if(is("a1")) {
					new G_Lvup(this, is(new String[] {"c134:b1","c134:a2","c134:a3"}));
				}
				else if(is("b1")) {
					new G_Lvup(this, is(new String[] {"c134:a1","c134:b2","c134:b3"}));
				}
				else {
					int i = AMath.random(5);
					while(is("o"+i)) i = AMath.random(5);
					new G_Lvup(this, is(new String[] {"c134:a1","c134:b1","c134:o"+i}));
				}
			} else {
				new G_Lvup(this, is(new String[] {"c134:"+t[p][0],"c134:"+t[p][1],"c134:"+t[p][2]}));
			}
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(!player.isSneaking()) {
			y+= 1;
			if(is("o5")) {
				if(y > 24) y = 24;
			} else {
				if(y > 12) y = 12;
			}
		} else {
			y-= 1;
			if(y < 1) y = 1;
		}
		player.sendTitle("","시아 높이 : "+y,0,20,0);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(target == unit) {
			return;
		}
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 20 + lv*4);
		}
		if(n.equals("2")) {
			if(is("b3")) {
				Wound w = new Wound(target);
				w.setValue(1 + (lv*0.3));
				w.setDelay(player,20,0);
				ARSystem.giveBuff(target, w, 100);
			}
			if(is("b2")) {
				ARSystem.giveBuff(target, new Stun(target), 20 + lv*4);
				ARSystem.giveBuff(target, new Silence(target), 20 + lv*4);
			} else {
				ARSystem.giveBuff(target, new Stun(target), 20 + lv*4);
			}
		}
	}
	
	@Override
	public void select(String i) {
		i = i.replace("c134:", "");
		if(!ts.contains(i)) {
			ts.add(i);
			p++;
			if(i.equals("b1")) {
				setcooldown[2]*=2;
			}
			if(i.equals("c1")) {
				setcooldown[3] = 10;
			}
			else if(i.equals("d1")) {
				addhp(10);
			}
			else if(i.equals("d3")) {
				xp(maxexp - exp);
			}
			else if(i.equals("e1")) {
				cooldown[1] *= 0.7;
			}
			else if(i.equals("e2")) {
				cooldown[2] *= 0.7;
			}
			else if(i.equals("e3")) {
				speed *= 1.3f;
			}
			else if(i.equals("o1")) {
				addhp(6);
			}
			else if(i.equals("o2")) {
				speed *= 1.2f;
			}
		}
	}
	
	void addhp(float hps) {
		hp+=hps;
		player.setMaxHealth(hp);
		unit.setMaxHealth(hp);
		ARSystem.heal(unit, hps);
		ARSystem.heal(player, hps);
	}
	
	boolean playerpb(Location loc) {
		if(BlockUtil.isPathable(loc.getBlock()) 
				&& BlockUtil.isPathable(loc.clone().add(0,1,0).getBlock())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(start2) return false;
		
		if(Rule.buffmanager.selectBuffType(unit, BuffType.HEADCC).size() <= 0) {
			if(player.getOpenInventory().getTitle().contains("Lv")) {
				ARSystem.giveBuff(unit, new Nodamage(unit), 5);
				return false;
			}
			if(Map.inMap(unit.getLocation())) {
				player.setMaxHealth(unit.getMaxHealth());
				player.setHealth(unit.getHealth());
				ARSystem.potion(player, 14, 20, 20);
				if(motion <= 0) {
					Location ploc = player.getLocation().clone();
					ploc.setY(unit.getLocation().getY());
					float spd = speed;
					if(player.isSprinting()) spd *= 1.25f;
					Location nloc = unit.getLocation().clone().add(unit.getLocation().clone().subtract(ploc).multiply(spd));
					//nloc = nloc.clone().toVector().normalize().toLocation(nloc.getWorld());
					Location lc = unit.getLocation().clone();
					if(lc.distance(nloc) > 0.001) {
						nloc.setYaw(ULocal.lookAt(ploc, nloc).getYaw());
					}
					nloc.setPitch(0);
					if(unit.getLocation().getBlock().getType().toString().contains("WATER")) {
						unit.teleport(nloc.add(0,0.1,0));
					} else if(unit.getLocation().clone().add(0,-1,0).getBlock().getType().toString().contains("WATER")) {
						unit.teleport(nloc);
					} else if(!BlockUtil.isPathable(unit.getLocation().clone().add(unit.getLocation().getDirection()).getBlock())) {
						float power = 0.3f;
						if(player.isSneaking()) power = 0;
						unit.teleport(nloc.add(0,power,0));
					} else if(BlockUtil.isAirbone(unit.getLocation(), 1) && BlockUtil.isAirbone(unit.getLocation().clone().add(unit.getLocation().getDirection()), 1)) {
						unit.teleport(unit.getLocation().add(0,-1,0));
					} else {
						unit.teleport(nloc);
					}
				} else {
					motion -= 1;
				}
				
				Location loc = unit.getLocation().clone().add(0,y,0);
				if(player.getLocation().distance(loc) > 0.001) {
					if(!player.isSprinting()) loc.setYaw(player.getLocation().getYaw());
					if(is("d2")) {
						loc.setPitch(player.getLocation().getPitch());
					} else {
						loc.setPitch(90);
					}
					player.teleport(loc);
				}
				player.setFallDistance(0);
				
				if(start) {
					start = false;
					player.setAllowFlight(true);
					player.setFlying(true);
					player.setFlySpeed(0.05f);
				}
			} else {
				unit.teleport(Map.randomLoc(player));
				player.teleport(unit.getLocation().clone().add(0,y,0));
			}
			if(unit.getLocation().getY() < Map.loc_f.getY()-2) {
				unit.teleport(unit.getLocation().clone().add(0,1,0));
			}
		} else {
			Location loc = unit.getLocation().clone().add(0,y,0);
			if(player.getLocation().distance(loc) > 0.001) {
				if(!player.isSprinting()) loc.setYaw(player.getLocation().getYaw());
				if(is("d2")) {
					loc.setPitch(player.getLocation().getPitch());
				} else {
					loc.setPitch(90);
				}
				player.teleport(loc);
			}
			player.setFallDistance(0);
		}
		if(!start) {
			if(unit.isDead() || unit == null) {
				Skill.remove(player, player);
			}
		}
		return super.firsttick();
	}
	
	public boolean tick() {
		if(start2) {
			unit = (Spider)player.getWorld().spawnEntity(player.getLocation(), EntityType.SPIDER);
			unit.setAI(false);
			unit.setGravity(true);
			unit.setMaxHealth(hp);
			unit.setHealth(hp);
			maptp = false;
			ts = new ArrayList<String>();
			for(Player plc : Rule.c.keySet()) {
				plc.hidePlayer(player);
			}
			
			start2 = false;
			player.getWorld().spawnEntity(player.getLocation(), EntityType.SPIDER);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&e[LV ] &f" + lv);
			scoreBoardText.add("&a[EXP] &f" + exp +" &e/&c " + maxexp);
			scoreBoardText.add("&a["+Text.get("c134:sk4")+"] &f" + (lv/3 - p));
		}
		return true;
	}


	@Override
	public void kill(LivingEntity death, LivingEntity killer) {
		if(killer == player) {
			xp((int)(death).getMaxHealth());
			ARSystem.playSound(unit, "c134eat");
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			e.setDamage(0);
			e.setCancelled(true);
			return false;
		} else {
			
		}
		return true;
	}
}
