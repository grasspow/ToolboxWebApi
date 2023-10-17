package io.grasspow.toolboxwebapi.controller;

import io.grasspow.toolboxwebapi.data.DataMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    private Logger logger = LogManager.getLogger(this);

    @GetMapping("jsonTest")
    @ResponseBody
    public DataMsg JsonTest(){
        TestData testString = new TestData("test String", 20, true);
        DataMsg stringDataMsg = new DataMsg("test json", testString);
        logger.info(stringDataMsg);
        return stringDataMsg;
    }

    class TestData{
        private String a;
        private int b;
        private boolean c;

        public TestData(String a, int b, boolean c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "TestData{" +
                    "a='" + a + '\'' +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }
}
