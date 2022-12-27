package util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Main.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Text {
	static public TextComponent hover(Player p,String text,String hover){
		TextComponent mainComponent = new TextComponent(text);
		BaseComponent[] texts = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', hover));
        mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, texts));

        return mainComponent;
	}
	
	static public String getAni(String text) {
		String txt = Main.GetText("ani:"+text);
		return txt;
	}
	
	static public int getTextint(String s) {
		return Integer.parseInt(Main.GetText(s));
	}
	static public String getType(String text) {
		String txt = "";
		String option[] = text.split(" ");
		for(int i=1;i<option.length;i++) {
			txt += Main.GetText("main:"+option[i])+", ";
		}
		return txt.substring(0,txt.length()-2);

	}
	public static String bubble(String val) {
		String retur ="";
		char[] arr = val.toCharArray();
		
		int j=0;
		while(j<val.length()-1)
		{
			int k=0;
			while(k<val.length()-1-j)
			{
				if(arr[k]<arr[k+1])
				{
					char tmp;
			 
					tmp = arr[k];
					arr[k] = arr[k+1];
					arr[k+1] = tmp;
				}
				k++;
			}
			j++;
		}
		for(char c :arr) {
			retur+=c;
		}
		return retur;
	}

}
