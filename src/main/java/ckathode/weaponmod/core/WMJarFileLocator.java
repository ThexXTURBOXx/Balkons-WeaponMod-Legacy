package ckathode.weaponmod.core;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WMJarFileLocator extends AbstractJarFileLocator {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<ModFile> scanMods() {
        try {
            URL url = WMJarFileLocator.class.getProtectionDomain().getCodeSource().getLocation();
            Path path = url != null && Paths.get(url.toURI()).getFileName().toString().toLowerCase().endsWith(".jar")
                    ? Paths.get(url.toURI()) : null;
            ModFile modFile = new ModFile(path, this);
            this.modJars.compute(modFile, (mf, fs) -> this.createFileSystem(mf));
            return Collections.singletonList(modFile);
        } catch (Throwable t) {
            LOGGER.error("Error scanning for BWM:L!", t);
        }
        return Collections.emptyList();
    }

    @Override
    public String name() {
        return "weaponmod_locator";
    }

    @Override
    public void initArguments(Map<String, ?> map) {
    }

}
