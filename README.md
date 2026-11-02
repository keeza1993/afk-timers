# AFK Timers

![AFK Timers logo](docs/images/afk-timers-logo.png)

RuneLite plugin created by **Th3keeza0**, from **OSRS geezers**. Translucent coloured timer bars above the inventory, with individual effect switches and low-duration flashing.

## Open and run

1. Extract this ZIP somewhere permanent, such as Documents\AFK-Timers.
2. In IntelliJ IDEA choose Open and select the AFK-Timers folder containing build.gradle.
3. Select Java 17 as the project/Gradle JVM. Allow Gradle to finish loading.
4. Open src/test/java/com/afktimers/AfkTimersLauncher.java and run its main method. Alternatively open a terminal in this folder and run .\gradlew.bat run.
5. In the development RuneLite client, search the plugin settings for AFK Timers and enable it.
6. Enable Preview bars in its settings to see sample bars. Hold Alt and drag the group above the inventory. Disable Preview bars to show live data.

Your normal RuneLite client does not load arbitrary plugin JARs. The included JAR is a compiled development artifact; this project has not been submitted to the Plugin Hub. Launch through the supplied development launcher for now.

## Controls and appearance

- Hold Alt and drag the group. RuneLite saves the overlay position through its normal overlay system.
- Alt + right-click resets an overlay's position using RuneLite's normal controls.
- Bars start above the inventory and adjust to their initial content until moved. Saved custom positions remain fixed.
- Bars move together as one stack and grow upwards from a saved bottom anchor.
- Background and fill opacity defaults to 75%; icons and text stay clear.
- Set width, height and opacity in the plugin settings.
- Antifire is purple, antivenom is dark green, prayer is blue, and saturated heart is cyan. Main effect colours have overrides.
- Bars flash below 10% of their tracked duration; prayer flashes below 10% of base prayer points. Flashing can be disabled.
- Switch each protection effect, divine effect, heart timer or boosted skill on/off separately.

## Included effects

- Antifire and super antifire, including extended durations reported through the same game timers.
- Venom immunity and poison immunity as separate bars. Poison immunity remaining does not imply venom immunity remaining.
- A single shared imbued/saturated heart cooldown bar.
- Divine attack, strength, defence, ranged, magic, combat, bastion and battlemage effects.
- Goading potion aggression timer.
- Normal stamina and extended stamina duration from the ring of endurance.
- Prayer points and an estimated time to empty after the plugin observes prayer drain.
- Ordinary positive boosts for each supported skill, with a per-skill toggle. These show the boosted amount and approximate time until the next decrease, not a guaranteed total expiry time. Icons/colours are representative for the skill rather than identification of every food or potion consumed.

## Accuracy and first-version limits

Protection and divine/heart timers read game state. Some game timers update in coarse intervals: antifire in 18-second steps, super antifire in 12-second steps, heart cooldown in 6-second steps, and immunity in 18-second steps. The display counts down between updates, but enabling the plugin or logging in partway through an interval can leave an offset of up to one interval. The initial observed duration is used as the bar maximum if it exceeds the standard duration.

Ordinary stat decreases share an estimated restoration cycle. The plugin learns its phase when it observes a boosted skill decrease by one. Before then, it shows an approximate full cycle. Preserve is estimated using a 90-second cycle while active; activation delays and special boost mechanics may differ. Divine and saturated magic boosts are represented by their dedicated timers instead of duplicate ordinary skill bars. Special activities that repeatedly reapply boosts (for example overloads), spicy stew restoration behaviour, and every individual consumable are not separately modelled in this version.

Prayer drain is measured while the active prayer selection is unchanged. Its estimate resets when prayers change, equipment changes, prayer is restored, or the player logs out. Flicking, external drain and changes to drain mechanics can affect the estimate. While enough data is not available the bar says estimating. With no active prayer it shows points only.

This is the first development build. Live verification is still needed for login recovery, all potion variants, Preserve transitions, Alt-drag persistence, fixed/resizable inventory layouts and flashing appearance. No game actions are automated.

## Build and validation

Standard commands: .\gradlew.bat test jar, and .\gradlew.bat run.

Built using Java 17 targeting Java 11 bytecode, against the locally available RuneLite 1.13.3 API. Main sources and the development launcher compile. Five JUnit tests pass, including upward stack anchoring: immunity boundaries, damage versus immunity, fraction clamping, and tick/time formatting.

The restricted build session could not connect to a Gradle daemon, so compilation and the JUnit run were performed directly using the installed JDK and cached dependencies. A normal Gradle run should still be checked on your machine. The Gradle wrapper uses the same 9.6.0 version as the existing development setup.

## Welcome and clan

The first-enable welcome introduces the plugin and explains Alt + drag, upwards stacking, preview mode and display settings. Reopen it using Show welcome in the plugin settings.

Want to join **OSRS geezers**? Add **Th3keeza0** in game or [join our Discord](https://discord.gg/UAAe6QEGD).

The Discord button only opens a browser after you click it. No game actions are automated.

The logo combines a stopwatch with the clan's OSRS GEEZAS artwork.
