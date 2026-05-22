package com.feng.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.AttrEntity;
import com.feng.mall.product.vo.AttrGroupRelationVo;
import com.feng.mall.product.vo.AttrResponseVo;
import com.feng.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * Service interface for product attribute operations, including
 * attribute CRUD and attribute-group association management.
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
public interface AttrService extends IService<AttrEntity> {

    /** Paginate all attributes with no filters. */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * Saves an attribute and, for base-type attrs, also inserts the
     * attr-group relation record. Sale-type attrs skip the relation insert.
     */
    void saveAttr(AttrVo attr);

    /**
     * Paginate attributes by type ("base" or "sale") and optional category.
     * Each result is enriched with its category name and group name.
     */
    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    /**
     * Returns full attribute detail enriched with category path and group name,
     * used by the edit form to pre-populate all fields.
     */
    AttrResponseVo getAttrInfo(Long attrId);

    /**
     * Updates an attribute. For base-type attrs, upserts the attr-group relation:
     * updates if one exists, inserts if not.
     */
    void updateAttr(AttrVo attrVo);

    /** Returns all base attributes linked to the given attribute group. */
    List<AttrEntity> getRelationAttr(Long attrGroupId);

    /** Batch-inserts attr-group relation records. */
    void saveRelation(List<AttrGroupRelationVo> vos);

    /** Batch-deletes attr-group relations by (attr_id, attr_group_id) pairs. */
    void deleteRelation(List<AttrGroupRelationVo> vos);

    /**
     * Returns paginated base attributes not yet associated with any group in the
     * same category. Excludes all attrs already linked to any group of that category,
     * not just the current group, to prevent duplicate cross-group assignments.
     */
    PageUtils getNonRelationAttr(Map<String, Object> params, Long attrGroupId);

    List<Long> selectSearchAttrs(List<Long> attrIds);
}
