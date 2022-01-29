package com.exelatech.ecx.backend.service;

import org.springframework.stereotype.Service;
import com.exelatech.ecx.backend.model.ECXEvent;

@Service("emailTemplateManager")
public interface EmailTemplateManager {
	void processEmail(ECXEvent event) throws Exception;
}
