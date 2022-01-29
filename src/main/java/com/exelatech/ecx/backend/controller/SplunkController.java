package com.exelatech.ecx.backend.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.exelatech.ecx.backend.service.HttpService; 
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.service.ISplunk;

/**
 * This class is for request data from splunk
 */
@Service
public class SplunkController implements ISplunk {
    static Logger log = Logger.getLogger(SplunkController.class.getName());

    private String jobid = "";
    private Setting setting;
    private String splunkResult;

    public SplunkController(Setting setting) {
        this.setting = setting;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    /**
     * This method send a get request to the splunk
     *
     * @return return a string with all data from splunk
     * @throws Exception
     */

    @Override
    public String getResults() throws Exception {
        int retries = 1;

        HttpService<Map<String, String>> http = new HttpService<Map<String, String>>();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("Getting Results For JOB ID " + this.getJobid() + "(" + retries + ")");
                this.splunkResult = http.SplunkGet(this.getJobid(), this.setting);

                if (this.splunkResult != null) {
                    if (this.splunkResult.contains("results")) {
                        log.info("Success to retrieve data from JOB ID");
                        return this.splunkResult;
                    } else {

                        log.warn("........Failed to retrieve data from  JOB ID (" + this.getJobid() + ") (" + retries
                                + ")");
                    }
                } else {
                    log.warn("........Null received from  JOB ID (" + this.getJobid() + ") (" + retries + ")");
                }
                retries = retries + 1;
                if (retries >= 50) { // if attemps 10 times without succesful, the entire process is canceled.
                    throw new Exception(
                            "Unabled for retrieve data from  JOB ID (" + this.getJobid() + ")  Process canceled");
                }
            } catch (Exception e) {
                log.warn("........Failed to retrieve JOB ID\n\r" + e);
                retries = retries + 1;
                if (e.getMessage().contains("Unknown sid")) {
                    log.warn("Unknown sid - process canceled \n\r" + e);
                    System.exit(1);
                }
                if (retries >= 50)
                    throw new Exception(
                            "Unabled for retrieve data from  JOB ID (" + this.getJobid() + ")  Process canceled");

            }
        }
    }

    /**
     * This method send a post request to the splunk
     *
     * @param Search The splunk query example key=search, value = "search
     *               earliest=-1d@d sourcetype="proftpd" "
     * @return Job SID from the post request
     * @throws Exception
     */

    @Override
    public String getJobId() throws Exception {
        int retries = 1;

        while (true) {
            try {
                MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
                request.add("search", this.setting.getQuery());

                log.info("Get Splunk JOB ID " + this.setting.getKeyName() + "(" + retries + ")");

                HttpService<Map<String, String>> http = new HttpService<Map<String, String>>();
                Map<String, String> job;

                job = http.SplunkPost(request, this.setting);

                if (job != null) {

                    this.setJobid(job.containsKey("sid") ? job.get("sid").toString() : "");
                    if (!this.getJobid().isEmpty()) {
                        log.info("JOB ID " + getJobid());
                    } else {
                        log.warn("........Failed to retrieve JOB ID");
                    }
                    return this.getJobid();
                } else {
                    log.warn("........Null received from  " + this.setting.getKeyName() + "(" + retries + ")");
                }
                retries = retries + 1;
                if (retries >= 10) { // if attemps 10 times without succesful, the entire process is canceled.
                    throw new Exception("Failed to retrieve JOB ID -  Process canceled");
                }
            } catch (Exception e) {
                retries = retries + 1;
                log.warn("........Failed to retrieve JOB ID\n\r" + e);
            }
        }

    }

    public SplunkController() {
    }

}