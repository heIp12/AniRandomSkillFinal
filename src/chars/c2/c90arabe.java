package chars.c2;

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
import buff.Choice;
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

public class c90arabe extends c00main{
	int tick = 200;
	int count = 1;
	int count2 = 0;
	List<LivingEntity> entitys;
	
	public c90arabe(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 90;
		load();
		text();
		c = this;
		
	}
	@Override
	public void setStack(float f) {
		count = (int)f;
	}
	
	@Override
	public boolean tick() {
		if(tick > 0) {
			tick--;
			if(tick == 0) {
				tick = 200 + AMath.random(200);
				count2++;
				if(count2 > 2) {
					count2 = 0;
					spskillon();
					spskillen();
					for(Player p : Rule.c.keySet()) {
						if(p != player) ARSystem.giveBuff(p, new Choice(p,(int) (count*2)), 400);
					}
				} else {
					ARSystem.giveBuff(player, new Choice(player,count++), 200);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
