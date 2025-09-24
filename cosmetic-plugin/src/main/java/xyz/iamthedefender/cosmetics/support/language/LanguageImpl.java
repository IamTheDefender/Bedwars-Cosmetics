package xyz.iamthedefender.cosmetics.support.language;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.api.handler.ILanguage;

import java.io.File;
import java.util.List;

@Getter
public class LanguageImpl implements ILanguage {

    private final File messagesFile;
    protected YamlConfiguration config;

    public LanguageImpl(File messagesFile) {
        this.messagesFile = messagesFile;

        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            try {
                messagesFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void save() {
        try {
            config.save(messagesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(messagesFile);
    }

    @Override
    public String getMessage(Player player, String path) {
        return config.getString(path);
    }

    @Override
    public List<String> getMessageList(Player player, String path) {
        return config.getStringList(path);
    }

    @Override
    public void saveIfNotExists(String path, Object data) {
        if (!config.contains(path)) {
            config.set(path, data);
            save();
        }
    }
}
