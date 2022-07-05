package c;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellCastEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Barrier;
import buff.Buff;
import buff.BuffAC;
import buff.Noattack;
import buff.Nodamage;
import buff.PlusHp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import manager.AdvManager;
import manager.BuffManager;
import manager.Holo;
import manager.ScoreBoard;
import util.Text;
import util.AMath;
import util.InvSkill;
import util.MSUtil;
import util.Map;

public class c00main implements Listener {
	public float hp = 20;
	public int number = 0;
	public Player player;
	public String name = "";
	public boolean spben = false;
	
	public boolean isps = false;
	public boolean psopen = false;
	public int s_kill = 0;
	public float s_damage = 0;
	public int s_score = 0;
	public int score = 0;
	public InvSkill invskill = null;
	
	public float coolm = 1;
	public float setcooldown[] = new float[10];
	public float cooldown[] = new float[10];
	public List<Buff> buffTexts = new ArrayList<Buff>();
	
	public double skillmult = 1;
	
	public double sskillmult = 0;
	public double sbuffmult = 0;
	
	public boolean isscoreboard = true;
	
	public String buffText = "";
	public boolean buffHardCC = false;
	
	public double myhp = 0;
	
	public c00main c = this;
	
	public int maptick = 0;

	protected Plugin plugin;
	protected int tk = 0;
	
	protected List<String> scoreBoardText = new ArrayList<String>();
	
	public void e(){
		if(myhp < player.getHealth()) Holo.create(player.getLocation().clone().add(0.5-AMath.random(10)*0.1,0.5,AMath.random(10)*0.1-AMath.random(10)*0.1),"§a§l✞ "+ (Math.round((player.getHealth()-myhp)*100)/100.0),10,new Vector(0,-0.01,0));
		myhp = player.getHealth();
	}
	public c00main(Player p,Plugin pl,c00main ch){
		c = ch;
		if(c == null) {
			ch = this;
		}
		if(Rule.buffmanager.getBuffs(p) != null) {
			Rule.buffmanager.getBuffs(p).clear();
		}
		ARSystem.giveBuff(p, new PlusHp(p), 0);
		ARSystem.giveBuff(p, new Barrier(p), 0);
		ARSystem.giveBuff(p, new BuffAC(p), 0);
		
		plugin = pl;
		player = p;
		MSUtil.resetbuff(p);
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1000);
		p.setGameMode(GameMode.ADVENTURE);
		if(ARSystem.AniRandomSkill != null) {
			int i = -(ARSystem.AniRandomSkill.time/3);
			ARSystem.giveBuff(p, new TimeStop(p), i*20);
			ARSystem.giveBuff(p, new Silence(p), -(ARSystem.AniRandomSkill.time/3)*40);
			ARSystem.giveBuff(p, new Nodamage(p), -(ARSystem.AniRandomSkill.time/3)*40);
		}
		s_score = 200;
		player.setWalkSpeed(0.2f);
	}
	public void repset() {
		s_score = c.s_score;
		s_kill = c.s_kill;
		s_damage = c.s_damage;
		sbuffmult = c.sbuffmult;
		skillmult = c.skillmult;
		sskillmult = c.sskillmult;
		myhp = c.myhp;
	}
	public c00main ccset(c00main c) {
		this.c = c;
		repset();
		return this;
	}
	public void infoload() {
		psopen = (boolean)Rule.Var.Load(player.getName()+".Sp.c"+(number%1000));
		Rule.savePoint(player.getName(), (int) Rule.playerinfo.get(player).getcradit(), 0);
		Rule.savePoint(player.getName(), (int) Rule.playerinfo.get(player).getScore(), 1);
	}
	
	public void info() {
		if(ARSystem.AniRandomSkill != null) {
			Rule.Var.addInt("ARSystem.c"+(number%1000)+"Play",1);
			Rule.Var.addInt("ARSystem.c"+(number%1000)+"Kill",s_kill);
			
			if(ARSystem.AniRandomSkill != null) {
				Rule.Var.addInt(player.getName()+".c"+(number%1000)+"Time",ARSystem.AniRandomSkill.getTime());
			}
			Rule.Var.addInt(player.getName()+".c"+(number%1000)+"Play",1);
			Rule.Var.addInt(player.getName()+".c"+(number%1000)+"Kill",s_kill);
			if(s_kill > 4) {
				Rule.playerinfo.get(player).tropy(0,4);
			}
		}
		Rule.Var.open(player.getName()+".Sp.c"+(number%1000),isps);
		if(Rule.playerinfo.get(player).spopen.length > number%1000) {
			Rule.playerinfo.get(player).spopen[number%1000] = (boolean)Rule.Var.Load(player.getName()+".Sp.c"+(number%1000));
		}
		Rule.playerinfo.get(player).save();
		Rule.savePoint(player.getName(), (int) Rule.playerinfo.get(player).getcradit(), 0);
		Rule.savePoint(player.getName(), (int) Rule.playerinfo.get(player).getScore(), 1);

	}
	
	public void text() {
		infoload();
		player.sendMessage("§a§l§m|=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
		String name = "§e§l[No."+(number%1000)+"]§b"+Main.GetText("c"+number+":name1")+" "+Main.GetText("c"+number+":name2");
		String lore = "§c§lName : §7" +Main.GetText("c"+number+":name1")+" "+Main.GetText("c"+number+":name2");
		lore+="\n&e&lAni : &6"+Text.getAni(Main.GetText("c"+number+":ani"));
		lore+="\n&4&lHp : &c" +Main.GetText("c"+number+":hp");
		lore+="\n&7Tag:&8&l "+Text.getType(Main.GetText("c"+number+":type"));
		player.spigot().sendMessage(Text.hover(player,name, lore));
		player.sendMessage("§a§l§m|=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
		String ps = "";
		
		for(int j=0;j<100;j++) {
			if(Main.GetText("c"+number+":ps_lore"+j)!=null) {
				if(Main.GetText("c"+number+":ps_lore"+j).indexOf(":") != -1) {
					ps+="&f&o"+Main.GetText("c"+number+":ps_lore"+j)+"\n";
				} else {
					ps+="&7"+Main.GetText("c"+number+":ps_lore"+j)+"\n";
				}
			}
		}
		ps+="&7Tag:&8&l "+Text.getType(Main.GetText("c"+number+":ps_type"));
		player.spigot().sendMessage(Text.hover(player,"§d§lPassive: §f§l"+Main.GetText("c"+number+":ps"), ps));
		for(int i=0;i<10;i++) {
			if(Main.GetText("c"+number+":sk"+i)!=null) {
				String text = "&e&lSkill : &f" + Main.GetText("c"+number+":sk"+i) + "\n";
				text += "&a&lCooldown : &c" + setcooldown[i] + "\n";
				for(int j=0;j<100;j++) {
					if(Main.GetText("c"+number+":sk"+i+"_lore"+j)!=null) {
						if(Main.GetText("c"+number+":sk"+i+"_lore"+j).indexOf(":") != -1) {
							text+="&f&o"+Main.GetText("c"+number+":sk"+i+"_lore"+j)+"\n";
						} else {
							text+="&7"+Main.GetText("c"+number+":sk"+i+"_lore"+j)+"\n";
						}
					}
				}
				text+="&7Tag:&8&l "+Text.getType(Main.GetText("c"+number+":sk"+i+"_type"));
				if(i==0 && !psopen) {
					String finaltext = "";
					String texts[] = text.split("\n");
					for(int texti=0;texti<texts.length;texti++) {
						String ndt = "";
						for(int textj=0;textj<texts[texti].length();textj++) {
							ndt += "a";
						}
						finaltext += "&k"+ndt+"\n";
					}
					finaltext += "&r&c"+Main.GetText("main:msg11")+"\n";
					finaltext += "&r&c"+Main.GetText("main:msg12")+"\n";
					text = finaltext;
				}
				
				if(i==0) {
					player.spigot().sendMessage(Text.hover(player,"§c§lSpecial Skill: §f§l"+Main.GetText("c"+number+":sk"+i), text));
				} else {
					player.spigot().sendMessage(Text.hover(player,"§6§lSkill"+i+": §f"+Main.GetText("c"+number+":sk"+i), text));
				}
			}
		}
		player.sendMessage("§a§l§m|=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
	}
	
	public void cooldown() {
		for(int i = 0; i < cooldown.length; i++) {
			if(cooldown[i] > 0) {
				cooldown[i] -= 0.1*(skillmult+sskillmult);
				if(cooldown[i] < 0) {
					cooldown[i] = 0;
				}
			}
		}
	}
	
	protected void load() {
		for(int i = 0; i < setcooldown.length; i++) {
			if(Main.GetText("c"+number+":sk"+i+"_cooldown") != null) {
				setcooldown[i] = (float) (Integer.parseInt(Main.GetText("c"+number+":sk"+i+"_cooldown"))/10.0);
			} else {
				setcooldown[i] = 0;
			}
		}
		setcooldown[7] = 10;
		setcooldown[6] = 3;
		setcooldown[9] = 2;
		hp = Integer.parseInt(Main.GetText("c"+number+":hp"));
		player.playSound(player.getLocation(), "c"+number+"select", 1, 1);
		player.setMaxHealth(hp);
		player.setHealth(hp);
		Rule.playerinfo.get(player).playchar = number;
		
		if(ARSystem.AniRandomSkill != null && (boolean)Rule.Var.Load("System.info.score")) {
			float sc = ARSystem.AniRandomSkill.gamescore;
			float sc2 = Rule.playerinfo.get(player).getScore();
			
			float mu = sc2/sc;
			mu = Math.round(mu*10)/10.f;

			if(mu > 3) mu = 3;
			if(mu < 0.1) mu = 0.1f;
			
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info24")+" "+sc2 +" ("+(mu*100)+"%)");
			if(sc2-sc > 30 || sc2-sc < -30 ) {
				if(mu > 1.5) {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info26")+" +"+(100+(mu/3*100)) +"%");
					for(int j=0; j < setcooldown.length;j++) {
						setcooldown[j] *= 1.0+(mu/3);
						coolm = (float) (1.0+(mu/3));
					}
				}
	
				if(mu >= 1.9) {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info27"));
					spben = true;
				} else {
					spben = false;
				}
				
				if(mu < 0.5) {
					player.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:info26")+" "+(50+(mu*50)) +"%");
					for(int j=0; j < setcooldown.length;j++) {
						setcooldown[j] *= 0.5+(mu/2);
						coolm = (float) (0.5+(mu/2));
					}
				}
			}
		}
	}
	
	protected boolean spskillon() {
		s_score += 300;
		isps = true;
		return true;
	}
	
	protected void spskillen() {
		for(Player p: Rule.c.keySet()) {
			Rule.c.get(p).PlayerSpCast(player);
		}
		String n = Main.GetText("c"+number+":name1") + " ";
		if(n.equals("-")) {
			n = "";
		}
		n +=Main.GetText("c"+number+":name2");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":sk0"));
		}
		String na = "c"+number+":sk0"+"/&c!!!";
		player.performCommand("tm anitext "+player.getName()+" SUBTITLE true 40 "+na);
	}
	
	protected boolean skill1(){ return true; }
	protected boolean skill2(){ return true; }
	protected boolean skill3(){ return true; }
	protected boolean skill4(){ return true; }
	protected boolean skill5(){ return false; }
	protected boolean skill6(){
		if(player.isSneaking()) {
			Rule.playerinfo.get(player).addcradit(-10,Main.GetText("main:msg105"));
			ARSystem.Death(player,player);
		} else {
			player.sendMessage("§a§l[ARSystem]§f§l : "+ Main.GetText("main:msg21"));
		}
		return false;
	}
	protected boolean skill7(){
		text();
		return false;
	}
	
	protected boolean skill9(){
		player.getWorld().playSound(player.getLocation(), "c"+number+"db", 1, 1);
		return false;
	}
	protected void skill(String name) {
		player.performCommand("c "+name);
	}
	protected boolean skillCooldown(int i) {
		if(cooldown[i] <= 0) {
			cooldown[i] = setcooldown[i];
			return true;
		}
		return false;
	}
	
	public boolean key(PlayerItemHeldEvent e) {
		PlayerSpellCast(e.getPlayer());
		try{
			for(Buff buff : Rule.buffmanager.getBuffs((LivingEntity) player).getBuff()) {
				if(buff != null) {
					buff.onSkill(e);
				}
			}
		} catch(ConcurrentModificationException ev) {
	
		}
		if(!e.isCancelled()) {
			if(e.getNewSlot() == 0 && skillCooldown(1)) skill1();
			if(e.getNewSlot() == 1 && skillCooldown(2)) skill2();
			if(e.getNewSlot() == 2 && skillCooldown(3)) skill3();
			if(e.getNewSlot() == 3 && skillCooldown(4)) skill4();
			if(e.getNewSlot() == 4 && skillCooldown(5)) skill5();
			if(e.getNewSlot() == 5 && skillCooldown(6)) skill6();
			if(e.getNewSlot() == 6 && skillCooldown(7)) skill7();
			if(e.getNewSlot() == 8 && skillCooldown(9)) skill9();
		}
		if(e.getNewSlot() != 7 && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			player.getInventory().setHeldItemSlot(7);
		}
		return false;
	}
	
	public boolean remove(Entity caster) {
		return true;
	}
	
	protected void delay(Runnable event, int delay) {
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, event, delay);
	}
	
	public boolean death(PlayerDeathEvent e) {
		for(PotionEffect pe : player.getActivePotionEffects()) player.removePotionEffect(pe.getType());
		MSUtil.resetbuff(player);
		scoreBoardText.clear();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,()->{ARSystem.Death(e.getEntity(),e.getEntity());});
		return true;
	}
	public void ScoreboardSet() {
		scoreBoardText.clear();
		scoreBoardText.add("&m&l=========================");
		scoreBoardText.add("§e§l [No."+(number%1000)+"] §b"+Main.GetText("c"+number+":name1")+" "+Main.GetText("c"+number+":name2"));
		scoreBoardText.add("&c&l ["+Main.GetText("main:s7")+"] : "+ (score-200)%100000);
		if(s_kill > 0) {
			scoreBoardText.add("&6 ["+Main.GetText("main:s5")+"] : &f"+ s_kill);
		}
		scoreBoardText.add("&6 ["+Main.GetText("main:s6")+"] : &f"+ (Math.round(s_damage*10.0)/10.0));
		scoreBoardText.add("&m&l=--=--=--=--=--=--=--=--=");
		if(buffTexts.size() > 0) {
			for(Buff s : buffTexts) {
				scoreBoardText.add(s.getText());
			}
			buffTexts.clear();
			scoreBoardText.add("&m&l==-=-==--==-=-==--==-=-==");
		}

		if(ARSystem.AniRandomSkill != null) {
			scoreBoardText.add("&f&l ["+Main.GetText("main:s10")+"] : &a&l"+ ARSystem.AniRandomSkill.getTime());
		}
		scoreBoardText.add("&6 ["+Main.GetText("main:s9")+"] : &f"+ ARSystem.getPlayerCount());
		scoreBoardText.add("&m&l-=-=-==--==-=-==--==-=-=-");
	}
	
	public boolean ticks() {
		if(isscoreboard && tk%20==0) {
			ScoreboardSet();
		}
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 0) {
			tick();
		} else if(ARSystem.AniRandomSkill == null){
			tick();
		}
		
		if(!Map.inMap(player) && player.getGameMode() != GameMode.SPECTATOR && ARSystem.AniRandomSkill != null) {
			maptick++;
		} else {
			maptick = 0;
		}
		if(player.getLocation().getBlock().getTypeId() == 30 || player.getLocation().clone().add(0,-1,0).getBlock().getTypeId() == 30) {
			skill("cloud");
		}
		if(player.getLocation().getBlock().getTypeId() == 165 || player.getLocation().clone().add(0,-1,0).getBlock().getTypeId() == 165) {
			skill("slime");
		}
		if(player.getLocation().getBlock().getTypeId() == 205 || player.getLocation().clone().add(0,-1,0).getBlock().getTypeId() == 204) {
			skill("speed");
		}
		if(!Map.inMap(player) &&  ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time < 0) {
			maptick = 0;
			Map.playeTp(player);
		}
		
		if(maptick > 60 && ARSystem.AniRandomSkill.time > 0) {
			maptick = 0;
			Map.playeTp(player);
			ARSystem.giveBuff(player, new Silence(player), 40);
			ARSystem.giveBuff(player, new Noattack(player), 40);
			ARSystem.giveBuff(player, new Nodamage(player), 40);
			player.setFallDistance(0);
			player.setHealth(player.getHealth()/2);
		}
		
		
		if(!Map.inMap(player) && player.getGameMode() != GameMode.SPECTATOR && ARSystem.AniRandomSkill == null) {
			Map.playeTp(player);
		}
		
		if(tk%20==0) {
			if(!isscoreboard) {
				scoreBoardText.clear();
			} else {
				if(skillmult != 1 || sskillmult != 0) {
					scoreBoardText.add("&9&l ["+Main.GetText("main:s4")+"] : &b"+ (Math.round((skillmult+sskillmult-1)*100))+"%");
				}
				if(Rule.buffmanager.GetBuffValue(player, "buffac") > 0) {
					scoreBoardText.add("&9&l ["+Main.GetText("main:s8")+"] : &b"+ (Math.round((Rule.buffmanager.GetBuffValue(player, "buffac"))*100))+"%");
				}
			}
			
			ScoreBoard.createScoreboard(player, scoreBoardText);
		}
		tk%=20;
		tk++;
		return false;
	}
	
	public boolean damage(EntityDamageEvent e) { return true; }
	public boolean entitydamage(EntityDamageByEntityEvent e,boolean isAttack) { return true; }
	public void TargetSpell(SpellTargetEvent e,boolean mycaster) {
		if (mycaster && e.getTarget() instanceof ArmorStand) {
			e.setCancelled(true);
		}
	}
	public void PlayerSpellCast(Player p) { return; }
	public void PlayerSpCast(Player p) { return; }
	public void PlayerDeath(Player p,Entity e) { return; }
	public void SpellCastEvent(SpellCastEvent e) {}
	
	public boolean firsttick() { return false; }
	public boolean tick() { return false; }
	
	public float getHp() {return hp;}
	public int getCode() { return number; }
	
	public void setStack(float f) {
		player.sendMessage("No Stack");
	}
	
	public int getScore() {
		score = s_score;
		score += s_damage * 10;
		score += s_kill * 200;
		return score;
		
	}
	
	public void makerSkill(LivingEntity target, String n) {
		
	}
	public void kill() {
		s_kill++;
		if(ARSystem.AniRandomSkill != null &&  ARSystem.AniRandomSkill.playerkill.size() == 0) {
			ARSystem.playSoundAll("fkill");
		} else if(s_kill <= 5) {
			ARSystem.playSoundAll("kill"+s_kill);
		} else {
			ARSystem.playSoundAll("killetc");
		}
	}
	public boolean chat(PlayerChatEvent e) {

		return true;
	}

}
