package util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class InvSkill {
		LivingEntity entity;
		Player player;
		public org.bukkit.inventory.Inventory inventory;
		int select[] = null;
		
		public InvSkill(Player p){
			player = p;
		}
		
		public void openInventory(Player p) {
			p.openInventory(inventory);
			select = new int[inventory.getSize()-9];
		}
		
		public void setTarget(LivingEntity e){
			entity = e;
		}
		
		public void setInventory(org.bukkit.inventory.Inventory i){
			inventory = i;
		}
		
		abstract public void Start(String displayName);
		
		public void End() {};
}
