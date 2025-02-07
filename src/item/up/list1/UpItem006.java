package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import chars.c.c16saki;
import event.Skill;
import util.ItemCreate;
import util.Text;

public class UpItem006 extends upitemBase{
	public UpItem006(Player p){
		super(p);
		itemCode = 6;
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 23) {
			delay(()->{
				LivingEntity en = (LivingEntity)e.getEntity();
				if(en.getHealth() < 8 || en.getHealth()/en.getMaxHealth() <= 0.375) {
					ARSystem.spellCast(player, en, "c23_sp_i");
					Skill.remove(en, player);
				}
			},0);
		} else {
			delay(()->{
				LivingEntity en = (LivingEntity)e.getEntity();
				if(en.getHealth() < 6 || en.getHealth()/en.getMaxHealth() <= 0.25) {
					ARSystem.spellCast(player, en, "c23_sp_i");
					Skill.remove(en, player);
				}
			},0);
		}
		return super.onAttack(e);
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 23 && e.getNewSlot() == 1 && Rule.c.get(player).cooldown[2] <= 0) {
			Rule.c.get(player).cooldown[2] = Rule.c.get(player).setcooldown[2]*0.25f;
			ARSystem.spellCast(player, "c23_item");
			ARSystem.playSound((Entity)player,"c23s3");
			e.setCancelled(true);
			return false;
		}
		return super.onSkill(e);
	}
}
