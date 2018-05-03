package com.maxwell.learning.designpattern.resposibilitychain;

import com.maxwell.learning.designpattern.resposibilitychain.handler.ContentHandler;
import com.maxwell.learning.designpattern.resposibilitychain.handler.NameHandler;
import com.maxwell.learning.designpattern.resposibilitychain.handler.NoopHandler;
import com.maxwell.learning.designpattern.resposibilitychain.handler.TimeHandler;

import java.util.Date;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {


    /**
     * 输出如下
     * <p>
     * [origin] Target{name='old name', content='old content', time=Thu May 03 20:35:39 CST 2018}
     * [NameHandler][order: 1][method: preHandle][return: true]
     * [NameHandler][order: 1][method: handle][origin: old name][now: new name]
     * [TimeHandler][order: 2][method: preHandle][return: true]
     * [TimeHandler][order: 2][method: handle][origin: 2018-5-3 20:35:39][now: 2018-5-4 20:35:39]
     * [ContentHandler][order: 3][method: preHandle][return: true]
     * [ContentHandler][order: 3][method: handle][origin: old content][now: new content]
     * [NoopHandler][order: 4][method: preHandle][return: false]
     * [ContentHandler][order: 3][method: afterCompletion]
     * [TimeHandler][order: 2][method: afterCompletion]
     * [NameHandler][order: 1][method: afterCompletion]
     * [new] Target{name='new name', content='new content', time=Fri May 04 20:35:39 CST 2018}
     *
     * @param argus
     */
    public static void main(String[] argus) {

        Chain chain = new Chain();
        chain.add(new TimeHandler());
        chain.add(new ContentHandler());
        chain.add(new NoopHandler());
        chain.add(new NameHandler());


        Target target = new Target();
        target.setTime(new Date());
        target.setName("old name");
        target.setContent("old content");
        System.out.println(String.format("[origin] %s", target.toString()));

        chain.execute(target);
        System.out.println(String.format("[new] %s", target.toString()));

    }


}
