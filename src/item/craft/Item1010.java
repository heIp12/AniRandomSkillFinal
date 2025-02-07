package item.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1010 extends itemBase{
	public Item1010(Player p){
		super(p);
		itemCode = 1010;
		setcooldown = 8;
	}
	
	@Override
	public boolean skillCast() {
		Rule.c.get(player).invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				player.closeInventory();
				Player p = Bukkit.getPlayer(st);
				p.setNoDamageTicks(0);
				p.damage(4,player);
				ARSystem.playSound((Entity)player, "0gun");
				ARSystem.spellCast(player, p, "item1010");
			}
		};
		Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		Player.remove(player);
		Inventory.getlist(Rule.c.get(player).invskill,player,Player);
		Rule.c.get(player).invskill.openInventory(player);
		return false;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		double range = e.getEntity().getLocation().distance(player.getLocation());
		e.setDamage(e.getDamage() * (1 + range*0.02));
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		ARSystem.spellCast(player, "item1010e");
	}
}