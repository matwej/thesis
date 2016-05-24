package sk.fei.stuba.xpivarcim.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "general")
public class Settings {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public final static FileAttribute<Set<PosixFilePermission>> ATTRS =
            PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));

    private String operationsDir;
    private String prototypesDir;
    private int unitTimeout;
    private int runTimeout;

    public String getOperationsDir() {
        if(System.getenv("TESTER_OP_DIR") != null)
            return System.getenv("TESTER_OP_DIR");
        else return operationsDir;
    }

    public String getPrototypesDir() {
        if(System.getenv("TESTER_PROTO_DIR") != null)
            return System.getenv("TESTER_PROTO_DIR");
        else return prototypesDir;
    }

    public int getUnitTimeout() {
        return unitTimeout;
    }

    public int getRunTimeout() {
        return runTimeout;
    }

    public int getUnitTimeoutMilis() {
        return getUnitTimeout()*1000;
    }

    public void setOperationsDir(String operationsDir) {
        this.operationsDir = operationsDir;
    }

    public void setPrototypesDir(String prototypesDir) {
        this.prototypesDir = prototypesDir;
    }

    public void setUnitTimeout(int unitTimeout) {
        this.unitTimeout = unitTimeout;
    }

    public void setRunTimeout(int runTimeout) {
        this.runTimeout = runTimeout;
    }
}
