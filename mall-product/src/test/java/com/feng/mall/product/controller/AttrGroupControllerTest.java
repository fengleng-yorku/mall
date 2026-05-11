package com.feng.mall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.AttrEntity;
import com.feng.mall.product.entity.AttrGroupEntity;
import com.feng.mall.product.service.AttrGroupService;
import com.feng.mall.product.service.AttrService;
import com.feng.mall.product.service.CategoryService;
import com.feng.mall.product.vo.AttrGroupRelationVo;
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
class AttrGroupControllerTest {

    @Mock
    private AttrGroupService attrGroupService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private AttrService attrService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        AttrGroupController controller = new AttrGroupController();
        ReflectionTestUtils.setField(controller, "attrGroupService", attrGroupService);
        ReflectionTestUtils.setField(controller, "categoryService", categoryService);
        ReflectionTestUtils.setField(controller, "attrService", attrService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ── List ──────────────────────────────────────────────────────────────────

    @Test
    void list_withCatelogId_returnsFilteredPage() throws Exception {
        when(attrGroupService.queryPage(anyMap(), eq(225L))).thenReturn(pageOf(3));

        mockMvc.perform(get("/product/attrgroup/list/225")
                        .param("page", "1").param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.page.totalCount").value(3));
    }

    @Test
    void list_withCatelogId0_returnsAllGroups() throws Exception {
        when(attrGroupService.queryPage(anyMap(), eq(0L))).thenReturn(pageOf(5));

        mockMvc.perform(get("/product/attrgroup/list/0")
                        .param("page", "1").param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalCount").value(5));
    }

    @Test
    void list_withKeyword_passesKeyToService() throws Exception {
        when(attrGroupService.queryPage(anyMap(), eq(225L))).thenReturn(pageOf(1));

        mockMvc.perform(get("/product/attrgroup/list/225")
                        .param("page", "1").param("limit", "10").param("key", "主体"))
                .andExpect(status().isOk());

        verify(attrGroupService).queryPage(
                argThat(p -> "主体".equals(p.get("key"))), eq(225L));
    }

    // ── Info ──────────────────────────────────────────────────────────────────

    @Test
    void info_returnsGroupWithCatelogPath() throws Exception {
        AttrGroupEntity group = new AttrGroupEntity();
        group.setAttrGroupId(1L);
        group.setAttrGroupName("主体");
        group.setCatelogId(225L);
        when(attrGroupService.getById(1L)).thenReturn(group);
        when(categoryService.findCatelogPath(225L)).thenReturn(new Long[]{1L, 2L, 225L});

        mockMvc.perform(get("/product/attrgroup/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.attrGroup.attrGroupId").value(1))
                .andExpect(jsonPath("$.attrGroup.attrGroupName").value("主体"))
                .andExpect(jsonPath("$.attrGroup.catelogPath").isArray());
    }

    // ── attr/relation ─────────────────────────────────────────────────────────

    @Test
    void attrRelation_returnsRelatedAttrList() throws Exception {
        AttrEntity a1 = attrEntity(1L, "品牌");
        AttrEntity a2 = attrEntity(2L, "型号");
        when(attrService.getRelationAttr(1L)).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/product/attrgroup/1/attr/relation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].attrName").value("品牌"));
    }

    @Test
    void attrRelation_noRelations_returnsNullData() throws Exception {
        when(attrService.getRelationAttr(99L)).thenReturn(null);

        mockMvc.perform(get("/product/attrgroup/99/attr/relation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    // ── attr/nonrelation ──────────────────────────────────────────────────────

    @Test
    void attrNonRelation_returnsPagedAvailableAttrs() throws Exception {
        when(attrService.getNonRelationAttr(anyMap(), eq(1L))).thenReturn(pageOf(2));

        mockMvc.perform(get("/product/attrgroup/1/attr/nonrelation")
                        .param("page", "1").param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.totalCount").value(2));
    }

    // ── Save / Update / Delete ────────────────────────────────────────────────

    @Test
    void save_callsAttrGroupServiceSave() throws Exception {
        AttrGroupEntity group = new AttrGroupEntity();
        group.setAttrGroupName("新分组");
        group.setCatelogId(225L);

        mockMvc.perform(post("/product/attrgroup/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrGroupService).save(any(AttrGroupEntity.class));
    }

    @Test
    void update_callsAttrGroupServiceUpdateById() throws Exception {
        AttrGroupEntity group = new AttrGroupEntity();
        group.setAttrGroupId(1L);
        group.setAttrGroupName("主体更新");

        mockMvc.perform(post("/product/attrgroup/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrGroupService).updateById(any(AttrGroupEntity.class));
    }

    @Test
    void delete_callsAttrGroupServiceRemoveByIds() throws Exception {
        mockMvc.perform(post("/product/attrgroup/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrGroupService).removeByIds(List.of(1L, 2L));
    }

    // ── Relation add / delete ─────────────────────────────────────────────────

    @Test
    void addRelation_callsAttrServiceSaveRelation() throws Exception {
        List<AttrGroupRelationVo> vos = List.of(relationVo(1L, 1L), relationVo(2L, 1L));

        mockMvc.perform(post("/product/attrgroup/attr/relation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).saveRelation(anyList());
    }

    @Test
    void deleteRelation_callsAttrServiceDeleteRelation() throws Exception {
        List<AttrGroupRelationVo> vos = List.of(relationVo(1L, 1L));

        mockMvc.perform(post("/product/attrgroup/attr/relation/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(attrService).deleteRelation(anyList());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private PageUtils pageOf(long total) {
        Page<AttrEntity> page = new Page<>(1, 10);
        page.setTotal(total);
        page.setRecords(Collections.emptyList());
        return new PageUtils(page);
    }

    private AttrEntity attrEntity(Long id, String name) {
        AttrEntity e = new AttrEntity();
        e.setAttrId(id);
        e.setAttrName(name);
        return e;
    }

    private AttrGroupRelationVo relationVo(Long attrId, Long attrGroupId) {
        AttrGroupRelationVo vo = new AttrGroupRelationVo();
        vo.setAttrId(attrId);
        vo.setAttrGroupId(attrGroupId);
        return vo;
    }
}
