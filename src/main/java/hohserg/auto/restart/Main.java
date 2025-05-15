package hohserg.auto.restart;

import java.time.LocalDateTime;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "auto_restart", name = "AutoRestart", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public class Main {


    private static final int oneMinute = 20 * 60;
    private static final int tenMinuted = oneMinute * 10;

    private static int tick = 0;
    private static int ticksPeriod = tenMinuted;

    private static LocalDateTime restartTime;
    private static LocalDateTime warningTime_1Min;
    private static LocalDateTime warningTime_5Min;
    private static LocalDateTime warningTimeFirst;
    private static LocalDateTime increaseCheckFrequencyTime;

    private static void initTimes() {
        if (restartTime == null) {
            restartTime = LocalDateTime.now().plusHours(6);

            warningTimeFirst = restartTime.minusMinutes(Confiiguration.countdown);
            warningTime_1Min = restartTime.minusMinutes(1);
            warningTime_5Min = restartTime.minusMinutes(5);
            increaseCheckFrequencyTime = restartTime.minusMinutes(Confiiguration.countdown * 2);
        }
    }

    @SubscribeEvent
    public static void autoRestartTick(TickEvent.ServerTickEvent event) {
        tick++;
        if (tick >= ticksPeriod) {
            tick = 0;
            checkForRestart();
        }
    }

    private static boolean periodChanged = false;
    private static boolean warningSended = false;
    private static boolean warningSended_1Min = false;
    private static boolean warningSended_5Min = false;
    private static boolean restart = false;

    private static void checkForRestart() {
        initTimes();

        LocalDateTime now = LocalDateTime.now();
        if (!periodChanged && now.isAfter(increaseCheckFrequencyTime)) {
            periodChanged = true;
            ticksPeriod = oneMinute;
        }
        if (!warningSended && now.isAfter(warningTimeFirst)) {
            warningSended = true;
            String msg = TextFormatting.RED + Confiiguration.warning;
            sendTitleToAll(msg);

            System.out.println(msg);
        }

        if (!warningSended_5Min && now.isAfter(warningTime_5Min)) {
            warningSended_5Min = true;
            String msg = TextFormatting.RED + Confiiguration.warning5;
            sendTitleToAll(msg);

            System.out.println(msg);

        }

        if (!warningSended_1Min && now.isAfter(warningTime_1Min)) {
            warningSended_1Min = true;
            String msg = TextFormatting.RED + Confiiguration.warning1;
            sendTitleToAll(msg);

            System.out.println(msg);

        }

        if (!restart && now.isAfter(restartTime)) {
            restart = true;
            executeCommandByServer(Confiiguration.restartCommand);
        }
    }

    private static void executeCommandByServer(String command) {
        MinecraftServer server = DimensionManager.getWorld(0).getMinecraftServer();
        server.getCommandManager().executeCommand(server, command);
    }

    private static void sendTitleToAll(String msg) {
        executeCommandByServer("title @a title \"" + msg + "\"");
    }
}
