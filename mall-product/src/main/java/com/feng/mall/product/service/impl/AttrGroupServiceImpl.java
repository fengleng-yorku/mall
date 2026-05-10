package com.feng.mall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;

import com.feng.mall.product.dao.AttrGroupDao;
import com.feng.mall.product.entity.AttrGroupEntity;
import com.feng.mall.product.service.AttrGroupService;

@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if (params.get("key") != null) {
            wrapper.and(q -> q.like("attr_group_id", params.get("key")).or().like("attr_group_name",
                    params.get("key")));
        }
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        } else {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper.eq("catelog_id", catelogId));

            return new PageUtils(page);
        }
    }

}