package util;

import java.util.Set;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import ars.Rule;


public class Inventory {
	public static void getlist(InvSkill s,Player owner, Set<Player> set) {
		int size = set.size()+9;
		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, size, owner.getName()+" : Skill");
		int i = 0;
		for(Player p : set) {
			inv.setItem(i, Rule.playerinfo.get(p).getHead());
			i++;
		}
		s.setInventory(inv);
	}
}