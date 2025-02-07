package item.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Exposure;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.Holo;
import util.InvSkill;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10001 extends itemBase{
	public HashMap<String,Stats> stats = new HashMap<>();
	
	float hpp = 0;
	float hp = 0;
	float dmg = -0.3f;
	float dfc = 0;
	float cd = -0.20f;
	int crt = 0;
	
	int upgard_gold = 0;
	int playercode;
	//특성
	int ab = 0;
	
	int abcooldown = 0;
	int abtime = 0;
	
	c00main c;
	
	public Item10001(Player p){
		super(p);
		itemCode = 100001;
		itemtable = false;
		stats.put("hp", new Stats("hp", 300, 120));
		stats.put("dmg", new Stats("dmg", 300, 100));
		stats.put("dfc", new Stats("dfc", 25, 250));
		stats.put("cd", new Stats("cd", 50, 150));
		stats.put("crt", new Stats("crt", 20, 250));
		cooldown = 0;
	}
	
	@Override
	protected void onTick() {
		if(ab == 3 && abcooldown <= 0) {
			if(player.getHealth()/ player.getMaxHealth() <= 0.3) {
				for(int i = 0; i< 100; i++) {
					ARSystem.heal(player, player.getMaxHealth()*0.006f);
				}
				abcooldown = 2400;
			}
		}
		if(timer%5 == 0 && c != Rule.c.get(player)) {
			onStart();
		}
		
		if(abcooldown > 0) abcooldown--;
		if(abtime > 0) abtime--;
	}
	
	@Override
	protected void onStart() {
		c = Rule.c.get(player);
		playercode = Rule.c.get(player).number;
		addHp();
		addDmg();
		addDfc();
		addCd();
		addCrt();
	}
	
	float getValue(String s) {
		return (float)(stats.get(s).getValue() * Text.getD("item:100001_"+s));
	}
	
	public void addHp() {
		hpp = getValue("hp");
		hp = hpp * Text.getI("c"+playercode+":hp");
		Rule.c.get(player).hp = (float) (Text.getI("c"+playercode+":hp")+hp);
		player.setMaxHealth(Rule.c.get(player).hp);
	}
	
	public void addDmg() {
		dmg = getValue("dmg") - 0.3f;
	}
	public void addDfc() {
		dfc = getValue("dfc");
	}
	public void addCd() {
		cd = getValue("cd") - 0.20f;
		Rule.c.get(player).sskillmult = cd;
	}
	public void addCrt() {
		crt = (int)(getValue("crt")*100);
	}
	
	

	
	@Override
	public boolean skillCast() {
		if(player.isSneaking()) return true;
		if(upgard_gold >= 5000 && ab == 0) {
			Rule.c.get(player).invskill = new InvSkill(player) {
				
				@Override
				public void Start(String st) {
					player.closeInventory();
					ab = Integer.parseInt(st);
					if(ab == 7) {
						for(int i = 0; i<10; i++) Rule.c.get(player).setcooldown[i]*=2;
						Rule.c.get(player).frist_damage += 0.5;
					}
				}
			};
			String t = "item:100001_";
			org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 18, player.getName()+" : Skill");
			inv.setItem(0, ItemCreate.Lore(ItemCreate.Item(279,1532), "1", Text.getLine(t+"1_lore", 1)));
			inv.setItem(1, ItemCreate.Lore(ItemCreate.Item(279,1533), "2", Text.getLine(t+"2_lore", 1)));
			inv.setItem(2, ItemCreate.Lore(ItemCreate.Item(279,1534), "3", Text.getLine(t+"3_lore", 1)));
			inv.setItem(3, ItemCreate.Lore(ItemCreate.Item(279,1535), "4", Text.getLine(t+"4_lore", 1)));
			inv.setItem(4, ItemCreate.Lore(ItemCreate.Item(279,1536), "5", Text.getLine(t+"5_lore", 1)));
			inv.setItem(5, ItemCreate.Lore(ItemCreate.Item(279,1537), "6", Text.getLine(t+"6_lore", 1)));
			inv.setItem(6, ItemCreate.Lore(ItemCreate.Item(279,1538), "7", Text.getLine(t+"7_lore", 1)));
			inv.setItem(7, ItemCreate.Lore(ItemCreate.Item(279,1539), "8", Text.getLine(t+"8_lore", 1)));
			inv.setItem(8, ItemCreate.Lore(ItemCreate.Item(279,1540), "9", Text.getLine(t+"9_lore", 1)));
			
			Rule.c.get(player).invskill.setInventory(inv);
			Rule.c.get(player).invskill.openInventory(player);
			return false;
		}
		Rule.c.get(player).invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				player.closeInventory();
				int c = 1;
				if(st.contains("[x10]")) c = 10;
				
				if(st.contains(Text.get("item:100001_hp_t"))) {
					for(int i = 0; i< c; i++) if(stats.get("hp").isUp()) addHp();
					delay(()->{skillCast();},0);
				} else if(st.contains(Text.get("item:100001_dmg_t"))) {
					for(int i = 0; i< c; i++) if(stats.get("dmg").isUp()) addDmg();
					delay(()->{skillCast();},0);
				} else if(st.contains(Text.get("item:100001_dfc_t"))) {
					for(int i = 0; i< c; i++) if(stats.get("dfc").isUp()) addDfc();
					delay(()->{skillCast();},0);
				} else if(st.contains(Text.get("item:100001_cd_t"))) {
					for(int i = 0; i< c; i++) if(stats.get("cd").isUp()) addCd();
					delay(()->{skillCast();},0);
				} else if(st.contains(Text.get("item:100001_crt_t"))) {
					for(int i = 0; i< c; i++) if(stats.get("crt").isUp()) addCrt();
					delay(()->{skillCast();},0);
				}
			}
		};
		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 26, player.getName()+" : Skill");
		inv.setItem(2, ItemCreate.Lore(ItemCreate.Item(279,1527), stats.get("hp").getName()+"§a§l [x10]", stats.get("hp").getText()));
		inv.setItem(3, ItemCreate.Lore(ItemCreate.Item(279,1528), stats.get("dmg").getName()+"§a§l [x10]",stats.get("dmg").getText()));
		inv.setItem(4, ItemCreate.Lore(ItemCreate.Item(279,1529), stats.get("dfc").getName()+"§a§l [x10]", stats.get("dfc").getText()));
		inv.setItem(5, ItemCreate.Lore(ItemCreate.Item(279,1530), stats.get("cd").getName()+"§a§l [x10]", stats.get("cd").getText()));
		inv.setItem(6, ItemCreate.Lore(ItemCreate.Item(279,1531), stats.get("crt").getName()+"§a§l [x10]", stats.get("crt").getText()));
		inv.setItem(11, ItemCreate.Lore(ItemCreate.Item(279,1527), stats.get("hp").getName(), stats.get("hp").getText()));
		inv.setItem(12, ItemCreate.Lore(ItemCreate.Item(279,1528), stats.get("dmg").getName(),stats.get("dmg").getText()));
		inv.setItem(13, ItemCreate.Lore(ItemCreate.Item(279,1529), stats.get("dfc").getName(), stats.get("dfc").getText()));
		inv.setItem(14, ItemCreate.Lore(ItemCreate.Item(279,1530), stats.get("cd").getName(), stats.get("cd").getText()));
		inv.setItem(15, ItemCreate.Lore(ItemCreate.Item(279,1531), stats.get("crt").getName(), stats.get("crt").getText()));

		
		Rule.c.get(player).invskill.setInventory(inv);
		Rule.c.get(player).invskill.openInventory(player);
		return false;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(ab == 1) {
			if(abcooldown <= 0) {
				abcooldown = 100;
				abtime = 60;
			}
			if(abtime > 0) {
				e.setDamage(e.getDamage()* 1.5f);
				Holo.create(e.getEntity().getLocation().clone().add(0,1,0), "§c§l[X1.5]",30, new Vector(0,0.12,0));
			}
		} else if(ab == 2) {
			if(!(e.getEntity() instanceof Player)) {
				e.setDamage(e.getDamage() * 2.5f);
			}
		} else if(ab == 4) {
			if(abcooldown <= 0) {
				abcooldown = 100;
				abtime = 10;
				LivingEntity en = (LivingEntity)e.getEntity();
				en.setNoDamageTicks(0);
				en.damage(e.getDamage()*0.5,player);
			} else if(abtime < 0) {
				e.setDamage(e.getDamage()*0.7f);
			}
		} else if(ab == 5) {
			if(abcooldown <= 0) {
				abcooldown = 10;
				LivingEntity en = (LivingEntity)e.getEntity();
				ARSystem.fixedDamage(en, player, player.getMaxHealth()*0.04);
			}
		} else if(ab == 6) {
			if(abcooldown <= 0) {
				abcooldown = 200;
				delay(()->{
					ARSystem.potion(player, 1, 100, 2);
					Rule.c.get(player).skillmult += 0.5;
					delay(()->{
						Rule.c.get(player).skillmult -= 0.5;
					},100);
				},20);
			}
		} else if(ab == 7) {
			ARSystem.heal(player, e.getDamage()*0.2);
		} else if(ab == 8) {
			if(abcooldown <= 0) {
				abcooldown = 60;	
				abtime = 0;
			} else {
				abtime += 100;
			}
			if(abtime >= 160) {
				abtime = 0;
				abcooldown = 0;
				LivingEntity en = (LivingEntity)e.getEntity();

				ARSystem.giveBuff(en, new Exposure(en) , 100, 1);
				en.setNoDamageTicks(0);
				en.damage(3,player);
			}
		} else if(ab == 9) {
			if(abcooldown <= 0) {
				abcooldown = 300;
				LivingEntity en = (LivingEntity)e.getEntity();
				for(int i = 0; i < 4;i++) {
					delay(()->{
						ARSystem.playSound((Entity)player, "entity.firework.launch",2);
						ARSystem.spellCast(player, en, "item100001_9");
					},2*i);
				}
			}
		}
		
		
		if(AMath.random(100) <= crt) {
			Holo.create(e.getEntity().getLocation().clone().add(0,1,0), "§c§l[X2]",30, new Vector(0,0.1,0));
			e.setDamage(e.getDamage() + e.getDamage()*(dmg*2));
		} else {
			e.setDamage(e.getDamage() + e.getDamage()*dmg);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(ab == 1) {
			if(abcooldown <= 0) {
				abcooldown = 100;
			}
		}
		
		e.setDamage(e.getDamage() - e.getDamage()*dfc);
		return super.onHit(e);
	}
	
	@Override
	public boolean onRemove(Entity caster) {
		// TODO Auto-generated method stub
		return super.onRemove(caster);
	}
	

	@Override
	public ItemStack getItem() {
		item = ItemCreate.Item(279,1541);
		List<String> lore = new ArrayList<String>();
		lore.add(Text.get("item:100001_lore1").replace("{hp}",""+ AMath.round(hp,1)).replace("{hp2}",""+ AMath.round(hpp*100,0)));
		lore.add(Text.get("item:100001_lore2").replace("{dmg}",""+ AMath.round(dmg*100,1)));
		lore.add(Text.get("item:100001_lore3").replace("{dfc}",""+ AMath.round(dfc*100,1)));
		lore.add(Text.get("item:100001_lore4").replace("{cd}",""+ AMath.round(cd*100,1)));
		lore.add(Text.get("item:100001_lore5").replace("{crt}",""+ crt));
		if(ab!=0) lore.add(Text.get("item:100001_lore6").replace("{t}",Text.get("item:100001_"+ab)));
		item = ItemCreate.Lore(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode), lore);
		return item;
	}
	
	public class Stats{
		String name;
		public int max = 300;
		int value = 0;
		int gold = 100;
		
		Stats(String name, int max, int gold){
			this.name = name;
			this.max = max;
			this.gold = gold;
		}

		public String getName() {
			if(value != max) return "§c§l"+Text.get("item:100001_"+name+"_t") + " §e[§7"+value+ " §c/ §f"+max +"§e]";
			return "§a§l"+Text.get("item:100001_"+name+"_t") + " §e[§fMAX§e]";
		}
		
		public List<String> getText() {
			List<String> lore = new ArrayList<String>();
			lore.add("§fValue : §7§l"+(int)(Text.getD("item:100001_"+name)*100*value)+"% "+"§a§l(+"+ (int)(Text.getD("item:100001_"+name)*100) + "%)");
			lore.add("§eGold : " + gold);
			return lore;
		}
		
		public int getValue() { return value; }
		
		public boolean isUp() {
			if(max <= value) return false;
			if(gold > Rule.playerinfo.get(player).gold) return false;
			Rule.playerinfo.get(player).gold -= gold;
			value++;
			upgard_gold += gold;
			gold +=(5*(gold/100));
			return true;
		}
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		if(abcooldown > 0) return "§c§l<§e"+Text.get("item:100001_"+ab)+" : " + AMath.round(abcooldown*0.05, 2)+"§c§l>";
		return super.getActionbar();
	}
}
