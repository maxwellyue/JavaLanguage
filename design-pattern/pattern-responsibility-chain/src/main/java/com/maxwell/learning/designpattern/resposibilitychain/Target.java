package com.maxwell.learning.designpattern.resposibilitychain;

import java.util.Date;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Target {

    private String name;
    private String content;
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Target{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
