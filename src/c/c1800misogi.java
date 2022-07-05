package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.Nodamage;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.Text;

public class c1800misogi extends c00main{
	int ticks = 0;
	boolean ps = true;
	HashMap<LivingEntity,Integer> attack;
	private boolean dbcount;
	
	int deathcount = 0;
	
	public c1800misogi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1018;
		load();
		text();
		attack = new HashMap<LivingEntity,Integer>();
		ARSystem.playSound(p, "c18select");
	}
	

	
	@Override
	public boolean skill1() {
		ARSystem.playSound(player, "c18b");
		skill("c"+number+"_s1_1");
		if(AMath.random(10) <= 3) {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		for(LivingEntity e : attack.keySet()) {
			if(!e.isDead() && !(e instanceof Player && ((Player) e).getGameMode() == GameMode.SPECTATOR)) {
				for(int i = 0; i < attack.get(e);i++) {
					ARSystem.giveBuff(e, new Stun(e), 20);
					delay(()->{
						Location loc = e.getLocation().clone();
						loc.add(0,1.2,0);
						loc.setYaw(AMath.random(360));
						loc.setPitch(AMath.random(180)-90);
						ARSystem.spellLocCast(player, loc, "c1018_s2");
						ARSystem.playSound(e, "c18a2");
						if(Rule.c.get(e) != null) {
							ARSystem.addBuff(e, new Stun(e), 2);
						}
					},i*2);
				}
				delay(()->{
					e.damage(attack.get(e)*2,player);
					skill("c1018_s2_2");
					ARSystem.playSound(e, "c18a");
				},(attack.get(e)*2)+5);
			}
		}
		return true;
	}

	@Override
	public boolean skill3() {
		ARSystem.playSound(player, "c18p2");
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.potion(player, 14, 60, 0);
		return true;
	}
	

	@Override
	protected boolean skill9(){
		if(dbcount) {
			player.getWorld().playSound(player.getLocation(), "c18db", 1, 1);
		} else {
			player.getWorld().playSound(player.getLocation(), "c18db2", 1, 1);
		}
		dbcount = !dbcount;
		return false;
	}
	
	@Override
	public boolean tick() {
		ticks++;
		return false;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		deathcount++;
	}
	boolean passive(Entity caster){
		
		if(ticks > 600 && ps && (Rule.c.size() > 2 || AMath.random(10) <=3) && ARSystem.gameMode2) {
			ARSystem.playSound((Entity)player, "c18spc");
			ps = false;
			player.setHealth(player.getMaxHealth());
			ARSystem.giveBuff(player, new Nodamage(player), 60);
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			ARSystem.giveBuff((LivingEntity) caster, new TimeStop((LivingEntity) caster), 100);
			
			delay(()->{ARSystem.playSound(player, "c18p1");},30);
			delay(()->{Skill.remove(caster, player);},60);
			delay(()->{
				for(int i = 0; i < 10;i++) {
					Location loc = caster.getLocation().clone();
					loc.add(0,1.2,0);
					loc.setYaw(AMath.random(360));
					loc.setPitch(AMath.random(180)-90);
					ARSystem.spellLocCast(player, loc, "c1018_s2");
				}
				ARSystem.playSound(caster, "c18a2");
			},30);
			delay(()->{
				for(int i = 0; i < 10;i++) {
					Location loc = caster.getLocation().clone();
					loc.add(0,1.2,0);
					loc.setYaw(AMath.random(360));
					loc.setPitch(AMath.random(180)-90);
					ARSystem.spellLocCast(player, loc, "c1018_s2");
				}
				ARSystem.playSound(caster, "c18a2");
			},40);
			delay(()->{
				for(int i = 0; i < 10;i++) {
					Location loc = caster.getLocation().clone();
					loc.add(0,1.2,0);
					loc.setYaw(AMath.random(360));
					loc.setPitch(AMath.random(180)-90);
					ARSystem.spellLocCast(player, loc, "c1018_s2");
				}
				ARSystem.playSound((Entity)caster, "c18a2");
			},50);
			delay(()->{skill("c1018_s2_2");},60);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(passive(caster)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(attack.get(e.getDamager()) == null) {
				attack.put((LivingEntity) e.getDamager(), 0);
			}
			attack.put((LivingEntity) e.getDamager(), attack.get(e.getDamager())+1);
			
			if(player.getHealth() - e.getFinalDamage() < 1 && passive(e.getDamager())) {
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
