package com.exelatech.ecxapi;

import java.util.Iterator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.BillerMapper;
import com.exelatech.ecxapi.model.Biller;
import com.exelatech.ecxapi.model.ExternalBillerIds;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillerControllerTest {

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
