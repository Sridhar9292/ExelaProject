package com.exelatech.ecxapi.mapper;

import java.util.List;
import com.exelatech.ecxapi.model.KaiserPrintRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KaiserUpdateMapper extends GenericMapper<KaiserPrintRecord, String> {

	List<KaiserPrintRecord> searchByColumnValue(String column,String value,String job);

//	 List<KaiserPrintRecord> searchByName(@Param("searchTerm") String searchTerm, @Param("job") String job) throws SearchException;

	int updateOn( String id,String shipDate,String status,String jobNumber);

	List<KaiserPrintRecord> search(String job);
//	 int updateAll(@Param("records") List<KaiserPrintRecord> records) throws SearchException;

}
