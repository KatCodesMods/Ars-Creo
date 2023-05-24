package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.CreoConfig;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;

public class StarbuncleWheelTile extends GeneratingKineticBlockEntity implements IAnimatable {
    public StarbuncleWheelTile(BlockPos pos, BlockState state) {
        super(ModBlockRegistry.STARBY_TILE, pos, state);
        setLazyTickRate(20);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1, this::idlePredicate));
    }

    AnimationFactory factory = new AnimationFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public float getGeneratedSpeed() {
        int spd = CreoConfig.WHEEL_BASE_SPEED.get();
        Direction direction = getBlockState().getValue(StarbuncleWheelBlock.FACING);
        if(direction != Direction.UP && direction != Direction.DOWN) {
            if (level.getBlockState(getBlockPos().relative(direction.getClockWise())).is(Tags.Blocks.STORAGE_BLOCKS_GOLD)) {
                spd = CreoConfig.WHEEL_BONUS_SPEED.get();
            }
        }

        return convertToDirection(spd, getBlockState().getValue(HandCrankBlock.FACING));
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        ModBlockRegistry.STARBY_WHEEL.updateAllSides(getBlockState(), level, worldPosition);
    }

    private PlayState idlePredicate(AnimationEvent event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
        return PlayState.CONTINUE;
    }

}
