package net.rutrum.replantswamp.mixin;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

@Mixin(SaplingGenerator.class)
public class SwampFromSaplings {
	@ModifyVariable(
		method = "generate",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/block/sapling/SaplingGenerator;getTreeFeature(Lnet/minecraft/util/math/random/Random;Z)Lnet/minecraft/registry/RegistryKey;"
		)
	)
	private RegistryKey<? extends ConfiguredFeature<?, ?>> mask_oak_trees(
			RegistryKey<? extends ConfiguredFeature<?, ?>> tree,
			ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random
	){
		if(
			tree == TreeConfiguredFeatures.OAK ||
			tree == TreeConfiguredFeatures.OAK_BEES_0002 ||
			tree == TreeConfiguredFeatures.OAK_BEES_002 ||
			tree == TreeConfiguredFeatures.OAK_BEES_005 ||
			tree == TreeConfiguredFeatures.FANCY_OAK ||
			tree == TreeConfiguredFeatures.FANCY_OAK_BEES_0002 ||
			tree == TreeConfiguredFeatures.FANCY_OAK_BEES_002 ||
			tree == TreeConfiguredFeatures.FANCY_OAK_BEES_005
		){
			Biome biome = world.getBiome(pos).value();
			Registry<Biome> biomes = world.getRegistryManager().get(RegistryKeys.BIOME);
			Biome swamp = biomes.entryOf(BiomeKeys.SWAMP).value();
			Biome mangroveSwamp = biomes.entryOf(BiomeKeys.MANGROVE_SWAMP).value();
			if(biome == swamp || biome == mangroveSwamp){
				return TreeConfiguredFeatures.SWAMP_OAK;
			}
		}
		return tree;
	}
}