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
import buff.Barrier;
import buff.Buff;
import buff.Nodamage;
import buff.Reflect;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c2.c62shinon;
import chars.c2.c63micoto;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;
import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Text;
import util.ULocal;

public class c2501Systers extends c00main{
	int s1 = 3;
	
	public c2501Systers(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 3025;
		load();
		text();
		setcooldown[9] = 8f;
	}
	
	@Override
	public boolean skill1() {
		for(int i = 0; i < s1; i++) {
			delay(()->{shrot();},2*i);
		}
		delay(()->{
			ARSystem.playSound((Entity)player, "c3025s1");
		},2*s1+2);
		s1 = 3;
		return true;
	}

	void shrot(){
		float sp = 10f;
		Location l = player.getLocation();

		l.setPitch((float) (l.getPitch() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		l.setYaw((float) (l.getYaw() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		
		ARSystem.playSound((Entity)player, "0gun2",1.3f);
		ARSystem.spellLocCast(player, l, "c3025_s1");
		ARSystem.playerAddRotate(player,0,(float) -4);
	}
	
		
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c3025s2");
		skill("c3025_s2");
		skill("c3025_s2e");
		return true;
	}

	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c3025s3"+AMath.random(2));
		s1 = 9;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(Rule.c.get(e).number == 3025) {
			skillmult += 0.33;
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 40);
		}
	}
	
	
}
