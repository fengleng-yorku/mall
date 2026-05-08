package com.feng.mall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;

import com.feng.mall.product.dao.CategoryDao;
import com.feng.mall.product.entity.CategoryEntity;
import com.feng.mall.product.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        // 1. 查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 2. 一级菜单
        List<CategoryEntity> level1 = entities.stream().filter((categoryEntity) -> categoryEntity.getParentCid() == 0)
                .map((level) -> {
                    level.setChildren(getChildren(level, entities));
                    return level;
                }).sorted((o1, o2) -> o1.getSort() == null ? 0 : o1.getSort().compareTo(o2.getSort()))
                .collect(Collectors.toList());

        return level1;
    }

    // 递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter((categoryEntity) -> categoryEntity.getParentCid() == root.getCatId())
                .map((level) -> {
                    level.setChildren(getChildren(level, all));
                    return level;
                }).sorted((o1, o2) -> {
                    return o1.getSort() == null ? 0 : o1.getSort() - o2.getSort();
                })
                .collect(Collectors.toList());
        return children;
    }

    @Override
    public void removeCategoryByIds(List<Long> asList) {
        
        //物理删除
        baseMapper.deleteByIds(asList);
    }

}