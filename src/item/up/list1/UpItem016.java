package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import buff.Exposure;
import util.ItemCreate;
import util.Text;

public class UpItem016 extends upitemBase{
	public UpItem016(Player p){
		super(p);
		itemCode = 16;
		setcooldown = 20;
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			ARSystem.spellCast(player, en, "item16");
			ARSystem.playSound(en, "minecraft:block.glass.break",0.6f,1);
			ARSystem.giveBuff(en, new Exposure(en), 100, 3);
		}
		return super.onAttack(e);
	}

}
