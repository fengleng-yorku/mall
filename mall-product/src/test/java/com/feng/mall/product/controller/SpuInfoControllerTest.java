package com.feng.mall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.mall.product.service.SpuInfoService;
import com.feng.mall.product.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SpuInfoControllerTest {

    @Mock
    private SpuInfoService spuInfoService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        SpuInfoController controller = new SpuInfoController();
        ReflectionTestUtils.setField(controller, "spuInfoService", spuInfoService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void save_fullVo_returns200AndDelegatesToService() throws Exception {
        SpuSaveVo vo = buildFullVo();

        mockMvc.perform(post("/product/spuinfo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(spuInfoService).saveSpuInfo(any(SpuSaveVo.class));
    }

    @Test
    void save_emptySkuList_stillCallsService() throws Exception {
        SpuSaveVo vo = buildFullVo();
        vo.setSkus(List.of());

        mockMvc.perform(post("/product/spuinfo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(spuInfoService).saveSpuInfo(any(SpuSaveVo.class));
    }

    @Test
    void save_serviceThrows_propagatesError() throws Exception {
        doThrow(new RuntimeException("db error")).when(spuInfoService).saveSpuInfo(any());

        // standalone MockMvc re-throws wrapped ServletException rather than mapping to 5xx
        assertThatThrownBy(() -> mockMvc.perform(post("/product/spuinfo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildFullVo()))))
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("db error");
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private SpuSaveVo buildFullVo() {
        Images img = new Images();
        img.setImgUrl("https://oss.example.com/phone.jpg");
        img.setDefaultImg(1);

        Attr saleAttr = new Attr();
        saleAttr.setAttrId(1L);
        saleAttr.setAttrName("颜色");
        saleAttr.setAttrValue("黑色");

        MemberPrice mp = new MemberPrice();
        mp.setId(1L);
        mp.setName("金牌会员");
        mp.setPrice(new BigDecimal("3999.00"));

        Skus sku = new Skus();
        sku.setSkuName("iPhone 15 黑色 128G");
        sku.setPrice(new BigDecimal("4299.00"));
        sku.setSkuTitle("Apple iPhone 15 黑色 128G");
        sku.setSkuSubtitle("正品行货");
        sku.setImages(List.of(img));
        sku.setAttr(List.of(saleAttr));
        sku.setFullCount(2);
        sku.setDiscount(new BigDecimal("0.9"));
        sku.setCountStatus(1);
        sku.setFullPrice(new BigDecimal("8000.00"));
        sku.setReducePrice(new BigDecimal("500.00"));
        sku.setPriceStatus(0);
        sku.setMemberPrice(List.of(mp));

        BaseAttrs baseAttr = new BaseAttrs();
        baseAttr.setAttrId(1L);
        baseAttr.setAttrValues("Apple");
        baseAttr.setShowDesc(1);

        Bounds bounds = new Bounds();
        bounds.setBuyBounds(new BigDecimal("500.00"));
        bounds.setGrowBounds(new BigDecimal("300.00"));

        SpuSaveVo vo = new SpuSaveVo();
        vo.setSpuName("iPhone 15");
        vo.setSpuDescription("Apple 旗舰手机");
        vo.setCatalogId(225L);
        vo.setBrandId(1L);
        vo.setWeight(new BigDecimal("0.3"));
        vo.setPublishStatus(0);
        vo.setDecript(List.of("https://oss.example.com/desc1.jpg", "https://oss.example.com/desc2.jpg"));
        vo.setImages(List.of("https://oss.example.com/spu1.jpg"));
        vo.setBounds(bounds);
        vo.setBaseAttrs(List.of(baseAttr));
        vo.setSkus(List.of(sku));
        return vo;
    }
}
