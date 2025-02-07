package item.craft;

import java.util.ArrayList;
import java.util.List;

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
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import mode.MLoboTomy;
import mode.ModeBase;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1006 extends itemBase{
	public Item1006(Player p){
		super(p);
		itemCode = 1006;
	}
	
	@Override
	protected void onStart() {
		if(ARSystem.isGameMode("lobotomy")) {
			for(ModeBase m : ARSystem.AniRandomSkill.modes) {
				if(m instanceof MLoboTomy) {
					((MLoboTomy)m).addbuff(player);
					break;
				}
			}
		} else {
			ARSystem.playSound(player,"item1006");
			new G_HeddenSelect(player);
		}
	}
	
	@Override
	protected void onTick() {
		if(!ARSystem.isGameMode("lobotomy")) {
			ARSystem.spellCast(player, "item1006");
		}
	}
}