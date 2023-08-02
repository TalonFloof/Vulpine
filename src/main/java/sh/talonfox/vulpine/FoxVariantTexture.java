package sh.talonfox.vulpine;

import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class FoxVariantTexture {
    public static HashMap<FoxEntity.Type, FoxVariantTexture> foxTextures = new HashMap<>();
    public static final FoxVariantTexture TALON = new FoxVariantTexture(new Identifier("vulpine","talon"), new Identifier("vulpine","talon_eep"));

    public final Identifier normalTexture;
    public final Identifier eepTexture;

    private FoxVariantTexture(Identifier normalTexture, Identifier eepTexture) {
        this.normalTexture = new Identifier(normalTexture.getNamespace(), "textures/entity/fox/" + normalTexture.getPath() + ".png");
        this.eepTexture = new Identifier(eepTexture.getNamespace(), "textures/entity/fox/" + eepTexture.getPath() + ".png");
    }

    public static Identifier getTexture(FoxEntity fox) {
        if("Talon".equals(fox.getName().getString())) {
            return fox.isSleeping()?TALON.eepTexture:TALON.normalTexture;
        }
        if(foxTextures.containsKey(fox.getVariant())) {
            return fox.isSleeping()?foxTextures.get(fox.getVariant()).eepTexture:foxTextures.get(fox.getVariant()).normalTexture;
        }
        return null;
    }

    public static void init() {
        foxTextures.put(Vulpine.SILVER_FOX,new FoxVariantTexture(new Identifier("vulpine","silver"),new Identifier("vulpine","silver_eep")));
        foxTextures.put(Vulpine.GRAY_FOX,new FoxVariantTexture(new Identifier("vulpine","gray"),new Identifier("vulpine","gray_eep")));
        foxTextures.put(Vulpine.CROSS_FOX,new FoxVariantTexture(new Identifier("vulpine","cross"),new Identifier("vulpine","cross_eep")));
    }
}
