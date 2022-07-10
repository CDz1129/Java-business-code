package com.cdz.javacode.pdf;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTest {
    public static void main(String[] args) throws JRException {
        String jrxmlPath = "/Users/cdz/IdeaProjects/Java-business-code/src/main/resources/user-report-demo.jrxml";
        String jasperPath = "/Users/cdz/IdeaProjects/Java-business-code/src/main/resources/user-report-demo.jasper";

        //编译模板
        JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

        //构造数据
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        List<Map> userBalanceList = new ArrayList<>();
        Map t1 = new HashMap();
        t1.put("coin", "BTC");
        t1.put("balance", "1");
        userBalanceList.add(t1);
        Map t2 = new HashMap();
        t2.put("coin", "ETH");
        t2.put("balance", "2");
        userBalanceList.add(t2);
        Map t3 = new HashMap();
        t3.put("coin", "LTC");
        t3.put("balance", "100");
        userBalanceList.add(t3);
        Map t4 = new HashMap();
        t4.put("coin", "JPY");
        t4.put("balance", "5,000");
        userBalanceList.add(t4);
        parameters.put("userBalanceList", userBalanceList);

        ArrayList<Map> userTradeList = new ArrayList<>();
        HashMap trade1 = new HashMap<>();
        trade1.put("uuid","123456122");
        trade1.put("symble","BTC_USD");
        trade1.put("direction","BUY");
        trade1.put("fill_price","1,222");
        trade1.put("fill_amont","10,222");
        trade1.put("fill_quantity","11");
        trade1.put("ctime","2022/01/02");
        trade1.put("fee","0.0001");
        trade1.put("feeCoin","BTC");
        userTradeList.add(trade1);

        HashMap trade2 = new HashMap<>();
        trade2.put("uuid","123456123");
        trade2.put("symble","ETH_USD");
        trade2.put("direction","BUY");
        trade2.put("fill_price","222");
        trade2.put("fill_amont","10,222");
        trade2.put("fill_quantity","11");
        trade2.put("ctime","2022/01/03");
        trade2.put("fee","12");
        trade2.put("feeCoin","ETH");
        userTradeList.add(trade2);
        parameters.put("userTradeList", userTradeList);


        ArrayList<Map> userFundList = new ArrayList<>();
        HashMap userFund1 = new HashMap<>();
        userFundList.add(userFund1);
        userFund1.put("uuid","JPY");
        userFund1.put("ctime","");
        userFund1.put("type","");
        userFund1.put("out_amont","");
        HashMap userFund2 = new HashMap<>();
        userFund2.put("uuid","123465465");
        userFund2.put("ctime","2022/01/03");
        userFund2.put("type","出金");
        userFund2.put("out_amont","1，000");
        userFund2.put("in_amont","");
        userFundList.add(userFund2);
        HashMap userFund3 = new HashMap<>();
        userFund3.put("uuid","123465465");
        userFund3.put("ctime","2022/01/03");
        userFund3.put("type","入金");
        userFund3.put("in_amont","1，000");
        userFund3.put("out_amont","");
        userFundList.add(userFund3);
        HashMap userFund4 = new HashMap<>();

        userFund3.put("uuid","");
        userFund3.put("ctime","");
        userFund4.put("type","出金");
        userFund4.put("out_amont","1，000");
        userFund2.put("in_amont","");
        userFundList.add(userFund4);
        HashMap userFund5 = new HashMap<>();

        userFund3.put("uuid","");
        userFund3.put("ctime","");
        userFund5.put("type","入金");
        userFund5.put("in_amont","1，000");
        userFund4.put("out_amont","");
        userFundList.add(userFund5);
        parameters.put("userFundList", userFundList);




        //填充数据---使用JavaBean数据源方式填充
//        JasperPrint jasperPrint =
//                JasperFillManager.fillReport(jasperPath,
//                        parameters,
//                        new JRBeanCollectionDataSource(list));
            JasperPrint jasperPrint = JasperReportUtil.getJasperPrint(jasperPath, parameters, new ArrayList<>());
            //输出文件
            String pdfPath = "/Users/cdz/IdeaProjects/Java-business-code/src/main/resources/test.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);


        }


}
