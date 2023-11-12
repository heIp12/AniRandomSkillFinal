package ars;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;
import com.nisovin.magicspells.events.MagicSpellsLoadingEvent;
import com.nisovin.magicspells.events.SpellCastEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.gui.G_Menu;
import buff.Buff;
import buff.Silence;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import types.box;
import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Map;
import util.Text;



public class Event
  implements Listener
{
  
	@SuppressWarnings("unused")
	private final Rule plugin;
	  
	public Event(Rule ins)
	{
	  this.plugin = ins;
	}
	
	@EventHandler
	public void moth(ServerListPingEvent e) {
		e.setMotd("§6ARSF Version : §a§l"+ Map.Version);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		if(Rule.isben(e.getPlayer())) e.getPlayer().kickPlayer("https://cafe.naver.com/helpgames");
		
		if(Bukkit.getOnlinePlayers().size() == 1) {
			ARSystem.spellLocCast(e.getPlayer(), new Location(Map.world,-7,33.5,-108), "heIp");
		}
		if(Rule.playerinfo.get(e.getPlayer()) == null) {
			Rule.playerinfo.put(e.getPlayer(), new PlayerInfo(e.getPlayer()));
		}
		if(ARSystem.AniRandomSkill != null) {
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			e.getPlayer().getInventory().clear();
			Map.playeTp(e.getPlayer());
			
		} else {
			e.getPlayer().setGameMode(GameMode.ADVENTURE);
			e.getPlayer().setMaxHealth(20);
			e.getPlayer().setHealth(20);
			Map.playeTp(e.getPlayer());
		}
		Rule.isEvent(e.getPlayer());
		
	}
	
	@EventHandler
	public void comand(PlayerCommandPreprocessEvent e) {
		if (!e.getPlayer().isOp() && !e.getMessage().split(" ")[0].toLowerCase().equals("/ars")) {
			e.getPlayer().sendMessage("§a§l[ARSystem] : §f§l/ars help §7" + Main.GetText("main:cmd1"));
			e.getPlayer().sendMessage("§a§l[ARSystem] : §f§l/ars info <Name> §7" + Main.GetText("main:cmd6"));
			e.getPlayer().sendMessage("§a§l[ARSystem] : §f§l/ars code <Number> §7" + Main.GetText("main:cmd16"));
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDestroyByEntity(HangingBreakByEntityEvent e)
	{
		if (e.getEntity().getType() == EntityType.PAINTING) {
			e.setCancelled(true);
		}
		if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
			e.setCancelled(true);
		}
	}
	
		
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		if(Rule.c.get(e.getPlayer()) != null) {
			Rule.c.remove(e.getPlayer());
			if(ARSystem.AniRandomSkill != null) ARSystem.Stop();
		}
	}
	
	@EventHandler
	public void noFarmlanddestroy(PlayerInteractEvent event){
	    if ((event.getAction() == Action.PHYSICAL) && (event.getClickedBlock().getType() == Material.SOIL)) {
	      event.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void invclick(InventoryInteractEvent e) {
		if (e.getInventory().getType() == InventoryType.CRAFTING && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			e.getWhoClicked().closeInventory();
			new G_Menu((Player)e.getWhoClicked());
		}
	}
	

	@EventHandler
	public void invclick(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if(inv.getName().indexOf("CREATE") != -1) Rule.c.get(e.getPlayer()).invskill.End();
	}
	
	@EventHandler
	public void invclick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		e.setCancelled(true);
		if (e.getInventory().getType() == InventoryType.CRAFTING && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			e.getWhoClicked().closeInventory();
			new G_Menu((Player)e.getWhoClicked());
		}
		
		if(e.getWhoClicked().isOp() && e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(false);
		}
		
		if(inv.getName().indexOf(" : Skill") != -1 && e.getSlot() != -999 && e.getSlot() < e.getInventory().getSize()-9) {
			if(e.getClickedInventory().getName() != null && e.getClickedInventory().getName() == e.getWhoClicked().getInventory().getName()) {
				return;
			}
			Player p = Bukkit.getPlayer(inv.getName().replace(" : Skill", ""));
			
			if(Rule.c.get(p) != null && Rule.c.get(p).invskill != null) {
				e.setCancelled(true);
				if(e.getCurrentItem().getTypeId() == 0) return;
				Rule.c.get(p).invskill.Start(e.getCurrentItem().getItemMeta().getDisplayName());
				e.getWhoClicked().closeInventory();
			}
		}
		if(inv.getName().indexOf("CREATE") != -1 && e.getSlot() != -999) {
			if(e.getClickedInventory().getName() != null && e.getClickedInventory().getName() == e.getWhoClicked().getInventory().getName()) {
				return;
			}
			
			if(Rule.c.get(e.getWhoClicked()) != null && Rule.c.get(e.getWhoClicked()).invskill != null) {
				e.setCancelled(true);
				if(e.getCurrentItem().getTypeId() == 0) return;
				Rule.c.get(e.getWhoClicked()).invskill.Start(e.getCurrentItem().getItemMeta().getDisplayName());
			}
		}
	}

	@EventHandler
	public void chat(PlayerChatEvent e) {
		if(Rule.playerinfo.get(e.getPlayer()) != null) {
			e.setCancelled(true);
			String msg = e.getMessage();
			String s = "f";
			String t = Rule.playerinfo.get(e.getPlayer()).playerTrophy;
			
			if(Rule.buffmanager.GetBuffTime(e.getPlayer(), "panic") > 0 || t.equals("21-1")|| t.equals("73-1")) {
				msg = "";
				if((t.equals("21-1")|| t.equals("73-1"))&& AMath.random(10) <= 1) e.setMessage(Text.get("tropy:c"+t).substring(4,Text.get("tropy:c"+t).length()));
				String str[] = e.getMessage().split(" ");
				for(String str2 : str) {
					int o = 0;
					for(char c : str2.toCharArray()) {
							msg+=(char)((int)c + (AMath.random(4)-2));
						o++;
					}
					msg+=" ";
				}
			}
			if( t.equals("112-1") || (Rule.c.get(e.getPlayer()) != null && Rule.c.get(e.getPlayer()).getCode() == 112)) {
				msg = msg.replace("아", "냐").replace("야", "냐");
				msg = msg.replace("안", "냥").replace("얀", "냥");
				msg = msg.replace("앙", "냥").replace("양", "냥");
				msg = msg.replace("나", "냐").replace("라", "냐");
				msg = msg.replace("난", "냥").replace("낭", "냥");
				msg = msg.replace("다", "냐").replace("댜", "냐");
				msg = msg.replace("당", "냥").replace("댱", "냥");
				msg = msg.replace("단", "냥").replace("댠", "냥");
				msg = msg.replace("라", "냐").replace("랴", "냐");
				msg = msg.replace("란", "냥").replace("랑", "냥");
				msg = msg.replace("랸", "냥").replace("량", "냥");
				
				if(AMath.random(50) <= 1) msg = "골골골골";
			}
			e.setMessage(msg);
			
			if(t.equals("12-1")) s = "7";
			if(t.equals("0-13")) s = "6";
			if(t.equals("0-14")) s = "e";
			if(t.equals("0-17")) s = "a";
			if(t.equals("0-31")) s = "l";
			if(t.equals("0-35")) s = "b";
			if(t.equals("23-2")) s = "d";
			if(t.equals("27-1")) s = "n";
			
			boolean chat = true;
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).chat(e)) {
					
				} else {
					chat = false;
				}
			}
			msg = e.getMessage();
			
			if(chat) {
				if(t.equals("0-37")) {
					String str[] = msg.split(" ");
					msg = "";
					for(String str2 : str) {
						int o = 0;
						msg += "§b";
						for(char c : str2.toCharArray()) {
							if(o == 1) msg+="§f";
								msg+=c;
							o++;
						}
						msg+=" ";
					}
					
				}
				Bukkit.broadcastMessage(Rule.playerinfo.get(e.getPlayer()).name + " "+e.getPlayer().getName() +"§f :§"+s+" " +msg);
			}
		}
		if(Main.GetText("general:skip").contentEquals(e.getMessage()) && ARSystem.AniRandomSkill != null) {
			ARSystem.AniRandomSkill.skip(e.getPlayer());
		}
	}
	  
	@EventHandler
	public void moveevent(PlayerMoveEvent e){
		try{
			if(Rule.buffmanager != null && Rule.buffmanager.getBuffs((LivingEntity) e.getPlayer()) != null && Rule.buffmanager.getBuffs((LivingEntity) e.getPlayer()).getBuff() != null) {
				for(Buff buff : Rule.buffmanager.getBuffs((LivingEntity) e.getPlayer()).getBuff()) {
					buff.onMove(e);
				}
			}
		} catch(Exception ev) {
			
		}
	}
	
	@EventHandler
	private void block(BlockFromToEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	private void block(BlockGrowEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	private void block(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	private void block(BlockPhysicsEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	private boolean key(PlayerItemHeldEvent e) {
		if(Rule.c.get(e.getPlayer()) != null) {
			if(Rule.c.get(e.getPlayer()).key(e)) {
				
			}
		}
		return true;
	}
	
	
	@EventHandler
	private boolean onEntityDamage(EntityDamageEvent e){
		if(e.getCause() == DamageCause.VOID) e.setCancelled(true);
		if(e.getCause() == DamageCause.SUFFOCATION) e.setCancelled(true);
		if(e.getEntity() instanceof ArmorStand) e.setCancelled(true);
		if(e.getCause() == DamageCause.FALL && !(e.getEntity() instanceof Player)) {
			if(e.getEntity().getLocation().getY() < 10) {
				((LivingEntity)e.getEntity()).teleport(Map.randomLoc());
			}
			e.setCancelled(true);
		}
		
		if(!e.isCancelled()) {
			if(e.getEntityType() == EntityType.PLAYER &&Rule.c.get(e.getEntity()) != null) {
				if(Rule.buffmanager.OnBuffTime((LivingEntity) e.getEntity(), "nodamage") || Rule.buffmanager.OnBuffTime((LivingEntity) e.getEntity(), "timestop")) {
					e.setCancelled(true);
				} else if(e.getCause() == DamageCause.FALL && Rule.c.get(e.getEntity()) != null) {
					if(e.getDamage()/5 > 0.5) {
						double mult = Text.getD("general:falldamage_mult");
						if(Main.GetText("general:falldamage_type").equalsIgnoreCase("stun")) {
							((Player)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (e.getDamage()*5*mult), (int) (e.getDamage()/2*mult)));
							ARSystem.addBuff(((Player)e.getEntity()), new Silence(((Player)e.getEntity())), (int) (e.getDamage()*4*mult));
							e.setCancelled(true);
						}
						if(Main.GetText("general:falldamage_type").equalsIgnoreCase("damage")) {
							e.setDamage(e.getDamage()*mult);
						}
					} else {
						e.setCancelled(true);
					}
				} else if(!Rule.c.get(e.getEntity()).damage(e)) {
					e.setDamage(0);
				}

			}
		}
		if(!e.isCancelled() && e.getFinalDamage() > 0&& e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.CUSTOM) {
			damageText(e.getEntity().getLocation(),"§d§l⚕ ",e.getDamage());
			if(Rule.c.get(e.getEntity()) != null && !e.isCancelled()) {
				if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1) {
					e.setCancelled(true);
					e.setDamage(0);
					Skill.death(e.getEntity(), e.getEntity());
				}
			}
		}
		return true;
	}
	
	public void damageText(Location e,String s,double damage) {
		int val = (int) AMath.round(damage,0);
		if(val > 20) val = 20;
		if(val <= 0) val = 1;
		
		double vector1 = (0.01*(21-val)) - AMath.random(21-val)*0.02;
		double vector3 =(0.01*(21-val)) -  AMath.random(21-val)*0.02;
		if(val > 10) vector1 = vector3 = 0;
		double vector2 = 0.55 - (val*0.05);
		if(vector2 <= 0.01) vector2 = 0.01;
		e = e.add(new Vector(0.5-AMath.random(100)*0.01,0.2-AMath.random(40)*0.01,0.5-AMath.random(100)*0.01));
		Holo.create(e,s+" "+ AMath.round(damage,2),2+((int)damage*4),new Vector(vector1,vector2,vector3));
	}
	
	@EventHandler
	private void TargetSpell(SpellTargetEvent e) {
		if(ARSystem.isTarget(e.getTarget(), e.getCaster(), box.TARGET)) {
			if(Rule.c.get(e.getCaster()) != null) {
				Rule.c.get(e.getCaster()).TargetSpell(e,true);
			}
			if(Rule.c.get(e.getTarget()) != null) {
				Rule.c.get(e.getTarget()).TargetSpell(e,false);
			}
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	private void Spell(SpellCastEvent e) {
		if(Rule.c.get(e.getCaster()) != null) {
			Rule.c.get(e.getCaster()).SpellCastEvent(e);
		}
	}
	
	@EventHandler
	private void WinEvent(WinEvent e) {
		for(Player p : Rule.c.keySet()) {
			if(!e.isCancelled()) {
				Rule.c.get(p).WinEvent(e);
			}
		}
	}
	
	@EventHandler
	private boolean Deathevent(PlayerDeathEvent e){
		if(Rule.buffmanager.getBuffs((LivingEntity) e.getEntity()) != null) {
			for(Buff buff : Rule.buffmanager.getBuffs((LivingEntity) e.getEntity()).getBuff()) {
				buff.onDeath(e);
			}
		}
		if(Rule.c.get(e.getEntity()) != null) {
			Rule.c.get(e.getEntity()).death(e);
		}
		
		return false;
	}

	@EventHandler 
	private boolean EntityDamageEvent(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && Rule.team.isTeamAttack((Player)e.getDamager(), (Player) e.getEntity())) {
			e.setCancelled(true);
			return true;
		}
		if(ARSystem.AniRandomSkill == null && e.getEntity() instanceof Player&& e.getDamager() instanceof Player) {
			e.setCancelled(true);
			return true;
		}
		if(e.getEntity() instanceof Player) {
			if(!(e.getDamager() instanceof Player)) ((LivingEntity)e.getEntity()).setNoDamageTicks(0);
		}
		if(ARSystem.isTarget(e.getEntity(), e.getDamager(), box.TARGET)) {
			if(Rule.c.get(e.getDamager()) != null) {
				Rule.c.get(e.getDamager()).setFrist_Damage(e, true);
			}
			
			if(!e.isCancelled() &&  Rule.buffmanager.getBuffs((LivingEntity) e.getDamager()) != null) {
				for(Buff buff : Rule.buffmanager.getBuffs((LivingEntity) e.getDamager()).getBuff()) {
					if(e.getDamage() > 0 && buff != null) {
						buff.onAttack(e);
					}
				}
			}
			
			if(!e.isCancelled() &&  Rule.buffmanager.getBuffs((LivingEntity) e.getEntity()) != null) {
				for(Buff buff : Rule.buffmanager.getBuffs((LivingEntity) e.getEntity()).getBuff()) {
					if(e.getDamage() > 0 && buff != null) {
						buff.onHit(e);
					}
				}
			}

			if(!e.isCancelled() &&e.getDamager().getType() == EntityType.PLAYER &&Rule.c.get(e.getDamager()) != null) {
				if(!Rule.c.get(e.getDamager()).entitydamage(e,true)) {
					
				}
			}
			
			if(!e.isCancelled() &&e.getEntity().getType() == EntityType.PLAYER &&Rule.c.get(e.getEntity()) != null) {
				if(!Rule.c.get(e.getEntity()).entitydamage(e,false)) {
	
				}
			}
			if(Rule.c.get(e.getEntity()) != null) {
				Rule.c.get(e.getEntity()).setFrist_Damage(e, false);
			}
		}
		if(e.getDamage() <= 0.01 || e.isCancelled()) {
			e.setCancelled(true);
		} else {
			if(!(e.getEntity() instanceof ArmorStand)) {
				if(((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) {
					if(e.getEntity().getType() != EntityType.PLAYER) {
						if(Rule.c.get(e.getDamager()) != null) {
							Rule.c.get(e.getDamager()).s_damage +=(e.getFinalDamage()/5);
						}
					} else {
						if(Rule.c.get(e.getDamager()) != null) {
							Rule.c.get(e.getDamager()).s_damage +=e.getFinalDamage();
						}
					}
				}
				if(!(e.getDamager() instanceof Player) || (e.getDamager() instanceof Player)) {
					if(e.isCancelled() && e.getFinalDamage() > 0 && ((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) {
						Location loc = e.getEntity().getLocation().clone();
						loc.add(0.5-AMath.random(100)*0.01, 0.5-AMath.random(100)*0.01, 0.5-AMath.random(100)*0.01);
						Holo.create(loc,"§9§l✖ "+ (Math.round(e.getFinalDamage()*100)/100.0f),40,new Vector(0,0.05,0));
					}
					if(!e.isCancelled() && e.getDamage() > 0 && e.getEntity().getLocation() != null) {
						if(e.getEntity() instanceof Player || e.getDamager() instanceof Player) {
							damageText(e.getEntity().getLocation(),"§c§l⚔ ",e.getDamage());
						}
						((LivingEntity)e.getEntity()).setNoDamageTicks(20);
					}
					
	
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity entity = (LivingEntity) e.getEntity();
						if(entity.getHealth() - e.getFinalDamage() < 1) {
							Skill.death(entity, e.getDamager());
							e.setCancelled(true);
						}
					}
				} else {
					e.setCancelled(true);
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void loading(MagicSpellsLoadingEvent e) {
		MSUtil.loading(e);
	}
}
