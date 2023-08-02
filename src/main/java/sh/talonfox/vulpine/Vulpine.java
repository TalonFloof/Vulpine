package sh.talonfox.vulpine;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vulpine implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("vulpine");
	public static final TrackedData<Integer> TAME_PROGRESS = DataTracker.registerData(net.minecraft.entity.passive.FoxEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Override
	public void onInitialize() {

	}
}