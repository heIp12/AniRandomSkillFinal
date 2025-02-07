package types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.structure.Mirror;

import ars.Rule;
import mode.MAdv;
import mode.MArena;
import mode.MEvent;
import mode.MGUN;
import mode.MHeal;
import mode.MItem;
import mode.MKagerou;
import mode.MKanna;
import mode.MKiller;
import mode.MLoboTomy;
import mode.MMirror;
import mode.MNormal;
import mode.MQb;
import mode.MRandom;
import mode.MSupply;
import mode.MTeam;
import mode.MTeamMatch;
import mode.MURF;
import mode.MZombie;
import mode.MZombieAdv;
import mode.ModeBase;
import util.Text;

public class GameModes {
	static private HashMap<String,Class<? extends ModeBase>> gameModes;
	static private List<Class<? extends ModeBase>> orignal;
	static private HashMap<String, ModeBase> gameMod;
	
	public static void set() {
		gameModes = new HashMap<>();
		gameMod = new HashMap<>();
		orignal = new ArrayList<>();
		orignal.add(MNormal.class);
		orignal.add(MRandom.class);
		orignal.add(MURF.class);
		orignal.add(MTeam.class);
		orignal.add(MTeamMatch.class);
		orignal.add(MZombie.class);
		orignal.add(MZombieAdv.class);
		orignal.add(MGUN.class);
		orignal.add(MKanna.class);
		orignal.add(MKiller.class);
		orignal.add(MMirror.class);
		orignal.add(MAdv.class);
		orignal.add(MSupply.class);
		orignal.add(MArena.class);
		orignal.add(MLoboTomy.class);
		orignal.add(MKagerou.class);
		orignal.add(MHeal.class);
		orignal.add(MItem.class);
		orignal.add(MQb.class);
		orignal.add(MGUN.class);
		orignal.add(MEvent.class);
		
		for(Class<? extends ModeBase> key : orignal) {
			ModeBase mb;
			try {
				mb = key.newInstance();
				gameModes.put(mb.getModeName(), key);
				gameMod.put(mb.getModeName(), mb);
				String name = mb.getModeName();
				int i = 1;
				while(Text.get("mode:"+name+"_"+i) != null) {
					String type = Text.get("mode:"+name+"_"+i+"_type").replace(" ", "").toLowerCase();
					if(Rule.Var.nullLoad("System.mode."+name+"."+i) == null) {
						String value = Text.get("mode:"+name+"_"+i+"_frist_value").replace(" ", "");
						if(type.contains("int:")) { 
							Rule.Var.Save("System.mode."+name+"."+i, Integer.parseInt(value));
						} else if(type.contains("bool")) { 
							Rule.Var.Save("System.mode."+name+"."+i, Boolean.parseBoolean(value));
						} else if(type.contains("chat")) { 
							Rule.Var.Save("System.mode."+name+"."+i, value.toString());
						}
					}
					i++;
				}
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				System.out.println("[ARSystem] : Not gameMode Class "+ key.getName());
			}
		}
		
	}
	public static ModeBase getGameModes(String name) {
		if(gameModes == null) set();
		try {
			return gameModes.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("[ARSystem] : Not gameMode Class "+ name);
		}
		return null;
	}
	
	public static void addGameMode(String name, Class<? extends ModeBase> modes) {
		if(gameModes == null) set();
		gameModes.put(name,modes);
		gameMod.put(name, getGameModes(name));
	}
	public static Collection<ModeBase> getModList() {
		if(gameModes == null) set();
		return gameMod.values();
	}
}