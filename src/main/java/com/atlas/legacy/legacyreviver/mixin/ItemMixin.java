package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.extensions.IItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin implements IItem {
    public Item.Settings settings;
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void injectSettings(Item.Settings settings, CallbackInfo ci) {
        this.settings = settings;
    }
    @Override
    public Item.Settings getSettings() {
        return settings;
    }
}
