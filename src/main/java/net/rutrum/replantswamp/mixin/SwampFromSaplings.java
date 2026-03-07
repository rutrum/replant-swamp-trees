package net.rutrum.replantswamp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TreeGrower.class)
public class SwampFromSaplings {
    @ModifyVariable(
        method = "growTree",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/level/block/grower/TreeGrower;getConfiguredFeature(Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/resources/ResourceKey;"
        )
    )
    private ResourceKey<ConfiguredFeature<?, ?>> replaceOakWithSwampOak(
            ResourceKey<ConfiguredFeature<?, ?>> tree,
            ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random
    ) {
        if (isOakVariant(tree)) {
            var biomeKey = level.getBiome(pos).unwrapKey().orElse(null);
            if (biomeKey == Biomes.SWAMP || biomeKey == Biomes.MANGROVE_SWAMP) {
                return TreeFeatures.SWAMP_OAK;
            }
        }
        return tree;
    }

    private static boolean isOakVariant(ResourceKey<ConfiguredFeature<?, ?>> tree) {
        return tree == TreeFeatures.OAK ||
               tree == TreeFeatures.OAK_BEES_002 ||
               tree == TreeFeatures.OAK_BEES_005 ||
               tree == TreeFeatures.FANCY_OAK ||
               tree == TreeFeatures.FANCY_OAK_BEES_002 ||
               tree == TreeFeatures.FANCY_OAK_BEES_005 ||
               tree == TreeFeatures.FANCY_OAK_BEES;
    }
}
