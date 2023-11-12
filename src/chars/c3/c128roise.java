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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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
import util.Text;

public class c128roise extends c00main{
	boolean t = false;
	
	int spell = 0;
	int spc = 0;
	int spt = 0;
	
	int s1tick = 0;
	
	int time = 30;
	
	String[] sk1;
	String[] sk2;
	String[] sk3;
	
	void revoid(int i) {
		spell = i;
		spc = 0;
		spt = time;
		String str = Text.get("c128:p"+i);
		String[] s = str.split(" ");
		for(int j=0;j<s.length;j++) {
			if(AMath.random(200) <= 1) s[j] = s[j].replace("'", "♥");
			if(AMath.random(200) <= 1) s[j] = s[j].replace("'", "☆");
			if(AMath.random(50) <= 1) s[j] = s[j].replace("'", ";");
			if(AMath.random(50) <= 1) s[j] = s[j].replace("'", "`");
			if(AMath.random(10) <= 2) s[j] = s[j].replace("'", "=");
			if(AMath.random(10) <= 2) s[j] = s[j].replace("'", ":");
			if(AMath.random(10) <= 2) s[j] = s[j].replace("'", "-");
			if(AMath.random(10) <= 6) s[j] = s[j].replace("'", "");
			
			if(j > 13 && AMath.random(10) <= 4) {
				if(AMath.random(10) <= 3) s[j] += "-";
				else if(AMath.random(10) <= 3) s[j] += ".";
				else if(AMath.random(10) <= 3) s[j] += "!";
				else if(AMath.random(10) <= 3) s[j] += "~";
			}
		}
		if(i == 1) sk1 = s;
		if(i == 2) sk2 = s;
		if(i == 3) sk3 = s;
	}
	
	boolean title() {
		if(spell > 0) {
			String[] text = null;
			if(spell == 1) text = sk1;
			if(spell == 2) text = sk2;
			if(spell == 3) text = sk3;
			if(text.length <= spc) return false;
			player.sendTitle("§c§l[§6 "+text[spc]+" §c§l]", "§atime : §e" + AMath.round(spt*0.05,1),0,10,0);
		}
		return true;
	}
	boolean next(String str) {
		if(spell > 0) {
			String[] text = null;
			if(spell == 1) text = sk1;
			if(spell == 2) text = sk2;
			if(spell == 3) text = sk3;
			if(spc < text.length && text[spc].equals(str)) {
				spc++;
				spt = time;
				if(spell == 1) {
					ARSystem.playSound((Entity)player, "c128s1"+spc,1,3);
				}
				if(spell == 2) {
					if(spc == 4) ARSystem.playSound((Entity)player, "c128s21",1,3);
					if(spc == 7) ARSystem.playSound((Entity)player, "c128s22",1,3);
					if(spc == 9) ARSystem.playSound((Entity)player, "c128s23",1,3);
					if(spc == 12) ARSystem.playSound((Entity)player, "c128s24",1,3);
				}
				if(spell == 3) {
					ARSystem.playSound((Entity)player, "c128s3"+spc,1,3);
				}
				if(text.length == spc) {
					end();
				}
				return true;
			}
			if(spc >= text.length) spc = text.length;
		}
		end();
		return false;
	}
	
	void end() {
		player.sendTitle("§c"+Text.get("c128:sk"+spell),"§6"+ spc + Text.get("c128:p"));
		if(spell == 1) {
			float damage = 5 + spc*2;
			int size = 5 + spc;
			if(spc > 15) {
				int i = spc-15;
				for(; i>0;i--) {
					damage*=1.2;
					size*=1.3;
				}
			}
			int s = size;
			float d = damage;
			int c = spc;
			Location loc = ULocal.offset(player.getLocation(), new Vector(10+(spc*0.5),0,0));
			if(spc == 20) ARSystem.playSoundAll("c128s121");
			delay(()->{
				for(int ex = s*s/2; ex>0;ex--) {
					Location lc = loc.clone();
					lc.setYaw(AMath.random(360));
					lc.setPitch(-90 + AMath.random(180));
					lc.add(lc.getDirection().multiply(AMath.random(s*10)*0.1));
					ARSystem.spellLocCast(player, lc, "c128_s1");
				}
				if(c == 0) {
					loc.getWorld().playSound(loc, "0explod", 2, s);
				}
				else if(c == 20) {
					ARSystem.playSoundAll("c128explod");
				} else {
					loc.getWorld().playSound(loc, "0explod", 2 - (0.06f*c), s);
				}
				for(Entity e : ARSystem.locEntity(loc, new Vector(s,s,s), player)) {
					LivingEntity en = (LivingEntity)e;
					if(c >= 20) {
						ARSystem.giveBuff(en,new TimeStop(en), 40);
						delay(()->{
							Skill.remove(en, player);
						},40);
					} else {
						en.setNoDamageTicks(0);
						en.damage(d,player);
					}
				}
			},40);
		}
		if(spell == 2) {
			if(spc == 12) {
				ARSystem.heal(player, 99999);
				ARSystem.playSoundAll("0lightning2", 0.2f);
				Rule.team.reload();
			}
			if(spc > 8) {
				for(Player p : Rule.c.keySet()) {
					for(Buff b : Rule.buffmanager.getBuffs(p).getBuff()){
						b.setTime(0);
					}
				}
				ARSystem.giveBuff(player, new Nodamage(player), 100);
			}
			if(spc > 6) {
				skill("removeall");
			}
			if(spc > 3) {
				for(Buff b : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
					b.setTime(0);
				}
			}
		}
		if(spell == 3) {
			if(spc == 6) {
				player.teleport(ULocal.offset(player.getLocation(), new Vector(70,0,0)));
			}
			else if(spc > 3) {
				player.teleport(ULocal.offset(player.getLocation(), new Vector(40,0,0)));
			}
			else if(spc > 2) {
				player.teleport(ULocal.offset(player.getLocation(), new Vector(20,0,0)));
			}
			else if(spc > 1) {
				player.teleport(ULocal.offset(player.getLocation(), new Vector(10,0,0)));
			}
		}
		spc = 0;
		spell = 0;
		spt = 0;
	}
	
	public c128roise(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 128;
		load();
		text();
		c = this;
		time = Integer.parseInt(Main.GetText("c128:ptime"));
	}


	@Override
	public boolean skill1() {
		if(s1tick > 0) return true;
		s1tick = 20;
		ARSystem.playSound((Entity)player, "c128s1");
		revoid(1);
		return true;
	}

	@Override
	public boolean skill2() {
		revoid(2);
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c128s3");
		revoid(3);
		return true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(spell > 0 && spt > 0 && e.getPlayer() == player) {
			next(e.getMessage());
			e.setCancelled(true);
			return false;
		}
		return super.chat(e);
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "fascination")) {
			Rule.buffmanager.selectBuffTime(player, "fascination",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "silence")) {
			Rule.buffmanager.selectBuffTime(player, "silence",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "timestop")) {
			Rule.buffmanager.selectBuffTime(player, "timestop",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "sleep")) {
			Rule.buffmanager.selectBuffTime(player, "sleep",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "mindcontrol")) {
			Rule.buffmanager.selectBuffTime(player, "mindcontrol",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "rampage")) {
			Rule.buffmanager.selectBuffTime(player, "rampage",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "timeshock")) {
			Rule.buffmanager.selectBuffTime(player, "timeshock",0);
		}
		for(int i =0;i<5;i++) {
			if(cooldown[i] > 0) cooldown[i] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(s1tick > 0) s1tick--;
		
		if(spt > 0) {
			title();
			spt--;
			if(spc >= 6) ARSystem.giveBuff(player, new Stun(player), 2);
			if(spc >= 10) ARSystem.potion(player, 24, 10, 1);
			if(spt == 0 || player.isSneaking()) end();
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
		} else {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				e.setDamage(e.getDamage()* 0.1);
			}
		}
		return true;
	}

}
