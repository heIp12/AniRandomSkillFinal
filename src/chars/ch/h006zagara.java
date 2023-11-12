package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
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
import buff.Buff;
import buff.MapVoid;
import buff.MindControl;
import buff.Noattack;
import buff.Nodamage;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.MapType;
import types.box;

import util.AMath;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class h006zagara extends c00main{

	int sp = 0;
	
	public h006zagara(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 994;
		load();
		text();
		ARSystem.playSound((Entity)player, "zagaraselect");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "zagaras1"+AMath.random(4));
		int size = 4;
		if(isps) size = 6;
		for(int i=0;i<size;i++) {
			delay(()->{
				skill("zagara_s1");
				ARSystem.giveBuff(player, new Stun(player), 5);
			},5*i);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "zagaras2"+AMath.random(2));
		int size = 4;
		if(isps) size = 6;
		for(int i=0; i<size;i++) { 
			skill("hidra");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "zagaras3"+AMath.random(2));
		ARSystem.giveBuff(player, new PowerUp(player), 300, 2);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "zagaras4"+AMath.random(2));
		for(int i=0; i<20;i++) {
			delay(()->{skill("baq_r");},i*2);
		}
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!s.equals("bc994") && Bgm.rep) {
				Bgm.setlockBgm("c994");
			}
		}
		
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player && e != player) {
			sp++;
			if(sp >= 2 && !isps) {
				spskillon();
				spskillen();
				for(int i=0;i<10;i++) {
					cooldown[i] = 0;
					setcooldown[i] *=0.33f;
				}
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "zagaradb");
		return true;
	}
}
