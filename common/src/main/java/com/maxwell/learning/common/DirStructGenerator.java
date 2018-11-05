package com.maxwell.learning.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.stream.Stream;

/************************************************************************************
 * 功能描述：
 *
 * 目录结构生成工具
 *
 *
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2018年01月31日 --  下午1:42 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class DirStructGenerator {

    private static final String ENDPOINT_END = "└──";
    private static final String ENDPOINT = "├──";
    private static final String SPACE = "   ";
    private static final String CONNECT_LINE = "│";
    private static final String LINE_BREAKS = "\n";

    public static GenerateConfig config = new GenerateConfig();

    private static String outputFileName = "folder_struct.md";

    public static void generate(String folder) {
        if(!new File(folder).exists()){
            throw new RuntimeException("目标目录不存在，请检查输入的目录");
        }

        StringBuilder res = new StringBuilder();
        if (config.includeRoot) {
            res.append(folder.substring(folder.lastIndexOf("/") + 1) + LINE_BREAKS);
        }
        walk(res, folder, "", false);
        output(res.toString(), folder);
    }

    /**
     * 输出
     * <p>
     * 默认输出到生成结构的目录中
     *
     * @param res
     * @param folder
     */
    private static void output(String res, String folder) {

        System.out.println(res);

        Path path = Paths.get(folder, outputFileName);
        if (config.outputPath != null && config.outputPath.length() > 0) {
            path = Paths.get(config.outputPath);
        }

        try {
            Files.write(path, res.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 递归打印
     *
     * @param folder
     * @param prefix
     * @param isLastFolder
     */
    private static void walk(StringBuilder res, String folder, String prefix, boolean isLastFolder) {
        File dir = new File(folder);
        if (dir.exists()) {
            File[] files = filterAndSort(dir.listFiles());
            int size = files.length;
            for (int i = 0; i < size; i++) {
                File file = files[i];
                String filename = file.getName();
                boolean isLast = i == size - 1;

                String pref = prefix;
                if (isLastFolder && prefix.contains(CONNECT_LINE)) {
                    pref = replace(prefix, prefix.lastIndexOf(CONNECT_LINE), ' ');
                }

                //输出
                res.append(spell(filename, pref, isLast) + LINE_BREAKS);
                //对目录进行递归
                if (file.isDirectory()) {
                    pref = pref + CONNECT_LINE + SPACE;
                    walk(res, folder + "/" + filename, pref, isLast);
                }
            }
        }
    }

    /**
     * 根据配置，对文件或文件夹过滤
     *
     * @param files
     * @return
     */
    private static File[] filterAndSort(File[] files) {
        Stream<File> stream = Stream.of(files);

        if (config.notIncludeBySuffix != null && config.notIncludeBySuffix.length > 0) {
            for (String suffix : config.notIncludeBySuffix) {
                stream = stream.filter(file -> !file.getName().endsWith(suffix));
            }
        }

        if (!config.includeHiddenFiles) {
            stream = stream.filter(file -> !file.getName().startsWith("."));
        }

        if (config.notIncludeByPrefix != null && config.notIncludeByPrefix.length > 0) {
            for (String suffix : config.notIncludeByPrefix) {
                stream = stream.filter(file -> !file.getName().startsWith(suffix));
            }
        }

        if (config.sortedByFileName) {
            stream = stream.sorted(Comparator.comparing(File::getName));
        } else {
            stream = stream.sorted((file1, file2) -> {
                int a = file1.isFile() ? 1 : 0;
                int b = file2.isFile() ? 1 : 0;
                return a - b;
            });
        }


        return stream.toArray(File[]::new);
    }

    /**
     * 拼接行
     *
     * @param filename
     * @param prefix
     * @param isLast
     * @return 每一行的数据
     */
    private static String spell(String filename, String prefix, boolean isLast) {
        String str = prefix;
        if (isLast) {
            str += ENDPOINT_END + filename;
        } else {
            str += ENDPOINT + filename;
        }
        return str;
    }

    /**
     * 将字符串str中index位置处的字符替换为replace
     *
     * @param str
     * @param index
     * @param replace
     * @return
     */
    private static String replace(String str, int index, char replace) {
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return new String(chars);
    }


    private DirStructGenerator() {
    }

    /**
     * 配置
     */
    static class GenerateConfig {

        private String[] notIncludeByPrefix;
        private String[] notIncludeBySuffix;
        private String outputPath;
        private boolean includeHiddenFiles;
        private boolean includeRoot;
        private boolean sortedByFileName;

        public GenerateConfig() {
            includeRoot = true;
            sortedByFileName = false;
            includeHiddenFiles = false;
        }

        private String[] getNotIncludeByPrefix() {
            return this.notIncludeByPrefix;
        }

        public GenerateConfig notIncludeByPrefix(String[] notIncludeByPrefix) {
            this.notIncludeByPrefix = notIncludeByPrefix;
            return this;
        }

        private String[] getNotIncludeBySuffix() {
            return this.notIncludeBySuffix;
        }

        public GenerateConfig notIncludeBySuffix(String[] notIncludeBySuffix) {
            this.notIncludeBySuffix = notIncludeBySuffix;
            return this;
        }

        private boolean isIncludeRoot() {
            return this.includeRoot;
        }

        public GenerateConfig includeRoot(boolean includeRoot) {
            this.includeRoot = includeRoot;
            return this;
        }

        private boolean isSortedByFileName() {
            return this.sortedByFileName;
        }

        public GenerateConfig sortedByFileName(boolean sortedByFileName) {
            this.sortedByFileName = sortedByFileName;
            return this;
        }

        private String getOutputPath() {
            return this.outputPath;
        }

        public GenerateConfig outputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        private boolean isIncludeHiddenFiles() {
            return this.includeHiddenFiles;
        }

        public GenerateConfig includeHiddenFiles(boolean includeHiddenFiles) {
            this.includeHiddenFiles = includeHiddenFiles;
            return this;
        }
    }
}
