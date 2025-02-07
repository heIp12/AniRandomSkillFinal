package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_AdvSelect;
import buff.TimeStop;
import manager.AdvManager;
import mode.MLoboTomy;
import mode.ModeBase;
import util.AMath;
import util.GetChar;
import util.ItemCreate;
import util.Text;

public class UpItem043 extends upitemBase{
	double damage = 0;
	public UpItem043(Player p){
		super(p);
		itemCode = 43;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		damage += e.getDamage();
		if(damage >= 50 && !quest) {
			questComplete();
			if(ARSystem.isGameMode("lobotomy")) {
				for(ModeBase m : ARSystem.AniRandomSkill.modes) {
					if(m instanceof MLoboTomy) {
						((MLoboTomy)m).addbuff(player);
						break;
					}
				}
			} else {
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				new G_AdvSelect(player);
			}
		}
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		if(quest) {
			return super.getActionbar();
		}
		return "§c§l<§6"+itemName+" : §e" + AMath.round(damage,1) + "/50§c§l>";
	}
}
