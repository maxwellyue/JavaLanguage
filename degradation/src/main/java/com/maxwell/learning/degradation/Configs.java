package com.maxwell.learning.degradation;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Properties;

/**
 * 监听配置文件的变化
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Configs {

    public static Properties properties;

    static {

        final String configFilename = "app.properties";
        final ClassPathResource resource = new ClassPathResource(configFilename);

        try {
            //先加载一次配置文件
            properties = PropertiesLoaderUtils.loadProperties(resource);

            //设置监听配置文件所在目录的文件删除/新增操作
            final WatchService watcher = FileSystems.getDefault().newWatchService();
            Paths.get(resource.getFile().getParent()).register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            //开启监听线程
            Thread watchThread = new Thread(() -> {
                try {
                    WatchKey watchKey = watcher.take();
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        //如果是配置文件发生变化，则重新加载配置文件
                        System.out.println(event.kind() + ":::" + event.context().toString());
                        if (Objects.equals(configFilename, event.context().toString())) {
                            properties = PropertiesLoaderUtils.loadProperties(resource);
                            break;
                        }
                    }
                    watchKey.reset();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            watchThread.setDaemon(true);
            watchThread.start();

            //JVM停止时，关闭watcher线程
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    watcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(String key) {
        String property = properties.getProperty(key, "false");
        return "true".equals(property);
    }

    public static int getInt(String key) {
        String property = properties.getProperty(key, "0");
        return Integer.valueOf(property);
    }
}
