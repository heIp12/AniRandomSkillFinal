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
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MQb;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10105 extends itemBase{
	public Item10105(Player p){
		super(p);
		itemCode = 100105;
		setcooldown = 1;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		if(Rule.buffmanager.selectBuffType(en, BuffType.HEADCC).size() > 0) {
			ARSystem.overheal(player, e.getDamage()* 0.3f);
		}
		return super.onAttack(e);
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1455);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			int gold = Rule.playerinfo.get(p).gold;
			if(gold > 500) gold = 500;
			Rule.playerinfo.get(p).gold -= gold;
			Rule.playerinfo.get(player).gold += gold;
		}
	}
}
