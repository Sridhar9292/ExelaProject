package com.exelatech.ecx.backend.util;

import java.io.File;

import org.apache.log4j.Logger;

public class CheckStatus {
    static Logger log = Logger.getLogger(CheckStatus.class.getName());

    /**
     * This method close the application if a stop.flg is found it on the
     * application directory
     *
     * @return nothing, only close de app
     */
    public static void Check(){
        if (new File("stop.flg").exists()) {
            log.info("Stop flag is found, close the process");
            System.exit(0);
        }
    }

    public CheckStatus() {
    }
}