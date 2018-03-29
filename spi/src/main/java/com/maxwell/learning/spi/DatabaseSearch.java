package com.maxwell.learning.spi;

import java.util.Arrays;
import java.util.List;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yueyuemax@gmail.com
 * 创建时间： 2018年03月29日 --  下午6:42 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class DatabaseSearch implements Search {
    @Override
    public List<String> search(String keyword) {
        System.out.println("数据搜索 " + keyword);
        return Arrays.asList("db_res1", "db_res2");
    }
}
