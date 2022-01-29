package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.MassMutualMapper;
import com.exelatech.ecxapi.model.MassMutualDetailInfo;
import com.exelatech.ecxapi.model.MassMutualFeedInfo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MassMutualControllerTest {

	@Mapper
	public MassMutualMapper massMutualMapper;

	@Test	
	@Order(1)  
	public void getAll()  {
		List<MassMutualFeedInfo> massMutualFeedInfo=null;
		try {
			massMutualFeedInfo=massMutualMapper.getMassMutualProcessInfo();
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(massMutualFeedInfo);
	}

	@Test	
	@Order(2) 
	public void handleDetailRequest() {
		 String infoId="";
		List<MassMutualDetailInfo> massMutualDetailInfo=null;
		try {
			massMutualDetailInfo=massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));	 
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(massMutualDetailInfo); 
	}
	@Test	
	@Order(3) 
	public void  handleSearchRequest()  {
		 String infoId="";
		 String condition="";
		 String query="";
		List<MassMutualDetailInfo> massMutualinfo=null;
		try {

			//System.out.println("condition===>>>>"+infoId+"/"+query+"/"+condition);
			if ((query == null || query.equals("")) && (infoId !=null && !infoId.equals(""))) {
				massMutualinfo= massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));
			} else {
				if (condition.equals("reference")) {

					massMutualinfo=	massMutualMapper.getmassmutualDetailInfoSearchRef("%" + query + "%", Integer.parseInt(infoId));
				}
				else if (condition.equals("tracking")){
					massMutualinfo=	massMutualMapper.getmassmutualDetailInfoSearchTN("%" + query + "%", Integer.parseInt(infoId));
				}else if (condition.equals("status")){
					if(query.equals("all")) {
						massMutualinfo= massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));
					}else {
						massMutualinfo=	massMutualMapper.getmassmutualDetailInfoStatus("%" + query + "%", Integer.parseInt(infoId));
					}
				}
			}
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(massMutualinfo); 
	}
}