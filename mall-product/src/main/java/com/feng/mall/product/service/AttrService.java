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
 * product attribute
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    void saveRelation(List<AttrGroupRelationVo> vos);

    void deleteRelation(List<AttrGroupRelationVo> vos);

    PageUtils getNonRelationAttr(Map<String, Object> params, Long attrGroupId);
}
