package com.youyu.chromeservice.service.impl;

import com.github.supermoonie.auto.AutoChrome;
import com.github.supermoonie.condition.Conditions;
import com.github.supermoonie.condition.ExpressionReturnTrueCondition;
import com.youyu.chromeservice.service.TradeMarkService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author tangyulin
 * @description
 * @createtime 2019/6/6
 */
@Service
public class TradeMarkServiceImpl implements TradeMarkService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String searchTradeMark(String code, String proxy, String type, String keyword) {
        try (AutoChrome autoChrome = new AutoChrome.Builder()
                .setProxy(proxy)
                .setPort(9222)
                //.setChromiumPath("C:\\Users\\B-0002\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe")
                .build()) {
            autoChrome.navigateUntilDomReady("http://wsjs.saic.gov.cn/txnT01.do", 10000);
            autoChrome.eval("document.querySelector(\"table[onclick=\\\"goUrl('/txnS02.do')\\\"]\").click();");
            autoChrome.waitCondition(Conditions.documentReady, 5000);
            String searchJs = String.format("document.querySelector(\"input[name=\\\"request:nc\\\"]\").value = '%s';" +
                    "document.querySelector(\"input[name=\\\"request:mn\\\"]\").value = '%s';" +
                    "document.getElementById(\"_searchButton\").click();", type, keyword);
            autoChrome.eval(searchJs);
            Elements elements;
            try (AutoChrome chrome = new AutoChrome(9222, "商标检索结果",5_000)) {
                chrome.waitCondition(new ExpressionReturnTrueCondition("document.querySelectorAll(\"#list_box>table>tbody>tr[class=\\\"ng-scope\\\"]\").length>0"), 10_000);
                Document document = Jsoup.parse(chrome.getContent());
                elements = document.select("#list_box");
                logger.info("keyword:{},search result size:{}", keyword, elements.select("#list_box>table>tbody>tr.ng-scope").size());
            }
            return Base64.getEncoder().encodeToString(elements.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
}
