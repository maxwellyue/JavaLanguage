package com.maxwell.learning.designpattern.state;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {
        LeaveRequestModel model = new LeaveRequestModel();
        model.setUser("小明");
        model.setBeginDate("2018-04-01");
        model.setLeaveDays(10);

        System.out.println("小明请假...");

        StateMachine context = new LeaveRequestContext();
        context.setBusinessVO(model);
        context.setState(new ProjectManagerState());
        context.process();

        context.process();

    }



}
