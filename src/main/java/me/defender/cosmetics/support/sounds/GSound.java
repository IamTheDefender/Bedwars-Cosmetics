package me.defender.cosmetics.support.sounds;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum GSound {
    /*
     *
     * GSound for 1.14 - 1.8 by Gober
     *
     */
    AMBIENT_CAVE("same", "same", "same", "same", "same", "AMBIENCE_CAVE"),
    AMBIENT_UNDERWATER_ENTER("same", "", "", "", "", ""),
    AMBIENT_UNDERWATER_EXIT("same", "", "", "", "", ""),
    AMBIENT_UNDERWATER_LOOP("same", "", "", "", "", ""),
    AMBIENT_UNDERWATER_LOOP_ADDITIONS("same", "", "", "", "", ""),
    AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE("same", "", "", "", "", ""),
    AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE("same", "", "", "", "", ""),
    BLOCK_ANVIL_BREAK("same", "same", "same", "same", "same", "ANVIL_BREAK"),
    BLOCK_ANVIL_DESTROY("same", "same", "same", "same", "same", ""),
    BLOCK_ANVIL_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_ANVIL_HIT("same", "same", "same", "same", "same", "ANVIL_HIT"),
    BLOCK_ANVIL_LAND("same", "same", "same", "same", "same", "ANVIL_LAND"),
    BLOCK_ANVIL_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_ANVIL_STEP("same", "same", "same", "same", "same", ""),
    BLOCK_ANVIL_USE("same", "same", "same", "same", "same", "ANVIL_USE"),
    BLOCK_BAMBOO_BREAK("", "", "", "", "", ""),
    BLOCK_BAMBOO_FALL("", "", "", "", "", ""),
    BLOCK_BAMBOO_HIT("", "", "", "", "", ""),
    BLOCK_BAMBOO_PLACE("", "", "", "", "", ""),
    BLOCK_BAMBOO_SAPLING_BREAK("", "", "", "", "", ""),
    BLOCK_BAMBOO_SAPLING_HIT("", "", "", "", "", ""),
    BLOCK_BAMBOO_SAPLING_PLACE("", "", "", "", "", ""),
    BLOCK_BAMBOO_STEP("", "", "", "", "", ""),
    BLOCK_BARREL_CLOSE("", "", "", "", "", ""),
    BLOCK_BARREL_OPEN("", "", "", "", "", ""),
    BLOCK_BEACON_ACTIVATE("same", "", "", "", "", ""),
    BLOCK_BEACON_AMBIENT("same", "", "", "", "", ""),
    BLOCK_BEACON_DEACTIVATE("same", "", "", "", "", ""),
    BLOCK_BEACON_POWER_SELECT("same", "", "", "", "", ""),
    BLOCK_BELL_RESONATE("", "", "", "", "", ""),
    BLOCK_BELL_USE("", "", "", "", "", ""),
    BLOCK_BLASTFURNACE_FIRE_CRACKLE("", "", "", "", "", ""),
    BLOCK_BREWING_STAND_BREW("same", "same", "same", "same", "same", ""),
    BLOCK_BUBBLE_COLUMN_BUBBLE_POP("same", "", "", "", "", ""),
    BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT("same", "", "", "", "", ""),
    BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE("same", "", "", "", "", ""),
    BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT("same", "", "", "", "", ""),
    BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE("same", "", "", "", "", ""),
    BLOCK_CAMPFIRE_CRACKLE("", "", "", "", "", ""),
    BLOCK_CHEST_CLOSE("same", "same", "same", "same", "same", "CHEST_CLOSE"),
    BLOCK_CHEST_LOCKED("same", "same", "same", "same", "same", ""),
    BLOCK_CHEST_OPEN("same", "same", "same", "same", "same", "CHEST_OPEN"),
    BLOCK_CHORUS_FLOWER_DEATH("same", "same", "same", "same", "same", ""),
    BLOCK_CHORUS_FLOWER_GROW("same", "same", "same", "same", "same", ""),
    BLOCK_COMPARATOR_CLICK("same", "same", "same", "same", "same", ""),
    BLOCK_COMPOSTER_EMPTY("", "", "", "", "", ""),
    BLOCK_COMPOSTER_FILL("", "", "", "", "", ""),
    BLOCK_COMPOSTER_FILL_SUCCESS("", "", "", "", "", ""),
    BLOCK_COMPOSTER_READY("", "", "", "", "", ""),
    BLOCK_CONDUIT_ACTIVATE("same", "", "", "", "", ""),
    BLOCK_CONDUIT_AMBIENT("same", "", "", "", "", ""),
    BLOCK_CONDUIT_AMBIENT_SHORT("same", "", "", "", "", ""),
    BLOCK_CONDUIT_ATTACK_TARGET("same", "", "", "", "", ""),
    BLOCK_CONDUIT_DEACTIVATE("same", "", "", "", "", ""),
    BLOCK_CORAL_BLOCK_BREAK("same", "", "", "", "", ""),
    BLOCK_CORAL_BLOCK_FALL("same", "", "", "", "", ""),
    BLOCK_CORAL_BLOCK_HIT("same", "", "", "", "", ""),
    BLOCK_CORAL_BLOCK_PLACE("same", "", "", "", "", ""),
    BLOCK_CORAL_BLOCK_STEP("same", "", "", "", "", ""),
    BLOCK_CROP_BREAK("", "", "", "", "", ""),
    BLOCK_DISPENSER_DISPENSE("same", "same", "same", "same", "same", ""),
    BLOCK_DISPENSER_FAIL("same", "same", "same", "same", "same", ""),
    BLOCK_DISPENSER_LAUNCH("same", "same", "same", "same", "same", ""),
    BLOCK_ENCHANTMENT_TABLE_USE("same", "same", "same", "same", "", ""),
    BLOCK_ENDER_CHEST_CLOSE("same", "BLOCK_ENDERCHEST_CLOSE", "BLOCK_ENDERCHEST_CLOSE", "BLOCK_ENDERCHEST_CLOSE", "BLOCK_ENDERCHEST_CLOSE", ""),
    BLOCK_ENDER_CHEST_OPEN("same", "BLOCK_ENDERCHEST_OPEN", "BLOCK_ENDERCHEST_OPEN", "BLOCK_ENDERCHEST_OPEN", "BLOCK_ENDERCHEST_OPEN", ""),
    BLOCK_END_GATEWAY_SPAWN("same", "same", "same", "same", "same", ""),
    BLOCK_END_PORTAL_FRAME_FILL("same", "same", "", "", "", ""),
    BLOCK_END_PORTAL_SPAWN("same", "same", "", "", "", ""),
    BLOCK_FENCE_GATE_CLOSE("same", "same", "same", "same", "same", ""),
    BLOCK_FENCE_GATE_OPEN("same", "same", "same", "same", "same", ""),
    BLOCK_FIRE_AMBIENT("same", "same", "same", "same", "same", "FIRE"),
    BLOCK_FIRE_EXTINGUISH("same", "same", "same", "same", "same", "FIZZ"),
    BLOCK_FURNACE_FIRE_CRACKLE("same", "same", "same", "same", "same", ""),
    BLOCK_GLASS_BREAK("same", "same", "same", "same", "same", "GLASS"),
    BLOCK_GLASS_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_GLASS_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_GLASS_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_GLASS_STEP("same", "same", "same", "same", "same", ""),
    BLOCK_GRASS_BREAK("same", "same", "same", "same", "same", "DIG_GRASS"),
    BLOCK_GRASS_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_GRASS_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_GRASS_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_GRASS_STEP("same", "same", "same", "same", "same", ""),
    BLOCK_GRAVEL_BREAK("same", "same", "same", "same", "same", "DIG_GRAVEL"),
    BLOCK_GRAVEL_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_GRAVEL_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_GRAVEL_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_GRAVEL_STEP("same", "same", "same", "same", "same", "STEP_GRAVEL"),
    BLOCK_GRINDSTONE_USE("", "", "", "", "", ""),
    BLOCK_IRON_DOOR_CLOSE("same", "same", "same", "same", "same", ""),
    BLOCK_IRON_DOOR_OPEN("same", "same", "same", "same", "same", ""),
    BLOCK_IRON_TRAPDOOR_CLOSE("same", "same", "same", "same", "same", ""),
    BLOCK_IRON_TRAPDOOR_OPEN("same", "same", "same", "same", "same", ""),
    BLOCK_LADDER_BREAK("same", "same", "same", "same", "same", ""),
    BLOCK_LADDER_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_LADDER_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_LADDER_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_LADDER_STEP("same", "same", "same", "same", "same", "STEP_LADDER"),
    BLOCK_LANTERN_BREAK("", "", "", "", "", ""),
    BLOCK_LANTERN_FALL("", "", "", "", "", ""),
    BLOCK_LANTERN_HIT("", "", "", "", "", ""),
    BLOCK_LANTERN_PLACE("", "", "", "", "", ""),
    BLOCK_LANTERN_STEP("", "", "", "", "", ""),
    BLOCK_LAVA_AMBIENT("same", "same", "same", "same", "same", "LAVA"),
    BLOCK_LAVA_EXTINGUISH("same", "same", "same", "same", "same", "FIZZ"),
    BLOCK_LAVA_POP("same", "same", "same", "same", "same", "LAVA_POP"),
    BLOCK_LEVER_CLICK("same", "same", "same", "same", "same", ""),
    BLOCK_LILY_PAD_PLACE("same", "BLOCK_WATERLILY_PLACE", "BLOCK_WATERLILY_PLACE", "BLOCK_WATERLILY_PLACE", "BLOCK_WATERLILY_PLACE", ""),
    BLOCK_METAL_BREAK("same", "same", "same", "same", "same", ""),
    BLOCK_METAL_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_METAL_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_METAL_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF("same", "BLOCK_METAL_PRESSUREPLATE_CLICK_OFF", "BLOCK_METAL_PRESSUREPLATE_CLICK_OFF", "BLOCK_METAL_PRESSUREPLATE_CLICK_OFF", "BLOCK_METAL_PRESSUREPLATE_CLICK_OFF", ""),
    BLOCK_METAL_PRESSURE_PLATE_CLICK_ON("same", "BLOCK_METAL_PRESSUREPLATE_CLICK_ON", "BLOCK_METAL_PRESSUREPLATE_CLICK_ON", "BLOCK_METAL_PRESSUREPLATE_CLICK_ON", "BLOCK_METAL_PRESSUREPLATE_CLICK_ON", ""),
    BLOCK_METAL_STEP("same", "same", "same", "same", "same", ""),
    BLOCK_NETHER_WART_BREAK("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_BANJO("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_BASEDRUM("same", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "NOTE_SNARE_DRUM"),
    BLOCK_NOTE_BLOCK_BASS("same", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "NOTE_BASS"),
    BLOCK_NOTE_BLOCK_BELL("same", "BLOCK_NOTE_BELL", "", "", "", ""),
    BLOCK_NOTE_BLOCK_BIT("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_CHIME("same", "BLOCK_NOTE_CHIME", "", "", "", ""),
    BLOCK_NOTE_BLOCK_COW_BELL("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_DIDGERIDOO("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_FLUTE("same", "BLOCK_NOTE_FLUTE", "", "", "", "NOTE_BASS_DRUM"),
    BLOCK_NOTE_BLOCK_GUITAR("same", "BLOCK_NOTE_GUITAR", "", "", "", "NOTE_BASS_GUITAR"),
    BLOCK_NOTE_BLOCK_HARP("same", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "NOTE_PIANO"),
    BLOCK_NOTE_BLOCK_HAT("same", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "NOTE_STICKS"),
    BLOCK_NOTE_BLOCK_IRON_XYLOPHONE("", "", "", "", "", ""),
    BLOCK_NOTE_BLOCK_PLING("same", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "NOTE_PLING"),
    BLOCK_NOTE_BLOCK_SNARE("same", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "NOTE_SNARE"),
    BLOCK_NOTE_BLOCK_XYLOPHONE("same", "BLOCK_NOTE_XYLOPHONE", "", "", "", ""),
    BLOCK_PISTON_CONTRACT("same", "same", "same", "same", "same", "PISTON_RETRACT"),
    BLOCK_PISTON_EXTEND("same", "same", "same", "same", "same", "PISTON_EXTEND"),
    BLOCK_PORTAL_AMBIENT("same", "same", "same", "same", "same", "PORTAL"),
    BLOCK_PORTAL_TRAVEL("same", "same", "same", "same", "same", "PORTAL_TRAVEL"),
    BLOCK_PORTAL_TRIGGER("same", "same", "same", "same", "same", "PORTAL_TRIGGER"),
    BLOCK_PUMPKIN_CARVE("same", "", "", "", "", ""),
    BLOCK_REDSTONE_TORCH_BURNOUT("same", "same", "same", "same", "same", ""),
    BLOCK_SAND_BREAK("same", "same", "same", "same", "same", "DIG_SAND"),
    BLOCK_SAND_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_SAND_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_SAND_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_SAND_STEP("same", "same", "same", "same", "same", "STEP_SAND"),
    BLOCK_SCAFFOLDING_BREAK("", "", "", "", "", ""),
    BLOCK_SCAFFOLDING_FALL("", "", "", "", "", ""),
    BLOCK_SCAFFOLDING_HIT("", "", "", "", "", ""),
    BLOCK_SCAFFOLDING_PLACE("", "", "", "", "", ""),
    BLOCK_SCAFFOLDING_STEP("", "", "", "", "", ""),
    BLOCK_SHULKER_BOX_CLOSE("same", "same", "same", "", "", ""),
    BLOCK_SHULKER_BOX_OPEN("same", "same", "same", "", "", ""),
    BLOCK_SLIME_BLOCK_BREAK("same", "BLOCK_SLIME_BREAK", "BLOCK_SLIME_BREAK", "BLOCK_SLIME_BREAK", "BLOCK_SLIME_BREAK", ""),
    BLOCK_SLIME_BLOCK_FALL("same", "BLOCK_SLIME_FALL", "BLOCK_SLIME_FALL", "BLOCK_SLIME_FALL", "BLOCK_SLIME_FALL", ""),
    BLOCK_SLIME_BLOCK_HIT("same", "BLOCK_SLIME_HIT", "BLOCK_SLIME_HIT", "BLOCK_SLIME_HIT", "BLOCK_SLIME_HIT", ""),
    BLOCK_SLIME_BLOCK_PLACE("same", "BLOCK_SLIME_PLACE", "BLOCK_SLIME_PLACE", "BLOCK_SLIME_PLACE", "BLOCK_SLIME_PLACE", ""),
    BLOCK_SLIME_BLOCK_STEP("same", "BLOCK_SLIME_STEP", "BLOCK_SLIME_STEP", "BLOCK_SLIME_STEP", "BLOCK_SLIME_STEP", ""),
    BLOCK_SMOKER_SMOKE("", "", "", "", "", ""),
    BLOCK_SNOW_BREAK("same", "same", "same", "same", "same", "DIG_SNOW"),
    BLOCK_SNOW_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_SNOW_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_SNOW_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_SNOW_STEP("same", "same", "same", "same", "same", "STEP_SNOW"),
    BLOCK_STONE_BREAK("same", "same", "same", "same", "same", "DIG_STONE"),
    BLOCK_STONE_BUTTON_CLICK_OFF("same", "same", "same", "same", "same", ""),
    BLOCK_STONE_BUTTON_CLICK_ON("same", "same", "same", "same", "same", ""),
    BLOCK_STONE_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_STONE_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_STONE_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF("same", "BLOCK_STONE_PRESSUREPLATE_CLICK_OFF", "BLOCK_STONE_PRESSUREPLATE_CLICK_OFF", "BLOCK_STONE_PRESSUREPLATE_CLICK_OFF", "BLOCK_STONE_PRESSUREPLATE_CLICK_OFF", ""),
    BLOCK_STONE_PRESSURE_PLATE_CLICK_ON("same", "BLOCK_STONE_PRESSUREPLATE_CLICK_ON", "BLOCK_STONE_PRESSUREPLATE_CLICK_ON", "BLOCK_STONE_PRESSUREPLATE_CLICK_ON", "BLOCK_STONE_PRESSUREPLATE_CLICK_ON", ""),
    BLOCK_STONE_STEP("same", "same", "same", "same", "same", "STEP_STONE"),
    BLOCK_SWEET_BERRY_BUSH_BREAK("", "", "", "", "", ""),
    BLOCK_SWEET_BERRY_BUSH_PLACE("", "", "", "", "", ""),
    BLOCK_TRIPWIRE_ATTACH("same", "same", "same", "same", "same", ""),
    BLOCK_TRIPWIRE_CLICK_OFF("same", "same", "same", "same", "same", ""),
    BLOCK_TRIPWIRE_CLICK_ON("same", "same", "same", "same", "same", ""),
    BLOCK_TRIPWIRE_DETACH("same", "same", "same", "same", "same", ""),
    BLOCK_WATER_AMBIENT("same", "same", "same", "same", "same", "WATER"),
    BLOCK_WET_GRASS_BREAK("same", "", "", "", "", ""),
    BLOCK_WET_GRASS_FALL("same", "", "", "", "", ""),
    BLOCK_WET_GRASS_HIT("same", "", "", "", "", ""),
    BLOCK_WET_GRASS_PLACE("same", "", "", "", "", ""),
    BLOCK_WET_GRASS_STEP("same", "", "", "", "", ""),
    BLOCK_WOODEN_BUTTON_CLICK_OFF("same", "BLOCK_WOOD_BUTTON_CLICK_OFF", "BLOCK_WOOD_BUTTON_CLICK_OFF", "BLOCK_WOOD_BUTTON_CLICK_OFF", "BLOCK_WOOD_BUTTON_CLICK_OFF", ""),
    BLOCK_WOODEN_BUTTON_CLICK_ON("same", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOOD_BUTTON_CLICK_ON", "WOOD_CLICK"),
    BLOCK_WOODEN_DOOR_CLOSE("same", "same", "same", "same", "same", "DOOR_CLOSE"),
    BLOCK_WOODEN_DOOR_OPEN("same", "same", "same", "same", "same", "DOOR_OPEN"),
    BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF("same", "BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF", "BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF", "BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF", "BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF", ""),
    BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON("same", "BLOCK_WOOD_PRESSUREPLATE_CLICK_ON", "BLOCK_WOOD_PRESSUREPLATE_CLICK_ON", "BLOCK_WOOD_PRESSUREPLATE_CLICK_ON", "BLOCK_WOOD_PRESSUREPLATE_CLICK_ON", ""),
    BLOCK_WOODEN_TRAPDOOR_CLOSE("same", "same", "same", "same", "same", ""),
    BLOCK_WOODEN_TRAPDOOR_OPEN("same", "same", "same", "same", "same", ""),
    BLOCK_WOOD_BREAK("same", "same", "same", "same", "same", "DIG_WOOD"),
    BLOCK_WOOD_FALL("same", "same", "same", "same", "same", ""),
    BLOCK_WOOD_HIT("same", "same", "same", "same", "same", ""),
    BLOCK_WOOD_PLACE("same", "same", "same", "same", "same", ""),
    BLOCK_WOOD_STEP("same", "same", "same", "same", "same", "STEP_WOOD"),
    BLOCK_WOOL_BREAK("same", "BLOCK_CLOTH_BREAK", "BLOCK_CLOTH_BREAK", "BLOCK_CLOTH_BREAK", "BLOCK_CLOTH_BREAK", "DIG_WOOL"),
    BLOCK_WOOL_FALL("same", "BLOCK_CLOTH_FALL", "BLOCK_CLOTH_FALL", "BLOCK_CLOTH_FALL", "BLOCK_CLOTH_FALL", ""),
    BLOCK_WOOL_HIT("same", "BLOCK_CLOTH_HIT", "BLOCK_CLOTH_HIT", "BLOCK_CLOTH_HIT", "BLOCK_CLOTH_HIT", ""),
    BLOCK_WOOL_PLACE("same", "BLOCK_CLOTH_PLACE", "BLOCK_CLOTH_PLACE", "BLOCK_CLOTH_PLACE", "BLOCK_CLOTH_PLACE", ""),
    BLOCK_WOOL_STEP("same", "BLOCK_CLOTH_STEP", "BLOCK_CLOTH_STEP", "BLOCK_CLOTH_STEP", "BLOCK_CLOTH_STEP", "STEP_CLOTH"),
    ENCHANT_THORNS_HIT("same", "same", "same", "same", "same", ""),
    ENTITY_ARMOR_STAND_BREAK("same", "ENTITY_ARMORSTAND_BREAK", "ENTITY_ARMORSTAND_BREAK", "ENTITY_ARMORSTAND_BREAK", "ENTITY_ARMORSTAND_BREAK", ""),
    ENTITY_ARMOR_STAND_FALL("same", "ENTITY_ARMORSTAND_FALL", "ENTITY_ARMORSTAND_FALL", "ENTITY_ARMORSTAND_FALL", "ENTITY_ARMORSTAND_FALL", ""),
    ENTITY_ARMOR_STAND_HIT("same", "ENTITY_ARMORSTAND_HIT", "ENTITY_ARMORSTAND_HIT", "ENTITY_ARMORSTAND_HIT", "ENTITY_ARMORSTAND_HIT", ""),
    ENTITY_ARMOR_STAND_PLACE("same", "ENTITY_ARMORSTAND_PLACE", "ENTITY_ARMORSTAND_PLACE", "ENTITY_ARMORSTAND_PLACE", "ENTITY_ARMORSTAND_PLACE", ""),
    ENTITY_ARROW_HIT("same", "same", "same", "same", "same", "ARROW_HIT"),
    ENTITY_ARROW_HIT_PLAYER("same", "same", "same", "same", "same", ""),
    ENTITY_ARROW_SHOOT("same", "same", "same", "same", "same", "SHOOT_ARROW"),
    ENTITY_BAT_AMBIENT("same", "same", "same", "same", "same", "BAT_IDLE"),
    ENTITY_BAT_DEATH("same", "same", "same", "same", "same", "BAT_DEATH"),
    ENTITY_BAT_HURT("same", "same", "same", "same", "same", "BAT_HURT"),
    ENTITY_BAT_LOOP("same", "same", "same", "same", "same", "BAT_LOOP"),
    ENTITY_BAT_TAKEOFF("same", "same", "same", "same", "same", "BAT_TAKEOFF"),
    ENTITY_BLAZE_AMBIENT("same", "same", "same", "same", "same", "BLAZE_BREATH"),
    ENTITY_BLAZE_BURN("same", "same", "same", "same", "same", ""),
    ENTITY_BLAZE_DEATH("same", "same", "same", "same", "same", "BLAZE_DEATH"),
    ENTITY_BLAZE_HURT("same", "same", "same", "same", "same", "BLAZE_HURT"),
    ENTITY_BLAZE_SHOOT("same", "same", "same", "same", "same", ""),
    ENTITY_BOAT_PADDLE_LAND("same", "same", "", "", "", ""),
    ENTITY_BOAT_PADDLE_WATER("same", "same", "", "", "", ""),
    ENTITY_CAT_AMBIENT("same", "same", "same", "same", "same", "CAT_MEOW"),
    ENTITY_CAT_BEG_FOR_FOOD("", "", "", "", "", ""),
    ENTITY_CAT_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_CAT_EAT("", "", "", "", "", ""),
    ENTITY_CAT_HISS("same", "same", "same", "same", "same", "CAT_HISS"),
    ENTITY_CAT_HURT("same", "same", "same", "same", "same", "CAT_HIT"),
    ENTITY_CAT_PURR("same", "same", "same", "same", "same", "CAT_PURR"),
    ENTITY_CAT_PURREOW("same", "same", "same", "same", "same", "CAT_PURREOW"),
    ENTITY_CAT_STRAY_AMBIENT("", "", "", "", "", ""),
    ENTITY_CHICKEN_AMBIENT("same", "same", "same", "same", "same", "CHICKEN_IDLE"),
    ENTITY_CHICKEN_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_CHICKEN_EGG("same", "same", "same", "same", "same", "CHICKEN_EGG_POP"),
    ENTITY_CHICKEN_HURT("same", "same", "same", "same", "same", "CHICKEN_HURT"),
    ENTITY_CHICKEN_STEP("same", "same", "same", "same", "same", "CHICKEN_WALK"),
    ENTITY_COD_AMBIENT("same", "", "", "", "", ""),
    ENTITY_COD_DEATH("same", "", "", "", "", ""),
    ENTITY_COD_FLOP("same", "", "", "", "", ""),
    ENTITY_COD_HURT("same", "", "", "", "", ""),
    ENTITY_COW_AMBIENT("same", "same", "same", "same", "same", "COW_IDLE"),
    ENTITY_COW_DEATH("same", "same", "same", "same", "same", "COW_DEATH"),
    ENTITY_COW_HURT("same", "same", "same", "same", "same", "COW_HURT"),
    ENTITY_COW_MILK("same", "same", "same", "same", "same", ""),
    ENTITY_COW_STEP("same", "same", "same", "same", "same", "COW_WALK"),
    ENTITY_CREEPER_DEATH("same", "same", "same", "same", "same", "CREEPER_DEATH"),
    ENTITY_CREEPER_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_CREEPER_PRIMED("same", "same", "same", "same", "same", "CREEPER_HISS"),
    ENTITY_DOLPHIN_AMBIENT("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_AMBIENT_WATER("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_ATTACK("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_DEATH("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_EAT("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_HURT("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_JUMP("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_PLAY("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_SPLASH("same", "", "", "", "", ""),
    ENTITY_DOLPHIN_SWIM("same", "", "", "", "", ""),
    ENTITY_DONKEY_AMBIENT("same", "same", "same", "same", "same", "DONKEY_IDLE"),
    ENTITY_DONKEY_ANGRY("same", "same", "same", "same", "same", "DONKEY_ANGRY"),
    ENTITY_DONKEY_CHEST("same", "same", "same", "same", "same", ""),
    ENTITY_DONKEY_DEATH("same", "same", "same", "same", "same", "DONKEY_DEATH"),
    ENTITY_DONKEY_HURT("same", "same", "same", "same", "same", "DONKEY_HIT"),
    ENTITY_DRAGON_FIREBALL_EXPLODE("same", "", "", "", "", ""),
    ENTITY_DROWNED_AMBIENT("same", "", "", "", "", ""),
    ENTITY_DROWNED_AMBIENT_WATER("same", "", "", "", "", ""),
    ENTITY_DROWNED_DEATH("same", "", "", "", "", ""),
    ENTITY_DROWNED_DEATH_WATER("same", "", "", "", "", ""),
    ENTITY_DROWNED_HURT("same", "", "", "", "", ""),
    ENTITY_DROWNED_HURT_WATER("same", "", "", "", "", ""),
    ENTITY_DROWNED_SHOOT("same", "", "", "", "", ""),
    ENTITY_DROWNED_STEP("same", "", "", "", "", ""),
    ENTITY_DROWNED_SWIM("same", "", "", "", "", ""),
    ENTITY_EGG_THROW("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_AMBIENT_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_CURSE("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_DEATH_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_FLOP("same", "same", "same", "", "", ""),
    ENTITY_ELDER_GUARDIAN_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_ELDER_GUARDIAN_HURT_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_ENDERMAN_AMBIENT("same", "ENTITY_ENDERMEN_AMBIENT", "ENTITY_ENDERMEN_AMBIENT", "ENTITY_ENDERMEN_AMBIENT", "ENTITY_ENDERMEN_AMBIENT", "ENDERMAN_IDLE"),
    ENTITY_ENDERMAN_DEATH("same", "ENTITY_ENDERMEN_DEATH", "ENTITY_ENDERMEN_DEATH", "ENTITY_ENDERMEN_DEATH", "ENTITY_ENDERMEN_DEATH", "ENDERMAN_DEATH"),
    ENTITY_ENDERMAN_HURT("same", "ENTITY_ENDERMEN_HURT", "ENTITY_ENDERMEN_HURT", "ENTITY_ENDERMEN_HURT", "ENTITY_ENDERMEN_HURT", "ENDERMAN_HIT"),
    ENTITY_ENDERMAN_SCREAM("same", "ENTITY_ENDERMEN_SCREAM", "ENTITY_ENDERMEN_SCREAM", "ENTITY_ENDERMEN_SCREAM", "ENTITY_ENDERMEN_SCREAM", "ENDERMAN_SCREAM"),
    ENTITY_ENDERMAN_STARE("same", "ENTITY_ENDERMEN_STARE", "ENTITY_ENDERMEN_STARE", "ENTITY_ENDERMEN_STARE", "ENTITY_ENDERMEN_STARE", "ENDERMAN_STARE"),
    ENTITY_ENDERMAN_TELEPORT("same", "ENTITY_ENDERMEN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT", "ENDERMAN_TELEPORT"),
    ENTITY_ENDERMITE_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_ENDERMITE_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_ENDERMITE_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_ENDERMITE_STEP("same", "same", "same", "same", "same", ""),
    ENTITY_ENDER_DRAGON_AMBIENT("same", "ENTITY_ENDERDRAGON_AMBIENT", "ENTITY_ENDERDRAGON_AMBIENT", "ENTITY_ENDERDRAGON_AMBIENT", "ENTITY_ENDERDRAGON_AMBIENT", ""),
    ENTITY_ENDER_DRAGON_DEATH("same", "ENTITY_ENDERDRAGON_DEATH", "ENTITY_ENDERDRAGON_DEATH", "ENTITY_ENDERDRAGON_DEATH", "ENTITY_ENDERDRAGON_DEATH", "ENDERDRAGON_DEATH"),
    ENTITY_ENDER_DRAGON_FLAP("same", "ENTITY_ENDERDRAGON_FLAP", "ENTITY_ENDERDRAGON_FLAP", "ENTITY_ENDERDRAGON_FLAP", "ENTITY_ENDERDRAGON_FLAP", "ENDERDRAGON_WINGS"),
    ENTITY_ENDER_DRAGON_GROWL("same", "ENTITY_ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL", "ENDERDRAGON_GROWL"),
    ENTITY_ENDER_DRAGON_HURT("same", "ENTITY_ENDERDRAGON_HURT", "ENTITY_ENDERDRAGON_HURT", "ENTITY_ENDERDRAGON_HURT", "ENTITY_ENDERDRAGON_HURT", "ENDERDRAGON_HIT"),
    ENTITY_ENDER_DRAGON_SHOOT("same", "ENTITY_ENDERDRAGON_SHOOT", "ENTITY_ENDERDRAGON_SHOOT", "ENTITY_ENDERDRAGON_SHOOT", "ENTITY_ENDERDRAGON_SHOOT", ""),
    ENTITY_ENDER_EYE_DEATH("same", "ENTITY_ENDEREYE_DEATH", "", "", "", ""),
    ENTITY_ENDER_EYE_LAUNCH("same", "ENTITY_ENDEREYE_LAUNCH", "ENTITY_ENDEREYE_LAUNCH", "ENTITY_ENDEREYE_LAUNCH", "ENTITY_ENDEREYE_LAUNCH", ""),
    ENTITY_ENDER_PEARL_THROW("same", "ENTITY_ENDERPEARL_THROW", "ENTITY_ENDERPEARL_THROW", "ENTITY_ENDERPEARL_THROW", "ENTITY_ENDERPEARL_THROW", ""),
    ENTITY_EVOKER_AMBIENT("same", "ENTITY_EVOCATION_ILLAGER_AMBIENT", "ENTITY_EVOCATION_ILLAGER_AMBIENT", "", "", ""),
    ENTITY_EVOKER_CAST_SPELL("same", "ENTITY_EVOCATION_ILLAGER_CAST_SPELL", "ENTITY_EVOCATION_ILLAGER_CAST_SPELL", "", "", ""),
    ENTITY_EVOKER_CELEBRATE("", "", "", "", "", ""),
    ENTITY_EVOKER_DEATH("same", "ENTITY_EVOCATION_ILLAGER_DEATH", "ENTITY_EVOCATION_ILLAGER_DEATH", "", "", ""),
    ENTITY_EVOKER_FANGS_ATTACK("same", "ENTITY_EVOCATION_FANGS_ATTACK", "ENTITY_EVOCATION_FANGS_ATTACK", "", "", ""),
    ENTITY_EVOKER_HURT("same", "ENTITY_EVOCATION_ILLAGER_HURT", "ENTITY_EVOCATION_ILLAGER_HURT", "", "", ""),
    ENTITY_EVOKER_PREPARE_ATTACK("same", "ENTITY_EVOCATION_ILLAGER_PREPARE_ATTACK", "ENTITY_EVOCATION_ILLAGER_PREPARE_ATTACK", "", "", ""),
    ENTITY_EVOKER_PREPARE_SUMMON("same", "ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON", "ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON", "", "", ""),
    ENTITY_EVOKER_PREPARE_WOLOLO("same", "ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO", "ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO", "", "", ""),
    ENTITY_EXPERIENCE_BOTTLE_THROW("same", "same", "same", "same", "same", ""),
    ENTITY_EXPERIENCE_ORB_PICKUP("same", "same", "same", "same", "same", "ORB_PICKUP"),
    ENTITY_FIREWORK_ROCKET_BLAST("same", "ENTITY_FIREWORK_BLAST", "ENTITY_FIREWORK_BLAST", "ENTITY_FIREWORK_BLAST", "ENTITY_FIREWORK_BLAST", "FIREWORK_BLAST"),
    ENTITY_FIREWORK_ROCKET_BLAST_FAR("same", "ENTITY_FIREWORK_BLAST_FAR", "ENTITY_FIREWORK_BLAST_FAR", "ENTITY_FIREWORK_BLAST_FAR", "ENTITY_FIREWORK_BLAST_FAR", "FIREWORK_BLAST2"),
    ENTITY_FIREWORK_ROCKET_LARGE_BLAST("same", "ENTITY_FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_LARGE_BLAST", "FIREWORK_LARGE_BLAST"),
    ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR("same", "ENTITY_FIREWORK_LARGE_BLAST_FAR", "ENTITY_FIREWORK_LARGE_BLAST_FAR", "ENTITY_FIREWORK_LARGE_BLAST_FAR", "ENTITY_FIREWORK_LARGE_BLAST_FAR", "FIREWORK_LARGE_BLAST2"),
    ENTITY_FIREWORK_ROCKET_LAUNCH("same", "ENTITY_FIREWORK_LAUNCH", "ENTITY_FIREWORK_LAUNCH", "ENTITY_FIREWORK_LAUNCH", "ENTITY_FIREWORK_LAUNCH", "FIREWORK_LAUNCH"),
    ENTITY_FIREWORK_ROCKET_SHOOT("same", "ENTITY_FIREWORK_SHOOT", "ENTITY_FIREWORK_SHOOT", "ENTITY_FIREWORK_SHOOT", "ENTITY_FIREWORK_SHOOT", ""),
    ENTITY_FIREWORK_ROCKET_TWINKLE("same", "ENTITY_FIREWORK_TWINKLE", "ENTITY_FIREWORK_TWINKLE", "ENTITY_FIREWORK_TWINKLE", "ENTITY_FIREWORK_TWINKLE", "FIREWORK_TWINKLE"),
    ENTITY_FIREWORK_ROCKET_TWINKLE_FAR("same", "ENTITY_FIREWORK_TWINKLE_FAR", "ENTITY_FIREWORK_TWINKLE_FAR", "ENTITY_FIREWORK_TWINKLE_FAR", "ENTITY_FIREWORK_TWINKLE_FAR", "FIREWORK_TWINKLE2"),
    ENTITY_FISHING_BOBBER_RETRIEVE("same", "ENTITY_BOBBER_RETRIEVE", "", "", "", ""),
    ENTITY_FISHING_BOBBER_SPLASH("same", "ENTITY_BOBBER_SPLASH", "ENTITY_BOBBER_SPLASH", "ENTITY_BOBBER_SPLASH", "ENTITY_BOBBER_SPLASH", "SPLASH2"),
    ENTITY_FISHING_BOBBER_THROW("same", "ENTITY_BOBBER_THROW", "ENTITY_BOBBER_THROW", "ENTITY_BOBBER_THROW", "ENTITY_BOBBER_THROW", ""),
    ENTITY_FISH_SWIM("same", "", "", "", "", ""),
    ENTITY_FOX_AGGRO("", "", "", "", "", ""),
    ENTITY_FOX_AMBIENT("", "", "", "", "", ""),
    ENTITY_FOX_BITE("", "", "", "", "", ""),
    ENTITY_FOX_DEATH("", "", "", "", "", ""),
    ENTITY_FOX_EAT("", "", "", "", "", ""),
    ENTITY_FOX_HURT("", "", "", "", "", ""),
    ENTITY_FOX_SCREECH("", "", "", "", "", ""),
    ENTITY_FOX_SLEEP("", "", "", "", "", ""),
    ENTITY_FOX_SNIFF("", "", "", "", "", ""),
    ENTITY_FOX_SPIT("", "", "", "", "", ""),
    ENTITY_GENERIC_BIG_FALL("same", "same", "same", "same", "same", "FALL_BIG"),
    ENTITY_GENERIC_BURN("same", "same", "same", "same", "same", ""),
    ENTITY_GENERIC_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_GENERIC_DRINK("same", "same", "same", "same", "same", "DRINK"),
    ENTITY_GENERIC_EAT("same", "same", "same", "same", "same", "EAT"),
    ENTITY_GENERIC_EXPLODE("same", "same", "same", "same", "same", "EXPLODE"),
    ENTITY_GENERIC_EXTINGUISH_FIRE("same", "same", "same", "same", "same", "FIZZ"),
    ENTITY_GENERIC_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_GENERIC_SMALL_FALL("same", "same", "same", "same", "same", "FALL_SMALL"),
    ENTITY_GENERIC_SPLASH("same", "same", "same", "same", "same", "SPLASH"),
    ENTITY_GENERIC_SWIM("same", "same", "same", "same", "same", "SWIM"),
    ENTITY_GHAST_AMBIENT("same", "same", "same", "same", "same", "GHAST_MOAN"),
    ENTITY_GHAST_DEATH("same", "same", "same", "same", "same", "GHAST_DEATH"),
    ENTITY_GHAST_HURT("same", "same", "same", "same", "same", "GHAST_SCREAM2"),
    ENTITY_GHAST_SCREAM("same", "same", "same", "same", "same", "GHAST_SCREAM"),
    ENTITY_GHAST_SHOOT("same", "same", "same", "same", "same", "GHAST_FIREBALL"),
    ENTITY_GHAST_WARN("same", "same", "same", "same", "same", "GHAST_CHARGE"),
    ENTITY_GUARDIAN_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_AMBIENT_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_ATTACK("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_DEATH_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_FLOP("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_GUARDIAN_HURT_LAND("same", "same", "same", "same", "same", ""),
    ENTITY_HORSE_AMBIENT("same", "same", "same", "same", "same", "HORSE_IDLE"),
    ENTITY_HORSE_ANGRY("same", "same", "same", "same", "same", "HORSE_ANGRY"),
    ENTITY_HORSE_ARMOR("same", "same", "same", "same", "same", "HORSE_ARMOR"),
    ENTITY_HORSE_BREATHE("same", "same", "same", "same", "same", "HORSE_BREATHE"),
    ENTITY_HORSE_DEATH("same", "same", "same", "same", "same", "HORSE_DEATH"),
    ENTITY_HORSE_EAT("same", "same", "same", "same", "same", ""),
    ENTITY_HORSE_GALLOP("same", "same", "same", "same", "same", "HORSE_GALLOP"),
    ENTITY_HORSE_HURT("same", "same", "same", "same", "same", "HORSE_HIT"),
    ENTITY_HORSE_JUMP("same", "same", "same", "same", "same", "HORSE_JUMP"),
    ENTITY_HORSE_LAND("same", "same", "same", "same", "same", "HORSE_LAND"),
    ENTITY_HORSE_SADDLE("same", "same", "same", "same", "same", "HORSE_SADDLE"),
    ENTITY_HORSE_STEP("same", "same", "same", "same", "same", "HORSE_SOFT"),
    ENTITY_HORSE_STEP_WOOD("same", "same", "same", "same", "same", "HORSE_WOOD"),
    ENTITY_HOSTILE_BIG_FALL("same", "same", "same", "same", "same", ""),
    ENTITY_HOSTILE_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_HOSTILE_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_HOSTILE_SMALL_FALL("same", "same", "same", "same", "same", ""),
    ENTITY_HOSTILE_SPLASH("same", "same", "same", "same", "same", ""),
    ENTITY_HOSTILE_SWIM("same", "same", "same", "same", "same", ""),
    ENTITY_HUSK_AMBIENT("same", "same", "same", "same", "", ""),
    ENTITY_HUSK_CONVERTED_TO_ZOMBIE("same", "", "", "", "", ""),
    ENTITY_HUSK_DEATH("same", "same", "same", "same", "", ""),
    ENTITY_HUSK_HURT("same", "same", "same", "same", "", ""),
    ENTITY_HUSK_STEP("same", "same", "same", "same", "", ""),
    ENTITY_ILLUSIONER_AMBIENT("same", "ENTITY_ILLUSION_ILLAGER_AMBIENT", "", "", "", ""),
    ENTITY_ILLUSIONER_CAST_SPELL("same", "ENTITY_ILLUSION_ILLAGER_CAST_SPELL", "", "", "", ""),
    ENTITY_ILLUSIONER_DEATH("same", "ENTITY_ILLUSION_ILLAGER_DEATH", "", "", "", ""),
    ENTITY_ILLUSIONER_HURT("same", "ENTITY_ILLUSION_ILLAGER_HURT", "", "", "", ""),
    ENTITY_ILLUSIONER_MIRROR_MOVE("same", "ENTITY_ILLUSION_ILLAGER_MIRROR_MOVE", "", "", "", ""),
    ENTITY_ILLUSIONER_PREPARE_BLINDNESS("same", "ENTITY_ILLUSION_ILLAGER_PREPARE_BLINDNESS", "", "", "", ""),
    ENTITY_ILLUSIONER_PREPARE_MIRROR("same", "ENTITY_ILLUSION_ILLAGER_PREPARE_MIRROR", "", "", "", ""),
    ENTITY_IRON_GOLEM_ATTACK("same", "ENTITY_IRONGOLEM_ATTACK", "ENTITY_IRONGOLEM_ATTACK", "ENTITY_IRONGOLEM_ATTACK", "ENTITY_IRONGOLEM_ATTACK", "IRONGOLEM_THROW"),
    ENTITY_IRON_GOLEM_DEATH("same", "ENTITY_IRONGOLEM_DEATH", "ENTITY_IRONGOLEM_DEATH", "ENTITY_IRONGOLEM_DEATH", "ENTITY_IRONGOLEM_DEATH", "IRONGOLEM_DEATH"),
    ENTITY_IRON_GOLEM_HURT("same", "ENTITY_IRONGOLEM_HURT", "ENTITY_IRONGOLEM_HURT", "ENTITY_IRONGOLEM_HURT", "ENTITY_IRONGOLEM_HURT", "IRONGOLEM_HIT"),
    ENTITY_IRON_GOLEM_STEP("same", "ENTITY_IRONGOLEM_STEP", "ENTITY_IRONGOLEM_STEP", "ENTITY_IRONGOLEM_STEP", "ENTITY_IRONGOLEM_STEP", "IRONGOLEM_WALK"),
    ENTITY_ITEM_BREAK("same", "same", "same", "same", "same", "ITEM_BREAK"),
    ENTITY_ITEM_FRAME_ADD_ITEM("same", "ENTITY_ITEMFRAME_ADD_ITEM", "ENTITY_ITEMFRAME_ADD_ITEM", "ENTITY_ITEMFRAME_ADD_ITEM", "ENTITY_ITEMFRAME_ADD_ITEM", ""),
    ENTITY_ITEM_FRAME_BREAK("same", "ENTITY_ITEMFRAME_BREAK", "ENTITY_ITEMFRAME_BREAK", "ENTITY_ITEMFRAME_BREAK", "ENTITY_ITEMFRAME_BREAK", ""),
    ENTITY_ITEM_FRAME_PLACE("same", "ENTITY_ITEMFRAME_PLACE", "ENTITY_ITEMFRAME_PLACE", "ENTITY_ITEMFRAME_PLACE", "ENTITY_ITEMFRAME_PLACE", ""),
    ENTITY_ITEM_FRAME_REMOVE_ITEM("same", "ENTITY_ITEMFRAME_REMOVE_ITEM", "ENTITY_ITEMFRAME_REMOVE_ITEM", "ENTITY_ITEMFRAME_REMOVE_ITEM", "ENTITY_ITEMFRAME_REMOVE_ITEM", ""),
    ENTITY_ITEM_FRAME_ROTATE_ITEM("same", "ENTITY_ITEMFRAME_ROTATE_ITEM", "ENTITY_ITEMFRAME_ROTATE_ITEM", "ENTITY_ITEMFRAME_ROTATE_ITEM", "ENTITY_ITEMFRAME_ROTATE_ITEM", ""),
    ENTITY_ITEM_PICKUP("same", "same", "same", "same", "same", "ITEM_PICKUP"),
    ENTITY_LEASH_KNOT_BREAK("same", "ENTITY_LEASHKNOT_BREAK", "ENTITY_LEASHKNOT_BREAK", "ENTITY_LEASHKNOT_BREAK", "ENTITY_LEASHKNOT_BREAK", ""),
    ENTITY_LEASH_KNOT_PLACE("same", "ENTITY_LEASHKNOT_PLACE", "ENTITY_LEASHKNOT_PLACE", "ENTITY_LEASHKNOT_PLACE", "ENTITY_LEASHKNOT_PLACE", ""),
    ENTITY_LIGHTNING_BOLT_IMPACT("same", "ENTITY_LIGHTNING_IMPACT", "ENTITY_LIGHTNING_IMPACT", "ENTITY_LIGHTNING_IMPACT", "ENTITY_LIGHTNING_IMPACT", ""),
    ENTITY_LIGHTNING_BOLT_THUNDER("same", "ENTITY_LIGHTNING_THUNDER", "ENTITY_LIGHTNING_THUNDER", "ENTITY_LIGHTNING_THUNDER", "ENTITY_LIGHTNING_THUNDER", "AMBIENCE_THUNDER"),
    ENTITY_LINGERING_POTION_THROW("same", "ENTITY_LINGERINGPOTION_THROW", "ENTITY_LINGERINGPOTION_THROW", "ENTITY_LINGERINGPOTION_THROW", "ENTITY_LINGERINGPOTION_THROW", ""),
    ENTITY_LLAMA_AMBIENT("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_ANGRY("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_CHEST("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_DEATH("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_EAT("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_HURT("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_SPIT("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_STEP("same", "same", "same", "", "", ""),
    ENTITY_LLAMA_SWAG("same", "same", "same", "", "", ""),
    ENTITY_MAGMA_CUBE_DEATH("same", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", ""),
    ENTITY_MAGMA_CUBE_DEATH_SMALL("same", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", "ENTITY_MAGMACUBE_DEATH", ""),
    ENTITY_MAGMA_CUBE_HURT("same", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", ""),
    ENTITY_MAGMA_CUBE_HURT_SMALL("same", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", "ENTITY_MAGMACUBE_HURT", ""),
    ENTITY_MAGMA_CUBE_JUMP("same", "ENTITY_MAGMACUBE_JUMP", "ENTITY_MAGMACUBE_JUMP", "ENTITY_MAGMACUBE_JUMP", "ENTITY_MAGMACUBE_JUMP", "MAGMACUBE_JUMP"),
    ENTITY_MAGMA_CUBE_SQUISH("same", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "MAGMACUBE_WALK"),
    ENTITY_MAGMA_CUBE_SQUISH_SMALL("same", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "ENTITY_MAGMACUBE_SQUISH", "MAGMACUBE_WALK2"),
    ENTITY_MINECART_INSIDE("same", "same", "same", "same", "same", "MINECART_BASE"),
    ENTITY_MINECART_RIDING("same", "same", "same", "same", "same", "MINECART_RIDING"),
    ENTITY_MOOSHROOM_CONVERT("", "", "", "", "", ""),
    ENTITY_MOOSHROOM_EAT("", "", "", "", "", ""),
    ENTITY_MOOSHROOM_MILK("", "", "", "", "", ""),
    ENTITY_MOOSHROOM_SHEAR("same", "same", "same", "same", "same", ""),
    ENTITY_MOOSHROOM_SUSPICIOUS_MILK("", "", "", "", "", ""),
    ENTITY_MULE_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_MULE_CHEST("same", "same", "same", "", "", ""),
    ENTITY_MULE_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_MULE_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_OCELOT_AMBIENT("", "", "", "", "", ""),
    ENTITY_OCELOT_DEATH("", "", "", "", "", ""),
    ENTITY_OCELOT_HURT("", "", "", "", "", ""),
    ENTITY_PAINTING_BREAK("same", "same", "same", "same", "same", ""),
    ENTITY_PAINTING_PLACE("same", "same", "same", "same", "same", ""),
    ENTITY_PANDA_AGGRESSIVE_AMBIENT("", "", "", "", "", ""),
    ENTITY_PANDA_AMBIENT("", "", "", "", "", ""),
    ENTITY_PANDA_BITE("", "", "", "", "", ""),
    ENTITY_PANDA_CANT_BREED("", "", "", "", "", ""),
    ENTITY_PANDA_DEATH("", "", "", "", "", ""),
    ENTITY_PANDA_EAT("", "", "", "", "", ""),
    ENTITY_PANDA_HURT("", "", "", "", "", ""),
    ENTITY_PANDA_PRE_SNEEZE("", "", "", "", "", ""),
    ENTITY_PANDA_SNEEZE("", "", "", "", "", ""),
    ENTITY_PANDA_STEP("", "", "", "", "", ""),
    ENTITY_PANDA_WORRIED_AMBIENT("", "", "", "", "", ""),
    ENTITY_PARROT_AMBIENT("same", "same", "", "", "", ""),
    ENTITY_PARROT_DEATH("same", "same", "", "", "", ""),
    ENTITY_PARROT_EAT("same", "same", "", "", "", ""),
    ENTITY_PARROT_FLY("same", "same", "", "", "", ""),
    ENTITY_PARROT_HURT("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_BLAZE("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_CREEPER("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_DROWNED("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ELDER_GUARDIAN("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ENDERMAN("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ENDERMITE("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ENDER_DRAGON("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_EVOKER("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_GHAST("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_GUARDIAN("", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_HUSK("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ILLUSIONER("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_MAGMA_CUBE("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_PANDA("", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_PHANTOM("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_PILLAGER("", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_POLAR_BEAR("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_RAVAGER("", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_SHULKER("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_SILVERFISH("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_SKELETON("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_SLIME("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_SPIDER("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_STRAY("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_VEX("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_VINDICATOR("same", "", "", "", "", ""),
    ENTITY_PARROT_IMITATE_WITCH("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_WITHER("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_WITHER_SKELETON("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_WOLF("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ZOMBIE("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ZOMBIE_PIGMAN("same", "same", "", "", "", ""),
    ENTITY_PARROT_IMITATE_ZOMBIE_VILLAGER("same", "same", "", "", "", ""),
    ENTITY_PARROT_STEP("same", "same", "", "", "", ""),
    ENTITY_PHANTOM_AMBIENT("same", "", "", "", "", ""),
    ENTITY_PHANTOM_BITE("same", "", "", "", "", ""),
    ENTITY_PHANTOM_DEATH("same", "", "", "", "", ""),
    ENTITY_PHANTOM_FLAP("same", "", "", "", "", ""),
    ENTITY_PHANTOM_HURT("same", "", "", "", "", ""),
    ENTITY_PHANTOM_SWOOP("same", "", "", "", "", ""),
    ENTITY_PIG_AMBIENT("same", "same", "same", "same", "same", "PIG_IDLE"),
    ENTITY_PIG_DEATH("same", "same", "same", "same", "same", "PIG_DEATH"),
    ENTITY_PIG_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_PIG_SADDLE("same", "same", "same", "same", "same", ""),
    ENTITY_PIG_STEP("same", "same", "same", "same", "same", "PIG_WALK"),
    ENTITY_PILLAGER_AMBIENT("", "", "", "", "", ""),
    ENTITY_PILLAGER_CELEBRATE("", "", "", "", "", ""),
    ENTITY_PILLAGER_DEATH("", "", "", "", "", ""),
    ENTITY_PILLAGER_HURT("", "", "", "", "", ""),
    ENTITY_PLAYER_ATTACK_CRIT("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_ATTACK_KNOCKBACK("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_ATTACK_NODAMAGE("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_ATTACK_STRONG("same", "same", "same", "same", "same", "SUCCESSFUL_HIT"),
    ENTITY_PLAYER_ATTACK_SWEEP("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_ATTACK_WEAK("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_BIG_FALL("same", "same", "same", "same", "same", "FALL_BIG"),
    ENTITY_PLAYER_BREATH("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_BURP("same", "same", "same", "same", "same", "BURP"),
    ENTITY_PLAYER_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_HURT("same", "same", "same", "same", "same", "HURT_FLESH"),
    ENTITY_PLAYER_HURT_DROWN("same", "same", "", "", "", ""),
    ENTITY_PLAYER_HURT_ON_FIRE("same", "same", "", "", "", ""),
    ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH("", "", "", "", "", ""),
    ENTITY_PLAYER_LEVELUP("same", "same", "same", "same", "same", "LEVELUP"),
    ENTITY_PLAYER_SMALL_FALL("same", "same", "same", "same", "same", "FALL_SMALL"),
    ENTITY_PLAYER_SPLASH("same", "same", "same", "same", "same", ""),
    ENTITY_PLAYER_SPLASH_HIGH_SPEED("same", "", "", "", "", ""),
    ENTITY_PLAYER_SWIM("same", "same", "same", "same", "same", ""),
    ENTITY_POLAR_BEAR_AMBIENT("same", "same", "same", "same", "", ""),
    ENTITY_POLAR_BEAR_AMBIENT_BABY("same", "", "", "", "", ""),
    ENTITY_POLAR_BEAR_DEATH("same", "same", "same", "same", "", ""),
    ENTITY_POLAR_BEAR_HURT("same", "same", "same", "same", "", ""),
    ENTITY_POLAR_BEAR_STEP("same", "same", "same", "same", "", ""),
    ENTITY_POLAR_BEAR_WARNING("same", "same", "same", "same", "", ""),
    ENTITY_PUFFER_FISH_AMBIENT("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_BLOW_OUT("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_BLOW_UP("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_DEATH("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_FLOP("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_HURT("same", "", "", "", "", ""),
    ENTITY_PUFFER_FISH_STING("same", "", "", "", "", ""),
    ENTITY_RABBIT_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_RABBIT_ATTACK("same", "same", "same", "same", "same", ""),
    ENTITY_RABBIT_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_RABBIT_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_RABBIT_JUMP("same", "same", "same", "same", "same", ""),
    ENTITY_RAVAGER_AMBIENT("", "", "", "", "", ""),
    ENTITY_RAVAGER_ATTACK("", "", "", "", "", ""),
    ENTITY_RAVAGER_CELEBRATE("", "", "", "", "", ""),
    ENTITY_RAVAGER_DEATH("", "", "", "", "", ""),
    ENTITY_RAVAGER_HURT("", "", "", "", "", ""),
    ENTITY_RAVAGER_ROAR("", "", "", "", "", ""),
    ENTITY_RAVAGER_STEP("", "", "", "", "", ""),
    ENTITY_RAVAGER_STUNNED("", "", "", "", "", ""),
    ENTITY_SALMON_AMBIENT("same", "", "", "", "", ""),
    ENTITY_SALMON_DEATH("same", "", "", "", "", ""),
    ENTITY_SALMON_FLOP("same", "", "", "", "", ""),
    ENTITY_SALMON_HURT("same", "", "", "", "", ""),
    ENTITY_SHEEP_AMBIENT("same", "same", "same", "same", "same", "SHEEP_IDLE"),
    ENTITY_SHEEP_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_SHEEP_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SHEEP_SHEAR("same", "same", "same", "same", "same", "SHEEP_SHEAR"),
    ENTITY_SHEEP_STEP("same", "same", "same", "same", "same", "SHEEP_WALK"),
    ENTITY_SHULKER_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_BULLET_HIT("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_BULLET_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_CLOSE("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_HURT_CLOSED("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_OPEN("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_SHOOT("same", "same", "same", "same", "same", ""),
    ENTITY_SHULKER_TELEPORT("same", "same", "same", "same", "same", ""),
    ENTITY_SILVERFISH_AMBIENT("same", "same", "same", "same", "same", "SILVERFISH_IDLE"),
    ENTITY_SILVERFISH_DEATH("same", "same", "same", "same", "same", "SILVERFISH_KILL"),
    ENTITY_SILVERFISH_HURT("same", "same", "same", "same", "same", "SILVERFISH_HIT"),
    ENTITY_SILVERFISH_STEP("same", "same", "same", "same", "same", "SILVERFISH_WALK"),
    ENTITY_SKELETON_AMBIENT("same", "same", "same", "same", "same", "SKELETON_IDLE"),
    ENTITY_SKELETON_DEATH("same", "same", "same", "same", "same", "SKELETON_DEATH"),
    ENTITY_SKELETON_HORSE_AMBIENT("same", "same", "same", "same", "same", "HORSE_SKELETON_AMBIENT"),
    ENTITY_SKELETON_HORSE_AMBIENT_WATER("same", "", "", "", "", ""),
    ENTITY_SKELETON_HORSE_DEATH("same", "same", "same", "same", "same", "HORSE_SKELETON_DEATH"),
    ENTITY_SKELETON_HORSE_GALLOP_WATER("same", "", "", "", "", ""),
    ENTITY_SKELETON_HORSE_HURT("same", "same", "same", "same", "same", "HORSE_SKELETON_HIT"),
    ENTITY_SKELETON_HORSE_JUMP_WATER("same", "", "", "", "", ""),
    ENTITY_SKELETON_HORSE_STEP_WATER("same", "", "", "", "", ""),
    ENTITY_SKELETON_HORSE_SWIM("same", "", "", "", "", ""),
    ENTITY_SKELETON_HURT("same", "same", "same", "same", "same", "SKELETON_HURT"),
    ENTITY_SKELETON_SHOOT("same", "same", "same", "same", "same", ""),
    ENTITY_SKELETON_STEP("same", "same", "same", "same", "same", "SKELETON_WALK"),
    ENTITY_SLIME_ATTACK("same", "same", "same", "same", "same", "SLIME_ATTACK"),
    ENTITY_SLIME_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_SLIME_DEATH_SMALL("same", "", "", "", "", ""),
    ENTITY_SLIME_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SLIME_HURT_SMALL("same", "", "", "", "", ""),
    ENTITY_SLIME_JUMP("same", "same", "same", "same", "same", "SLIME_WALK"),
    ENTITY_SLIME_JUMP_SMALL("same", "", "", "", "", ""),
    ENTITY_SLIME_SQUISH("same", "same", "same", "same", "same", "SLIME_WALK2"),
    ENTITY_SLIME_SQUISH_SMALL("same", "", "", "", "", ""),
    ENTITY_SNOWBALL_THROW("same", "same", "same", "same", "same", ""),
    ENTITY_SNOW_GOLEM_AMBIENT("same", "ENTITY_SNOWMAN_AMBIENT", "ENTITY_SNOWMAN_AMBIENT", "ENTITY_SNOWMAN_AMBIENT", "ENTITY_SNOWMAN_AMBIENT", ""),
    ENTITY_SNOW_GOLEM_DEATH("same", "ENTITY_SNOWMAN_DEATH", "ENTITY_SNOWMAN_DEATH", "ENTITY_SNOWMAN_DEATH", "ENTITY_SNOWMAN_DEATH", ""),
    ENTITY_SNOW_GOLEM_HURT("same", "ENTITY_SNOWMAN_HURT", "ENTITY_SNOWMAN_HURT", "ENTITY_SNOWMAN_HURT", "ENTITY_SNOWMAN_HURT", ""),
    ENTITY_SNOW_GOLEM_SHOOT("same", "ENTITY_SNOWMAN_SHOOT", "ENTITY_SNOWMAN_SHOOT", "ENTITY_SNOWMAN_SHOOT", "ENTITY_SNOWMAN_SHOOT", ""),
    ENTITY_SPIDER_AMBIENT("same", "same", "same", "same", "same", "SPIDER_IDLE"),
    ENTITY_SPIDER_DEATH("same", "same", "same", "same", "same", "SPIDER_DEATH"),
    ENTITY_SPIDER_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SPIDER_STEP("same", "same", "same", "same", "same", "SPIDER_WALK"),
    ENTITY_SPLASH_POTION_BREAK("same", "same", "same", "same", "same", ""),
    ENTITY_SPLASH_POTION_THROW("same", "same", "same", "same", "same", ""),
    ENTITY_SQUID_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_SQUID_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_SQUID_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_SQUID_SQUIRT("same", "", "", "", "", ""),
    ENTITY_STRAY_AMBIENT("same", "same", "same", "same", "", ""),
    ENTITY_STRAY_DEATH("same", "same", "same", "same", "", ""),
    ENTITY_STRAY_HURT("same", "same", "same", "same", "", ""),
    ENTITY_STRAY_STEP("same", "same", "same", "same", "", ""),
    ENTITY_TNT_PRIMED("same", "same", "same", "same", "same", "FUSE"),
    ENTITY_TROPICAL_FISH_AMBIENT("same", "", "", "", "", ""),
    ENTITY_TROPICAL_FISH_DEATH("same", "", "", "", "", ""),
    ENTITY_TROPICAL_FISH_FLOP("same", "", "", "", "", ""),
    ENTITY_TROPICAL_FISH_HURT("same", "", "", "", "", ""),
    ENTITY_TURTLE_AMBIENT_LAND("same", "", "", "", "", ""),
    ENTITY_TURTLE_DEATH("same", "", "", "", "", ""),
    ENTITY_TURTLE_DEATH_BABY("same", "", "", "", "", ""),
    ENTITY_TURTLE_EGG_BREAK("same", "", "", "", "", ""),
    ENTITY_TURTLE_EGG_CRACK("same", "", "", "", "", ""),
    ENTITY_TURTLE_EGG_HATCH("same", "", "", "", "", ""),
    ENTITY_TURTLE_HURT("same", "", "", "", "", ""),
    ENTITY_TURTLE_HURT_BABY("same", "", "", "", "", ""),
    ENTITY_TURTLE_LAY_EGG("same", "", "", "", "", ""),
    ENTITY_TURTLE_SHAMBLE("same", "", "", "", "", ""),
    ENTITY_TURTLE_SHAMBLE_BABY("same", "", "", "", "", ""),
    ENTITY_TURTLE_SWIM("same", "", "", "", "", ""),
    ENTITY_VEX_AMBIENT("same", "same", "same", "", "", ""),
    ENTITY_VEX_CHARGE("same", "same", "same", "", "", ""),
    ENTITY_VEX_DEATH("same", "same", "same", "", "", ""),
    ENTITY_VEX_HURT("same", "same", "same", "", "", ""),
    ENTITY_VILLAGER_AMBIENT("same", "same", "same", "same", "same", "VILLAGER_IDLE"),
    ENTITY_VILLAGER_CELEBRATE("", "", "", "", "", ""),
    ENTITY_VILLAGER_DEATH("same", "same", "same", "same", "same", "VILLAGER_DEATH"),
    ENTITY_VILLAGER_HURT("same", "same", "same", "same", "same", "VILLAGER_HIT"),
    ENTITY_VILLAGER_NO("same", "same", "same", "same", "same", "VILLAGER_NO"),
    ENTITY_VILLAGER_TRADE("same", "ENTITY_VILLAGER_TRADING", "ENTITY_VILLAGER_TRADING", "ENTITY_VILLAGER_TRADING", "ENTITY_VILLAGER_TRADING", "VILLAGER_HAGGLE"),
    ENTITY_VILLAGER_WORK_ARMORER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_BUTCHER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_CARTOGRAPHER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_CLERIC("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_FARMER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_FISHERMAN("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_FLETCHER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_LEATHERWORKER("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_LIBRARIAN("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_MASON("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_SHEPHERD("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_TOOLSMITH("", "", "", "", "", ""),
    ENTITY_VILLAGER_WORK_WEAPONSMITH("", "", "", "", "", ""),
    ENTITY_VILLAGER_YES("same", "same", "same", "same", "same", "VILLAGER_YES"),
    ENTITY_VINDICATOR_AMBIENT("same", "ENTITY_VINDICATION_ILLAGER_AMBIENT", "ENTITY_VINDICATION_ILLAGER_AMBIENT", "", "", ""),
    ENTITY_VINDICATOR_CELEBRATE("", "", "", "", "", ""),
    ENTITY_VINDICATOR_DEATH("same", "ENTITY_VINDICATION_ILLAGER_DEATH", "ENTITY_VINDICATION_ILLAGER_DEATH", "", "", ""),
    ENTITY_VINDICATOR_HURT("same", "ENTITY_VINDICATION_ILLAGER_HURT", "ENTITY_VINDICATION_ILLAGER_HURT", "", "", ""),
    ENTITY_WANDERING_TRADER_AMBIENT("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_DEATH("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_DISAPPEARED("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_DRINK_MILK("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_DRINK_POTION("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_HURT("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_NO("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_REAPPEARED("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_TRADE("", "", "", "", "", ""),
    ENTITY_WANDERING_TRADER_YES("", "", "", "", "", ""),
    ENTITY_WITCH_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_WITCH_CELEBRATE("", "", "", "", "", ""),
    ENTITY_WITCH_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_WITCH_DRINK("same", "same", "same", "same", "same", ""),
    ENTITY_WITCH_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_WITCH_THROW("same", "same", "same", "same", "same", ""),
    ENTITY_WITHER_AMBIENT("same", "same", "same", "same", "same", "WITHER_IDLE"),
    ENTITY_WITHER_BREAK_BLOCK("same", "same", "same", "same", "same", ""),
    ENTITY_WITHER_DEATH("same", "same", "same", "same", "same", "WITHER_DEATH"),
    ENTITY_WITHER_HURT("same", "same", "same", "same", "same", "WITHER_HURT"),
    ENTITY_WITHER_SHOOT("same", "same", "same", "same", "same", "WITHER_SHOOT"),
    ENTITY_WITHER_SKELETON_AMBIENT("same", "same", "same", "same", "", ""),
    ENTITY_WITHER_SKELETON_DEATH("same", "same", "same", "same", "", ""),
    ENTITY_WITHER_SKELETON_HURT("same", "same", "same", "same", "", ""),
    ENTITY_WITHER_SKELETON_STEP("same", "same", "same", "same", "", ""),
    ENTITY_WITHER_SPAWN("same", "same", "same", "same", "same", "WITHER_SPAWN"),
    ENTITY_WOLF_AMBIENT("same", "same", "same", "same", "same", "WOLF_BARK"),
    ENTITY_WOLF_DEATH("same", "same", "same", "same", "same", "WOLF_DEATH"),
    ENTITY_WOLF_GROWL("same", "same", "same", "same", "same", "WOLF_GROWL"),
    ENTITY_WOLF_HOWL("same", "same", "same", "same", "same", "WOLF_HOWL"),
    ENTITY_WOLF_HURT("same", "same", "same", "same", "same", "WOLF_HURT"),
    ENTITY_WOLF_PANT("same", "same", "same", "same", "same", "WOLF_PANT"),
    ENTITY_WOLF_SHAKE("same", "same", "same", "same", "same", "WOLF_SHAKE"),
    ENTITY_WOLF_STEP("same", "same", "same", "same", "same", "WOLF_WALK"),
    ENTITY_WOLF_WHINE("same", "same", "same", "same", "same", "WOLF_WHINE"),
    ENTITY_ZOMBIE_AMBIENT("same", "same", "same", "same", "same", "ZOMBIE_IDLE"),
    ENTITY_ZOMBIE_ATTACK_IRON_DOOR("same", "same", "same", "same", "same", "ZOMBIE_METAL"),
    ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR("same", "ENTITY_ZOMBIE_ATTACK_DOOR_WOOD", "ENTITY_ZOMBIE_ATTACK_DOOR_WOOD", "ENTITY_ZOMBIE_ATTACK_DOOR_WOOD", "ENTITY_ZOMBIE_ATTACK_DOOR_WOOD", "ZOMBIE_WOOD"),
    ENTITY_ZOMBIE_BREAK_WOODEN_DOOR("same", "ENTITY_ZOMBIE_BREAK_DOOR_WOOD", "ENTITY_ZOMBIE_BREAK_DOOR_WOOD", "ENTITY_ZOMBIE_BREAK_DOOR_WOOD", "ENTITY_ZOMBIE_BREAK_DOOR_WOOD", "ZOMBIE_WOODBREAK"),
    ENTITY_ZOMBIE_CONVERTED_TO_DROWNED("same", "", "", "", "", ""),
    ENTITY_ZOMBIE_DEATH("same", "same", "same", "same", "same", "ZOMBIE_DEATH"),
    ENTITY_ZOMBIE_DESTROY_EGG("same", "", "", "", "", ""),
    ENTITY_ZOMBIE_HORSE_AMBIENT("same", "same", "same", "same", "same", "HORSE_ZOMBIE_IDLE"),
    ENTITY_ZOMBIE_HORSE_DEATH("same", "same", "same", "same", "same", "HORSE_ZOMBIE_DEATH"),
    ENTITY_ZOMBIE_HORSE_HURT("same", "same", "same", "same", "same", "HORSE_ZOMBIE_HIT"),
    ENTITY_ZOMBIE_HURT("same", "same", "same", "same", "same", "ZOMBIE_HURT"),
    ENTITY_ZOMBIE_INFECT("same", "same", "same", "same", "same", "ZOMBIE_INFECT"),
    ENTITY_ZOMBIE_PIGMAN_AMBIENT("same", "ENTITY_ZOMBIE_PIG_AMBIENT", "ENTITY_ZOMBIE_PIG_AMBIENT", "ENTITY_ZOMBIE_PIG_AMBIENT", "ENTITY_ZOMBIE_PIG_AMBIENT", "ZOMBIE_PIG_IDLE"),
    ENTITY_ZOMBIE_PIGMAN_ANGRY("same", "ENTITY_ZOMBIE_PIG_ANGRY", "ENTITY_ZOMBIE_PIG_ANGRY", "ENTITY_ZOMBIE_PIG_ANGRY", "ENTITY_ZOMBIE_PIG_ANGRY", "ZOMBIE_PIG_ANGRY"),
    ENTITY_ZOMBIE_PIGMAN_DEATH("same", "ENTITY_ZOMBIE_PIG_DEATH", "ENTITY_ZOMBIE_PIG_DEATH", "ENTITY_ZOMBIE_PIG_DEATH", "ENTITY_ZOMBIE_PIG_DEATH", "ZOMBIE_PIG_DEATH"),
    ENTITY_ZOMBIE_PIGMAN_HURT("same", "ENTITY_ZOMBIE_PIG_HURT", "ENTITY_ZOMBIE_PIG_HURT", "ENTITY_ZOMBIE_PIG_HURT", "ENTITY_ZOMBIE_PIG_HURT", "ZOMBIE_PIG_HURT"),
    ENTITY_ZOMBIE_STEP("same", "same", "same", "same", "same", "ZOMBIE_WALK"),
    ENTITY_ZOMBIE_VILLAGER_AMBIENT("same", "same", "same", "same", "same", ""),
    ENTITY_ZOMBIE_VILLAGER_CONVERTED("same", "same", "same", "same", "same", "ZOMBIE_UNFECT"),
    ENTITY_ZOMBIE_VILLAGER_CURE("same", "same", "same", "same", "same", "ZOMBIE_REMEDY"),
    ENTITY_ZOMBIE_VILLAGER_DEATH("same", "same", "same", "same", "same", ""),
    ENTITY_ZOMBIE_VILLAGER_HURT("same", "same", "same", "same", "same", ""),
    ENTITY_ZOMBIE_VILLAGER_STEP("same", "same", "same", "same", "same", ""),
    EVENT_RAID_HORN("", "", "", "", "", ""),
    ITEM_ARMOR_EQUIP_CHAIN("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_DIAMOND("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_ELYTRA("same", "same", "same", "", "", ""),
    ITEM_ARMOR_EQUIP_GENERIC("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_GOLD("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_IRON("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_LEATHER("same", "same", "same", "same", "same", ""),
    ITEM_ARMOR_EQUIP_TURTLE("same", "", "", "", "", ""),
    ITEM_AXE_STRIP("same", "", "", "", "", ""),
    ITEM_BOOK_PAGE_TURN("", "", "", "", "", ""),
    ITEM_BOOK_PUT("", "", "", "", "", ""),
    ITEM_BOTTLE_EMPTY("same", "same", "same", "", "", ""),
    ITEM_BOTTLE_FILL("same", "same", "same", "same", "same", ""),
    ITEM_BOTTLE_FILL_DRAGONBREATH("same", "same", "same", "same", "same", ""),
    ITEM_BUCKET_EMPTY("same", "same", "same", "same", "same", ""),
    ITEM_BUCKET_EMPTY_FISH("same", "", "", "", "", ""),
    ITEM_BUCKET_EMPTY_LAVA("same", "same", "same", "same", "same", ""),
    ITEM_BUCKET_FILL("same", "same", "same", "same", "same", ""),
    ITEM_BUCKET_FILL_FISH("same", "", "", "", "", ""),
    ITEM_BUCKET_FILL_LAVA("same", "same", "same", "same", "same", ""),
    ITEM_CHORUS_FRUIT_TELEPORT("same", "same", "same", "same", "same", ""),
    ITEM_CROP_PLANT("", "", "", "", "", ""),
    ITEM_CROSSBOW_HIT("", "", "", "", "", ""),
    ITEM_CROSSBOW_LOADING_END("", "", "", "", "", ""),
    ITEM_CROSSBOW_LOADING_MIDDLE("", "", "", "", "", ""),
    ITEM_CROSSBOW_LOADING_START("", "", "", "", "", ""),
    ITEM_CROSSBOW_QUICK_CHARGE_1("", "", "", "", "", ""),
    ITEM_CROSSBOW_QUICK_CHARGE_2("", "", "", "", "", ""),
    ITEM_CROSSBOW_QUICK_CHARGE_3("", "", "", "", "", ""),
    ITEM_CROSSBOW_SHOOT("", "", "", "", "", ""),
    ITEM_ELYTRA_FLYING("same", "same", "same", "same", "same", ""),
    ITEM_FIRECHARGE_USE("same", "same", "same", "same", "same", ""),
    ITEM_FLINTANDSTEEL_USE("same", "same", "same", "same", "same", "FIRE_IGNITE"),
    ITEM_HOE_TILL("same", "same", "same", "same", "same", ""),
    ITEM_NETHER_WART_PLANT("", "", "", "", "", ""),
    ITEM_SHIELD_BLOCK("same", "same", "same", "same", "same", ""),
    ITEM_SHIELD_BREAK("same", "same", "same", "same", "same", ""),
    ITEM_SHOVEL_FLATTEN("same", "same", "same", "same", "same", ""),
    ITEM_SWEET_BERRIES_PICK_FROM_BUSH("", "", "", "", "", ""),
    ITEM_TOTEM_USE("same", "same", "same", "", "", ""),
    ITEM_TRIDENT_HIT("same", "", "", "", "", ""),
    ITEM_TRIDENT_HIT_GROUND("same", "", "", "", "", ""),
    ITEM_TRIDENT_RETURN("same", "", "", "", "", ""),
    ITEM_TRIDENT_RIPTIDE_1("same", "", "", "", "", ""),
    ITEM_TRIDENT_RIPTIDE_2("same", "", "", "", "", ""),
    ITEM_TRIDENT_RIPTIDE_3("same", "", "", "", "", ""),
    ITEM_TRIDENT_THROW("same", "", "", "", "", ""),
    ITEM_TRIDENT_THUNDER("same", "", "", "", "", ""),
    MUSIC_CREATIVE("same", "same", "same", "same", "same", ""),
    MUSIC_CREDITS("same", "same", "same", "same", "same", ""),
    MUSIC_DISC_11("same", "RECORD_11", "RECORD_11", "RECORD_11", "RECORD_11", ""),
    MUSIC_DISC_13("same", "RECORD_13", "RECORD_13", "RECORD_13", "RECORD_13", ""),
    MUSIC_DISC_BLOCKS("same", "RECORD_BLOCKS", "RECORD_BLOCKS", "RECORD_BLOCKS", "RECORD_BLOCKS", ""),
    MUSIC_DISC_CAT("same", "RECORD_CAT", "RECORD_CAT", "RECORD_CAT", "RECORD_CAT", ""),
    MUSIC_DISC_CHIRP("same", "RECORD_CHIRP", "RECORD_CHIRP", "RECORD_CHIRP", "RECORD_CHIRP", ""),
    MUSIC_DISC_FAR("same", "RECORD_FAR", "RECORD_FAR", "RECORD_FAR", "RECORD_FAR", ""),
    MUSIC_DISC_MALL("same", "RECORD_MALL", "RECORD_MALL", "RECORD_MALL", "RECORD_MALL", ""),
    MUSIC_DISC_MELLOHI("same", "RECORD_MELLOHI", "RECORD_MELLOHI", "RECORD_MELLOHI", "RECORD_MELLOHI", ""),
    MUSIC_DISC_STAL("same", "RECORD_STAL", "RECORD_STAL", "RECORD_STAL", "RECORD_STAL", ""),
    MUSIC_DISC_STRAD("same", "RECORD_STRAD", "RECORD_STRAD", "RECORD_STRAD", "RECORD_STRAD", ""),
    MUSIC_DISC_WAIT("same", "RECORD_WAIT", "RECORD_WAIT", "RECORD_WAIT", "RECORD_WAIT", ""),
    MUSIC_DISC_WARD("same", "RECORD_WARD", "RECORD_WARD", "RECORD_WARD", "RECORD_WARD", ""),
    MUSIC_DRAGON("same", "same", "same", "same", "same", ""),
    MUSIC_END("same", "same", "same", "same", "same", ""),
    MUSIC_GAME("same", "same", "same", "same", "same", ""),
    MUSIC_MENU("same", "same", "same", "same", "same", ""),
    MUSIC_NETHER("same", "same", "same", "same", "same", ""),
    MUSIC_UNDER_WATER("same", "", "", "", "", ""),
    UI_BUTTON_CLICK("same", "same", "same", "same", "same", "CLICK"),
    UI_CARTOGRAPHY_TABLE_TAKE_RESULT("", "", "", "", "", ""),
    UI_LOOM_SELECT_PATTERN("", "", "", "", "", ""),
    UI_LOOM_TAKE_RESULT("", "", "", "", "", ""),
    UI_STONECUTTER_SELECT_RECIPE("", "", "", "", "", ""),
    UI_STONECUTTER_TAKE_RESULT("", "", "", "", "", ""),
    UI_TOAST_CHALLENGE_COMPLETE("same", "same", "", "", "", ""),
    UI_TOAST_IN("same", "same", "", "", "", ""),
    UI_TOAST_OUT("same", "same", "", "", "", ""),
    WEATHER_RAIN("same", "same", "same", "same", "same", "AMBIENCE_RAIN"),
    WEATHER_RAIN_ABOVE("same", "same", "same", "same", "same", "");
    private final ArrayList<String> snds = new ArrayList<String>();
    GSound(String... sounds) {
        for (String str : sounds) {
            if (str.equals("same")) {
                snds.add(this.toString());
                continue;
            }
            snds.add(str);
            continue;
        }
    }
    public ArrayList<String> getSoundArrays() {
        return this.snds;
    }

    /**
     * Credit to MrIvanPlays
     */
    public static GSound matchStrong(String val) {
        String upperCase = val.toUpperCase();
        for (GSound gsound : GSound.values()) {
            List<String> sounds = gsound.getSoundArrays();
            for (String sound : sounds) {
                if (!sound.equalsIgnoreCase(upperCase)) {
                    if (upperCase.contains(" ")) {
                        String[] split = upperCase.split(" ");
                        for (int i = 0; i < split.length; i++) {
                            if (split.length < i + 1) {
                                if (sound.contains(split[i])) {
                                    return gsound;
                                }
                            }
                            if (sound.contains(split[i]) && sound.contains(split[i + 1])) {
                                return gsound;
                            }
                        }
                    } else {
                        matchStrong(upperCase.replace("_", " "));
                    }
                } else {
                    return gsound;
                }
            }
        }
        return null;
    }
    @Nullable
    public static GSound match(String val) {
        val = val.toUpperCase();
        for (GSound gs : GSound.values()) {
            ArrayList<String> snds = gs.getSoundArrays();
            for (String str : snds) {
                if (!str.equals(val)) continue;
                return gs;
            }
        }
        return null;
    }
    @Nullable
    public Sound parseSound() {
        Sound test = sounds.get(this);
        if (test != null) {
            return test;
        }
        switch (serverVersion) {
            case UNKNOWN:
            case V1_8:
            {
                String val = this.getSoundArrays().get(5);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_9:
            {
                String val = this.getSoundArrays().get(4);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_10:
            {
                String val = this.getSoundArrays().get(3);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_11:
            {
                String val = this.getSoundArrays().get(2);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_12:
            {
                String val = this.getSoundArrays().get(1);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_13:
            {
                String val = this.getSoundArrays().get(0);
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
            case V1_14:
            {
                String val = this.toString();
                Sound soun = null;
                try {
                    soun = Sound.valueOf(val);
                } catch (IllegalArgumentException e) {
                    soun = this.tryAll();
                }
                if (soun == null) {
                    return null;
                }
                sounds.put(this,soun);
                return soun;
            }
        }
        return null;
    }
    @Nullable
    private Sound tryAll() {
        String lm = this.toString();
        Sound x = null;
        try {
            x = Sound.valueOf(lm);
        } catch (IllegalArgumentException e) {

        }
        if (x != null) {
            return x;
        }
        for (String str : this.getSoundArrays()) {
            if (str.isEmpty()) continue;
            Sound s = null;
            try {
                s = Sound.valueOf(str);
            } catch (IllegalArgumentException e) {
                continue;
            }
            return s;
        }
        return null;
    }
    public enum MinecraftVersion {
        UNKNOWN,
        V1_8,
        V1_9,
        V1_10,
        V1_11,
        V1_12,
        V1_13,
        V1_14;

        public static final MinecraftVersion[] VALUES = MinecraftVersion.values();
    }
    /*
     *
     * Minecraft Versions and enum values
     *	 WEATHER_RAIN("same", "same", "same", "same", "same", ""),
     *	   V1_14	   V1_13	V1_12   V1_11   V1_10   V1_9  V1_8
     */
    static {
        String ver = Bukkit.getVersion();
        boolean found = false;
        if (ver.contains("1.14")) {
            serverVersion = MinecraftVersion.V1_14;
            found = true;
        }
        if (!found && ver.contains("1.13")) {
            serverVersion = MinecraftVersion.V1_13;
            found = true;
        }
        if (!found && ver.contains("1.12")) {
            serverVersion = MinecraftVersion.V1_12;
            found = true;
        }
        if (!found && ver.contains("1.11")) {
            serverVersion = MinecraftVersion.V1_11;
            found = true;
        }
        if (!found && ver.contains("1.10")) {
            serverVersion = MinecraftVersion.V1_10;
            found = true;
        }
        if (!found && ver.contains("1.9")) {
            serverVersion = MinecraftVersion.V1_9;
            found = true;
        }
        if (!found && ver.contains("1.8")) {
            serverVersion = MinecraftVersion.V1_8;
            found = true;
        }
        if (!found) {
            serverVersion = MinecraftVersion.UNKNOWN;
        }
    }
    public static MinecraftVersion getServerVersion() {
        return serverVersion;
    }
    private static MinecraftVersion serverVersion;
    private static final HashMap<GSound, Sound> sounds = new HashMap<GSound, Sound>();

}