package com.youyu.chromeservice.service;

/**
 * @author tangyulin
 * @description 商标网爬取服务
 * @createtime 2019/6/6
 */
public interface TradeMarkService {

    /**
     * 根据type、keyWord 爬取商标结果
     * @param code
     * @param proxy
     * @param type
     * @param keyword
     * @return 返回爬取的商标结果
     */
    String searchTradeMark(String code,String proxy,String type,String keyword);
}
