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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
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
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.ca.c1000gay;
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

public class c138cotoco extends c00main{
	int stack = 0;
	boolean kill = false;
	Player killer;
	Player death;
	List<String> word1 = new ArrayList<String>();
	List<String> word12 = new ArrayList<String>();
	List<String> word2 = new ArrayList<String>();
	List<String> word3 = new ArrayList<String>();
	List<String> word4 = new ArrayList<String>();
	List<String> word5 = new ArrayList<String>();
	List<String> word6 = new ArrayList<String>();
	List<String> word7 = new ArrayList<String>();
	List<String> word8 = new ArrayList<String>();
	List<String> word9 = new ArrayList<String>();
	List<String> word10 = new ArrayList<String>();
	HashMap<Player,Integer> code;
	
	public c138cotoco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 138;
		load();
		text();
		c = this;
		hpCost(10, false);
		for(String s : Text.get("c138:dbf1").split(",")) word1.add(s);
		for(String s : Text.get("c138:dbf2").split(",")) word12.add(s);
		for(String s : Text.get("c138:kill").split(",")) word2.add(s);
		for(String s : Text.get("c138:heal").split(",")) word3.add(s);
		for(String s : Text.get("c138:escape").split(",")) word4.add(s);
		for(String s : Text.get("c138:dmg").split(",")) word5.add(s);
		for(String s : Text.get("c138:heal2").split(",")) word6.add(s);
		for(String s : Text.get("c138:str").split(",")) word7.add(s);
		word8.add("로리콘");
		word8.add("로리신");
		word8.add("레퀴엠");
		word9.add("우이빔");
		word10.add("게이");
		word10.add("남자");
		word10.add("동성");
	}

	@Override
	public void setStack(float f) {
		stack = (int)f;
		if(stack == 11223344) {
			if(!isps) {
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c138sp");
			}
		}
	}

	@Override
	public boolean skill1() {
		player.sendMessage("광화: "+Text.get("c138:dbf1"));
		player.sendMessage("공포: "+Text.get("c138:dbf2"));
		player.sendMessage("킬: "+Text.get("c138:kill"));
		player.sendMessage("부활: "+Text.get("c138:heal"));
		player.sendMessage("도망: "+Text.get("c138:escape"));
		player.sendMessage("피해: "+Text.get("c138:dmg"));
		player.sendMessage("회복: "+Text.get("c138:heal2"));
		player.sendMessage("강화: "+Text.get("c138:str"));
		return true;
	}

	@Override
	public boolean skill4() {
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(!kill && e == killer && p == death) {
			if(!isps) {
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c138sp");
				stack += 150;
				stack += code.size() * 50;
				if(e == p) {
					stack = 200;
				} else {
					Rule.playerinfo.get(player).tropy(138, 1);
				}
				s_score += 10000;
				score += 10000;
			}
		}
		kill = true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() != player || !isps) {
			if(e.getPlayer() == player) {
				switch(AMath.random(3)) {
					case 1:
						ARSystem.playSound((Entity)player, "c138next" + AMath.random(3));
						break;
					case 2:
						ARSystem.playSound((Entity)player, "c138no" + AMath.random(2));
						break;
					case 3:
						ARSystem.playSound((Entity)player, "c138ok" + AMath.random(4));
				}
			}
			return true;
		}
		String s = e.getMessage();
		Player p = null;
		for(Player pl : Bukkit.getOnlinePlayers()) {
			if(s.contains(pl.getName())) {
				if(p == null) p = pl;
				s=s.replace(pl.getName(), "§d"+pl.getName()+"§f");
			}
		}
		int stk = stack;
		if(stack >= 40) for(String st : word1) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§5"+st+"§f");
			stack -=40;
			ARSystem.giveBuff(p, new Rampage(p), 100);
			
		}
		if(stack >= 30) for(String st : word12) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§5"+st+"§f");
			stack -=30;
			ARSystem.giveBuff(p, new Panic(p), 100);
		}
		if(stack >= 75) for(String st : word3) if(code.get(p) != null && s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§a"+st+"§f");
			stack -=75;
			if(code.get(p) != null) Rule.c.put(p, GetChar.get(p, plugin, ""+code.get(p))); ARSystem.playSoundAll("c138stun");
			
		}
		if(stack >= 40) for(String st : word6) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§a"+st+"§f");
			stack -=40;
			ARSystem.heal(p, p.getMaxHealth() * 0.3 + 4);
			
		}
		if(stack >= 200) for(String st : word2) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§4"+st+"§f");
			stack -=100;
			Skill.remove(p, player); ARSystem.playSoundAll("c138kill");
		}
		if(stack >= 70) for(String st : word7) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§e"+st+"§f");
			stack -=70;
			 ARSystem.giveBuff(p, new PowerUp(p), 200, 1);
			
		}
		if(stack > 50) {
			for(String st : word5) {
				if(s.contains(st) && skillCooldown(0)) {
					s=s.replace(st, "§4"+st+"§f");
					stack -=50;
					p.damage(10,player); ARSystem.playSoundAll("c138light");
				}
			}
		}
		if(stack >= 40) {
			for(String st : word4) {
				if(s.contains(st) && skillCooldown(0)) {
					s=s.replace(st, "§b"+st+"§f");
					ARSystem.playSoundAll("c138sk2");
					p.teleport(Map.randomLoc());
					stack -= 40;
					break;
				}
			}
		}
		Player pl = p;
		
		if(stack >= 60) for(String st : word8) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§d"+st+"§f");
			stack -=60;
			ARSystem.playSoundAll("ui1");
			delay(()->{
				ARSystem.giveBuff(pl, new Fascination(pl, player), 200 , 0.5);
			},50);
		}
		if(stack >= 300) for(String st : word9) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§e§d"+st+"§f");
			stack -=300;
			ARSystem.playSoundAll("ui3");
			delay(()->{
				for(int i =0; i<70;i++) {
					delay(()->{
						pl.setNoDamageTicks(0);
						pl.damage(0.5,player);
						ARSystem.heal(pl, 1);
					},i);
				}
				delay(()->{
					Skill.quit(pl);
				},70);
			},30);
		}
		if(stack >= 800) for(String st : word10) if(s.contains(st) && skillCooldown(0)) {
			s=s.replace(st, "§d§l"+st+"§f");
			stack -=800;
			ARSystem.giveBuff(p, new TimeStop(p), 100);
			for(int i=0;i<10;i++) {
				delay(()->{ARSystem.playSound((Entity)pl, "c1000ang");},10*i);
			}
			delay(()->{
				Rule.c.put(pl, new c1000gay(pl, plugin, null));
			},100);
			
		}
		e.setMessage("§f"+s);

		if(stk == stack) {
			switch(AMath.random(3)) {
			case 1:
				ARSystem.playSound((Entity)player, "c138next" + AMath.random(3));
				break;
			case 2:
				ARSystem.playSound((Entity)player, "c138no" + AMath.random(2));
				break;
			case 3:
				ARSystem.playSound((Entity)player, "c138ok" + AMath.random(4));
			}
		}
		return super.chat(e);
	}

	@Override
	public boolean tick() {
		if(psopen && killer != null) scoreBoardText.add("&c ["+Main.GetText("c138:sk0")+ "] : Killer : " + killer.getName());
		if(psopen && death != null) scoreBoardText.add("&c ["+Main.GetText("c138:sk0")+ "] : Death : " + death.getName());
		if(psopen) scoreBoardText.add("&c ["+Main.GetText("c138:t1")+"] : " + stack);
		
		if(death == null && player.getOpenInventory().getTitle().equals("container.crafting")) {
			invskill = new InvSkill(player) {
				@Override
				public void Start(String st) {
					if(killer == null) {
						killer = Bukkit.getPlayer(st);
					} else {
						death = Bukkit.getPlayer(st);
						player.closeInventory();
						player.sendTitle("", killer.getName() +" 가 " + death.getName() + " 를 죽여야 합니다!",40,40,40);
					}
				}
			};

			Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
			Player.remove(player);
			Inventory.getlist(invskill,player,Player);
			invskill.openInventory(player);
		}
		if(code == null) {
			code = new HashMap<>();
			for(Player p : Rule.c.keySet()) {
				code.put(p, Rule.c.get(p).getCode());
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
		} else {
			if(!(e.getDamager() instanceof Player)) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(Rule.c.get(e.getDamager()) != null && isps) {
				String s = Main.GetText("c"+Rule.c.get(e.getDamager()).getCode()+":tag");
				if(s.indexOf("tg4") == -1) {
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				}
			}
		}
		return true;
	}
	

}
