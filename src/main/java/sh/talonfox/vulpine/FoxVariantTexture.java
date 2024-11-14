package sh.talonfox.vulpine;

import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class FoxVariantTexture {
    public static HashMap<FoxEntity.Type, FoxVariantTexture> foxTextures = new HashMap<>();
    public static final FoxVariantTexture TALON = new FoxVariantTexture(Identifier.of("vulpine","talon"), Identifier.of("vulpine","talon_seep"));

    public final Identifier normalTexture;
    public final Identifier sleepTexture;

    private FoxVariantTexture(Identifier normalTexture, Identifier sleepTexture) {
        this.normalTexture = Identifier.of(normalTexture.getNamespace(), "textures/entity/fox/" + normalTexture.getPath() + ".png");
        this.sleepTexture = Identifier.of(sleepTexture.getNamespace(), "textures/entity/fox/" + sleepTexture.getPath() + ".png");
    }

    public static Identifier getTexture(FoxEntity fox) {
        if("Talon".equals(fox.getName().getString())) {
            return fox.isSleeping()?TALON.sleepTexture :TALON.normalTexture;
        }
        if(foxTextures.containsKey(fox.getVariant())) {
            return fox.isSleeping()?foxTextures.get(fox.getVariant()).sleepTexture :foxTextures.get(fox.getVariant()).normalTexture;
        }
        return null;
    }

    public static void init() {
        foxTextures.put(Vulpine.SILVER_FOX,new FoxVariantTexture(Identifier.of("vulpine","silver"),Identifier.of("vulpine","silver_sleep")));
        foxTextures.put(Vulpine.GRAY_FOX,new FoxVariantTexture(Identifier.of("vulpine","gray"),Identifier.of("vulpine","gray_sleep")));
        foxTextures.put(Vulpine.CROSS_FOX,new FoxVariantTexture(Identifier.of("vulpine","cross"),Identifier.of("vulpine","cross_sleep")));
    }
}
