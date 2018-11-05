package com.maxwell.learning.designpattern.state;

import java.util.Scanner;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ProjectManagerState implements LeaveRequestState {

    @Override
    public void process(StateMachine request) {

        LeaveRequestModel model = (LeaveRequestModel) request.getBusinessVO();

        //项目经理进行审核：（这里写死了是同意）
        System.out.println("项目经理正在审核...");

        //模拟审批过程
        mockAudit(model);

        if("同意".equals(model.getResult())){
            if(model.getLeaveDays() < 3){
                //小于三天，将流程状态设置为结束
                request.setState(new AuditOverState());
            }else {
                //大于三天，交给部门经理继续审核
                request.setState(new DepManagerState());
            }

        }else {
            //项目经理不同意，则将流程状态设置为结束
            request.setState(new AuditOverState());
        }

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
