package chars.c4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import chars.c.c00main;
import item.list1.itemBase;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.Pair;
import util.Text;

public class c153nir extends c00main{
	List<Pair<Location,String>> move = new ArrayList<>();
	int s2 = 0;
	float nextcooldown = 0;
	LivingEntity en = null;
	Location loc;
	int chatcool = 0;
	int ptime = 0;
	
	boolean s3sft = false;
	
	@Override
	public void setStack(float f) {
		
	}
	
	public c153nir(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 153;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "fascination")) {
			if(ptime <= 0) {
				ptime = 100;
				ARSystem.playSound((Entity)player, "c153p");
			}
			Holo.create(player.getLocation(), Text.get("c153:t"), 80, new Vector(0,0,0));
			Rule.buffmanager.selectBuffTime(player, "fascination",0);
		}
		return true;
	}
	
	@Override
	public boolean skill1() {
		if(s2 <= 0 && move.size() > 0) {
			ARSystem.playSound((Entity)player, "c153s1");
			if(player.isSneaking()) {
				en = player;
				cooldown[2] = 3;
			} else {
				List<Entity> e = ARSystem.PlayerBeamBox(player, 15, 5, box.TARGET);
				if(e.size() > 0) {
					en = (LivingEntity)e.get(0);
					cooldown[2] = nextcooldown;
				}
				else cooldown[1] = 0;
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c153s2");
		s2 = 100;
		en = null;
		move.clear();
		loc = player.getLocation();
		s3sft = player.isSneaking();
		return true;
	}
	
	@Override
	public boolean skill3() {
		invskill = new InvSkill(player) {
			@Override
			public void Start(String st) {
				player.closeInventory();
			}
		};

		Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		Player.remove(player);
		Inventory.getlistD(invskill,player,Player);
		invskill.openInventory(player);
		return true;
	}
	

	@Override
	public boolean tick() {
		if(tk%20000 == 0) {
			player.setVelocity(new Vector(0,0.4,0));
		}
		if(en == null && s2 > 0) {
			s2--;
			player.sendTitle("<"+Text.get("c153:sk2")+">", AMath.round(s2*0.05,2) +" (s)",0,5,0);
			if(loc.distance(player.getLocation()) > 1) loc = player.getLocation();
			move.add(new Pair<>(player.getLocation().clone().subtract(loc),""));

			if(!s3sft && player.isSneaking()) {
				nextcooldown = setcooldown[2] *  Math.max(1,0.5f*(100-s2));
				s2 = 0;
				player.sendTitle("<"+Text.get("c153:sk2")+">", "-End-",0,5,0);
			}
			else if(s2 == 0) {
				nextcooldown = setcooldown[2] * 50;
				player.sendTitle("<"+Text.get("c153:sk2")+">", "-End-",0,5,0);
			}
			loc = player.getLocation();
		}
		
		if(en != null && s2 == 0) {
			Pair<Location,String> pair = move.get(0);
			Location lc = pair.getKey().clone();
			if(BlockUtil.isPathable(en.getLocation().clone().add(lc.getX(),0,lc.getZ()).getBlock())) {
				lc.setX(lc.getX() + en.getLocation().getX());
				lc.setZ(lc.getZ() + en.getLocation().getZ());
			} else {
				lc.setX(en.getLocation().getX());
				lc.setZ(en.getLocation().getZ());
			}
			if(!BlockUtil.isPathable(lc.clone().add(0,en.getLocation().clone().getY(),0).getBlock())) {
				lc.setY(en.getLocation().getY());
			} else {
				lc.setY(lc.getY() + en.getLocation().getY());
			}
			
			en.teleport(lc);
			String key = pair.getValue(); 
			if(key.contains("key:") && Rule.c.get(en) != null) {
				c00main c = Rule.c.get(en);
				
				String s = key.split(":")[1];
				boolean sft = Boolean.parseBoolean(key.split(":")[2]);
				if(sft) ((Player)en).setSneaking(true);
				
				if(s.equals("1") && c.cooldown[1] <= 0) {
					c.cooldown[1] = c.setcooldown[1];
					c.skill1();
				} else if(s.equals("2") && c.cooldown[2] <= 0) {
					c.cooldown[2] = c.setcooldown[2];
					c.skill2();
				} else if(s.equals("3") && c.cooldown[3] <= 0) {
					c.cooldown[3] = c.setcooldown[3];
					c.skill3();
				} else if(s.equals("4") && c.cooldown[4] <= 0) {
					c.cooldown[4] = c.setcooldown[4];
					c.skill4();
				} else if(s.equals("f")) {
					for(itemBase it : ARSystem.playerItem.get(en).items) {
						if(it.cooldown <= 0) {
							it.cooldown = it.setcooldown;
							if(!it.skillCast()) {
								break;
							} else {
								it.cooldown = 0;
							}
						}
					}
				} else if(s.equals("5") && c.cooldown[5] <= 0) {
					c.cooldown[5] = c.setcooldown[5];
					c.skill5();
				} else if(s.equals("9") && c.cooldown[9] <= 0) {
					c.cooldown[9] = c.setcooldown[9];
					c.skill9();
				}
				if(sft) ((Player)en).setSneaking(false);
			} else if(key.contains("chat:") && en instanceof Player) {
				String s = key.replace("chat:", "").replace("/", "./");
				System.out.print("["+player.getName() +"] : Chat control -> " + en.getName() +"  ");
				((Player)en).chat(s);
			}
			move.remove(0);
			if(move.size() <= 0) {
				en = null;
			}
		}
		if(chatcool > 0) chatcool--;
		if(ptime > 0) ptime--;
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(s3sft && s2 > 0 && e.getNewSlot() == 6) {
			player.getInventory().setHeldItemSlot(7);
			nextcooldown = setcooldown[2] *  Math.max(1,0.5f*(100-s2));
			s2 = 0;
			player.sendTitle("<"+Text.get("c153:sk2")+">", "-End-",0,5,0);
			return false;
		}
		if(en == null && s2 > 0 && (e.getNewSlot() < 6 || e.getNewSlot() == 8)) {
			move.get(move.size()-1).setValue("key:"+(e.getNewSlot()+1)+":" + player.isSneaking());
			player.getInventory().setHeldItemSlot(7);
			return false;
		}
		return super.key(e);
	}
	
	@Override
	public boolean key_f() {
		if(en == null && s2 > 0) {
			move.get(move.size()-1).setValue("key:f");
			return false;
		}
		return super.key_f();
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && s_kill >= 2) {
			Rule.playerinfo.get(this.player).tropy(153,1);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(en != null && en != player && e.getDamage() > 0.01 && !(Rule.c.get(en) != null && Rule.c.get(en).number == 153)) {
				s_damage += e.getDamage();
				en.setNoDamageTicks(0);
				en.damage(e.getDamage(),e.getDamager());
				
				e.setDamage(e.getDamage() * 0.1);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(en == null && s2 > 0 && e.getPlayer() == player && chatcool <= 0) {
			chatcool = 5;
			move.get(move.size()-1).setValue("chat:"+e.getMessage());
			e.setMessage("");
			e.setCancelled(true);
			return false;
		}
		return super.chat(e);
	}
	
}
