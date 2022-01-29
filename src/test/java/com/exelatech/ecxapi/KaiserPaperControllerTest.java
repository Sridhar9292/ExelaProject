package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.KaiserTicketMapper;
import com.exelatech.ecxapi.mapper.KaiserUpdateMapper;
import com.exelatech.ecxapi.model.KaiserPaperTicketInfo;
import com.exelatech.ecxapi.model.KaiserPrintRecord;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KaiserPaperControllerTest {

	@Autowired
	private KaiserTicketMapper kaiserTicketMapper;
	@Autowired
	private KaiserUpdateMapper kaiserUpdateMapper;

	@Test	
	@Order(1) 
	public void getKaiProcess() {
		List<KaiserPaperTicketInfo> kaiserPaperTicketInfo = null;
		try {
			kaiserPaperTicketInfo = kaiserTicketMapper.getAll();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(kaiserPaperTicketInfo); 
	}

	@Test	
	@Order(2) 
	public void getsearch() {
		
		String column="job";
		String value="A0X8YX";
		
		List<KaiserPaperTicketInfo> result =null;
		try {
			if (column.equalsIgnoreCase("status")) {
				List<KaiserPaperTicketInfo> all = kaiserTicketMapper.getAll();
			result = all.stream() // convert list to stream
						.filter(line -> line.getStatus().equals(value)).collect(Collectors.toList());
			
			} else if (column.equalsIgnoreCase("job")) {
				result = kaiserTicketMapper.search_job(value);
			} else if (column.equalsIgnoreCase("purchaser")) {
				result = kaiserTicketMapper.search_Purchaser(value);
			}else {
			result =  kaiserTicketMapper.getAll();
			}
			assertNotNull(result); 
		} catch (Exception e) {
			assert(false);
		}
		
	}

	@Test	
	@Order(2) 
	public void getPrintRecord() {
		 String jobNumber="A0X8YX";
		List<KaiserPrintRecord> KaiserPrintRecord = null;
		try {
			KaiserPrintRecord = kaiserUpdateMapper.search(jobNumber);
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(KaiserPrintRecord);  ;
	}

	@Test	
	@Order(3) 	
	public void updateAll()  {
		int count=0;
		 String jobNumber="A0X8YX";
		try {
			count=kaiserTicketMapper.updateSent(jobNumber);
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (Exception e) {
			assert(false);
		}
	}

	@Test	
	@Order(4)
	public void save( ) {
		List<KaiserPrintRecord> KaiserPrintRecordList =new ArrayList<KaiserPrintRecord>();
		KaiserPrintRecord  KaiserPrintRecord= new KaiserPrintRecord();
		
		KaiserPrintRecord.setRowNo(1);
		KaiserPrintRecord.setId("78c9a5e9-6015-49aa-8665-49b67b812a68");
		KaiserPrintRecord.setJob("A0X8YX");
		KaiserPrintRecord.setShipName(null);
		KaiserPrintRecord.setShipAttention("EVA GRAJEDA");
		KaiserPrintRecord.setContract("000350934-001-001");;
		KaiserPrintRecord.setContractCopies(null);
		KaiserPrintRecord.setStatus("Sent");
		KaiserPrintRecord.setShipDate("2021-01-21");
		KaiserPrintRecord.setPrintTypeCode(null);
		KaiserPrintRecord.setCompletionDate(null);
		KaiserPrintRecordList.add(KaiserPrintRecord);
		
		int count=0;
		try {
			for (KaiserPrintRecord rec : KaiserPrintRecordList) {
				if (rec.getShipDate() != null && !rec.getShipDate().equals("") && rec.getJob() != null
						&& !rec.getJob().equals("")) {
					count =kaiserUpdateMapper.updateOn(rec.getId(), rec.getShipDate(), rec.getStatus(), rec.getJob());
				} 
			}
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (Exception e) {
			assert(false);
		}
		
	}
}
