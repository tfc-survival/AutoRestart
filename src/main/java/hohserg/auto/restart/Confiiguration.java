package hohserg.auto.restart;

import net.minecraftforge.common.config.Config;

@Config(modid = "auto_restart")
public class Confiiguration {
    @Config.Comment({"Отсчет до рестарта в минутах", "За {countdown} минут до рестарта игроки будут предупреждены"})
    public static int countdown = 17;

    @Config.Comment({"Время рестарта сервера", "чч:мм", "чч - от 00 до 23", "мм - от 00 до 59"})
    public static String restartTime = "04:00";

    @Config.Comment("Сообщение предупреждения о рестарте за {countdown} минут")
    public static String warning = "Хуяйп через 17 минут!";

    @Config.Comment("Сообщение предупреждения о рестарте за 5 минут")
    public static String warning5 = "Рестарт через 5 минут!";

    @Config.Comment("Сообщение предупреждения о рестарте за 1 минуту")
    public static String warning1 = "Рестарт через 1 минуту!";

    @Config.Comment({"Команда останавливающая сервер", "Перезапуск через цикличный скрипт"})
    public static String restartCommand = "stop";
}
