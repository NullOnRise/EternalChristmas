package me.nullonrise.eternalchristmas;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import java.util.function.Supplier;


@Mod(Eternalchristmas.MODID)
public class Eternalchristmas {
    public static final String MODID = "eternalchristmas";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "eternalchristmas");
    public static final Supplier<MapCodec<BiomesModifier>> BIOME_MODIFIER_CODEC = BIOME_MODIFIER_SERIALIZERS.register("change_to_snow", () -> MapCodec.unit(BiomesModifier::new));


    public Eternalchristmas(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Starting up Eternal Christmas mod");
        modContainer.registerConfig(ModConfig.Type.COMMON, ECConfig.GENERAL_SPEC);
        BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
    }
}
