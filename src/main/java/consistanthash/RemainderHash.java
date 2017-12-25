package main.java.consistanthash;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月11日 --  下午2:25 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
class RemainderHash {

    private List<String> serverNodes;
    private int serverNodeSize;

    public RemainderHash(List<String> serverNodes) {
        this.serverNodes = serverNodes;
        this.serverNodeSize = serverNodes.size();
    }

    public String getServerNode(String key) {
        return serverNodes.get(hash(key));
    }

    public int hash(String key) {
        return Math.abs(key.hashCode() % serverNodeSize);
    }
}


class Client {


    @Test
    public void testBalance() {

        int serverNum = 5;
        int objectNum = 10000;

        List<String> serverNodes = new ArrayList<>(serverNum);
        Map<String, Integer> countMap = new HashMap<>(serverNum);

        for (int i = 1; i <= serverNum; i++) {
            String serverNode = "服务器" + i;
            serverNodes.add(serverNode);
            countMap.put(serverNode, 0);
        }

        RemainderHash hash = new RemainderHash(serverNodes);
        for (int i = 0; i < objectNum; i++) {
            String serverNode = hash.getServerNode("数据" + i);
            countMap.put(serverNode, countMap.get(serverNode) + 1);
        }

        for(Map.Entry entry : countMap.entrySet()){
            System.out.println(entry.getKey() + "上数据占比：" + (double)(int)entry.getValue()/(double)objectNum * 100 + "%");
        }

    }


    @Test
    public void testRepeatHitAfterServerExpand() {

        int originServerNum = 100;
        int serverNum = 101;
        int objectNum = 10000;

        List<String> originServerNodes = new ArrayList<>(originServerNum);
        List<String> serverNodes = new ArrayList<>(serverNum);

        Map<String, List<String>> originCountMap = new HashMap<>(originServerNum);
        Map<String, List<String>> countMap = new HashMap<>(serverNum);

        for (int i = 1; i <= serverNum; i++) {
            String serverNode = "服务器" + i;
            if (i != serverNum) {
                originServerNodes.add(serverNode);
                originCountMap.put(serverNode, new ArrayList<>());
                serverNodes.add(serverNode);
                countMap.put(serverNode, new ArrayList<>());
            } else {
                serverNodes.add(serverNode);
                countMap.put(serverNode, new ArrayList<>());
            }
        }

        RemainderHash originHash = new RemainderHash(originServerNodes);
        for (int i = 0; i < objectNum; i++) {
            String data = "数据" + i;
            String serverNode = originHash.getServerNode(data);
            originCountMap.get(serverNode).add(data);
        }

        RemainderHash hash = new RemainderHash(serverNodes);
        for (int i = 0; i < objectNum; i++) {
            String data = "数据" + i;
            String serverNode = hash.getServerNode(data);
            countMap.get(serverNode).add(data);
        }

        int repeatHit = 0;
        for (int i = 0; i < originServerNum; i++) {
            List<String> first = originCountMap.get(originServerNodes.get(i));
            List<String> second = countMap.get(serverNodes.get(i));
            for (int j = 0; j < objectNum; j++) {
                String data = "数据" + j;
                if (first.contains(data) && second.contains(data)) {
                    repeatHit++;
                }
            }
        }

        System.out.println("服务器数量由" + originServerNum + "台变为" + serverNum + "后，数据的重复命中率为：" + (((double) repeatHit) / (double) objectNum) * 100);

    }
}