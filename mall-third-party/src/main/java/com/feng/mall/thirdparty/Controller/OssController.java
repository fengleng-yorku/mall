package com.feng.mall.thirdparty.Controller;

import com.feng.mall.thirdparty.Entity.PresignRequest;
import com.feng.mall.thirdparty.Entity.PresignResult;
import com.feng.mall.thirdparty.Service.S3PresignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OssController {

    @Autowired
    private S3PresignService presignService;

    @PostMapping("/presign")
    public ResponseEntity<PresignResult> presign(@RequestBody PresignRequest req) {
        PresignResult result = presignService.generatePresignedUrl(
                req.filename(), req.contentType()
        );
        return ResponseEntity.ok(result);
    }

}
