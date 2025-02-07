package util;

import org.bukkit.entity.LivingEntity;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import types.MobBuffs;

public class ARSMobAdd extends SkillMechanic implements ITargetedEntitySkill{
	protected String spellName;
    
    public ARSMobAdd(String skill, MythicLineConfig mlc)  {
        super(skill, mlc);
        this.spellName = config.getString(new String[] {"name", "n"}, "base");
        
	}

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		MobBuffs.get(spellName, (LivingEntity)data.getCaster().getEntity().getBukkitEntity());
		return false;
	}

}