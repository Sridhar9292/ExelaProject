package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.BillerConfigMapper;
import com.exelatech.ecxapi.mapper.BillerMapper;
import com.exelatech.ecxapi.model.Biller;
import com.exelatech.ecxapi.model.BillerConfig;
import com.exelatech.ecxapi.model.ExternalBillerIds;
import com.exelatech.ecxapi.util.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillerConfigControllerTest {
	@Autowired
	private BillerConfigMapper billerConfigMapper;
	@Test	
	@Order(1)
	public void getAll() throws NotFoundException {
		List<BillerConfig> billerConfig = null;
		try {
			billerConfig = billerConfigMapper.getAll();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(billerConfig); 
	}
	@Test	
	@Order(2)
		public void getBillerDetails() {
		
		String clientCode="ABZ";
		 String clientLobCode="TTT156";
		BillerConfig biller = null;
		try {
			biller = billerConfigMapper.getBillerConfigByclientCodeclientLobCode(clientCode, clientLobCode, null);

		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(biller); 
	}
	@Test	
	@Order(3)
	public void serach()  {
		String query="ABZ";
		List<BillerConfig> biller=null;
		try {
			if (query == null || query.equals("")) {
				biller=  billerConfigMapper.getAll();
			}
			biller= billerConfigMapper.search("%" + query + "%", null);
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(biller); 
	}
	@Autowired
	private BillerMapper billerMapper;

	@Test	
	@Order(1)
	public void updateBiller() {
		int count1=0; int count2=0; int count3=0;int count4=0;

	
		Biller biller=new Biller();
		
		
		
		try {
			for (Iterator<ExternalBillerIds> i = biller.getExternalBillerIdsList().iterator(); i.hasNext();) {
				ExternalBillerIds ebi = i.next();
				if (ebi.getBillerId() == null || ebi.getBillerId().equals("")) {
					i.remove();
				}

			}
			for (ExternalBillerIds ebi : biller.getExternalBillerIdsList()) {
				ebi.setClientCode(biller.getClientCode());
				ebi.setClientLobCode(biller.getClientLobCode());
				ebi.setReturnsConfEnabled(
						(ebi.getReturnsEnabled().equals("Y") && ebi.getVendorCode().equals("RPPS")) ? "Y" : "N");
			}
			if (("true").equalsIgnoreCase(biller.getBillerEnabled())) {
				biller.setBillerEnabled("Y");
				billerMapper.updateBillerEnabled(biller);
			} else {
				biller.setBillerEnabled("N");
				billerMapper.updateBillerEnabled(biller);
			}
			count1=billerMapper.updateClient(biller);
			count2=billerMapper.updateClientLob(biller);
			count3=billerMapper.updateBiller(biller);

			billerMapper.deleteBillerVendor(biller);
			count4=billerMapper.addBillerVendor(biller.getExternalBillerIdsList());
			if (count1 != 0 && count2 != 0 &&count3 != 0 && count4 != 0) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	@Test	
	@Order(2)
	public void addBiller()  {
		Biller biller=new Biller();
		System.out.println("biller--->" + biller);
		int count1=0; int count2=0; int count3=0;int count4=0;
		try {

			for (Iterator<ExternalBillerIds> i = biller.getExternalBillerIdsList().iterator(); i.hasNext();) {
				ExternalBillerIds ebi = i.next();
				if (ebi.getBillerId() == null || ebi.getBillerId().equals("")) {
					i.remove();
				}
			}
			for (ExternalBillerIds ebi : biller.getExternalBillerIdsList()) {
				ebi.setClientCode(biller.getClientCode());
				ebi.setClientLobCode(biller.getClientLobCode());
				ebi.setReturnsConfEnabled(
						(ebi.getReturnsEnabled().equals("Y") && ebi.getVendorCode().equals("RPPS")) ? "Y" : "N");
			}

			boolean isNew = billerMapper.billerExists(biller).getCount() == 0 ? true : false;

			if (isNew) {
				System.out.println("new one...");

				count1=billerMapper.addClient(biller);
				count2=billerMapper.addClientLob(biller);
				count3=billerMapper.addBiller(biller);
				count4=billerMapper.addBillerVendor(biller.getExternalBillerIdsList());
			}
			if (count1 != 0 && count2 != 0 &&count3 != 0 && count4 != 0) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			assert(false);
		}
	}

	@Test	
	@Order(3)
	public void deleteBiller()
			 {
		int count1=0; int count2=0; 
		String clientCode =""; 
		String clientLobCode=""; 
		
		Biller biller = new Biller();
		biller.setClientCode(clientCode);
		biller.setClientLobCode(clientLobCode);
		try {
			count1=billerMapper.deleteBillerVendor(biller);
			count2=billerMapper.deleteBiller(biller);
			if (count1 != 0 && count2 != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
}
