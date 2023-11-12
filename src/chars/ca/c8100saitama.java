package chars.ca;

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
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
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
import chars.c.c44izuna;
import chars.c2.c51zenos;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c8100saitama extends c00main{
	int pt = 0;
	public c8100saitama(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1081;
		load();
		text();
		c = this;
		if(p != null) ARSystem.potion(p, 1, 10000, 0);
		ARSystem.playSound((Entity)p,"c81sel");
	}

	
	@Override
	public boolean tick() {
		if(pt > 0) pt--;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack && pt <= 0) {
			if(e.getEntity().getLocation().distance(player.getLocation()) <= 8 && e.getDamage() <= 10) {
				ARSystem.playSound((Entity)player, "c81a");
				pt = 10;
				LivingEntity tg = (LivingEntity)e.getEntity();
				Vector vt = player.getLocation().getDirection();
				
				for(int i=0; i<60; i++) {
					delay(()->{
						tg.setVelocity(vt.multiply(1));
						for(Entity en : ARSystem.box(tg, new Vector(5, 5, 5), box.ALL)) {
							if(en != player && en != tg) {
								en.teleport(tg);
							}
						}
						ARSystem.playSound(tg, "0explod", 0.1f);
						ARSystem.spellCast(player, tg, "c1081_p");
					},i);
				}
				delay(()->{
					ARSystem.spellCast(player, tg, "c1081_p2");
					for(Entity en : ARSystem.box(tg, new Vector(12, 12, 12), box.MYALL)) {
						if(en != player) {
							((LivingEntity)en).setNoDamageTicks(0);
							((LivingEntity)en).damage(999,player);
						}
					}
					tg.setNoDamageTicks(0);
					tg.damage(999,player);
					ARSystem.playSound(tg, "0explod2", 0.7f , 2);
				},60);
			}
		}
		
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c51zenos) {
					is = "zanos";
					break;
				}
			}
		}
		
		if(is.equals("zanos")) {
			ARSystem.playSound((Entity)player, "c81zanos");
		} else {
			ARSystem.playSound((Entity)player, "c81db");
		}
		
		return true;
	}
}
