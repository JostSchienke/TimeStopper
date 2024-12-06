package eng.jes_ster.timeStopper;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.Instrument;
import org.bukkit.Note;

import java.util.HashMap;
import java.util.UUID;

public final class TimeStopper extends JavaPlugin implements Listener {
    private final HashMap<UUID, Integer> playerTimers = new HashMap<>();
    private final HashMap<UUID, Integer> taskIds = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Time Stopper is Running!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Time Stopper is Disabled!");
        for (int taskId : taskIds.values()) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        switch (command.getName().toLowerCase()) {
            case "timerstart":
                startTimer(player, playerId);
                break;

            case "timerstop":
                stopTimer(player, playerId);
                break;

            case "timerreset":
                resetTimer(player, playerId);
                break;

            default:
                return false;
        }
        return true;
    }

    private void startTimer(Player player, UUID playerId) {
        if (taskIds.containsKey(playerId)) {
            player.sendMessage("Your timer is already running.");
            return;
        }

        player.sendMessage("Starting your timer...");
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            int timeInSeconds  = playerTimers.getOrDefault(playerId, 0);
            playerTimers.put(playerId, timeInSeconds + 1);

            String timeFormatted = formatTime(timeInSeconds);

            sendActionBar(player, timeFormatted);
        }, 0L, 20L);

        taskIds.put(playerId, taskId);
    }

    private void stopTimer(Player player, UUID playerId) {
        if (!taskIds.containsKey(playerId)) {
            player.sendMessage("You don't have a running timer.");
            return;
        }

        int remainingTime = playerTimers.getOrDefault(playerId, 0);

        Bukkit.getScheduler().cancelTask(taskIds.get(playerId));
        taskIds.remove(playerId);

        String formatedtime = formatTime(remainingTime);

        player.sendMessage("Your Time: " + formatedtime);
    }

    private void resetTimer(Player player, UUID playerId) {
        stopTimer(player, playerId);
        playerTimers.remove(playerId);
        player.sendMessage("Reset your timer.");
    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
    }

    @EventHandler
    private void PlayerPicksUpExpierience(PlayerExpChangeEvent event){
        Player player = event.getPlayer();

        if(taskIds.containsKey(player.getUniqueId())){
            player.setGameMode(GameMode.SPECTATOR);
            PlayBellScale(player, player.getUniqueId());
            stopTimer(player, player.getUniqueId());
            playerTimers.remove(player.getUniqueId());
        }
    }

    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        // Format as "hh:mm:ss"
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    void PlayBellScale(Player player, UUID playerId) {
        Note[] scale = {
                Note.natural(0, Note.Tone.F),
                Note.natural(0, Note.Tone.E)
        };

        for (int i = 0; i < scale.length; i++) {
            final int currentIndex = i; // Create a final copy of `i`
            int delay = currentIndex * 5; // Calculate delay based on the current index

            getLogger().info("now playing: " + scale[currentIndex].toString());

            // Schedule the note to play with a delay
            Bukkit.getScheduler().runTaskLater(this, () -> {
                player.playNote(player.getLocation(), Instrument.BELL, scale[currentIndex]);
            }, delay);
        }
    }
}
