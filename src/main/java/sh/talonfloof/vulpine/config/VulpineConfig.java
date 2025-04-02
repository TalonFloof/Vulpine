package sh.talonfloof.vulpine.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import static sh.talonfloof.vulpine.Vulpine.MOD_ID;

public class VulpineConfig extends Config {
    public VulpineConfig() {
        super(Identifier.of(MOD_ID, "vulpine"));
    }

    public SpawningSection spawning = new SpawningSection();

    public static class SpawningSection extends ConfigSection {
        public SpawningSection() {
            super();
        }
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableBrightColorFoxes =  new ValidatedBoolean(true);
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableSculkFoxes = new ValidatedBoolean(true);
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableAlternateFoxVariants = new ValidatedBoolean(true);
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableFennecFoxes = new ValidatedBoolean(true);
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableNetherFoxes = new ValidatedBoolean(true);
        @RequiresAction(action = Action.RESTART)
        public ValidatedBoolean enableEndFoxes = new ValidatedBoolean(true);
    }

    @Override
    public int defaultPermLevel() {
        return 4;
    }
}