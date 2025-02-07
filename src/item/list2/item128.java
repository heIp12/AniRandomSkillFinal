package item.list2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item128 extends itemBase{
	boolean cast = false;
	public item128(Player p){
		super(p);
		itemCode = 128;
		setcooldown = 30;
	}

	@Override
	public boolean skillCast() {
		ARSystem.spellCast(player, "item128");
		cast = true;
		ARSystem.giveBuff(player, new Silence(player), 80);
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		delay(()->{cast=false;},80);
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(cast && e.getDamage() > 999) {
			e.setDamage(0);
			e.setCancelled(true);
			ARSystem.giveBuff(player, new Silence(player), 0);
			ARSystem.giveBuff(player, new Nodamage(player), 0);
			if(Rule.c.get(e.getEntity()) != null) {
				Player p = (Player)e.getEntity();
				ARSystem.removeItemAll(p);
				Rule.c.put(p, GetChar.get(p, Rule.gamerule, ""+AMath.random(GetChar.getCount())));
				ARSystem.spellCast(player ,p , "item128e");
				p.teleport(Map.randomLoc(p));
				Bukkit.broadcastMessage("§a§l[ARSystem] §f"+p.getName()+ " " + Text.get("item:128t"+AMath.random(20)));
			}
		}
		return super.onAttack(e);
	}
}
