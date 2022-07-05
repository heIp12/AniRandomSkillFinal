package manager;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.JSONMessage;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import net.minecraft.server.v1_12_R1.PacketPlayInRecipeDisplayed;


public class AdvManager {
	
	static public void set(Player p,int id,int i,String message) {
		Material mat = new ItemStack(id,1,(byte)i).getType();
		
		
		AdvancementDisplay display = new AdvancementDisplay(mat, message, "", AdvancementFrame.TASK, true, true, AdvancementVisibility.ALWAYS);
		Advancement advancement = new Advancement(null, new NameKey("toast", "message"), display);
		advancement.displayToast(p);
		
	}
	
}
