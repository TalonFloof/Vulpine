package sh.talonfox.vulpine;

import net.minecraft.entity.passive.FoxEntity;

public interface IFoxTypeCreator {
    FoxEntity.Type vulpine$newFoxVariant(String enumName, int ordinal, int id, String typeName);


}
