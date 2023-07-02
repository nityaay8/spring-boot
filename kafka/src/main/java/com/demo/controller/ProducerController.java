package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.MsgInfo;
import com.demo.service.ProducerService;

@RestController
@RequestMapping("produce")
@CrossOrigin(origins = "*")
public class ProducerController {

	@Autowired
	private ProducerService producerService;

	@PostMapping("send")
	public MsgInfo send(@RequestBody String msg) throws Exception {

		MsgInfo msgInfo = producerService.send(msg);

		return msgInfo;
	}
	
	@GetMapping("send1")
	public MsgInfo send1(@RequestParam String msg) throws Exception {

		MsgInfo msgInfo = producerService.send(msg);

		return msgInfo;
	}
}
