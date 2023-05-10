package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Main;
import ars.ARSystem;
import ars.PlayerInfo;
import ars.Rule;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_Imj extends GUIBase{
	PlayerInfo info;

	public G_Imj(Player p) {
		super(p);
		name = "Adv Select";
		int count = 100;
		line = 6;
		page = new int[Math.max(count,this.line*9) + (Math.max(count,this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		int q = 0;
		for(int i=0;j<count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			if((boolean) Rule.Var.Load(player.getName()+":imj."+j)) {
				page[q] = j;
				ItemStack item = ItemCreate.Item(293,1400+j);
				ItemRep(j, ItemCreate.Lore(item, "§f"+Text.get("imj:"+j), new String[] {"§7"+Text.get("imj:info")}));
				q++;
				if((q+1)%9 == 0) q++;
			}
			j++;
		}
		for(int i = 1; i< page.length; i++) {
			if(page[i] == 0) {
				page[i] = 996;
			}
		}
		ScrollInvCreate(6,0);
	}
	
	public ItemStack gui996() {
		// TODO Auto-generated method stub
		return super.gui0();
	}

	public void click996(boolean right, boolean shift) {}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(click > 0 && shift) {
			Rule.playerinfo.get(player).MainImg = click;
			ARSystem.playSound(player,"0click",1.2f);
			return true;
		}
		else if(!(ARSystem.AniRandomSkill != null && (Rule.c.get(player) == null || player.getGameMode() == GameMode.SPECTATOR)) &&
				click > 0 && Rule.playerinfo.get(player).LastImgTime <= System.currentTimeMillis()) {
			Rule.playerinfo.get(player).LastImgTime = System.currentTimeMillis() + 5000;
			ARSystem.spellCast(player, "img"+click);
			ARSystem.playSound(player,"0click");
			return true;
		}
		return false;
	}
}