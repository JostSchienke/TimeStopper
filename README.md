# Timer Stopper Plugin

**Time Stopper Plugin** adds a unique gameplay mechanic to your Minecraft server: a personal timer for every player, displayed in their action bar. Players can challenge themselves to beat the timer by picking up an XP orb, which stops the timer and automatically switches them to Spectator Mode.

## Features
- **Start Timer**: Use `/timerstart` to begin a personal timer for a player.
- **Stop Timer**: Manually stop the timer with `/timerstop`.
- **Reset Timer**: Reset the timer with `/timerreset`.
- **Automatic Action**: The timer stops automatically when the player picks up an XP orb, and they are switched to Spectator Mode.

## Commands
| Command         | Description                                   |
|-----------------|-----------------------------------------------|
| `/timerstart`   | Starts the timer for the player.             |
| `/timerstop`    | Stops the timer for the player manually.     |
| `/timerreset`   | Resets the timer for the player.             |

## Installation
1. Download the plugin `.jar` file.
2. Place it in the `plugins` folder of your Minecraft server.
3. Restart or reload the server to enable the plugin.

## Usage
1. Start the timer for a player using `/timerstart`.
2. The timer will display in their action bar and continue running until:
   - They pick up an XP orb (stops the timer and switches them to Spectator Mode).
   - The timer is manually stopped using `/timerstop`.
3. Reset the timer at any time using `/timerreset`.

## Compatibility
- Minecraft Version: `1.21.4`
- Requires a Spigot server.

## License
This project is licensed under the **Apache License 2.0**.  
You can find the full text of the license [here](LICENSE).

---

Enjoy the Timer Challenge Plugin! 🎉
