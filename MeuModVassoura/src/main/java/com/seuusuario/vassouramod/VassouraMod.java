package com.seuusuario.vassouramod;

import com.seuusuario.vassouramod.entity.BroomEntity;
import com.seuusuario.vassouramod.item.BroomItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("vassouramod")
public class VassouraMod {
    public static final String MODID = "vassouramod";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<Item> BROOM_ITEM = ITEMS.register("vassoura", () -> new BroomItem(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<EntityType<BroomEntity>> BROOM_ENTITY = ENTITIES.register("broom", 
        () -> EntityType.Builder.<BroomEntity>of(BroomEntity::new, MobCategory.MISC)
                .sized(1.0F, 0.6F)
                .build("broom"));

    public VassouraMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BROOM_ITEM.get());
        }
    }
}