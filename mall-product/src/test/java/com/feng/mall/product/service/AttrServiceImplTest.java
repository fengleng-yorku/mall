package com.feng.mall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.dao.AttrAttrgroupRelationDao;
import com.feng.mall.product.dao.AttrGroupDao;
import com.feng.mall.product.dao.CategoryDao;
import com.feng.mall.product.entity.AttrAttrgroupRelationEntity;
import com.feng.mall.product.entity.AttrEntity;
import com.feng.mall.product.entity.AttrGroupEntity;
import com.feng.mall.product.entity.CategoryEntity;
import com.feng.mall.product.service.impl.AttrServiceImpl;
import com.feng.mall.product.vo.AttrGroupRelationVo;
import com.feng.mall.product.vo.AttrResponseVo;
import com.feng.mall.product.vo.AttrVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttrServiceImplTest {

    private AttrServiceImpl attrService;

    @Mock
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Mock
    private AttrGroupDao attrGroupDao;
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        attrService = Mockito.spy(new AttrServiceImpl());
        ReflectionTestUtils.setField(attrService, "attrAttrgroupRelationDao", attrAttrgroupRelationDao);
        ReflectionTestUtils.setField(attrService, "attrGroupDao", attrGroupDao);
        ReflectionTestUtils.setField(attrService, "categoryDao", categoryDao);
        ReflectionTestUtils.setField(attrService, "categoryService", categoryService);
    }

    // ── saveAttr ──────────────────────────────────────────────────────────────

    @Test
    void saveAttr_baseType_insertsRelation() {
        AttrVo vo = new AttrVo();
        vo.setAttrName("品牌");
        vo.setAttrType(1);
        vo.setAttrGroupId(10L);
        vo.setCatelogId(225L);

        doReturn(true).when(attrService).save(any(AttrEntity.class));

        attrService.saveAttr(vo);

        ArgumentCaptor<AttrAttrgroupRelationEntity> captor =
                ArgumentCaptor.forClass(AttrAttrgroupRelationEntity.class);
        verify(attrAttrgroupRelationDao).insert(captor.capture());
        assertThat(captor.getValue().getAttrGroupId()).isEqualTo(10L);
    }

    @Test
    void saveAttr_saleType_doesNotInsertRelation() {
        AttrVo vo = new AttrVo();
        vo.setAttrName("颜色");
        vo.setAttrType(0);
        vo.setCatelogId(225L);

        doReturn(true).when(attrService).save(any(AttrEntity.class));

        attrService.saveAttr(vo);

        verifyNoInteractions(attrAttrgroupRelationDao);
    }

    // ── getAttrInfo ───────────────────────────────────────────────────────────

    @Test
    void getAttrInfo_baseAttr_includesGroupAndCatelogInfo() {
        AttrEntity entity = attrEntity(1L, "品牌", 1, 225L);
        doReturn(entity).when(attrService).getById(1L);

        AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
        relation.setAttrGroupId(10L);
        when(attrAttrgroupRelationDao.selectOne(any())).thenReturn(relation);

        AttrGroupEntity group = new AttrGroupEntity();
        group.setAttrGroupName("主体");
        when(attrGroupDao.selectById(10L)).thenReturn(group);

        CategoryEntity category = new CategoryEntity();
        category.setCatId(225L);
        category.setName("手机");
        when(categoryDao.selectById(225L)).thenReturn(category);
        when(categoryService.findCatelogPath(225L)).thenReturn(new Long[]{1L, 2L, 225L});

        AttrResponseVo result = attrService.getAttrInfo(1L);

        assertThat(result.getAttrName()).isEqualTo("品牌");
        assertThat(result.getGroupName()).isEqualTo("主体");
        assertThat(result.getCatelogName()).isEqualTo("手机");
        assertThat(result.getCatelogPath()).containsExactly(1L, 2L, 225L);
    }

    @Test
    void getAttrInfo_saleAttr_skipsGroupLookup() {
        AttrEntity entity = attrEntity(9L, "颜色", 0, 225L);
        doReturn(entity).when(attrService).getById(9L);

        CategoryEntity category = new CategoryEntity();
        category.setCatId(225L);
        category.setName("手机");
        when(categoryDao.selectById(225L)).thenReturn(category);
        when(categoryService.findCatelogPath(225L)).thenReturn(new Long[]{1L, 2L, 225L});

        AttrResponseVo result = attrService.getAttrInfo(9L);

        assertThat(result.getGroupName()).isNull();
        verify(attrAttrgroupRelationDao, never()).selectOne(any());
    }

    @Test
    void getAttrInfo_noCatelogEntity_onlyBasicInfo() {
        AttrEntity entity = attrEntity(1L, "品牌", 1, 225L);
        doReturn(entity).when(attrService).getById(1L);

        when(attrAttrgroupRelationDao.selectOne(any())).thenReturn(null);
        when(categoryDao.selectById(225L)).thenReturn(null);

        AttrResponseVo result = attrService.getAttrInfo(1L);

        assertThat(result.getAttrName()).isEqualTo("品牌");
        assertThat(result.getCatelogName()).isNull();
        assertThat(result.getGroupName()).isNull();
    }

    // ── updateAttr ────────────────────────────────────────────────────────────

    @Test
    void updateAttr_baseType_existingRelation_updatesRelation() {
        AttrVo vo = new AttrVo();
        vo.setAttrId(1L);
        vo.setAttrType(1);
        vo.setAttrGroupId(20L);

        doReturn(true).when(attrService).updateById(any(AttrEntity.class));
        when(attrAttrgroupRelationDao.selectCount(any(QueryWrapper.class))).thenReturn(1L);

        attrService.updateAttr(vo);

        verify(attrAttrgroupRelationDao).update(
                argThat(r -> r.getAttrGroupId().equals(20L)),
                any(UpdateWrapper.class));
        verify(attrAttrgroupRelationDao, never()).insert((AttrAttrgroupRelationEntity) any());
    }

    @Test
    void updateAttr_baseType_noExistingRelation_insertsRelation() {
        AttrVo vo = new AttrVo();
        vo.setAttrId(1L);
        vo.setAttrType(1);
        vo.setAttrGroupId(20L);

        doReturn(true).when(attrService).updateById(any(AttrEntity.class));
        when(attrAttrgroupRelationDao.selectCount(any(QueryWrapper.class))).thenReturn(0L);

        attrService.updateAttr(vo);

        verify(attrAttrgroupRelationDao).insert(
                argThat((AttrAttrgroupRelationEntity r) -> r.getAttrGroupId().equals(20L) && r.getAttrId().equals(1L)));
        verify(attrAttrgroupRelationDao, never()).update(any(), any());
    }

    @Test
    void updateAttr_saleType_doesNotTouchRelationTable() {
        AttrVo vo = new AttrVo();
        vo.setAttrId(9L);
        vo.setAttrType(0);

        doReturn(true).when(attrService).updateById(any(AttrEntity.class));

        attrService.updateAttr(vo);

        verifyNoInteractions(attrAttrgroupRelationDao);
    }

    // ── getRelationAttr ───────────────────────────────────────────────────────

    @Test
    void getRelationAttr_returnsAttrsForGroup() {
        AttrAttrgroupRelationEntity r1 = new AttrAttrgroupRelationEntity();
        r1.setAttrId(1L);
        AttrAttrgroupRelationEntity r2 = new AttrAttrgroupRelationEntity();
        r2.setAttrId(2L);
        when(attrAttrgroupRelationDao.selectList(any())).thenReturn(List.of(r1, r2));

        AttrEntity a1 = attrEntity(1L, "品牌", 1, 225L);
        AttrEntity a2 = attrEntity(2L, "型号", 1, 225L);
        doReturn(List.of(a1, a2)).when(attrService).listByIds(List.of(1L, 2L));

        List<AttrEntity> result = attrService.getRelationAttr(1L);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(AttrEntity::getAttrName)
                .containsExactlyInAnyOrder("品牌", "型号");
    }

    @Test
    void getRelationAttr_noRelations_returnsNull() {
        when(attrAttrgroupRelationDao.selectList(any())).thenReturn(Collections.emptyList());

        List<AttrEntity> result = attrService.getRelationAttr(99L);

        assertThat(result).isNull();
        verify(attrService, never()).listByIds(any());
    }

    // ── saveRelation ──────────────────────────────────────────────────────────

    @Test
    void saveRelation_insertsOneRecordPerVo() {
        List<AttrGroupRelationVo> vos = List.of(
                relationVo(1L, 10L),
                relationVo(2L, 10L),
                relationVo(3L, 10L));

        attrService.saveRelation(vos);

        verify(attrAttrgroupRelationDao, times(3)).insert((AttrAttrgroupRelationEntity) any());
    }

    @Test
    void saveRelation_mapsAttrIdAndGroupIdCorrectly() {
        attrService.saveRelation(List.of(relationVo(5L, 7L)));

        ArgumentCaptor<AttrAttrgroupRelationEntity> captor =
                ArgumentCaptor.forClass(AttrAttrgroupRelationEntity.class);
        verify(attrAttrgroupRelationDao).insert(captor.capture());
        assertThat(captor.getValue().getAttrId()).isEqualTo(5L);
        assertThat(captor.getValue().getAttrGroupId()).isEqualTo(7L);
    }

    // ── deleteRelation ────────────────────────────────────────────────────────

    @Test
    void deleteRelation_callsDeleteBatchWithCorrectEntities() {
        List<AttrGroupRelationVo> vos = List.of(
                relationVo(1L, 10L),
                relationVo(2L, 10L));

        attrService.deleteRelation(vos);

        ArgumentCaptor<List<AttrAttrgroupRelationEntity>> captor = ArgumentCaptor.captor();
        verify(attrAttrgroupRelationDao).deleteBatchRelation(captor.capture());

        List<AttrAttrgroupRelationEntity> entities = captor.getValue();
        assertThat(entities).hasSize(2);
        assertThat(entities.get(0).getAttrId()).isEqualTo(1L);
        assertThat(entities.get(0).getAttrGroupId()).isEqualTo(10L);
        assertThat(entities.get(1).getAttrId()).isEqualTo(2L);
    }

    @Test
    void deleteRelation_singleRecord_deletesCorrectly() {
        attrService.deleteRelation(List.of(relationVo(3L, 5L)));

        ArgumentCaptor<List<AttrAttrgroupRelationEntity>> captor = ArgumentCaptor.captor();
        verify(attrAttrgroupRelationDao).deleteBatchRelation(captor.capture());
        assertThat(captor.getValue()).hasSize(1);
        assertThat(captor.getValue().get(0).getAttrId()).isEqualTo(3L);
        assertThat(captor.getValue().get(0).getAttrGroupId()).isEqualTo(5L);
    }

    // ── getNonRelationAttr ────────────────────────────────────────────────────

    @Test
    void getNonRelationAttr_excludesAllAlreadyAssignedAttrs() {
        AttrGroupEntity currentGroup = new AttrGroupEntity();
        currentGroup.setAttrGroupId(1L);
        currentGroup.setCatelogId(225L);
        when(attrGroupDao.selectById(1L)).thenReturn(currentGroup);

        AttrGroupEntity g1 = new AttrGroupEntity(); g1.setAttrGroupId(1L);
        AttrGroupEntity g2 = new AttrGroupEntity(); g2.setAttrGroupId(2L);
        when(attrGroupDao.selectList(any())).thenReturn(List.of(g1, g2));

        AttrAttrgroupRelationEntity r1 = new AttrAttrgroupRelationEntity(); r1.setAttrId(1L);
        AttrAttrgroupRelationEntity r2 = new AttrAttrgroupRelationEntity(); r2.setAttrId(2L);
        AttrAttrgroupRelationEntity r3 = new AttrAttrgroupRelationEntity(); r3.setAttrId(3L);
        when(attrAttrgroupRelationDao.selectList(any())).thenReturn(List.of(r1, r2, r3));

        Page<AttrEntity> mockPage = new Page<>(1, 10);
        mockPage.setTotal(0);
        mockPage.setRecords(Collections.emptyList());
        doReturn(mockPage).when(attrService).page(any(), any());

        PageUtils result = attrService.getNonRelationAttr(
                new java.util.HashMap<>(java.util.Map.of("page", "1", "limit", "10")), 1L);

        assertThat(result).isNotNull();
        // selectList called once for groups (all groups in catelog, including current)
        verify(attrGroupDao).selectList(any());
        // selectList called once for relations of those groups
        verify(attrAttrgroupRelationDao).selectList(any());
    }

    @Test
    void getNonRelationAttr_noGroupsInCatelog_skipsRelationQuery() {
        AttrGroupEntity currentGroup = new AttrGroupEntity();
        currentGroup.setAttrGroupId(99L);
        currentGroup.setCatelogId(999L);
        when(attrGroupDao.selectById(99L)).thenReturn(currentGroup);
        when(attrGroupDao.selectList(any())).thenReturn(Collections.emptyList());

        Page<AttrEntity> mockPage = new Page<>(1, 10);
        mockPage.setTotal(0);
        mockPage.setRecords(Collections.emptyList());
        doReturn(mockPage).when(attrService).page(any(), any());

        attrService.getNonRelationAttr(new java.util.HashMap<>(java.util.Map.of("page", "1", "limit", "10")), 99L);

        // When no groups exist, relation table should not be queried
        verify(attrAttrgroupRelationDao, never()).selectList(any());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private AttrEntity attrEntity(Long id, String name, int type, Long catelogId) {
        AttrEntity e = new AttrEntity();
        e.setAttrId(id);
        e.setAttrName(name);
        e.setAttrType(type);
        e.setCatelogId(catelogId);
        return e;
    }

    private AttrGroupRelationVo relationVo(Long attrId, Long attrGroupId) {
        AttrGroupRelationVo vo = new AttrGroupRelationVo();
        vo.setAttrId(attrId);
        vo.setAttrGroupId(attrGroupId);
        return vo;
    }
}
