import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UtilLoggingConfiguration {
    private static final Logger LOGGER = Logger.getLogger(UtilLoggingConfiguration.class.getName());
    static {
        try {
            InputStream stream = UtilLoggingConfiguration.class.getClassLoader()
                    .getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void log(Level level, String msg) {
        LOGGER.log(level, msg);
    }
}
