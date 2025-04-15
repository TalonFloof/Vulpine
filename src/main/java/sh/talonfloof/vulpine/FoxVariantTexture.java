package sh.talonfloof.vulpine;

import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class FoxVariantTexture {
    public static HashMap<FoxEntity.Type, ModFoxType> foxTextures = new HashMap<>();
    public static final FoxVariantTexture TALON = new FoxVariantTexture(Identifier.of("vulpine","talon"), Identifier.of("vulpine","talon_seep"));

    public final Identifier normalTexture;
    public final Identifier sleepTexture;

    public FoxVariantTexture(Identifier normalTexture, Identifier sleepTexture) {
        this.normalTexture = Identifier.of(normalTexture.getNamespace(), "textures/entity/fox/" + normalTexture.getPath() + ".png");
        this.sleepTexture = Identifier.of(sleepTexture.getNamespace(), "textures/entity/fox/" + sleepTexture.getPath() + ".png");
    }

    public static Identifier getTexture(FoxEntity fox) {
        if ("Talon".equals(fox.getName().getString())) {
            return foxTextures.get(Vulpine.TALON).getTextureIdentifier(fox.isSleeping());
        }
        if (foxTextures.containsKey(fox.getVariant())) {
            return foxTextures.get(fox.getVariant()).getTextureIdentifier(fox.isSleeping());
        }
        return null;
    }

    public static void addTexture(FoxEntity.Type type, String name){
        foxTextures.put(type, new ModFoxType(name));
    }

    public static void init() {
        foxTextures.put(Vulpine.SILVER_FOX,new ModFoxType("silver"));
        foxTextures.put(Vulpine.GRAY_FOX,new ModFoxType("gray"));
        foxTextures.put(Vulpine.CROSS_FOX,new ModFoxType("cross"));
        foxTextures.put(Vulpine.TALON,new ModFoxType("talon"));
        foxTextures.put(Vulpine.FENNEC_FOX,new ModFoxType("fennec"));
        foxTextures.put(Vulpine.NETHER_FOX,new ModFoxType("nether"));
    }
}
