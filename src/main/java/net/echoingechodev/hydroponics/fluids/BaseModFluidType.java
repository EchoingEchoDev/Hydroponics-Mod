package net.echoingechodev.hydroponics.fluids;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class BaseModFluidType extends FluidType {
    private final ResourceLocation stillTexture = ResourceLocation.withDefaultNamespace("block/water_still");
    private final ResourceLocation flowingTexture = ResourceLocation.withDefaultNamespace("block/water_flow");;
    private final ResourceLocation overlayTexture = ResourceLocation.withDefaultNamespace("block/water_overlay");;
    private final int tintColor;
    private final Vector3f fogColor;


    public BaseModFluidType(Properties properties, int tintColor, Vector3f fogColor) {
        super(properties);
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public int getTintColor() {
        return tintColor;
    }

    public Vector3f getFogColor() {
        return fogColor;
    }

    @Override
    public @Nullable SoundEvent getSound(SoundAction action) {
        if (action.equals(SoundActions.BUCKET_FILL)) {
            return SoundEvents.BUCKET_FILL;
        }
        if (action.equals(SoundActions.BUCKET_EMPTY)) {
            return SoundEvents.BUCKET_EMPTY;
        }
        if (action.equals(SoundActions.CAULDRON_DRIP)) {
            return SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON;
        }
        return SoundEvents.EMPTY;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }
        });
    }
}
