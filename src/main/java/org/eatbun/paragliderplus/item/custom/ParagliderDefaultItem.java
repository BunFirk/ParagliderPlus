package org.eatbun.paragliderplus.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.eatbun.paragliderplus.item.client.AnimatedItemRender;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

//
// ГЛАВНЫЙ ФАЙЛ ОБЫЧНОГО (ДЫРЯВОГО) ПАРАГЛАЙДЕРА + АНИМАЦИЯ
//

public class ParagliderDefaultItem extends Item implements GeoItem {

    public ParagliderDefaultItem(Settings settings) {
        super(settings);
    }

    private AnimatableInstanceCache cashe = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);


    // Логика планирования
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            // Проверяем, держит ли игрок параплан в основной или левой руке
            boolean holdingInHand = player.getStackInHand(Hand.MAIN_HAND) == stack || player.getStackInHand(Hand.OFF_HAND) == stack;

            if (holdingInHand && !player.isOnGround() && !player.isTouchingWater() && !player.isInLava() && !player.isFallFlying()) {

                double velocityX = player.getVelocity().x;
                double velocityY = player.getVelocity().y;
                double velocityZ = player.getVelocity().z;

                boolean overLava = false;
                boolean notOverLavaFlyFix =  false;

                for(int i = 1; i <= 2; i++) {
                    if (world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.LAVA) || world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.MAGMA_BLOCK)) {
                        overLava = true;
                        break;
                    }
                }
                for(int i = 1; i <= 3; i++) {
                    if (world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.LAVA) || world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.MAGMA_BLOCK)) {
                        notOverLavaFlyFix = true;
                        break;
                    }
                }

                if (overLava) {
                    player.setVelocity(velocityX, 0.15, velocityZ);
                    player.fallDistance = 0;
                } else if (notOverLavaFlyFix) {
                    player.setVelocity(velocityX, 0, velocityZ);
                    player.fallDistance = 0;
                } else if (velocityY < 0) {
                    // Логика планирования
                    double glideSpeed = -0.39; // Сделал падение более плавным (было -0.39)
                    player.setVelocity(velocityX * 1.05, glideSpeed, velocityZ * 1.05);
                    player.fallDistance = 0;
                }
            }
        }
    }



    // GecoLib Animation



    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {

            private final AnimatedItemRender renderer = new AnimatedItemRender();


            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::perdicate));
    }

    private PlayState perdicate(AnimationState<GeoAnimatable> tAnimatableAnimationState) {
        tAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cashe;
    }
}