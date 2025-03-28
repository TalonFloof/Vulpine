package sh.talonfloof.vulpine.mixin;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import sh.talonfloof.vulpine.IFoxTypeCreator;
import sh.talonfloof.vulpine.Vulpine;

@SuppressWarnings("unused")
@Mixin(FoxEntity.Type.class)
public abstract class FoxTypeMixin implements IFoxTypeCreator {
    @Invoker("<init>")
    private static FoxEntity.Type makeVariant(String enumName, int ordinal, int id, String typeName) {
        throw new AssertionError();
    }


    /**
     * @author TalonFox
     * @reason To allow us to add new fox variants
     */
    @Overwrite
    public static FoxEntity.Type[] values() {
        //don't remove this line. It's a hacky way to ensure that TrackedData regiser in correct order.
        FoxEntity.toGrowUpAge(0);
        if(Vulpine.SILVER_FOX == null) {
            int ordinal = 2;
            Vulpine.SILVER_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("SILVER",ordinal++, "silver");
            Vulpine.GRAY_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("GRAY",ordinal++, "gray");
            Vulpine.CROSS_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("CROSS",ordinal++, "cross");
            Vulpine.TALON = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("TALON",ordinal++, "talon");
            Vulpine.FENNEC_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("FENNEC",ordinal++, "fennec");
            Vulpine.NETHER_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("NETHER",ordinal++, "nether");
            Vulpine.ENDER_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("ENDER",ordinal++, "ender");
            Vulpine.SCULK_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("SCULK",ordinal++, "sculk");
            Vulpine.ZOMBIE_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("ZOMBIE",ordinal++, "zombie");
            Vulpine.SKELETON_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("SKELETON",ordinal++, "skeleton");
            Vulpine.ZOMBIFIED_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("ZOMBIFIED",ordinal++, "zombified");
            Vulpine.POLAR_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("POLAR",ordinal++, "polar");
            Vulpine.BLACK_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("BLACK",ordinal++, "black");
            Vulpine.BLUE_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("BLUE",ordinal++, "blue");
            Vulpine.RED_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("RED2",ordinal++, "red2");
            Vulpine.GRAY2_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("GRAY2",ordinal++, "gray2");
            Vulpine.ALBINO_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("ALBINO",ordinal++, "albino");
            Vulpine.SOUL_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("SOUL",ordinal++, "soul");
            Vulpine.PINK_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("PINK",ordinal++, "pink");
            Vulpine.addFoxes();
            //FoxVariantTexture.init();
        }
        FoxEntity.Type[] fops = new FoxEntity.Type[Vulpine.foxes.size()];
        for(FoxEntity.Type fop : Vulpine.foxes) {
            fops[fop.getId()] = fop;
        }
        return fops;
    }

    public FoxEntity.Type vulpine$newFoxVariant(String enumName, int ordinal, String typeName) {
        try {
            FoxEntity.Type newFox = makeVariant(enumName, ordinal, ordinal, typeName);
            return newFox;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
