package lv.id.bonne.vaulthunters.jewelvoiding.sophisticatedcore;

import lv.id.bonne.vaulthunters.jewelvoiding.config.MixinConfigPlugin;
import net.minecraftforge.fml.loading.LoadingModList;

public class SophisticatedCoreIntegrationModConfiguration extends MixinConfigPlugin
{
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("sophisticatedcore") != null;

    }
}
