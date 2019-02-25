package com.altimetrik.poc.demo.nf.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.poc.demo.bean.Response_VO;
import com.altimetrik.poc.demo.common.Versions;
import com.altimetrik.poc.demo.entity.UpiVirtualAddr;
import com.altimetrik.poc.demo.service.VirtualAddrService;

@RestController
@RequestMapping(value = "/app/demo/", consumes = Versions.V1_0, produces = Versions.V1_0)
public class VirtualAddrController {

	@Inject
	private VirtualAddrService virtualAddrService;

	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualAddrController.class);

	@RequestMapping(value = "/registervpa", method = RequestMethod.POST)
	public Response_VO registerVpa(@RequestBody UpiVirtualAddr upiVirtualAddr) {

		return virtualAddrService.registerVpa(upiVirtualAddr);
	}


	@RequestMapping(value = "/getvpa", method = RequestMethod.GET)
	public List<UpiVirtualAddr> getVpa(@RequestParam String vpa) {
		LOGGER.info("getvpa vpa {}", vpa);
		List<UpiVirtualAddr> response = virtualAddrService.getVpa(vpa);
		LOGGER.info("Response {}", response);
		return response;
	}

}
