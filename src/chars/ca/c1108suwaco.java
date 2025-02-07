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

public class c1108suwaco extends c00main{
	boolean s2 = false;
	Location s2l = null;
	int sk4 = 0;
	TargetMap<LivingEntity, Double> ps = new TargetMap<>();
	
	public c1108suwaco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1108;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c108s1");
		skill("c1108_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c108s2");
		skill("c1108_s2");
		s2 = true;
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c108s3");
		skill("c1108_s3");
		return true;
	}

	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c108s4");
		ARSystem.giveBuff(player, new Stun(player), 200);
		ARSystem.potion(player, 14, 200, 1);
		skill("c108_s4");
		sk4 = 200;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(6,player);
			target.setVelocity(new Vector(0,1.5,0));
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Stun(target), 200);
			ARSystem.giveBuff(target, new Nodamage(target), 160);
			ARSystem.giveBuff(target, new Noattack(target), 200);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(5,player);
			ARSystem.giveBuff(target, new Stun(target), 40);
			delay(()->{
				ARSystem.spellCast(player, target, "c1108_s23");
			},10);
		}

		if(n.equals("4")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			if(s2l != null) {
				Location lc = ULocal.lookAt(s2l.clone(),target.getLocation().clone());
				target.setVelocity(lc.getDirection().multiply(1.2));
			}
			if(s2) {
				s2 = false;
				delay(()->{
					s2 = true;
					ARSystem.spellCast(player, target, "c1108_s23");
				},2);
			}
		}
		if(AMath.random(100) <= 3) {
			ps.set(target, 30);
		}
	}
	
	@Override
	public void LocmakerSkill(Location loc, String name) {
		if(name.equals("wako")) {
			s2l = loc;
		}
	}
	
	@Override
	public boolean tick() {
		for(LivingEntity e : ps.get().keySet()) {
			if(e == null || e.isDead()) ps.add(e, -100);
			if((e instanceof Player) && (Rule.c.get(player) == null|| ((Player) e).getGameMode() == GameMode.SPECTATOR)) ps.add(e, -100);
			ARSystem.spellCast(player, e, "c1108_pe");
			if(tk%2 == 0 && AMath.random(100) <= 3) {
				ARSystem.spellCast(player, e, "c1108_p");
				for(int i = 0; i<10; i++) if(cooldown[i] > 0) cooldown[i] -= 1;
			}
		}
		ps.addAll(-0.05);
		ps.removes();
		

		if(sk4>0 && sk4%2 == 0) ARSystem.heal(player, 1);
		if(player.isSneaking() && sk4 > 0) {
			skill("c108_s4_remove");
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			sk4 = 0;
			Rule.buffmanager.selectBuff(player, "stun").setTime(0);
		}
		if(sk4 > 0) sk4--;
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(AMath.random(100) <= 5) {
				ps.set((LivingEntity)e.getEntity(), 30);
			}
		} else {
			if(sk4 > 0 && e.getDamage() > 8) e.setDamage(0);
		}
		return true;
	}
	
}
