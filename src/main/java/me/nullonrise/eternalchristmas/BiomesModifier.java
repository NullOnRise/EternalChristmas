package me.nullonrise.eternalchristmas;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import javax.swing.text.html.parser.DTDConstants;

public class BiomesModifier implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.MODIFY) {
            ResourceLocation registryName = biome.unwrapKey().get().location();
            if (ECConfig.GENERAL.listMode.get() == ListMode.BLACKLIST) {
                if (ECConfig.GENERAL.biomeList.get().stream().anyMatch(s -> s.equals(registryName.getPath()) || s.equals(registryName.toString())))
                    return;
            } else if (ECConfig.GENERAL.listMode.get() == ListMode.WHITELIST) {
                if (ECConfig.GENERAL.biomeList.get().stream().noneMatch(s -> s.equals(registryName.getPath()) || s.equals(registryName.toString())))
                    return;
            } else {
                throw new RuntimeException("Wrong list mode! " + ECConfig.GENERAL.biomeList.get());
            }

            float downfall = ECConfig.GENERAL.downfall.get().floatValue();
            if (downfall != -1 && downfall < 0) {
                Eternalchristmas.LOGGER.error("Invalid downfall " + downfall + ", defaulting to -1!");
                downfall = -1F;
            }
            if (downfall != -1F)
                builder.getClimateSettings().setDownfall(downfall);
            builder.getClimateSettings().setTemperature(0F);
            builder.getClimateSettings().setTemperatureModifier(Biome.TemperatureModifier.NONE);
            builder.getClimateSettings().setHasPrecipitation(true);
            Eternalchristmas.LOGGER.debug("Modified Biome {} successful!", registryName);
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return Eternalchristmas.BIOME_MODIFIER_CODEC.get();
    }
}