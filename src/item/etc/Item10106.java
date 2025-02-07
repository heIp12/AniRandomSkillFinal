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
import buff.Buff;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.FixedDealEvent;
import event.Skill;
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

public class Item10106 extends itemBase{
	public Item10106(Player p){
		super(p);
		itemCode = 100106;
		setcooldown = 1;
	}
	
	@Override
	protected void onTick() {
		for(Buff b : Rule.buffmanager.getBuffs(player).getBuff()) {
			b.addTime(-1);
		}
	}
	
	@Override
	public void fixedDamage(FixedDealEvent e) {
		if(e.getCaster() != player) {
			e.setDamage(e.getDamage()*0.2f);
		}
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1456);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
