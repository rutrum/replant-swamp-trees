package net.rutrum.replantswamp.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SaplingGenerator.class)
public class SwampFromSaplings {
	@ModifyVariable(
		method = "generate",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/block/sapling/SaplingGenerator;getTreeFeature"
				+ "(Ljava/util/Random;Z)"
				+ "Lnet/minecraft/world/gen/feature/ConfiguredFeature;"
		)
	)
	private ConfiguredFeature<TreeFeatureConfig, ?> mask_oak_trees(
		ConfiguredFeature<TreeFeatureConfig, ?> tree,
		ServerWorld world, ChunkGenerator chunkGenerator,
		BlockPos pos, BlockState state, Random random
	) {
		if (tree == ConfiguredFeatures.OAK
				|| tree == ConfiguredFeatures.OAK_BEES_0002
				|| tree == ConfiguredFeatures.OAK_BEES_002
				|| tree == ConfiguredFeatures.OAK_BEES_005
				|| tree == ConfiguredFeatures.FANCY_OAK
				|| tree == ConfiguredFeatures.FANCY_OAK_BEES_0002
				|| tree == ConfiguredFeatures.FANCY_OAK_BEES_002
				|| tree == ConfiguredFeatures.FANCY_OAK_BEES_005) {

			if (world.getBiome(pos).getCategory() == Biome.Category.SWAMP) {
				return ConfiguredFeatures.SWAMP_OAK;
			}
		}
		return tree;
	}
}