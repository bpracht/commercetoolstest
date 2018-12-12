package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.ConnectorShop;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConnectorShopFileService {
    public Map<Long,ConnectorShop> getConnectorShops(File connectorShopDirectory) throws IOException {
        Map<Long,ConnectorShop> result = new HashMap<Long,ConnectorShop>();
        for(File file : connectorShopDirectory.listFiles()){
            if(file.isFile() && file.getName().endsWith(".json")){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String lineIn = bufferedReader.readLine();
                StringBuilder jsonStringBuilder = new StringBuilder();
                while(lineIn!=null){
                    jsonStringBuilder.append(lineIn);
                    lineIn = bufferedReader.readLine();
                }
                ConnectorShop connectorShop = new ConnectorShop(jsonStringBuilder.toString());
                result.put(connectorShop.getShopId(),connectorShop);
            }
        }
        return result;

    }

    public void writeConnectorShops(File connectorShopDirectory,List<ConnectorShop> connectorShops) throws IOException {
        for(ConnectorShop connectorShop : connectorShops) {
            String filename = connectorShop.getShopId() + ".json";
            File file = new File(connectorShopDirectory, filename);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(connectorShop.getAsJsonString());
            fileWriter.flush();
            fileWriter.close();
        }

    }

}
