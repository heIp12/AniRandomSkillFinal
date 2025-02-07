package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import ars.gui.G_AdvSelect;
import ars.gui.G_Nitory;
import ars.gui.G_RareShop;
import ars.gui.G_Supply;
import ars.gui.solo.G_MapSelect;
import buff.Airborne;
import buff.Medusa;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.etc.Item10001.Stats;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MQb;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10104 extends itemBase{
	public Item10104(Player p){
		super(p);
		itemCode = 100104;
		setcooldown = 30;
	}
	
	@Override
	protected void onTick() {
		List<Entity> e = ARSystem.PlayerBeamV(player, 15, 2, box.TARGET);

		if(e.size() > 0) {
			Entity en = e.get(0);
			float y = Math.abs(Math.abs((en.getLocation().getYaw()-180%180) - (player.getLocation().getYaw()))-180);
			float pc = Math.abs(en.getLocation().getPitch() - (player.getLocation().getPitch()*-1));
			if(y+pc < 22 && en instanceof Player && isCooldown()) {
				ARSystem.giveBuff((LivingEntity) e.get(0), new Medusa((LivingEntity) e.get(0)), 100 , 20);
			}
		}
	}
	@Override
	protected void onStart() {
		for(itemBase i : ARSystem.playerItem.get(player).items) {
			if(i instanceof Item10001) {
				for(Stats st : ((Item10001)i).stats.values()) {
					if(st.name.equals("cd")) {
						st.max *= 1.3;
					}
				}
			}
		}
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1454);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
