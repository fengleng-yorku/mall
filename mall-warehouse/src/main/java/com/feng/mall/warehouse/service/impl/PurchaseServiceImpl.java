package com.feng.mall.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.constant.WarehouseConstant;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;
import com.feng.mall.warehouse.Vo.MergeVo;
import com.feng.mall.warehouse.dao.PurchaseDao;
import com.feng.mall.warehouse.entity.PurchaseDetailEntity;
import com.feng.mall.warehouse.entity.PurchaseEntity;
import com.feng.mall.warehouse.service.PurchaseDetailService;
import com.feng.mall.warehouse.service.PurchaseService;

@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService detailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1));

        return new PageUtils(page);

    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WarehouseConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);

            purchaseId = purchaseEntity.getId();
        } else {
            PurchaseEntity purchase = this.getById(purchaseId);
            int status = purchase.getStatus();
            if (status != WarehouseConstant.PurchaseStatusEnum.CREATED.getCode()
                    && status != WarehouseConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return;
            }
        }

        List<Long> items = mergeVo.getItems();
        Long finalPruchaseId = purchaseId;
        List<PurchaseDetailEntity> collection = items.stream().map(i -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();

            detailEntity.setId(i);
            detailEntity.setPurchaseId(finalPruchaseId);
            detailEntity.setStatus(WarehouseConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());

            return detailEntity;
        }).collect(Collectors.toList());

        detailService.updateBatchById(collection);

    }

    @Override
    public void received(List<Long> ids) {

        List<PurchaseEntity> collection = ids.stream().map(id -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
        }).filter(item -> {
            if (item.getStatus() == WarehouseConstant.PurchaseStatusEnum.CREATED.getCode()
                    || item.getStatus() == WarehouseConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            } else {
                return false;
            }
        }).map(item -> {
            item.setStatus(WarehouseConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collection);

        collection.forEach((item) -> {
            List<PurchaseDetailEntity> details = detailService.listDetailByPurchaseId(item.getId());
            List<PurchaseDetailEntity> detailsCollection = details.stream().map(detail -> {
                PurchaseDetailEntity entity = new PurchaseDetailEntity();
                entity.setId(detail.getId());
                entity.setStatus(WarehouseConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return entity;
            }).collect(Collectors.toList());

            detailService.updateBatchById(detailsCollection);
        });

    }

}