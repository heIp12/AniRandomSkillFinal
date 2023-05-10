package chars.c2;

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
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

public class c75gon extends c00main{
	int sk = 0;
	int tsk = 0;
	int lose = 0;
	int ps = 0;
	
	public c75gon(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 75;
		load();
		text();
		c = this;
	}
	


	public boolean Jjan() {
		LivingEntity e = (LivingEntity) ARSystem.boxSOne(player, new Vector(10,6,10), box.TARGET);
		if(e != null) {
			if(player.getHealth() <= 2) Rule.playerinfo.get(player).tropy(75,1);
			cooldown[3] = cooldown[2] = cooldown[1] = setcooldown[1];
			ARSystem.giveBuff(player,new TimeStop(player), 80);
			ARSystem.giveBuff(e,new TimeStop(e), 80);
			ARSystem.playSound((Entity)player, "c75s1");
			ARSystem.spellCast(player, e, "c75_s1");
			delay(()->{ARSystem.spellCast(player, e, "c75_s2");},17);
			delay(()->{
				if(e instanceof Player) {
					tsk = 0;
					invskill = new InvSkill(player) {
						
						@Override
						public void Start(String st) {
							if(Integer.parseInt(st.substring(0, 1)) != 0) {
								tsk = Integer.parseInt(st.substring(0, 1));
							}
							((Player)e).closeInventory();
						}
						
					};
					org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 27,player.getName() + " : Skill");
	
					ItemStack is = new ItemStack(293,1, (short) 134);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("1§c["+Main.GetText("c75:t1")+ "]");
					im.setUnbreakable(true);
					is.setItemMeta(im);
					inv.setItem(10, is);
					
					is = new ItemStack(293,1, (short) 135);
					im = is.getItemMeta();
					im.setDisplayName("2§c["+Main.GetText("c75:t2")+ "]");
					im.setUnbreakable(true);
					is.setItemMeta(im);
					inv.setItem(13, is);
					
					is = new ItemStack(293,1, (short) 136);
					im = is.getItemMeta();
					im.setDisplayName("3§c["+Main.GetText("c75:t3")+ "]");
					im.setUnbreakable(true);
					is.setItemMeta(im);
					inv.setItem(16, is);
					invskill.setInventory(inv);
					invskill.openInventory((Player) e);
				} else {
					tsk = AMath.random(3);
				}
			},10);
			delay(()->{
				if(tsk == 0) {tsk = AMath.random(3);}
				ARSystem.spellLocCast(player, player.getLocation(), "c75e"+sk);
				ARSystem.spellLocCast(player, e.getLocation(), "c75e"+tsk);
				ARSystem.playSound((Entity)player, "c75s1"+sk);
			},70);
			
			delay(()->{
				if(e instanceof Player) {
					((Player)e).closeInventory();
				}
				Rule.buffmanager.selectBuffTime(e, "timestop",0);
				if(sk==1) {
					if(tsk==3) {
						for(int t=0;t<15;t++) delay(()->{
							e.setNoDamageTicks(0);
							e.damage(1,player);
						},t);
						lose = 0;
					} else {
						if(tsk==2) {

						} else {
							cooldown[3] = cooldown[2] = cooldown[1] = setcooldown[1]/5f;
						}
						lose++;
					}
					}
				if(sk==3) {
					if(tsk==2) {
						e.setNoDamageTicks(0);
						e.damage(17,player);
						lose = 0;
					} else {
						if(tsk==1) {

						} else {
							cooldown[3] = cooldown[2] = cooldown[1] = setcooldown[1]/5f;
						}
						lose++;
					}
				}
				if(sk==2) {
					if(tsk==1) {
						double damage = 10;
						s_damage += damage;
						if(e.getHealth()+1 >= damage) {
							e.setHealth(e.getHealth()-damage);
						} else {
							Skill.remove(e, player);
						}
						lose = 0;
					} else {
						if(tsk==3) {

						} else {
							cooldown[3] = cooldown[2] = cooldown[1] = setcooldown[1]/5f;
						}
						lose++;
					}
				}
			},80);
		} else {
			cooldown[3] = cooldown[2] = cooldown[1] = 0;
			return false;
		}
		return true;
	}

	@Override
	public boolean skill1() {
		if(!isps) {
			sk = 1;
			Jjan();
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!isps) {
			sk = 2;
			Jjan();
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!isps) {
			sk = 3;
			Jjan();
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(isps) {
			player.setVelocity(player.getLocation().getDirection().multiply(5));
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(1.2));
		}
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c75:sk0")+ "] : "+ lose+" / 5");		
			if(isps) scoreBoardText.add("&c ["+Main.GetText("c75:sk0")+ "] : "+ AMath.round(ps/20,2));
		}
		if(lose >=5 && !isps) {
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player, "c75sp");
			player.setMaxHealth(300);
			player.setHealth(300);
			ps = 600;
			ARSystem.potion(player, 1, 100000, 30);
		}
		if(ps > 0) {
			ps--;
			if(ps == 0) Skill.remove(player, player);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(isps) e.setDamage(999);
		} else {
			if(isps) e.setDamage(e.getDamage()*0.01);
			if(Rule.c.get(e.getDamager()) != null) {
				String s = Main.GetText("c"+Rule.c.get(e.getDamager()).getCode()+":tag");
				if(s.indexOf("tg4") == -1) {
					e.setDamage(e.getDamage()*0.5);
				}
			}
		}
		return true;
	}
	
}
