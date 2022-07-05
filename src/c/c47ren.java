package c;

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
import buff.Nodamage;
import c2.c62shinon;
import event.Skill;
import manager.AdvManager;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c47ren extends c00main{

	int bullet = 50;
	int stack = 0;
	int tropy = 0;
	
	public c47ren(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 47;
		load();
		text();
		bullet = 50;
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[4] *= 0.3;
	}
	

	@Override
	public boolean skill1() {
		if(bullet > 0) {
			if(bullet>0) { bullet--; shrot();}
			delay(()->{if(bullet>0) { bullet--; shrot();}}, 2);
			delay(()->{if(bullet>0) { bullet--; shrot();}}, 4);
			if(ARSystem.gameMode == modes.LOBOTOMY) delay(()->{if(bullet>0) { bullet--; shrot();}}, 6);
			if(ARSystem.gameMode == modes.LOBOTOMY) delay(()->{if(bullet>0) { bullet--; shrot();}}, 8);
			if(ARSystem.gameMode == modes.LOBOTOMY) delay(()->{if(bullet>0) { bullet--; shrot();}}, 10);
		}
		return true;
	}
	void shrot(){
		float sp = 10f;
		Location l = player.getLocation();

		l.setPitch((float) (l.getPitch() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		l.setYaw((float) (l.getYaw() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		
		ARSystem.playSound((Entity)player, "c47gun",(0.5f + (1.5f-(1.5f/bullet+1))));
		ARSystem.spellLocCast(player, l, "c47_s1");
		ARSystem.playerAddRotate(player,0,(float) -4);
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c47s2",(float) 1);
		ARSystem.potion(player, 1, 10, 20);
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(stack < 25) {
			ARSystem.playSound((Entity)player, "c47s3",(float) 1);
			skill("c47_s3");
		} else {
			spskillen();
			spskillon();
			skill("c47_sp");
			stack = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c47s4",(float) 1);
		ARSystem.playSound((Entity)player, "c47s42",(float) 1);
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		skill("c47_s4");
		bullet = 50;
		return true;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			tropy++;
			if(tropy >= 2) {
				Rule.playerinfo.get(player).tropy(47,1);
			}
		}
	}

	@Override
	public boolean tick() {
		scoreBoardText.add("&c ["+Main.GetText("c47:ps")+ "] : "+ bullet);
		if(psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c47:sk0")+ "] : "+ stack +" / 25");
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			stack++;
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c08yuuki) {
					is = "yuuki";
					break;
				}
				if(Rule.c.get(e) instanceof c02kirito){
					is = "kirito";
					break;
				}
				if(Rule.c.get(e) instanceof c62shinon){
					is = "kirito";
					break;
				}
			}
		}
		
		if(is.equals("yuuki")) {
			ARSystem.playSound((Entity)player, "c47yuuki");
		} else if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c47kirito");
		} else if(is.equals("sinon")) {
			ARSystem.playSound((Entity)player, "c47sinon");
		}else {
			ARSystem.playSound((Entity)player, "c47db");
		}
		
		return true;
	}
}
