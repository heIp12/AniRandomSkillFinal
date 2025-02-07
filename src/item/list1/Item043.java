package item.list1;

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

public class Item043 extends itemBase{
	double damage = 0;
	public Item043(Player p){
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
						((MLoboTomy)m).dmg += 0.1f;
						break;
					}
				}
			} else {
				if(GetChar.advList().contains(Rule.playerinfo.get(player).playerc)) {
					Rule.c.put(player,GetChar.getAdv(player, Rule.gamerule, ""+ Rule.playerinfo.get(player).playerc, null));
				} else {
					int i = GetChar.advList().get(AMath.random(GetChar.advList().size()-1));
					Rule.c.put(player,GetChar.getAdv(player, Rule.gamerule, ""+i , null));
				}
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
