package quartz;

import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private final Properties prop = new Properties();

    public String getValues(String key) {
        System.out.println(this.prop.getProperty(key));
        return this.prop.getProperty(key);
    }

    public void load(InputStream input)  {
        try {
            this.prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
