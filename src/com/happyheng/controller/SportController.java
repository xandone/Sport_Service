package com.happyheng.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.happyheng.entity.result.SportListResult;
import com.happyheng.entity.result.SportMessageResult;
import com.happyheng.entity.result.SportRecordResult;
import com.happyheng.service.SportIdService;
import com.happyheng.service.SportListService;
import com.happyheng.service.SportMessageService;
import com.happyheng.service.SportRecordService;
import com.happyheng.service.impl.BaseService;
import com.happyheng.utils.ContextUtils;

@Controller
public class SportController extends BaseController {
//	public SportController() {
//		super();
//	}

	@RequestMapping(value = "/SportId", method = RequestMethod.POST)
	public void getSportId(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userKey = (String) req.getAttribute("ukey");

		// 得到返回的结果
		SportIdService service = (SportIdService) ContextUtils.getContext().getBean("sportIdService");
		SportRecordResult result = service.getSportId(userKey);

		Map<String, Object> responseMap = new HashMap<>();
		// 说明插入成功
		if (result.getCode() == BaseService.RESULT_CODE_SUCCESS) {
			responseMap.put("id", result.getSportId());
		}
		responseMap.put(RESULT_KEY, result.getCode());

		String JsonResult = JSON.toJSONString(responseMap);
		System.out.println("结果为" + JsonResult);

		PrintWriter printWriter = resp.getWriter();
		printWriter.write(JsonResult);
		printWriter.close();
	}

	@RequestMapping(value = "/Record", method = RequestMethod.POST)
	public void record(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int sportId = (int) req.getAttribute("id");
		double posx = ((BigDecimal) req.getAttribute("posx")).doubleValue();
		double posy = ((BigDecimal) req.getAttribute("posy")).doubleValue();
		String location = (String) req.getAttribute("location");

		SportRecordService service = (SportRecordService) ContextUtils.getContext().getBean("sportRecordService");

		Map<String, Object> responseMap = new HashMap<>();
		// 不为空，则说明是来插入信息并获取sportId的
		int resultCode = service.record(sportId, posx, posy, location);
		responseMap.put(RESULT_KEY, resultCode);

		String result = JSON.toJSONString(responseMap);
		System.out.println("结果为" + result);

		PrintWriter printWriter = resp.getWriter();
		printWriter.write(result);
		printWriter.close();
	}
	
	@RequestMapping(value = "/SportList", method = RequestMethod.POST)
	public void getSportList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userKey = (String) req.getAttribute("userkey");
		
		SportListService service = (SportListService)ContextUtils.getContext().getBean("sportListService");
		SportListResult result = service.getSportList(userKey);
		
		String resultJson = JSON.toJSONString(result);
		System.out.println("结果为" + resultJson);

		PrintWriter printWriter = resp.getWriter();
		printWriter.write(resultJson);
		printWriter.close();
	}
	
	@RequestMapping(value = "/SportMessage", method = RequestMethod.POST)
	public void getSportMessage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String sportId = (String) req.getAttribute("sportId");
		
		SportMessageService service = (SportMessageService)ContextUtils.getContext().getBean("sportMessageService");
		SportMessageResult result = service.getSportMessage("", sportId);
		
		String resultJson = JSON.toJSONString(result);
		System.out.println("结果为" + resultJson);

		PrintWriter printWriter = resp.getWriter();
		printWriter.write(resultJson);
		printWriter.close();
	}
	
}
