package sh.talonfloof.vulpine;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.entity.passive.FoxEntity.OWNER;

public class FoxAttackWithOwnerGoal extends TrackTargetGoal {
    private final FoxEntity fop;
    private LivingEntity attacking;
    private int lastAttackTime;

    public FoxAttackWithOwnerGoal(FoxEntity tameable) {
        super(tameable, false);
        this.fop = tameable;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (this.fop.isSitting()) {
            return false;
        }
        UUID uuid = this.fop.getDataTracker().get(OWNER).orElse(null);
        if(uuid == null) {
            return false;
        }
        LivingEntity livingEntity = Objects.requireNonNull(this.fop.getWorld().getServer()).getPlayerManager().getPlayer(uuid);
        if (livingEntity == null) {
            return false;
        }
        this.attacking = livingEntity.getAttacking();
        int i = livingEntity.getLastAttackTime();
        return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacking);
        UUID uuid = this.fop.getDataTracker().get(OWNER).orElse(null);
        if(uuid != null) {
            LivingEntity livingEntity = Objects.requireNonNull(this.fop.getWorld().getServer()).getPlayerManager().getPlayer(uuid);
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
        }
        super.start();
    }
}
