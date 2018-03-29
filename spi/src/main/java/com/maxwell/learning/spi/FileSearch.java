package com.maxwell.learning.spi;

import java.util.Arrays;
import java.util.List;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yueyuemax@gmail.com
 * 创建时间： 2018年03月29日 --  下午6:37 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class FileSearch implements Search {

    @Override
    public List<String> search(String keyword) {
        System.out.println("文件搜索 " + keyword);
        return Arrays.asList("file_res1", "file_res2");
    }
}
