package com.exelatech.ecx.backend.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.service.IElasticSearch;
import com.exelatech.ecx.backend.service.IKey;
import com.exelatech.ecx.backend.util.JsonUtl;
import com.exelatech.ecx.backend.service.HttpService; 

public class ElasticSearchController implements IElasticSearch {
    private static Logger log = Logger.getLogger(ElasticSearchController.class.getName());

    private Setting _setting; 
    private ArrayList<String> bulkStatment = new ArrayList<String>();
    private int limit = 3000;
    private HttpService<String> _service = new HttpService<String> ();
    
    @Override
    public void Setting(Setting setting) {

        _setting = setting;
    }

    @Override
    public <T extends IKey>  void BuildBulkStatment(ArrayList<T> data) {
        int count = 0;
        log.info("Building JSON data to bulk into ElasticSearch " +  _setting.getKeyName());
        StringBuilder strBuilder = new StringBuilder();
       
        for (IKey var : data) {
            strBuilder.append("	{ \"index\" : { \"_index\" : \"" + _setting.getIndex() + "\", \"_type\" : \"" + "_doc"
                    + "\", \"_id\":  \"" + var.GetPK() + "\"} }");
            strBuilder.append(System.getProperty("line.separator"));
            strBuilder.append(JsonUtl.ToJson(var));
            strBuilder.append(System.getProperty("line.separator"));
            strBuilder.append(System.getProperty("line.separator"));

            
            if(count > limit){
                strBuilder.append(System.getProperty("line.separator"));
                bulkStatment.add(strBuilder.toString());

                strBuilder.delete(0, strBuilder.length());
                count =  0; //restart counter
            }
            count ++;
        }
        // Add remain data
        strBuilder.append(System.getProperty("line.separator"));
        bulkStatment.add(strBuilder.toString());

    }

    @Override
    public void Bulk() {
        
        for(int index = 0; index < this.bulkStatment.size(); index++){
            _service.POST(_setting.getUrl(), _setting.getUser(),_setting.getPassword() ,bulkStatment.get(index));
            log.info("Posting data " + (index + 1)  + " from " + this.bulkStatment.size());
        }
        bulkStatment.clear();
    }

    public String RunQuery(Setting sett) {
        return _service.POST(sett.getUrl(), sett.getUser(),sett.getPassword() ,sett.getQuery());
    }

    public ElasticSearchController() {
    }
    
}