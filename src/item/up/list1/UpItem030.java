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
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Ice;
import buff.Sleep;
import chars.c3.c105suya;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class UpItem030 extends upitemBase{
	List<Double> damage = new ArrayList<Double>();
	int count = 0;
	int t = 0;
	
	public UpItem030(Player p){
		super(p);
		itemCode = 30;
		setcooldown = 3;
	}
	
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(cooldown <= 0 && count <= 3) {
			damage.add(e.getDamage());
			count++;
		}
		if(count >= 3 && isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			for(int i =0; i<3 ;i++) {
				int j = i;
				delay(()->{
					ARSystem.spellCast(player, en, "c7_p");
					ARSystem.playSound(en, "0fire",1.4f,1f);
					en.setNoDamageTicks(0);
					en.damage(damage.get(j),player);
					count = 0;
				},i*2);
				delay(()->{
					damage.clear();
				},7);
			}
		}

		if(Rule.c.get(player).number == 7 && t <= 0) {
			t = 2;
			delay(()->{
				((LivingEntity)e.getEntity()).setNoDamageTicks(0);
				((LivingEntity)e.getEntity()).damage(e.getDamage(),player);
				ARSystem.spellCast(player, e.getEntity(), "c7_p");
			},1);
		}
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		if(MSUtil.isbuff(player, "item30")) {
			if(player.isSneaking()) {
				player.setVelocity(player.getLocation().getDirection().multiply(0.75));
			}
		}
		if(t > 0) {
			t--;
		}
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 7 && e.getNewSlot() == 1 && Rule.c.get(player).cooldown[2] <= 0) {
			Rule.c.get(player).cooldown[2] = Rule.c.get(player).setcooldown[2];
			ARSystem.spellCast(player, "item30");
			e.setCancelled(true);
			return false;
		}
		return super.onSkill(e);
	}
}

