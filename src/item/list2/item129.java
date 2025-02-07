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
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item129 extends itemBase{
	public item129(Player p){
		super(p);
		itemCode = 129;
		setcooldown = 100;
	}
	
	@Override
	public boolean skillCast() {
		List<Player> list = ARSystem.PlayerOnlyBeamBox(player, 15, 4, box.TARGET);
		if(list.size() <= 0) return true;
		Player tg = list.get(0);
		ARSystem.spellCast(tg, "item129e1");
		player.sendTitle(""+tg.getName(), "" , 0, 20, 0);
		ARSystem.playSound(player, "item129s1");
		ARSystem.playSound(tg, "item129s1");
		Rule.c.get(player).delay(()->{
			ARSystem.playSound(player, "item129s2");
			ARSystem.playSound(tg, "item129s2");
			player.sendTitle("§c§l"+tg.getName(), "§4§lX10" , 0, 20, 0);
			if(Rule.c.get(tg) != null) {
				Rule.c.get(tg).frist_defence += 10;
				ARSystem.spellCast(tg, "item129e2");
			}
		},400);
		return false;
	}
}
