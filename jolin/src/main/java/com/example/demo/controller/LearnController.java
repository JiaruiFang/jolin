package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.LearnService;
import com.example.demo.tools.Page;

@Controller
@RequestMapping("/learn")
public class LearnController {

	@Autowired
	private LearnService learnService;
	
	@RequestMapping("")
	public String index() {
		return "learn-resource";
	}
	
	@RequestMapping(value = "/queryLeanList",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public List queryLearnList(HttpServletRequest request ,HttpServletResponse response) {
		//正常要封装的条件 都是前台页面请求过来的,这里只做一个假想 dao层会把条件写死。
		//获取当前页数
		String page = request.getParameter("page");
		//获取没页行数
		String rows = request.getParameter("rows");
		String author = request.getParameter("author");
        String title = request.getParameter("title");
        //建立map集合，用于封装条件
        Map<String,Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", rows);
        params.put("author", author);
        params.put("title", title);
        //调用service方法 根据条件查询数据
        Page pageObject = learnService.queryLearnResouceList(params);
        List<Map<String,Object>> list = pageObject.getResultList();
		return list;
	}
}
