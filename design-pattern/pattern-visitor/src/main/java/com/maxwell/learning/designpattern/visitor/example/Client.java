package com.maxwell.learning.designpattern.visitor.example;


/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {
        CustomerStructure structure = new CustomerStructure();

        Customer xiaoming = new PersonalCustomer();
        ((PersonalCustomer) xiaoming).setName("xiaoming");

        Customer google=  new EnterpriseCustomer();
        ((EnterpriseCustomer) google).setName("google");

        structure.addElement(xiaoming);
        structure.addElement(google);

        //对客户进行行为预测
        Visitor predictionAnalyze = new PredictionAnalyzeVisitor();
        structure.handleRequest(predictionAnalyze);

        //客户申请服务
        Visitor serviceRequest = new ServiceRequestVisitor();
        structure.handleRequest(serviceRequest);

        //对客户进行价值分析
        Visitor worthAnalyze = new WorthAnalyzeVisitor();
        structure.handleRequest(worthAnalyze);

    }
}
