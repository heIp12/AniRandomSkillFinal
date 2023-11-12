package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Fix;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.TargetMap;
import types.box;
import util.GetChar;
import util.Holo;
import util.Map;
import util.Text;
import util.ULocal;

public class e003mix extends c00main{
	List<c00main> ch = new ArrayList<c00main>();
	int[] sks = new int[5];
	public e003mix(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 903;
		load();
		text();
		this.ch.add(new c00main(null, plugin, null));
		this.ch.add(new c00main(null, plugin, null));
	}
	
	@Override
	public void setStack(float f) {
		String s = ""+(int)f;
		int i = 0;
		while(s.length() > 0) {
			sks[i] = Integer.parseInt(s.substring(0, 1));
			s = s.substring(1);
			i++;
		}
		String o = "";
		for(int z =0; z < 5; z++) {
			o+=sks[z];
		}
		player.sendMessage("stack : " + o);
	}
	
	@Override
	public boolean skill1() {
		if(sks[1] == 1 || sks[1] == 3) {
			cooldown[1] = ch.get(0).setcooldown[1];
			ch.get(0).skill1();
		}
		if(sks[1] == 3 || sks[1] == 2) {
			cooldown[1] = ch.get(1).setcooldown[1];
			ch.get(1).skill1();
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sks[2] == 1 || sks[2] == 3) {
			cooldown[2] = ch.get(0).setcooldown[2];
			ch.get(0).skill2();
		}
		if(sks[2] == 3 || sks[2] == 2) {
			cooldown[2] = ch.get(1).setcooldown[2];
			ch.get(1).skill2();
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sks[3] == 1 || sks[3] == 3) {
			cooldown[3] = ch.get(0).setcooldown[3];
			ch.get(0).skill3();
		}
		if(sks[3] == 3 || sks[3] == 2) {
			cooldown[3] = ch.get(1).setcooldown[3];
			ch.get(1).skill3();
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(sks[4] == 1 || sks[4] == 3) {
			cooldown[4] = ch.get(0).setcooldown[4];
			ch.get(0).skill4();
		}
		if(sks[4] == 3 || sks[4] == 2) {
			cooldown[4] = ch.get(1).setcooldown[4];
			ch.get(1).skill4();
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).makerSkill(target, n);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).makerSkill(target, n);
		}
		super.makerSkill(target, n);
	}
	
	@Override
	public boolean firsttick() {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).firsttick();
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).firsttick();
		}
		return false;
	}
	
	
	@Override
	public boolean tick() {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).ticks();
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).ticks();
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).damage(e);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).damage(e);
		}
		return super.damage(e);
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).remove(caster);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).remove(caster);
		}
		return false;
	}
	
	@Override
	public void WinEvent(event.WinEvent e) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).WinEvent(e);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).WinEvent(e);
		}
	}
	
	@Override
	public boolean death(PlayerDeathEvent e) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).death(e);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).death(e);
		}
		return false;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).PlayerDeath(p,e);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).PlayerDeath(p,e);
		}
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() == player) {
			if(e.getMessage().contains("c1")) {
				ch.set(0,GetChar.get(null, plugin, e.getMessage().replace(" ", "").replace("c1", "")));
				ch.get(0).player = player;
				player.sendMessage("Select1 : " + ch.get(0).number);
			}
			if(e.getMessage().contains("c2")) {
				ch.set(1,GetChar.get(null, plugin, e.getMessage().replace(" ", "").replace("c2", "")));
				ch.get(1).player = player;
				player.sendMessage("Select2 : " + ch.get(1).number);
			}
		}
		return super.chat(e);
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).entitydamage(e,isAttack);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).entitydamage(e,isAttack);
		}
		return true;
	}
	@Override
	public void delayLoop(double time) {
		if(sks[0] == 1 || sks[0] == 3) {
			ch.get(0).delayLoop(time);
		}
		if(sks[0] == 3 || sks[0] == 2) {
			ch.get(1).delayLoop(time);
		}
	}
}
