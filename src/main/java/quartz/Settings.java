package quartz;

import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private final Properties prop = new Properties();

    public int getValues(String key) {
        return Integer.parseInt(this.prop.getProperty(key));
    }

    public void load(InputStream input)  {
        try {
            this.prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
