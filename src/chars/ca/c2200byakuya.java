package chars.ca;

import java.util.List;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import chars.c.c00main;
import chars.c.c31ichigo;
import event.Skill;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;

public class c2200byakuya extends c00main{
	int ticks = 0;
	boolean start = true;
	
	public c2200byakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1022;
		load();
		text();
		ARSystem.playSound(p, "c22select");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c22s1");
		skill("c1022_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c1022_s2");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(start){
			start = false;
			for(int i=0;i<30;i++) {
				skill("c1022_p");
			}
		}
		return false;
	}
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c20select");
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*10);
		} else {

		}
		return true;
	}
}
