import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import sun.jvm.hotspot.tools.jcore.ClassFilter;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.tools.jcore.ClassDump;

public class MyFilter implements ClassFilter {
    static String[] includeClasses = null;
    static String[] excludeClasses = null;

    static {
        Properties cfgKv = new Properties();
        try {
            cfgKv.load(MyFilter.class.getResourceAsStream("filter.cfg"));
            String includeClassesCfg = cfgKv.getProperty("includeClasses");
            if (includeClassesCfg != null) {
                includeClasses = includeClassesCfg.split(",");
                System.out.println("include classes: " + Arrays.asList(includeClasses));
            } else {
                System.err.println("includeClasses is not configured in filter.cfg");
            }

            String excludeClassesCfg = cfgKv.getProperty("excludeClasses");
            if (excludeClassesCfg != null) {
                excludeClasses = excludeClassesCfg.split(",");
                System.out.println("exclude classes: " + Arrays.asList(excludeClasses));
            } else {
                System.err.println("excludeClasses is not configured in filter.cfg");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean canInclude(InstanceKlass kls) {
        String klassName = kls.getName().asString();
        if (includeClasses != null) {
            if (excludeClasses != null) {
                for (String exclude : excludeClasses) {
                    if (klassName.startsWith(exclude)) {
                        System.out.println("exclude class: " + klassName + ", for exclude config: " + exclude);
                        return false;
                    }
                }
            }

            for (String include : includeClasses) {
                if (klassName.startsWith(include)) {
                    System.out.println("begin to dump class: " + klassName);
                    return true;
                }
            }
        }

        return false;
        /*return klassName.startsWith("java/lang/Exception")
                || klassName.startsWith("java/lang/Throw")
                || klassName.startsWith("java/net/Socket/")
                || klassName.startsWith("com/yourkit");*/
    }
}  
