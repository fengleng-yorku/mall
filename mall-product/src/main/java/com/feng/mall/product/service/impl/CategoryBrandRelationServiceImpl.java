package com.feng.mall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Locale.Category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;
import com.feng.mall.product.dao.BrandDao;
import com.feng.mall.product.dao.CategoryBrandRelationDao;
import com.feng.mall.product.dao.CategoryDao;
import com.feng.mall.product.entity.CategoryBrandRelationEntity;
import com.feng.mall.product.service.CategoryBrandRelationService;

@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>
        implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>());

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {

        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

        // 1.查询品牌的名字
        String brandName = brandDao.selectById(brandId).getName();
        // 2.查询分类的名字
        String catelogName = categoryDao.selectById(catelogId).getName();

        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCatelogName(catelogName);

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity, new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId, name);


        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setCatelogId(catId);
        categoryBrandRelationEntity.setCatelogName(name);
        this.update(categoryBrandRelationEntity, new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
    }

}