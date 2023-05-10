package chars.c;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition.EnumPlayerTeleportFlags;
import types.TargetMap;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c37subaru extends c00main{
	boolean ps = true;
	TargetMap<LivingEntity, Double> target = new TargetMap<>();
	float damage = 0.05f;
	
	public c37subaru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 37;
		load();
		text();
	}
	
	
	@Override
	public boolean skill1() {
		if(player.getHealth() > 2) {
			player.setHealth(player.getHealth()-1);
		} else {
			cooldown[1] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c37s1");
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c37p");
		delay(()->{
			for(Entity target : ARSystem.box(player, new Vector(6,3,6), box.TARGET)) {
				((LivingEntity)target).damage(8,player);
			}
			ARSystem.heal(player, 16);
			player.teleport(Map.randomLoc(player));
			ARSystem.playSound((Entity)player, "c37s2");
		},60);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c37s3");
		skill("c"+number+"_s3");
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%10 == 0) {
			if(Rule.c.size() >= 2) {
				boolean ok = true;
				for(Player p : Rule.c.keySet()) {
					if(p != player) {
						if(target.get().get(p) == null || target.get().get(p) <= p.getMaxHealth()) {
							ok = false;
						}
					}
				}
				if(ok && !isps) {
					Bgm.setBgm("c37");
					spskillon();
					spskillen();
					ARSystem.playSoundAll("c37sp");
					for(Player p : Rule.c.keySet()) {
						ARSystem.giveBuff(p, new Nodamage(p), 200);
						ARSystem.giveBuff(p, new Stun(p), 200);
						ARSystem.giveBuff(p, new Silence(p), 200);
					}

					delay(()->{
						target.clear();
						WinEvent event = new WinEvent(player);
						Bukkit.getPluginManager().callEvent(event);
						if(!event.isCancelled()) {
							Rule.playerinfo.get(player).tropy(37, 1);
							Skill.win(player);
						}
					},200);
				}
			}
			
			for(LivingEntity e : target.get().keySet()) {
				if(target.get(e) > e.getMaxHealth()) {
					ARSystem.giveBuff(e, new Stun(e), 20);
					ARSystem.giveBuff(e, new Silence(e), 20);
					ARSystem.spellCast(player, e, "c37_p");
				}
				if(e.isDead() || !e.isValid() || ((e instanceof Player) && Rule.c.get(e) == null)) target.removeAdd(e);
			}
			target.removes();
			
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() > 1) {
				target.add((LivingEntity)e.getEntity(),e.getDamage());
			}
			e.setDamage(0);
			return false;
		} else {
			for(LivingEntity entity : target.get().keySet()) {
				if(target.get(entity) > entity.getMaxHealth()) {
					entity.damage(e.getDamage(), e.getDamager());
					e.setDamage(0);
					return false;
				}
			}
		}
		return true;
	}
}
