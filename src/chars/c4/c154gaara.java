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
import buff.Medusa;
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

public class c154gaara extends c00main{
	int stack = 0;
	int pt = 0;
	int s4 = 0;
	int s4c = 0;
	Location s4l;
	Location s4cl;
	TargetMap<LivingEntity, Double> target;
	
	Location spl;
	int sp = 0;
	
	int spstack = 0;
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c154gaara(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 154;
		load();
		text();
		c = this;
		target = new TargetMap<>();
	}

	@Override
	public boolean skill1() {
		if(s4 > 0) {
			cooldown[1] = 0;
			return false;
		}
		skill("c154_s1");
		ARSystem.playSound((Entity)player, "c154s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(s4 > 0 || target.get().size() <= 0) {
			cooldown[2] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c154s2");
		for(LivingEntity e : target.get().keySet()) {
			ARSystem.spellCast(player, e, "c154_s2");
			ARSystem.giveBuff(e, new Stun(e), 8);
			delay(()->{
				ARSystem.spellCast(player, e, "c154_s22");
				e.setNoDamageTicks(0);
				e.damage(4,player);
			},20);
		}
		target.clear();
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(s4 > 0) {
			cooldown[3] = 0;
			return false;
		}
		if(player.isSneaking() && player.isOnGround() && spstack >= 600 && skillCooldown(0)) {
			spstack = 0;
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c154sp");
			ARSystem.giveBuff(player, new TimeStop(player), 120);
			delay(()->{
				sp = 800;
				spl = player.getLocation().clone();
				spl.setPitch(0);
			},120);
		} else {
			skill("c154_s3");
			ARSystem.playSound((Entity)player, "c154s3");
			for(int i = 0; i < 10; i++) {
				delay(()->{
					spstack += 8;
					tropy += 8;
				},8*i);
			}
		}
		return true;
	}

	@Override
	public boolean skill4() {
		s4l = player.getLocation();
		s4 = 160;
		ARSystem.playSound((Entity)player, "c154s3");
		s4cl = player.getLocation();
		s4c = 0;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 20);
		}
		if(n.equals("2")) {
			ARSystem.addBuff(target, new Medusa(target), 5);
		}
		if(n.equals("3")) {
			 target.setNoDamageTicks(0);
			 target.damage(1,player);
			ARSystem.giveBuff(target, new Silence(target), 5);
		}
		if(n.equals("4")) {
			 target.setNoDamageTicks(0);
			 target.damage(3,player);
			 target.setVelocity(target.getVelocity().add(spl.getDirection().multiply(1.5f).setY(0.3f)));
		}
		this.target.set(target, 40);
	}
	
	int tropy = 0;
	@Override
	public boolean tick() {
		if(sp > 0 && spl != null) {
			sp--;
			if(sp%2 == 0) ARSystem.spellLocCast(player, spl, "c154_sp");
		}
		if(tropy >= 100) {
			Rule.playerinfo.get(this.player).tropy(154,1);
		}
		if(s4 > 0) {
			s4--;
			spstack++;
			tropy++;
			if(player.isSneaking()) s4 = 0;
			
			if(s4l.distance(player.getLocation()) > 0.2) {
				s4l.setPitch(player.getLocation().getPitch());
				s4l.setYaw(player.getLocation().getYaw());
				player.teleport(s4l);
				s4l = player.getLocation();
			}
			if(s4%4 == 0) {
				skill("c154_s4");
			}
		}
		if(stack < 3) {
			pt++;
			if(pt > 60) {
				pt = 0;
				stack++;
			}
		} else {
			pt = 0;
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c154:ps")+ "] : "+ stack +" / 3");
			scoreBoardText.add("&c ["+Main.GetText("c154:sk0")+ "] : "+ AMath.round(Math.min(600,spstack)*0.05,2) +" / 30");
		}

		for(LivingEntity e : target.get().keySet()) {
			ARSystem.spellCast(player, e, "c154_s2p");
			ARSystem.potion(e, 2, 20, 1);
		}
		
		target.addAll(-1);
		target.removes();
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(stack > 0) {
				stack--;
				skill("c154_p");
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
	
}
