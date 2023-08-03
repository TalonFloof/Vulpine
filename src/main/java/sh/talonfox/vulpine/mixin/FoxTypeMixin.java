package sh.talonfox.vulpine.mixin;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import sh.talonfox.vulpine.FoxVariantTexture;
import sh.talonfox.vulpine.IFoxTypeCreator;
import sh.talonfox.vulpine.Vulpine;

import java.util.ArrayList;

import static sh.talonfox.vulpine.Vulpine.foxes;

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
        if(foxes == null) {
            foxes = new ArrayList<>();
            foxes.add(FoxEntity.Type.RED);
            foxes.add(FoxEntity.Type.SNOW);
            Vulpine.SILVER_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("SILVER",2,2, "silver");
            Vulpine.GRAY_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("GRAY",3,3, "gray");
            Vulpine.CROSS_FOX = IFoxTypeCreator.class.cast(FoxEntity.Type.RED).vulpine$newFoxVariant("CROSS",4,4, "cross");
            foxes.add(Vulpine.SILVER_FOX);
            foxes.add(Vulpine.GRAY_FOX);
            foxes.add(Vulpine.CROSS_FOX);
            FoxVariantTexture.init();
        }
        FoxEntity.Type[] fops = new FoxEntity.Type[foxes.size()];
        for(FoxEntity.Type fop : foxes) {
            fops[fop.getId()] = fop;
        }
        return fops;
    }

    public FoxEntity.Type vulpine$newFoxVariant(String enumName, int ordinal, int id, String typeName) {
        try {
            FoxEntity.Type newFox = makeVariant(enumName, ordinal, id, typeName);
            return newFox;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
