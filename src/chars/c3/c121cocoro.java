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

public class c121cocoro extends c00main{
	int s1 = 0;
	int head = 1;
	int sethead = 0;
	
	int sp = 0;
	
	public c121cocoro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 121;
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
		s1++;
		if(s1 == 1) {
			skill("c121_a1");
			ARSystem.playSound(player, "0sword", 1);
			skill("c121_s1");
		}
		if(s1 == 2) {
			skill("c121_a2");
			ARSystem.playSound(player, "0sword", 1.2f);
			skill("c121_s1");
		}
		if(s1 == 3) {
			skill("c121_a3");
			skill("c121_a4");
			ARSystem.playSound(player, "0sword2", 0.7f);
			if(sethead == 4 || isps) {
				skill("c121_s1-2");
			}
			s1 = 0;
		}
		if(sethead == 2|| isps) player.setVelocity(player.getLocation().getDirection().multiply(1).setY(0.2));
		if(sethead == 3|| isps) cooldown[1] -= 0.5;
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c121_pr");
		skill("c121_p"+head);
		skill("c121_s2-"+head);
		ARSystem.playSound((Entity)player, "c121s2"+AMath.random(4));
		if(sethead != head) sp++;
		sethead = head;
		if(sethead == 1) player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0.2));
		return true;
	}
	

	@Override
	public boolean skill3() {
		cooldown[2] = 0;
		head = AMath.random(4);
		while(head == sethead) head = AMath.random(4);
		ARSystem.playSound((Entity)player, "c121s3");
		skill("c121_pr");
		skill("c121_p"+head);
		sp++;
		sethead = head;
		return true;
	}
	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			head--;
			if(head < 1) head = 4;
		} else {
			head++;
			if(head > 4) head = 1;
		}
		player.sendTitle(""+Main.GetText("c121:p"+head), buffText);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			if(sethead == 3|| isps) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},200);
			}
			for(int i=0;i<10;i++) if(cooldown[i] > 0) cooldown[i] -=0.5f;
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c121:sk2")+ "] : &f" + Main.GetText("c121:p"+sethead));
			scoreBoardText.add("&c ["+Main.GetText("c121:sk4")+ "] : &f" + Main.GetText("c121:p"+head));
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c121:sk0")+ "] : &f" + sp +" / 20");
		}
		if(sp >= 30 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c121sp");
		}
		if(isps&& tk%20 == 0) {
			skill("c121_sp1");
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sethead == 1 || isps) ARSystem.heal(player, e.getDamage());
		} else {
			if(isps) e.setDamage(e.getDamage() * 0.6);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player,"c121db"+AMath.random(5));
		return true;
	}
}
