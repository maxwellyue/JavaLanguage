package com.maxwell.learning.designpattern.state;

import java.util.Scanner;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DepManagerState implements LeaveRequestState {

    @Override
    public void process(StateMachine request) {

        LeaveRequestModel model = (LeaveRequestModel) request.getBusinessVO();

        //模拟审批过程
        mockAudit(model);

        //将流程状态设置为结束
        request.setState(new AuditOverState());
    }


    private void mockAudit(LeaveRequestModel model) {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext()){
            int res = scanner.nextInt();
            if(1 == res){
                model.setResult("同意");
            }else {
                model.setResult("不同意");
            }
        }
    }
}
