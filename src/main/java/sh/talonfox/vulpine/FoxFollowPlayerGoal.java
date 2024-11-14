package sh.talonfox.vulpine;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.entity.passive.FoxEntity.OWNER;

public class FoxFollowPlayerGoal extends Goal {
    private final FoxEntity fop;
    private LivingEntity owner;
    private final double speed;
    private final float minDistance;
    private final float maxDistance;
    private int updateCountdownTicks;
    private final EntityNavigation navigation;
    private float oldWaterPathfindingPenalty;
    public FoxFollowPlayerGoal(FoxEntity entity, double speed, float minDistance, float maxDistance) {
        this.fop = entity;
        this.navigation = fop.getNavigation();
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }
    @Override
    public boolean canStart() {
        UUID uuid = this.fop.getDataTracker().get(OWNER).orElse(null);
        if(uuid == null) {
            return false;
        }
        LivingEntity livingEntity = Objects.requireNonNull(this.fop.getWorld().getServer()).getPlayerManager().getPlayer(uuid);
        if (livingEntity == null) {
            return false;
        }
        if (livingEntity.isSpectator()) {
            return false;
        }
        if (this.fop.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        }
        owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.fop.isSitting() || this.fop.isSleeping()) {
            return false;
        }
        return !(this.fop.squaredDistanceTo(this.owner) <= (double)(this.maxDistance * this.maxDistance));
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.fop.getPathfindingPenalty(PathNodeType.WATER);
        this.fop.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.fop.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.fop.getLookControl().lookAt(this.owner, 10.0f, this.fop.getMaxLookPitchChange());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        if (this.fop.squaredDistanceTo(this.owner) >= 144.0) {
            this.tryTeleport();
        } else {
            this.navigation.startMovingTo(this.owner, this.speed);
        }
    }

    private void tryTeleport() {
        BlockPos blockPos = this.owner.getBlockPos();
        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (!bl) continue;
            return;
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.getX()) < 2.0 && Math.abs((double)z - this.owner.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.fop.refreshPositionAndAngles((double)x + 0.5, y, (double)z + 0.5, this.fop.getYaw(), this.fop.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(fop, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockState blockState = this.fop.getWorld().getBlockState(pos.down());
        BlockPos blockPos = pos.subtract(this.fop.getBlockPos());
        return this.fop.getWorld().isSpaceEmpty(this.fop, this.fop.getBoundingBox().offset(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.fop.getRandom().nextInt(max - min + 1) + min;
    }
}
