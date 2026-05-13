package com.feng.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feng.mall.product.entity.AttrEntity;
import com.feng.mall.product.entity.AttrGroupEntity;
import com.feng.mall.product.service.AttrGroupService;
import com.feng.mall.product.service.AttrService;
import com.feng.mall.product.service.CategoryService;
import com.feng.mall.product.vo.AttrGroupRelationVo;
import com.feng.mall.product.vo.AttrGroupWithAttrsVo;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST controller for managing attribute groups and their associations with
 * attributes.
 * Base path: /product/attrgroup
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public AttrService attrService;

    /**
     * Returns a paginated list of attribute groups filtered by category.
     * catelogId = 0 means no category filter — returns all groups.
     */
    @RequestMapping("/list/{catelogId}")
    // @RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {

        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }

    /**
     * Returns detail of a single attribute group, including its full category path
     * (e.g. [Phone, Storage]) so the frontend can render breadcrumb navigation.
     */
    @RequestMapping("/info/{attrGroupId}")
    // @RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        // Attach the full category path array for breadcrumb display on the frontend
        attrGroup.setCatelogPath(categoryService.findCatelogPath(attrGroup.getCatelogId()));
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * Returns all base attributes currently linked to the given attribute group.
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> attrList = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", attrList);
    }

    /**
     * Returns paginated base attributes not yet linked to any group in the same
     * category,
     * used to populate the "add relation" picker on the frontend.
     */
    @GetMapping("/{attrGroupId}/attr/nonrelation")
    public R attrnonRelation(@PathVariable Long attrGroupId, @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNonRelationAttr(params, attrGroupId);
        return R.ok().put("data", page);
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {

        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);

        return R.ok().put("data", vos);
    }

    /** Creates a new attribute group. */
    @RequestMapping("/save")
    // @RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /** Updates an existing attribute group by its primary key. */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /** Deletes one or more attribute groups by their IDs. */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /** Links one or more attributes to their respective attribute groups. */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrService.saveRelation(vos);
        return R.ok();
    }

    /**
     * Removes attribute-group associations.
     * Uses a batch delete keyed on (attr_id, attr_group_id) pairs.
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

}
