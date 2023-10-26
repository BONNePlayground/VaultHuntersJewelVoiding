package lv.id.bonne.vaulthunters.jewelvoiding;


import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;


/**
 * The main class for Vault Jewels Sorting mod.
 */
@Mod("vault_hunters_jewel_voiding")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class VaultJewelVoiding
{
    /**
     * The main class initialization.
     */
    public VaultJewelVoiding()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }


    /**
     * The logger for this mod.
     */
    public static final Logger LOGGER = LogUtils.getLogger();
}
