package net.rutrum.replantswamp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.Biome;

@Mixin(Biome.class)
public interface BiomeCategoryAccessor {
	@Accessor
	Biome.Category getCategory();
}
