package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Main;
import ars.ARSystem;
import ars.PlayerInfo;
import ars.Rule;
import mode.ModeBase;
import types.GameModes;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_OpModes extends GUIBase{
	PlayerInfo info;
	List<String> modeNames = new ArrayList<String>();
	public G_OpModes(Player p) {
		super(p);
		name = "Modes";
		int count = GameModes.getModList().size();
		page = new int[27];
		info = Rule.playerinfo.get(player);
		line = 6;
		int i = 1;
		for(ModeBase mode : GameModes.getModList()) {
			if(mode.IsSecret() && !ARSystem.selectGameMode.contains(mode.getModeName())) continue;
			page[i-1] = i;
			ItemStack is = null;
			modeNames.add(mode.getModeName());
			String name = "";
			List<String> lores = new ArrayList<>();
			if(ARSystem.selectGameMode.contains(mode.getModeName())){
				if(mode.IsSecret()) {
					is = ItemCreate.Item(403);
				} else {
					is = ItemCreate.Item(340);
				}
				name = "§a§l" + mode.getDisPlayName();
			} else {
				is = ItemCreate.Item(386);
				name = "§c§l" + mode.getDisPlayName();
			}
			if(GameModes.getGameModes(mode.getModeName()).IsOne()) {
				lores.add("§7["+Text.get("main:modeinfo1")+"]");
			}
			if(GameModes.getGameModes(mode.getModeName()).IsOnlyOne()) {
				lores.add("§c["+Text.get("main:modeinfo2")+"]");
			}
			is = ItemCreate.Lore(is,name,lores);
			ItemRep(i, is);
			i++;
		}
		InvCreate(page.length/9,0);
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(modeNames.size()-1 < clickLocal) return false;
		
		String modename = modeNames.get(clickLocal);
		ARSystem.redyMode(modename);
		
		String mode = "";
		for(String s : ARSystem.selectGameMode) {
			mode += " §c[§f"+GameModes.getGameModes(s).getDisPlayName()+"§c]";
		}
		Bukkit.broadcastMessage("§a§l[ARSystem] §c§l<" + Main.GetText("main:info36") +"("+player.getName()+")> §f:"+ mode);
		new G_OpModes(player);
		return false;
	}
}