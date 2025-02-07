package chars.c3;

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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import ars.gui.G_Saito;
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.Ice;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Nodie;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.c2.c69himi;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c150tenshi extends c00main{
	boolean skill = false;
	boolean s1 = false;
	int sk4 = 0;
	int s1c = 0;
	int sp = 0;
	int spt = 0;
	@Override
	public void setStack(float f) {
		s1c = (int)f;
		super.setStack(f);
	}
	
	public c150tenshi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 150;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c150s1"+AMath.random(4));
		skill("c150_d"+AMath.random(2));
		skill("c150_s1");
		skill = true;
		s1 = true;
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c150s2");
		for(int i = 0; i < 2 + ARSystem.box(player, new Vector(10,6,10), box.TARGET).size();i++) {
			int o = i;
			delay(()->{
				player.sendTitle("§cHit : " + o,"",0,10,0);
				for(int j=0;j<3;j++) skill("c150_s2");
				for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(2,player);
				}
			},i*3);
		}
		skill = true;
		return true;
	}
	

	@Override
	public boolean skill3() {
		for(int i =0; i< 200; i++) {
			delay(()->{
				ARSystem.heal(player, 0.05);
			},i*1);
		}
		skill = true;
		return true;
	}
	
	Location lc;
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c150s4");
		skill("c150_s4");
		ARSystem.giveBuff(player, new NoCC(player), 200);
		ARSystem.potion(player, 1, 60, 4);
		skill = true;
		return true;
	}

	List<Entity> spc;
	@Override
		public boolean skill5() {
		if(s1c > 6 && skillCooldown(0)) {
			s1c = 0;
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c150sp");
			skill("c150_sp");
			lc = player.getLocation();
			player.setVelocity(new Vector(0,1,0));
			delay(()->{
				player.setVelocity(new Vector(0,-2,0));
			},10);
			spc = ARSystem.box(player, new Vector(20,14,20), box.TARGET);
			if(spc.size() >= 5) Rule.playerinfo.get(player).tropy(150, 1);
			for(Entity e : spc) {
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(8,player);
				ARSystem.spellCast(player, e, "c150_spe");
			}
			sp = spc.size();
			spt = 600;
			delay(()->{
				spc.clear();
			},200);
		}
		return true;
	}
	@Override
	public boolean tick() {
		if(sk4 > 0) sk4--;
		if(spt > 0) {
			spt--;
			if(spt <= 0) sp = 0;
		}
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c150:sk0")+"] : "+ s1c+" / 7");
			if(spt > 0) {
				scoreBoardText.add("&c [Power] : "+sp +" "+AMath.round(spt *0.05, 2)+"t");
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			s1c++;
			if(s1) {
				s1 = false;
				delay(()->{
					player.setVelocity(player.getLocation().getDirection().multiply(1.8f));
				},10);
			}
		}
		if(n.equals("2") && spc != null && spc.contains(target)) {
			spc.remove(target);
			target.setNoDamageTicks(0);
			target.damage(18,player);
			skill("c150_spl");
			target.setVelocity(ULocal.lookAt(target.getLocation(), lc).getDirection().multiply(1.5f));
		}
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 3 && skillCooldown(4)) skill4();
		return super.key(e);
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
			if(skill && e.getDamage() <= 1) {
				skill = false;
				ARSystem.playSound((Entity)player, "c150p"+AMath.random(3));
				skill("c150_p");
				e.setDamage(e.getDamage() + 3);
				ARSystem.giveBuff(player, new Nodamage(player), 10);
				for(int i =0;i<10;i++) {
					if(cooldown[i] > 0) {
						cooldown[i] *= 0.8f;
						cooldown[i] -= 2;
					}
				}
			}
			

			e.setDamage(e.getDamage() + sp);
			if(cooldown[3] > 0) cooldown[3] -= e.getDamage();
		} else {
			if(sk4 > 0) {
				e.setDamage(e.getDamage() * 1.35f);
			}
		}
		return true;
	}
}
