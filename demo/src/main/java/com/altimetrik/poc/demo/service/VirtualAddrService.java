package com.altimetrik.poc.demo.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.altimetrik.poc.demo.bean.Response_VO;
import com.altimetrik.poc.demo.entity.UpiVirtualAddr;
import com.altimetrik.poc.demo.entity.repo.UpiVirtualAddrRepo;

@Service
public class VirtualAddrService {

	@Inject
	private UpiVirtualAddrRepo upiVirtualAddrRepo;

	public Response_VO registerVpa(UpiVirtualAddr upiVirtualAddr) {
		UpiVirtualAddr addr = upiVirtualAddrRepo.save(upiVirtualAddr);
		Response_VO response = new Response_VO();
		if (addr == null) {
			response.setResponseCode("11");
			response.setResponseMsg("FAILURE");
		} else {
			response.setResponseCode("00");
			response.setResponseMsg("SUCCESS");

		}
		return response;
	}


	public List<UpiVirtualAddr> getVpa(String vpa) {

		return upiVirtualAddrRepo.findAllByVpa(vpa);
	}

}
