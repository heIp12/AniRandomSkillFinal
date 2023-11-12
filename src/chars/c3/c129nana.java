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

public class c129nana extends c00main{
	int sp = 0;
	int count = 1;
	int mob = 0;
	
	public c129nana(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 129;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		sp = (int)f;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c129s1");
		Map.spawn("nana1", player.getLocation(),count);
		sp = -1;
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c129s2");
		int i = AMath.random(4);
		Map.spawn("nana"+(1+i), player.getLocation(),count);
		
		sp = -1;
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c129s3");
		for(Entity e : ARSystem.box(player,new Vector(50,50,50) , box.ALL)) {
			if(e.getName() != null && e.getName().contains("[소환수]")) {
				e.teleport(player.getLocation());
			}
		}
		return true;
	}


	@Override
	public boolean skill4() {
		List<Player> p = ARSystem.PlayerOnlyBeamBox(player, 20, 5,box.TARGET);
		if(p.size() > 0) {
			ARSystem.playSound((Entity)player, "c129s4");
			for(Entity e : ARSystem.box(player,new Vector(30,30,30) , box.ALL)) {
				if(e.getName() != null && e.getName().contains("[소환수]")) {
					if(e instanceof Monster) {
						Monster en = (Monster)e;
						en.setTarget(p.get(0));
					}
					((LivingEntity)e).damage(0.01,p.get(0));
					ARSystem.giveBuff((LivingEntity) e, new Fascination((LivingEntity) e, p.get(0)), 20,1.5f);
				}
			}
		} else {
			cooldown[4] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0 && sp >= 0) {
			sp++;
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c129:sk0")+ "] : "+ sp+" / 20");
			if(sp >= 20 && !isps) {
				spskillon();
				spskillen();
				ARSystem.playSound((Entity)player, "c129sp");
				count = 2;
			}
		}
		if(tk%10 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c129:p")+ "] : "+ mob*2+"%");	
			mob = ARSystem.box(player, new Vector(10,10,10), box.ALL).size();
			if(mob > 0) {
				for(int i=0;i<10;i++) {
					if(cooldown[i] > 0) cooldown[i] -= mob*0.02f;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			e.setDamage(e.getDamage() * (1 - (0.02*mob)));
			if(!(e.getDamager() instanceof Player)) {
				e.setDamage(0);
				e.setCancelled(true);
				return true;
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c45momo) {
					is = "m";
					break;
				}

			}
		}

		if(is.equals("m")) {
			ARSystem.playSound((Entity)player, "c129momo");
		} else {
			ARSystem.playSound((Entity)player, "c129db");
		}
		return true;
	}
}
