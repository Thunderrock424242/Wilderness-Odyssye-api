import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChangelogGenerator
{
    public static void main(String[] args)
    {
        String inputFilePath = "changelog.txt";
        String outputFilePath = "formatted_changelog.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath)))
        {

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null)
            {   lines.add(line);
            }

            int lastDashIndex = -1;
            for (int i = lines.size() - 1; i >= 0; i--)
            {
                if (lines.get(i).trim().matches("^-+$"))
                {   // Check for line full of dashes
                    lastDashIndex = i;
                    break;
                }
            }

            writer.write("<div style=\"background-color: #212121; color: #aaafb6; font-family: 'JetBrains Mono', monospace; font-size: 9.8pt;\">");
            writer.write("<pre>");

            for (int i = lastDashIndex + 1; i < lines.size(); i++)
            {
                line = lines.get(i);
                String lineText = line.trim();
                boolean isMajorWarning = lineText.startsWith("!!");
                boolean isWarning = !isMajorWarning && lineText.startsWith("!");
                boolean isImportant = lineText.startsWith("*");
                boolean isListElement = lineText.startsWith("-") || isImportant || isMajorWarning || isWarning;
                boolean isSectionTitle = !isListElement && lineText.endsWith(":");
                boolean isTitleLine = !isListElement && !isSectionTitle;

                if (isTitleLine)
                {   line = "<span style=\"font-size: 18px; color: #ffffff; font-weight: bold;\">" + line + "</span>";
                }
                else if (isSectionTitle)
                {   line = "<u><strong style=\"font-size: 14px; color: #ffffff;\">" + line + "</strong></u>";
                }
                else if (isMajorWarning)
                {   line = "<span style=\"color: #ff4d49;\">" + line + "</span>";
                }
                else if (isWarning)
                {   line = "<span style=\"color: #ff9900;\">" + line + "</span>";
                }
                else if (isImportant)
                {   line = "<strong style=\"color: #ffffff;\">" + line + "</strong>";
                }

                writer.write(line + "<br />");
            }

            writer.write("</pre></div>");
            writer.write("</body></html>");

        }
        catch (IOException e)
        {   System.err.println("Error processing the files: " + e.getMessage());
        }
    }
}

/// credits go to https://github.com/Momo-Softworks/Cold-Sweat/blob/1.20.1-FG/src/main/java/ChangelogGenerator.java


/// example
/// 2.3
/// !! Several config files have formatting changes. Please back up all configs before updating
///
/// New Boiler & Icebox Behavior:-
/// - Added new block: Smokestack
///       - A smokestack can be placed on top of these blocks to make them act like a half-power hearth
///           - They give up to Insulation V and can only heat or cool, respectively
///           - They have about 60% of the radius and much less maximum volume
///       - They now produce ambient air particles when fueled and have a smokestack
///           - Added a new button to their GUIs to toggle this effect
/// * The icebox now has an animation/sounds when being used, opening like a chest
/// - The icebox has a unique texture for when a smokestack is placed on top
/// - Lava/water can be piped into them, similar to the hearth
///
/// Hearth Changes:-
/// - The hearth has been altered to be a post-Nether block
///       - Crafted with nether bricks and soul sand/soil, as well as the new smokestack block
///       - All other functionality is the same. This block is now an "upgrade" to the smokestack/boiler/icebox
/// - The hearth-like blocks now protect against temperature 50% more effectively
///       - This means the boiler & icebox have ~75% the effectiveness of the old hearth, and the new hearth is better than before
/// * Hearth-like blocks (including the boiler and icebox) now require redstone power to emit heat/cold
///       - While one of these blocks are powered, they will constantly heat/cool the area and consume fuel
///       - The thermolith can be used to detect the nearby temperature and power these blocks dynamically
///       * A config option has been added to re-enable the "smart" behavior of these blocks
///       - If a hearth is powered from the sides, it will cool the nearby area
///       - If a hearth is powered from the back, it will heat the nearby area
///       - Powering a hearth from both areas will make it heat and cool
///       - Powering a boiler or icebox from any side will make it heat or cool, respectively
///
/// Waterskin Changes:-
/// - Waterskins are now drinkable via sneaking and right-clicking
/// - Added more info about the filled waterskin's uses to its tooltip
/// - Waterskins can now fill (and be filled from) cauldrons
/// - Added custom filling and pouring sounds for the waterskin
///
/// Chameleon Changes:-
/// * Chameleons can now be given items by right-clicking
///       - Aside from being intuitive, this is a way of ensuring which chameleon will eat the item if there are multiple nearby. Only the chosen chameleon will attempt to eat the item
/// * Tamed chameleons can now be bred using their normal taming items (fish, spider eye), but only once
///       - This is to make chameleons slightly easier to obtain, and help with potential scarcity on large servers
///       - Baby chameleons shed chameleon molt immediately upon turning in to an adult, but otherwise don't
/// - Chameleons now drop 1 chameleon molt when killed, except if they are a baby
/// - Made baby chameleons slightly smaller
/// - Slightly decreased the rate at which chameleons shed
///
/// Loot Changes:-
/// - Increased hoglin hide drop rate to between 1 and 3 (from 1)
/// - Chameleon molt dropped from chameleons now lasts 20 minutes before despawning
/// - Significantly reduced the drop rate of sticks from soul stalk blocks
///
/// Ice Changes:-
/// * The hearth & icebox now no longer accept water by default
///       - Now, the primary cold fuel source is ice
/// * Ice is now much easier to obtain/farm, due to a few key changes:
///       - Ice now freezes/thaws according to Cold Sweat's temperature system
///       - This new system takes altitude, time, and the season into account
///       - Ice freezes at roughly 32 °F (0 °C)
///       - Ice can now be obtained with a stone pickaxe without silk touch
///             - An iron pickaxe is required for packed ice and blue ice
///             - If ice is mined with a wooden pickaxe or by hand, it will turn into water as normal
///       - Packed and Blue ice can now be un-crafted into their "lower-level" variants (i.e. blue ice yields packed ice) in the crafting table or stonecutter
///             - The crafting table yields 4 ice blocks, while the stonecutter yields 9
/// - Ice and snow now have slightly lower fuel values in the icebox and hearth
///
/// UI Changes:-
/// * The "vague" temperature gauge (world temperature gauge without a thermometer) has been turned into a frame around the body temperature icon
///       - Functionality is the same; these UI elements have just been combined to make the UI less cluttered. It looks pretty good, too!
///       - The exact world temperature readout will still be shown as normal when the player has a thermometer
/// * The body temperature readout is now hidden unless a thermometer is equipped
///       - Instead, the body temperature icon will now gradually fill up, turning either orange or blue as the player's temperature increases/decreases
/// - Added new "compact" view for insulation tooltips with 10 bars of insulation or more
///
/// Mod Compatibility:-
/// * (1.20) Added Origins integration as a standalone mod:
///       - Allows for users to modify the attribute values that players spawn with for origins via datapacks or config files
///       - This could already be done for your own origins via attribute modifiers, but this addon is useful for modpack devs
/// * Added support for Thirst Was Taken:
///       - Purity defaults to "Slightly Dirty"
///       - Can be purified by the same means as water bottles (furnace, campfire)
///             - This does not affect the temperature of the waterskin, only its purification level
///       - Drinkable items can now be purified in the boiler:
///             - When a waterskin is up to max temperature (120 F / 48 C), it will purify by one level every 10 seconds
///             - Other drinkable items don't need to heat up. They will start purifying immediately
///             - Purification rate is also affected by the configured temperature rate
/// - Waterskins can now be filled by Create machines with 250mb of water
/// * Added better support for netherite diving gear from Create
///       - A complete netherite diving suit (Create) now provides complete resistance to block-related temperature (like lava!)
///             - It also gives 100% resistance to overheating, so your temperature will not increase
///             - These effects last until the backtank is empty
///       - The backtank will now drain air (and show the time indicator) when the player is in a hot environment
///       - This leverages the new entity predicate system added in this update
/// - Added Primal Winter support by default
/// * (1.20) Added Weather, Storms & Tornadoes (Weather2) support
/// * Aquamirae three-bold armor set now provides some freezing protection, as well as water immunity
/// * Added support for Spoiled:
///       - Placing food items in the icebox will stop food spoilage as long as it is fueled
///       - This will gradually drain fuel from the icebox
/// - Added support for using a Create display link on the thermolith
///       - The current world temperature will be displayed
///       - A field will be added upon placing the display link that allows the user to define the temperature units
///
/// Misc. Changes:-
/// * Added "Cold Sweat: Insulators" creative tab
///       - Contains all items that can be used as insulation, or provide insulation when worn
/// * Shift-clicking an armor item from the output slot of the sewing table now applies all available insulation at once
/// - Removed the Insulated effect from the list of top-tier beacon effects
/// - The inside of naturally-generated igloos is now habitable
/// - The soulspring lamp can now be given one fuel item at a time by right-clicking
/// - Improved the output formatting for the /temp debug command
/// - Made heatstroke camera swaying more aggressive
/// - Increased the duration of the soul sprout's effect to 1 minute
/// - The soulspring lamp now emits particles while in use to help signify its area of effect
/// - Lava cauldrons now emit heat by default
///       ! This will not take effect until the config setting for block temperatures is reset
/// - Reduced the food value of soul sprouts to 3 (from 4), and the saturation to 0.5 (from 1)
///
///
/// Config Changes:
/// - Due to many configs requiring access to world registries, the Cold Sweat config button is now disabled in the main menu
/// * Config settings can now accept a comma-separated list of biomes, dimensions, or structures to apply the same settings to multiple things
/// * The following config settings now have a configurable range of intensity, instead of just on/off:
///       - Freezing hearts: Ranges from 0 to 1, with 1 freezing all hearts
///       - Chilled mining speed: Ranges from 0 to 1, with 1 preventing the player from mining completely
///       - Chilled movement speed: Ranges from 0 to 1, with 1 preventing the player from moving completely
///       - Chilled knockback reduction: Ranges from 0 to 1, with 1 nullifying the player's knockback to other entities
///       - Heatstroke fog: Ranges from 0 to infinity; Controls the distance of the fog, with infinity disabling it altogether
///       * These settings now have entries in the config menu as well
/// * Added config option for setting the temperature of natural structures
///       - This controls the world temperature when the player is inside the structure
/// * Added config option for armor insulation slots to dynamically scale based on protection value:
///       - There are 4 modes: Static (no scaling), Linear, Exponential, and Logarithmic
///       - A maximum number of slots can be set to prevent excessive scaling
/// - Added config option to allow certain blocks to be slept on regardless of the outside temperature
/// - The temperature rate config setting now affects how fast waterskins heat/cool in the boiler and icebox
/// * Most item configs now support defining NBT tags (i.e. {Color:"blue"}).
///       - Item configs with NBT tags defined will require the specified NBT data to be present on the item
/// * Added the option for food items to change the player's temperature for a period of time, like soul sprouts
/// - Moved the "Cold Soul Fire" and "Check Sleep Conditions" settings to world-settings.toml
/// - Renamed some too-verbose config settings/sections
/// - Added new config option to smooth out how rapidly changing temperatures are represented by the world temperature gauge
///       - In other words, the gauge won't jitter as much when the player is moving around near heat sources
///       - This option is defaulted to 10, double the smoothing of previous versions
/// * Added option to hide individual UI elements in the config menu
/// - Altered/touched up the config screen in a few other areas
/// - The soulspring lamp can now be given one fuel item at a time by right-clicking
/// - Added structure temperature offset config option
/// - Added config setting to change the drop rate of chameleon molt
///
///
/// Technical Changes:
/// * Added support for defining config settings using JSON:
///       - This is a more modular way of configuring things, without having to add them to the config files
///       * It can be defined by mod developers in the data files, or via datapacks:
///             - Insulation: data/<yourmod>/cold_sweat/item/insulator
///             - Fuel items: .../item/fuel
///             - Food temperatures: .../item/food
///             - Block Temps: .../block/block_temp
///             - Biome Temps: .../world/biome_temp
///             - Dimension Temps: .../world/dimension_temp
///             - Structure temperatures: .../world/structure_temp
///             - Temperature regions: .../world/depth_temp
///             - Insulating mounts: .../entity/mount
///             - Entity spawn biomes: .../entity/spawn_biome
///       * It can also be placed in the user config folder (config/coldsweat/data/*) to be globally applied (i.e. config/coldsweat/data/insulator)
///       * Insulation items have additional functionality when defined in JSON:
///             - They can now contain entity predicates:
///                 - Insulation items with a predicate will only provide insulation if the wearer has the specified data
///                       - Deactivated insulation items can still be equipped, but will not provide insulation or show in the tooltip
///                       - This is a new system, but is structured like a Vanilla entity predicates with some slight tweaks
///             - They can now give attribute modifiers:
///                   - Attribute modifiers will be applied when the insulation item is on a piece of armor
///                   - Any attributes (even Vanilla and modded ones) are supported
///       * Config settings have a set order of precedence depending on how they are defined:
///             - user datapacks (in config folder) > datapacks (from mods or worlds) > traditional configs (.toml files)
///       ! Datapack configs are read slightly differently in 1.16:
///             - Data files are kept in data/cold_sweat/config/* instead of data/<yourmod>/cold_sweat/*
///                   - Otherwise, the internal structure is the same
///                   ! Datapacks in world folders still can't be read by this system. The alternative is to put datapack configs in configs/coldsweat/data/*
///       * More info on this system is available on the mod's documentation site; a link to which is on the CurseForge or Modrinth page
/// * Insulation items can now affect non-player entities
///       - If an insulation item has special properties, like attribute modifiers, it will now apply to any entity, not just players
/// * Insulation items can now make entities immune to certain TempModifiers
///       - This reduces the efficacy of the specified TempModifiers on the entity
///       - The the entity requirement must be met for immunity to be applied
/// * Added block tags for hearth spread whitelist/blacklist (cold_sweat:tags/block/hearth/spread_whitelist and spread_blacklist)
/// * Added block tag for blocks that should ignore sleeping conditions (cold_sweat:tags/block/ignore_sleep_check) (ignore the "Check Sleeping Conditions" setting)
/// * Added item tag for non-insulatable armor items (cold_sweat:tags/item/not_insulatable)
/// * Added effect tag for blacklisted potion effects in the hearth (cold_sweat:tags/mob_effect/hearth_blacklisted)
/// * Temperature.Type and Ability have been merged together into Trait
///       - These two things were functionally identical anyway, so separating them only made them more annoying to work with
///       - This also means that previous Temperature.Ability types can now have TempModifiers applied to them
/// - The TempModifier registration system has been slightly changed:
///       - The ID for TempModifiers is no longer provided by a method in the TempModifier class
///       - IDs are now assigned at registration via ResourceLocations like most other things
/// - The Temperature.addModifier() method will now add/replace for every TempModifier instance that matches the given predicate if allowDupes is true
/// * All methods that add/remove/replace TempModifiers now have more advanced options for handling duplicates:
///       - This replaces the old "allowDupes" parameter
///       - ALLOW: Allows duplicates of this TempModifier to exist on the entity
///       - BY_CLASS: Adding/replacing a TempModifier fails if one of the same class is already present
///       - EXACT: Adding/replacing a TempModifier fails if there is an instance of the same class and matching NBT present
///       ! The old methods with the "allowDupes" parameter are still present, but are now deprecated and marked for removal in a future version
/// - Replaced TempModifierEvent.Calculate.Pre and Post with more useful events:
///       - Override: Fires before the TempModifier is calculated, and allows the incoming temperature to be changed
///             - If the event is canceled, the TempModifier's output will be overridden with the event's temperature until it is calculated again
///             - Otherwise, the event's temperature is passed into the TempModifier for calculation
///       - Modify: Fires after the TempModifier is calculated, and allows the resulting function to be modified
///             - The event is not cancelable
/// * Some config settings now require a RegistryAccess when saving, loading, or getting (see ConfigSettings.java)
/// * Added an automatic config updater, which will change existing config settings when updating to new versions
///      - This prevents outdated config settings causing new features to not work as intended
///      - This system will be used very spar