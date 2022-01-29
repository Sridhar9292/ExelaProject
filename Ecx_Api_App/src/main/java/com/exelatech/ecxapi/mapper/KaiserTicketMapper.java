package com.exelatech.ecxapi.mapper;

import java.util.List;
import com.exelatech.ecxapi.model.KaiserPaperTicketInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KaiserTicketMapper {

	List<KaiserPaperTicketInfo> getAll();

	List<KaiserPaperTicketInfo> search(String serarhColumn, String searchValue);

	List<KaiserPaperTicketInfo> search_Purchaser(String value);

	List<KaiserPaperTicketInfo> search_job(String value);

	int updateSent(String jobNumber);

	List<Integer> searchStatus(String jobNumber);

	// void updateOn(String id, String shipDate, String status, String job);

}
