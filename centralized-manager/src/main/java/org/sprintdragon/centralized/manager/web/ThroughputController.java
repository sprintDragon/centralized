package org.sprintdragon.centralized.manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sprintdragon.centralized.manager.statistics.throughput.ThroughputStatService;
import org.sprintdragon.centralized.manager.statistics.throughput.view.Highcharts;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/throughput")
public class ThroughputController {

    @Resource
    private ThroughputStatService throughputStatService;

    @RequestMapping(value = "/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "index.html";
    }


    @ResponseBody
    @RequestMapping(value = "/listReal", method = {RequestMethod.GET, RequestMethod.POST})
    public Highcharts listReal() {
        return throughputStatService.listTimeLineThroughtForView();
    }
}