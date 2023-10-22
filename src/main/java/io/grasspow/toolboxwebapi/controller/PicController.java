package io.grasspow.toolboxwebapi.controller;

import io.grasspow.toolboxwebapi.data.DataMsg;
import io.grasspow.toolboxwebapi.util.PicUtils;
import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PicController {

    @GetMapping("baLogo")
    public DataMsg genBALogo(String left, String right,@Nullable String down){
        Optional<String> optionalFile = PicUtils.genBALogo(left,right,down);
        return optionalFile.map(img -> DataMsg.success("success", img)).orElseGet(() -> DataMsg.error_500("fail", null));
    }
}
