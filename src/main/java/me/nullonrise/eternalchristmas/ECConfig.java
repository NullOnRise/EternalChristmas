package me.nullonrise.eternalchristmas;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ECConfig {
    public static final ModConfigSpec GENERAL_SPEC;
    public static final ECConfig.General GENERAL;

    static {
        final Pair<General, ModConfigSpec> specPair =
                new ModConfigSpec.Builder().configure(ECConfig.General::new);

        GENERAL = specPair.getLeft();
        GENERAL_SPEC = specPair.getRight();
    }

    public static class General {

        public final ModConfigSpec.EnumValue<ListMode> listMode;
        public final ModConfigSpec.ConfigValue<List<? extends String>> biomeList;
        public final ModConfigSpec.DoubleValue downfall;

        General(ModConfigSpec.Builder builder) {
            listMode = builder.comment(
                    "If BLACKLIST, the list below will be treated as a blacklist what not to touch.",
                    "If WHITELIST, the will be treated as a whitelist what biomes should only be touched"
            ).defineEnum("listMode", ListMode.BLACKLIST);

            List<String> defaultVals = Arrays.asList(
                    "river",
                    "ocean",
                    "deep_ocean",
                    "warm_ocean",
                    "lukewarm_ocean",
                    "cold_ocean",
                    "deep_warm_ocean",
                    "deep_lukewarm_ocean",
                    "deep_cold_ocean",
                    "deep_frozen_ocean"
            );

            biomeList = builder.comment("The blacklist or whitelist, depending on listMode")
                    .defineList("biomeList", defaultVals, o -> true);

            downfall = builder.comment(
                    "The downfall value each biome should get. If -1, Eternal Christmas will keep the original downfall of the biome, otherwise it takes a value between 0 and 1"
            ).defineInRange("downfall", -1D, -1D, 1D);
        }
    }
}
