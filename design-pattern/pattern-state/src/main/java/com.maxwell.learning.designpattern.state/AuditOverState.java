package com.maxwell.learning.designpattern.state;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class AuditOverState implements LeaveRequestState {
    @Override
    public void process(StateMachine stateMachine) {

        LeaveRequestModel model = (LeaveRequestModel)stateMachine.getBusinessVO();

        System.out.println("请假审批结束，审批结果：" + model.getResult());
    }
}
