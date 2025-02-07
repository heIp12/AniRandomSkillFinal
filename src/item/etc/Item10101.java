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
import org.bukkit.potion.PotionEffectType;
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
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10101 extends itemBase{
	public Item10101(Player p){
		super(p);
		itemCode = 100101;
		setcooldown = 1;
	}
	
	@Override
	protected void onTick() {
		if(Rule.c.get(player) != null && Rule.c.get(player).isBattleTime() > 200) {
			ARSystem.potion(player, 14, 100, 1);
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) player.removePotionEffect(PotionEffectType.INVISIBILITY);
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) player.removePotionEffect(PotionEffectType.INVISIBILITY);
		return super.onHit(e);
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1451);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
