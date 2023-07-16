package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CarbuncleWheelModel extends GeoModel<StarbuncleWheelTile> {

    @Override
    public void setCustomAnimations(StarbuncleWheelTile entity, long uniqueID, AnimationState<StarbuncleWheelTile> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        CoreGeoBone head = this.getAnimationProcessor().getBone("wheel");
        Direction facing = entity.getBlockState().getValue(StarbuncleWheelBlock.FACING);
        float angle = getAngleForTe(entity, entity.getBlockPos(), ModBlockRegistry.STARBY_WHEEL.get().getRotationAxis(entity.getBlockState()));
        if(facing ==  Direction.SOUTH || facing == Direction.EAST) {
            angle = -angle;
        }
        head.setRotY(angle);
    }

    public static float getAngleForTe(KineticBlockEntity te, final BlockPos pos, Direction.Axis axis) {
        float time = AnimationTickHolder.getRenderTime(te.getLevel());
        float offset = getRotationOffsetForPosition(te, pos, axis);
        return ((time * te.getSpeed() * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;
    }
    protected static float getRotationOffsetForPosition(KineticBlockEntity te, final BlockPos pos, final Direction.Axis axis) {
        float offset = ICogWheel.isLargeCog(te.getBlockState()) ? 11.25f : 0;
        double d = (((axis == Direction.Axis.X) ? 0 : pos.getX()) + ((axis == Direction.Axis.Y) ? 0 : pos.getY())
                + ((axis == Direction.Axis.Z) ? 0 : pos.getZ())) % 2;
        if (d == 0) {
            offset = 22.5f;
        }
        return offset;
    }
    static final ResourceLocation model = new ResourceLocation(ArsCreo.MODID, "geo/starbuncle_wheel.geo.json");
    static final ResourceLocation texture = new ResourceLocation(ArsCreo.MODID, "textures/blocks/starbuncle_wheel.png");
    static final ResourceLocation animations = new ResourceLocation(ArsCreo.MODID, "animations/starbuncle_wheel_animation.json");

    @Override
    public ResourceLocation getModelResource(StarbuncleWheelTile object) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(StarbuncleWheelTile object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(StarbuncleWheelTile animatable) {
        return animations;
    }

}
