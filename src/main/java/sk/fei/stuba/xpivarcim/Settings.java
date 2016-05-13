package sk.fei.stuba.xpivarcim;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Configuration
@PropertySource("classpath:settings.properties")
public class Settings {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public final static FileAttribute<Set<PosixFilePermission>> ATTRS =
            PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));

    @Value("${operations.dir}")
    public String opDir;

    @Value("${unit.prototypes.dir}")
    public String unitProtoDir;

    @Value("${unit.c.dir}")
    public String cUnitDir;

    @Value("${unit.java.dir}")
    public String javaUnitDir;

}
