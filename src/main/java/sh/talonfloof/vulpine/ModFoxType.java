package sh.talonfloof.vulpine;

import net.minecraft.util.Identifier;

public class ModFoxType {
    private String variantName;
    private Identifier textureIdentifier;
    private Identifier sleepingTextureIdentifier;

    public ModFoxType(String variantName) {
        this.variantName = variantName;
        textureIdentifier = Identifier.of(Vulpine.MOD_ID,"textures/entity/fox/"+variantName+".png");
        sleepingTextureIdentifier = Identifier.of(Vulpine.MOD_ID,"textures/entity/fox/"+variantName+"_sleep.png");
;    }

    public Identifier getTextureIdentifier(boolean isSleepling) {
        return isSleepling?sleepingTextureIdentifier:textureIdentifier;
    }
}
