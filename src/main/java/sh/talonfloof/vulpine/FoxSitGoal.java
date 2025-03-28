package sh.talonfloof.vulpine;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.entity.passive.FoxEntity.OWNER;

public class FoxSitGoal extends Goal {
    private final FoxEntity fop;
    private LivingEntity owner = null;

    public FoxSitGoal(FoxEntity entity) {
        this.fop = entity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return this.fop.isSitting();
    }

    @Override
    public boolean canStart() {
        if (this.fop.isInsideWaterOrBubbleColumn()) {
            return false;
        }
        if (!this.fop.isOnGround()) {
            return false;
        }
        UUID uuid = this.fop.getDataTracker().get(OWNER).orElse(null);
        if(uuid == null) {
            return false;
        }
        LivingEntity livingEntity = Objects.requireNonNull(this.fop.getWorld().getServer()).getPlayerManager().getPlayer(uuid);
        if (livingEntity == null) {
            return true;
        }
        if (this.fop.squaredDistanceTo(livingEntity) < 144.0 && livingEntity.getAttacker() != null) {
            return false;
        }
        owner = livingEntity;
        return this.fop.isSitting();
    }

    @Override
    public void start() {
        this.fop.getNavigation().stop();
    }
}
