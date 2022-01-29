package com.exelatech.ecxapi.mapper;

import com.exelatech.ecxapi.model.Biller;
import com.exelatech.ecxapi.model.ExternalBillerIds;
import com.exelatech.ecxapi.model.LookUp;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillerMapper extends GenericMapper<Biller, String> {

	//Biller get(String clientCode, String clientLobCode);
	 
	int addClient(Biller biller);

	int addClientLob(Biller biller);
	
	int addBiller(Biller biller);
	
	int addBillerVendor(List<ExternalBillerIds> externalBillerIds);

	Biller clientExists(String clientCode);

	Biller clientLobExists(String clientCode, String clientLobCode);

	Biller billerExists(Biller biller);

	Biller billerVendorExists(String vendorCode, String billerId);

	int updateClient(Biller biller);
	int updateClientLob(Biller biller);
	
	int updateBiller(Biller biller);

	int enableBillerVendor(ExternalBillerIds externalBillerIds);

	int disableBillerVendor(ExternalBillerIds externalBillerIds);

	int updateBillerEnabled(Biller biller);

	Biller get(String clientCode, String clientLobCode, List<String> filter);

	List<LookUp> getLookupDetails();

	LookUp getPostingId(String value);

	int deleteBiller(Biller biller);

	int deleteBillerVendor(Biller biller);
}
