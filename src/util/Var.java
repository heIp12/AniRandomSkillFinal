package util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


public class Var{
	FileConfiguration config;
	Plugin p;
	File file;
	
	public Var(Plugin pl){

		p = pl;
        reload();
    }
	
    public void reload() {
    	boolean isfile = true;
    	file = null;
    	for(File f : p.getDataFolder().listFiles()) {
    		if(f.getName().equals("var.yml")) {
    			file = f;
    			isfile = false; 
    		}
    	}
    	
    	if(isfile) {
    		config = YamlConfiguration.loadConfiguration(new File(p.getDataFolder(), "var.yml"));
    	} else {
    		config = YamlConfiguration.loadConfiguration(file);
    	}
    	
    	for(File f : p.getDataFolder().listFiles()) {
    		if(f.getName().equals("var.yml")) {
    			file = f;
    		}
    	}
    }
    
    
    public void saveAll() {
    	try {
			config.save(p.getDataFolder()+"\\var.yml");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

	public Object Load(String s) {
		if(config.get(s) == null) {
			return false;
		}
		return config.get(s);
	}
	
	public int Loadint(String s){
		if(config.get(s) == null || config.get(s) instanceof Boolean) {
			return 0;
		}
		return config.getInt(s);
	}
	
	
	public void Save(String s,Object o){
		config.set(s, o);
	}
	
	public void addDouble(String s,double o){
		
		if(config.get(s) == null || config.get(s) instanceof Boolean) {
			config.set(s, 0);
		}
		config.set(s , config.getDouble(s)+o);
	}
	
	public void addInt(String s,int o) {
		if(config.get(s) == null || config.get(s) instanceof Boolean) {
			config.set(s, 0);
		}
		config.set(s , config.getInt(s)+o);
	}
	
	public void setInt(String s,int o) {
		if(config.get(s) == null || config.get(s) instanceof Boolean) {
			config.set(s, 0);
		}
		config.set(s , o);
	}
	
	public void open(String s,boolean o) {
		if(config.get(s) == null) {
			config.set(s, false);
		}
		if(!(boolean) config.get(s)) {
			config.set(s , o);
		}
	}
	
	public ItemStack getItemStack(String s) {
		if(config.get(s) == null) {
			return null;
		}
		return config.getItemStack(s);
	}
}