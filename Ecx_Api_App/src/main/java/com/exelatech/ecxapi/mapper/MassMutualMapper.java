package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.MassMutualDetailInfo;
import com.exelatech.ecxapi.model.MassMutualFeedInfo;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MassMutualMapper extends GenericMapper<MassMutualDetailInfo, String> {

	public List<MassMutualFeedInfo> getMassMutualProcessInfo();

	public List<MassMutualDetailInfo> getmassmutualDetailInfo(int infoId);

	public List<MassMutualDetailInfo> getmassmutualDetailInfoSearchRef(String searchTerm, int infoId);

	public List<MassMutualDetailInfo> getmassmutualDetailInfoSearchTN(String searchTerm, int infoId);

	public List<MassMutualDetailInfo> getmassmutualDetailInfoStatus(String searchTerm, int infoId);
}
