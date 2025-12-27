package org.eatbun.paragliderplus.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class ParagliderDefaultItem extends Item {

    public ParagliderDefaultItem(Settings settings) {
        super(settings);
    }

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
/*
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
*/
}