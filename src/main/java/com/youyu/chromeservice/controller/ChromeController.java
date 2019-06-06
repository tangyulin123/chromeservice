package com.youyu.chromeservice.controller;

import com.youyu.chromeservice.service.TradeMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangyulin
 * @description
 * @createtime 2019/6/6
 */
@RestController
public class ChromeController {

    @Autowired
    TradeMarkService tradeMarkService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/tradeMark/{code}")
    public String tradeMarkSpider(@PathVariable("code") String code,
                                  @RequestParam("proxy") String proxy,
                                  @RequestParam("type")String type,
                                  @RequestParam("keyword") String keyword){
        return tradeMarkService.searchTradeMark(code,proxy,type,keyword);
    }
}
