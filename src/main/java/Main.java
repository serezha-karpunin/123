import jmx.Example;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws NotCompliantMBeanException,
            InstanceAlreadyExistsException,
            MBeanRegistrationException,
            MalformedObjectNameException, InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("jmx:type=Example");
        Example example = new Example();
        mbs.registerMBean(example, name);

        System.out.println("Wow");
        Thread.sleep(Long.MAX_VALUE);
    }
}
