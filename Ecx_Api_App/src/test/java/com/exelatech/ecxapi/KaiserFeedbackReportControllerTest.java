package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.KaiserFeedbackReportMapper;
import com.exelatech.ecxapi.model.KaiserFeedbackReport;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KaiserFeedbackReportControllerTest {

	@Autowired
	private KaiserFeedbackReportMapper kaiserFeedbackReportMapper;

	@Test
	@Order(1)
	public void getall() {
		List<KaiserFeedbackReport> kaiserFeedbackReports = null;
		try {
			kaiserFeedbackReports = kaiserFeedbackReportMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			assert (false);
		}
		assertNotNull(kaiserFeedbackReports);
	}

}
