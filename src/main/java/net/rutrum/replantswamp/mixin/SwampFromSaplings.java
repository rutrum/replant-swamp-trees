package net.rutrum.replantswamp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

@Mixin(SaplingGenerator.class)
public class SwampFromSaplings {
	@ModifyVariable(
		method = "generate",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/block/sapling/SaplingGenerator;getTreeFeature"
				+ "(Lnet/minecraft/util/math/random/Random;Z)"
				+ "Lnet/minecraft/util/registry/RegistryEntry;"
		)
	)
	private RegistryEntry<? extends ConfiguredFeature<?, ?>> mask_oak_trees(
		RegistryEntry<? extends ConfiguredFeature<?, ?>> tree,
		ServerWorld world, ChunkGenerator chunkGenerator,
		BlockPos pos, BlockState state, Random random
	) {
		if (tree == TreeConfiguredFeatures.OAK
				|| tree == TreeConfiguredFeatures.OAK_BEES_0002
				|| tree == TreeConfiguredFeatures.OAK_BEES_002
				|| tree == TreeConfiguredFeatures.OAK_BEES_005
				|| tree == TreeConfiguredFeatures.FANCY_OAK
				|| tree == TreeConfiguredFeatures.FANCY_OAK_BEES_0002
				|| tree == TreeConfiguredFeatures.FANCY_OAK_BEES_002
				|| tree == TreeConfiguredFeatures.FANCY_OAK_BEES_005) {

			Biome swamp = world.getRegistryManager().get(Registry.BIOME_KEY).entryOf(BiomeKeys.SWAMP).value();
			if (world.getBiome(pos).value() == swamp) {
				return TreeConfiguredFeatures.SWAMP_OAK;
			}
		}
		return tree;
	}

}