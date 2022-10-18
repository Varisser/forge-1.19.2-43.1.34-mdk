package net.varisser.tutorialmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EightBallItem  extends Item {

    public EightBallItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            //output a random number
            int rn = getRandomNumber();

            if (rn == 0){
                player.sendSystemMessage(Component.literal("Unlucky"));
                level.explode(null, player.getX(), player.getY(), player.getZ(), 20.0F, true, Explosion.BlockInteraction.DESTROY);
                outputRandomNumber(player, rn);
            }else{
                outputRandomNumber(player, rn);
            }
            //set a cooldown
            player.getCooldowns().addCooldown(this,20);

        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Right click to get random number!").withStyle(ChatFormatting.AQUA));
        } else {
            components.add(Component.literal("Press SHIFT for more info.").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(itemStack, level, components, flag);
    }

    private void outputRandomNumber(Player player, int rn){
        player.sendSystemMessage(Component.literal("your number is " + rn));
    }
    private int getRandomNumber(){
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
