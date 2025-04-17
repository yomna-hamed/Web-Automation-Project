package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsUtils {
    public static Logger logger = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString());
}
