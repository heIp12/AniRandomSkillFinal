package chars.ca;

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
import buff.PowerUp;
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

public class c9200susu extends c00main{
	public Player owner;
	
	int s2 = 0;
	int s2t = 0;
	
	public c9200susu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1092;
		load();
		text();
		c = this;
		if(p != null) ARSystem.potion(player, 14, 100000, 1);
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c1092s3");
		ARSystem.spellCast(owner, "c1092_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(s2 > 0) {
			((c9201flan)Rule.c.get(owner)).Attack();
			s2--;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1092s1");
		ARSystem.potion(owner, 1, 40, 4);
		ARSystem.giveBuff(owner, new PowerUp(owner),40 , 0.5);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c92s3");
		ARSystem.heal(owner,15);
		return true;
	}
	
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == owner){
			Skill.remove(player, player);
			ARSystem.Stop();
		}
	}

	@Override
	public boolean tick() {
		s_damage = Rule.c.get(owner).s_damage;
		s_score = Rule.c.get(owner).s_score;
		score = Rule.c.get(owner).score;
		
		if(s2 < 8) {
			s2t++;
			if(s2t > 100) {
				s2t = 0;
				s2++;
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c1092:sk2")+"] : "+s2 +" / 8");
		}
		if(owner != null) {
			player.teleport(owner.getLocation().add(0,2,0));
		}
		if(Rule.c.size() == 2) {
			Skill.win(player.getName() +" | " + owner.getName());
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		e.setDamage(0);
		e.setCancelled(true);
		return false;
	}
}
