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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.NoHeal;
import buff.Silence;
import buff.Stun;
import buff.Timeshock;
import chars.c.c00main;
import chars.c2.c62shinon;
import chars.c2.c83sora;
import chars.c2.c84siro;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c4400izuna extends c00main{
	LivingEntity target;
	int s2 = 0;
	int s3 = 0;
	
	int i = 1;
	
	public c4400izuna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1044;
		load();
		text();
		ARSystem.playSound(player, "c44sp");
	}
	

	@Override
	public boolean skill1() {
		player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0));
		delay(()->{
			target = (LivingEntity)ARSystem.boxSOne(player, new Vector(5,2,5), box.TARGET);
			if(target != null) {
				s2 = 8;
				s3 = 0;
				ARSystem.playSound((Entity)player, "c44db");
				target.setNoDamageTicks(0);
				target.damage(5,player);
				target.setVelocity(new Vector(0,1.2,0));
				player.setVelocity(new Vector(0,1.3,0));
				ARSystem.giveBuff(target, new Silence(target), 80);
				delay(()->{
					ARSystem.giveBuff(target, new Stun(target), 60);
					ARSystem.giveBuff(player, new Airborne(player), 60);
				},10);
			} else {
				cooldown[1] *= 0.2;
			}
		},10);
		return true;
	}
	@Override
	public boolean skill2() {
		if(s2 > 0) {
			s2--;
			skill("c1044_s2-i"+i++);
			skill("c1044_s2_a");
			if(i > 2) i = 1;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(s3 >= 5) {
			s3 = 0;
			Rule.buffmanager.selectBuffTime(player, "airborne",0);
			player.setVelocity(ULocal.lookAt(player.getLocation(), target.getLocation()).getDirection().multiply(3).setY(-5));
			ARSystem.playSound((Entity)player, "c44s4");
			skill("c1044_s3");
			LivingEntity t =  target;
			delay(()->{
				ARSystem.spellCast(player, t, "bload");
				t.setNoDamageTicks(0);
				t.damage(12,player);
			},10);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			s3++;
			target.setNoDamageTicks(0);
			target.damage(1,player);
		}
	}
	
	@Override
	public boolean tick() {
		
		
		return true;
	}

	int attck = 0;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			ARSystem.heal(player, 2);
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c44select");
		return true;
	}
}
