package com.feng.mall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.AttrEntity;
import com.feng.mall.product.service.AttrService;
import com.feng.mall.product.vo.AttrResponseVo;
import com.feng.mall.product.vo.AttrVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AttrControllerTest {

    @Mock
    private AttrService attrService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        AttrController controller = new AttrController();
        ReflectionTestUtils.setField(controller, "attrService", attrService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ── List ──────────────────────────────────────────────────────────────────

    @Test
    void baseAttrList_withCatelogId_returnsPage() throws Exception {
        when(attrService.queryBaseAttrPage(anyMap(), eq(225L), eq("base")))
                .thenReturn(pageOf(3));

        mockMvc.perform(get("/product/attr/base/list/225")
                        .param("page", "1").param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.page").exists())
                .andExpect(jsonPath("$.page.totalCount").value(3));
    }

    @Test
    void saleAttrList_withCatelogId0_queriesAllCategories() throws Exception {
        when(attrService.queryBaseAttrPage(anyMap(), eq(0L), eq("sale")))
                .thenReturn(pageOf(2));

        mockMvc.perform(get("/product/attr/sale/list/0")
                        .param("page", "1").param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalCount").value(2));
    }

    @Test
    void baseAttrList_withKeyword_passesKeyToService() throws Exception {
        when(attrService.queryBaseAttrPage(anyMap(), eq(225L), eq("base")))
                .thenReturn(pageOf(1));

        mockMvc.perform(get("/product/attr/base/list/225")
                        .param("page", "1").param("limit", "10").param("key", "品牌"))
                .andExpect(status().isOk());

        verify(attrService).queryBaseAttrPage(
                argThat(p -> "品牌".equals(p.get("key"))), eq(225L), eq("base"));
    }

    // ── Info ──────────────────────────────────────────────────────────────────

    @Test
    void info_baseAttr_returnsVoWithGroupAndCatelog() throws Exception {
        AttrResponseVo vo = new AttrResponseVo();
        vo.setAttrId(1L);
        vo.setAttrName("品牌");
        vo.setAttrType(1);
        vo.setCatelogName("手机");
        vo.setGroupName("主体");
        vo.setCatelogPath(new Long[]{1L, 2L, 225L});
        when(attrService.getAttrInfo(1L)).thenReturn(vo);

        mockMvc.perform(get("/product/attr/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.attr.attrId").value(1))
                .andExpect(jsonPath("$.attr.attrName").value("品牌"))
                .andExpect(jsonPath("$.attr.groupName").value("主体"))
                .andExpect(jsonPath("$.attr.catelogName").value("手机"));
    }

    @Test
    void info_saleAttr_returnsVoWithoutGroup() throws Exception {
        AttrResponseVo vo = new AttrResponseVo();
        vo.setAttrId(9L);
        vo.setAttrName("颜色");
        vo.setAttrType(0);
        vo.setCatelogName("手机");
        when(attrService.getAttrInfo(9L)).thenReturn(vo);

        mockMvc.perform(get("/product/attr/info/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attr.attrType").value(0))
                .andExpect(jsonPath("$.attr.groupName").doesNotExist());
    }

    // ── Save ──────────────────────────────────────────────────────────────────

    @Test
    void save_baseAttr_callsSaveAttr() throws Exception {
        AttrVo vo = attrVo(0L, "品牌", 1, 1L);

        mockMvc.perform(post("/product/attr/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).saveAttr(any(AttrVo.class));
    }

    @Test
    void save_saleAttr_callsSaveAttr() throws Exception {
        AttrVo vo = attrVo(0L, "颜色", 0, null);

        mockMvc.perform(post("/product/attr/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).saveAttr(any(AttrVo.class));
    }

    // ── Update ────────────────────────────────────────────────────────────────

    @Test
    void update_callsUpdateAttr() throws Exception {
        AttrVo vo = attrVo(1L, "品牌更新", 1, 2L);

        mockMvc.perform(post("/product/attr/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).updateAttr(any(AttrVo.class));
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @Test
    void delete_singleId_callsRemoveByIds() throws Exception {
        mockMvc.perform(post("/product/attr/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).removeByIds(List.of(1L));
    }

    @Test
    void delete_multipleIds_callsRemoveByIds() throws Exception {
        mockMvc.perform(post("/product/attr/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2,3]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).removeByIds(List.of(1L, 2L, 3L));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private PageUtils pageOf(long total) {
        Page<AttrEntity> page = new Page<>(1, 10);
        page.setTotal(total);
        page.setRecords(Collections.emptyList());
        return new PageUtils(page);
    }

    private AttrVo attrVo(Long attrId, String name, int type, Long groupId) {
        AttrVo vo = new AttrVo();
        vo.setAttrId(attrId);
        vo.setAttrName(name);
        vo.setAttrType(type);
        vo.setCatelogId(225L);
        vo.setAttrGroupId(groupId);
        vo.setEnable(1L);
        vo.setValueType(0);
        return vo;
    }
}
