package c;

import java.util.ArrayList;
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
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import util.AMath;
import util.MSUtil;
import util.Text;

public class c18misogi extends c00main{
	int ticks = 0;
	int bookmake = 0;
	boolean ps = true;
	LivingEntity en;
	boolean dbcount = true;
	boolean skbuff = false;
	int count = 0;
	
	double maxHp = 2;
	
	public c18misogi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 18;
		load();
		text();
		
	}
	
	@Override
	public void setStack(float f) {
		bookmake = (int) f;
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1_1");
		if(bookmake > 0) delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_1");}}, 1);
		if(bookmake > 1) delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_2");}}, 2);
		if(bookmake > 2) delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_3");}}, 3);
		if(bookmake > 3) delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_4");}}, 4);
		if(bookmake > 4) {
			for(int i=3; i <bookmake;i++) {
				if(bookmake > 0) delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_"+AMath.random(5));}}, i+1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		for(int i = 0; i < bookmake; i++) {
			skill("c"+number+"_s2_b");
		}
		return true;
	}

	
	@Override
	protected boolean skill9(){
		if(dbcount) {
			player.getWorld().playSound(player.getLocation(), "c"+number+"db", 1, 1);
		} else {
			player.getWorld().playSound(player.getLocation(), "c"+number+"db2", 1, 1);
		}
		dbcount = !dbcount;
		return false;
	}
	
	@Override
	public boolean tick() {
		ticks++;
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c18:ps")+ "]&f : "+ bookmake);
		}
		if(ticks%10 == 0 && !ps) {
			if(en instanceof Player) {
				if(((Player)en).getGameMode() == GameMode.SPECTATOR) {
					en = null;
					player.setMaxHealth(maxHp);
					ps = true;
				}
			} 
			else if(en.isDead() || en == null) {
				en = null;
				player.setMaxHealth(maxHp);
				ps = true;
			}
		}
		
		ticks%=20;
		return false;
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(skillCooldown(0)&&!spben) {
			ARSystem.giveBuff(player, new TimeStop(player), 60);
			player.setMaxHealth(maxHp);
			spskillen();
			spskillon();
			bookmake+=2;
			
			Player[] p = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
			player.setHealth(player.getMaxHealth());
			if(caster instanceof Player) {
				ARSystem.spellCast(player, player, "c18_e2");
				ARSystem.spellCast(player, player, "c18_e2");
				ARSystem.spellCast(player, player, "c18_e2");
				ARSystem.spellCast(player, player, "c18_e2");
				ARSystem.spellCast(player, player, "c18_e2");
				if(Rule.c.get(caster) != null && Rule.c.get(caster).getCode() == 12) {
					ARSystem.playSound(p, "c18spc");
					delay(new Runnable() { @Override public void run() {ARSystem.playSoundAll("c18sp");}}, 20);
					delay(new Runnable() { @Override public void run() {ARSystem.playSoundAll("c18spc2");}}, 50);
					delay(new Runnable() { @Override public void run() {
						passive(caster);}
					}, 90);
				} else if(Rule.c.get(caster) != null && Rule.c.get(caster).getCode() == 70) {
					ARSystem.playSound(p, "c18spc");
					delay(new Runnable() { @Override public void run() {ARSystem.playSoundAll("c18sp");}}, 20);
					delay(new Runnable() { @Override public void run() {ARSystem.playSoundAll("c18spc2");}}, 50);
					delay(new Runnable() { @Override public void run() {
						ARSystem.playSoundAll("c70t4");
						passive(caster);}
					}, 90);
				} else  {
					ARSystem.playSound(p, "c18sp");
					delay(new Runnable() { @Override public void run() {ARSystem.playSound(p, "c18sp2");}}, 30);
					delay(new Runnable() { @Override public void run() {passive(caster);}}, 50);
				}
			} else {
				ARSystem.playSound(p, "c18sp");
				delay(new Runnable() { @Override public void run() {ARSystem.playSound(p, "c18sp2");}}, 30);
				delay(new Runnable() { @Override public void run() {passive(caster);}}, 70);
			}
		return false;
		} else {
			return true;
		}
	}
	public void passive(Entity e) {
		ARSystem.giveBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 40);
		ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 60);
		ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 60);
		ARSystem.playSound(player, e, "c18p2");
		en = (LivingEntity)e;
		double hp = en.getMaxHealth();
		double maxHps = player.getMaxHealth();
		count++;
		if(count >= 3) {
			Rule.playerinfo.get(player).tropy(18,1);
		}
		delay(new Runnable() { @Override public void run()
			{
				ARSystem.spellCast(player,e,"c18_p");
				ARSystem.playSound(player, e, "c18p1");
				en.setMaxHealth(maxHps);
				player.setMaxHealth(hp);
				player.setHealth(hp);
				bookmake++;
			}
		}, 40);
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(ps && player.getMaxHealth() - e.getDamage() <= 0) {
				ps = false;
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				passive(e.getDamager());
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
