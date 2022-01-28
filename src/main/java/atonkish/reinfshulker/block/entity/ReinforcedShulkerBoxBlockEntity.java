package atonkish.reinfshulker.block.entity;

import java.util.stream.IntStream;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import org.jetbrains.annotations.Nullable;

import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcingMaterial;

public class ReinforcedShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {
    private final ReinforcingMaterial cachedMaterial;

    public ReinforcedShulkerBoxBlockEntity(ReinforcingMaterial material, @Nullable DyeColor color, BlockPos pos,
            BlockState state) {
        super(color, pos, state);
        ((BlockEntityInterface) this).setType(ModBlockEntityType.REINFORCED_SHULKER_BOX_MAP.get(material));
        this.setInvStackList(DefaultedList.ofSize(material.getSize(), ItemStack.EMPTY));
        this.cachedMaterial = material;
    }

    public ReinforcedShulkerBoxBlockEntity(ReinforcingMaterial material, BlockPos pos, BlockState state) {
        super(pos, state);
        ((BlockEntityInterface) this).setType(ModBlockEntityType.REINFORCED_SHULKER_BOX_MAP.get(material));
        this.setInvStackList(DefaultedList.ofSize(material.getSize(), ItemStack.EMPTY));
        this.cachedMaterial = material;
    }

    protected Text getContainerName() {
        String namespace = BlockEntityType.getId(this.getType()).getNamespace();
        return new TranslatableText("container." + namespace + "." + this.cachedMaterial.getName() + "ShulkerBox");
    }

    public int[] getAvailableSlots(Direction side) {
        return IntStream.range(0, this.size()).toArray();
    }

    public ReinforcingMaterial getMaterial() {
        return this.cachedMaterial;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return ReinforcedStorageScreenHandler.createShulkerBoxScreen(this.cachedMaterial, syncId, playerInventory,
                this);
    }
}
