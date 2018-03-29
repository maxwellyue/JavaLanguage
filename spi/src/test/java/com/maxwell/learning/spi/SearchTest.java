package com.maxwell.learning.spi;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yueyuemax@gmail.com
 * 创建时间： 2018年03月29日 --  下午6:47 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
class SearchTest {

    @Test
    public void searchTest(){
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = s.iterator();
        while (iterator.hasNext()) {
            Search search =  iterator.next();
            List<String> res = search.search("hello");
        }
    }

}