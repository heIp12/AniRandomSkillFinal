package chars.ca;

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

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.TimeStop;
import chars.c.c00main;

import util.AMath;
import util.MSUtil;
import util.Text;

public class c2400sinobu extends c00main{

	int ticks = 0;
	
	int shdow = -1;
	LivingEntity target;
	
	public c2400sinobu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		repset();
		number = 1024;
		load();
		text();
		isps = true;
		Rule.Var.open(player.getName()+".c"+number+"Sp",true);
	}
	
	@Override
	public boolean skill1() {
		if(shdow == -1) {
			ARSystem.playSound((Entity)player, "c1024s");
			skill("c"+number+"_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		if(shdow == -1) for(int i=0;i<3;i++) ARSystem.spellCast(player,player, "c1024_s2_i1");
		player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(10)).add(0,0.2,0));
		ARSystem.playSound((Entity)player, "c24s3");
		if(shdow == -1) for(int i=0;i<3;i++) ARSystem.spellCast(player,player, "c1024_s2_i1");
		return true;
	}

	
	@Override
	public boolean skill3() {
		if(shdow == -1) {
			shdow = 15;
			for(int i=0;i<2;i++) ARSystem.spellCast(player,player, "c1024_s2_i1");
			skill("c1024_s3");
			ARSystem.potion(player, 14, 300, 0);
			ARSystem.giveBuff(player, new Nodamage(player), 300);
			ARSystem.giveBuff(player, new Noattack(player), 300);
			cooldown[3] = 0;
			ARSystem.playSound((Entity)player, "c24s3",0.5f);
			player.getWorld().setTime(20000);
		}
		else{
			shdow = -1;
			for(int i=0;i<10;i++) ARSystem.spellCast(player,player, "c1024_s2_i1");
			skill("c1024_s3-2");
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			ARSystem.playSound((Entity)player, "c24s3",2f);
			
		}
		return true;
	}



	@Override
	public boolean tick() {
		ticks++;
		if(ticks < 20 && ARSystem.getPlayerCount() < 4) {
			ticks+=20;
		}
		if(tk%20 == 0 && shdow > 0) {
			shdow--;
			scoreBoardText.add("&c ["+Main.GetText("c24:sk3")+ "]&f : "+ shdow);
		}
		if(shdow == 0) {
			shdow = -1;
			cooldown[3] = setcooldown[3];
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			ARSystem.playSound((Entity)player, "c24s3");
		}
		
		ticks%=40;
		return false;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			ARSystem.overheal(player, e.getDamage()/2);
		} else {
			
		}
		return true;
	}
}
