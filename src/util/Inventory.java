package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import item.list1.itemBase;


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
	public static void getlistC(InvSkill s,Player owner, Set<Player> set) {
		int size = set.size()+9;
		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, size, owner.getName()+" : Skill");
		int i = 0;
		for(Player p : set) {
			ItemStack item =  Rule.playerinfo.get(p).getHead().clone();
			inv.setItem(i,ItemCreate.Lore(item, p.getCustomName(),new String[] {Text.get("c"+Rule.c.get(p).number +":name1") + " " + Text.get("c"+Rule.c.get(p).number+":name2")} ));
			
			i++;
		}
		s.setInventory(inv);
	}
	public static void getlistD(InvSkill s,Player owner, Set<Player> set) {
		int size = set.size()+9;
		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, size, owner.getName()+" : Skill");
		int i = 0;
		for(Player p : set) {
			ItemStack item =  Rule.playerinfo.get(p).getHead().clone();
			List<String> lore = new ArrayList<String>();
			lore.add("§f"+Text.get("c"+Rule.c.get(p).number +":name1") + " " + Text.get("c"+Rule.c.get(p).number+":name2"));
			lore.add("§cHp : " + p.getHealth() +" / "+p.getMaxHealth());
			lore.add("§eLoc : §f"+p.getLocation().getBlockX()+","+p.getLocation().getBlockY()+","+p.getLocation().getBlockZ());
			lore.add("§a["+Text.get("main:s4")+"] : " + AMath.round((Rule.c.get(p).skillmult+Rule.c.get(p).sskillmult)*100,2)+"%");
			String msg = "";
			String ismsg = "";
			
			lore.add("§a§l§m=-=-=--=-=-=--=-=-=-=-=-=-=-=-=");
			for(Buff bf : Rule.buffmanager.getBuffs(p).getBuff()) {
				ismsg += bf.getText()+"§7§l,§f";
			}
			lore.add("§e§l[Buff]"+ismsg.replace("&", "§"));
			
			//cooldown
			ismsg = ""+Math.round(Rule.c.get(p).cooldown[0]*100)/100.0;
			if(!ismsg.equals("0.0")) msg+="§2[§eSp : §6§l"+ismsg+"§2]";
			
			for(int j=1; j<9;j++) {
				ismsg = ""+Math.round(Rule.c.get(p).cooldown[j]*100)/100.0;
				if(Math.round(Rule.c.get(p).cooldown[j]*100)/100.0 < 0) {
					Rule.c.get(p).cooldown[j] = 0;
				}
				if(!ismsg.equals("0.0")) msg+="§a[§2s"+j+"§a : §c"+ismsg+"§a]";
			}
			ismsg = ""+Math.round(Rule.c.get(p).cooldown[9]*100)/100.0;
			if(!ismsg.equals("0.0")) msg+="§a[§e:) §a: §c"+ismsg+"§a]";
			
			//
			lore.add("§a§l§m=-=-=--=-=-=--=-=-=-=-=-=-=-=-=");
			lore.add("§e§l[Cooldown]"+msg);
			lore.add("§a§l§m=-=-=--=-=-=--=-=-=-=-=-=-=-=-=");
			msg = "";
			if(ARSystem.playerItem.get(p) != null && Rule.c.get(p) != null) {
				for(itemBase it : ARSystem.playerItem.get(p).items) {
					msg += it.getActionbar();
				}
			}
			lore.add("§e§l[Item]"+msg);
			lore.add("§a§l§m=-=-=--=-=-=--=-=-=-=-=-=-=-=-=");
			
			inv.setItem(i,ItemCreate.Lore(item, p.getCustomName(),lore));
			
			i++;
		}
		s.setInventory(inv);
	}
}