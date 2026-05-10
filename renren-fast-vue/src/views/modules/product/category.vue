<template>
  <div class="category-page">
    <el-card class="category-card">
      <div slot="header" class="category-card__header">
        <div class="category-card__title">
          <span class="category-page__title">商品分类管理</span>
          <span class="category-page__desc">支持拖拽排序、批量操作、分类编辑与新增</span>
        </div>
        <div class="category-card__toolbar">
          <el-switch v-model="draggable" active-text="开启拖拽" inactive-text="关闭拖拽"></el-switch>
          <el-button type="success" size="mini" icon="el-icon-plus" @click="appendRoot">新增顶级分类</el-button>
          <el-button v-if="draggable" type="primary" size="mini" @click="batchSave">批量保存</el-button>
          <el-button type="danger" size="mini" @click="batchDelete">批量删除</el-button>
        </div>
      </div>

      <div class="category-tree-wrapper">
        <el-tree
          :data="menus"
          :props="defaultProps"
          :expand-on-click-node="false"
          show-checkbox
          node-key="catId"
          :default-expanded-keys="expandedKey"
          :draggable="draggable"
          :allow-drop="allowDrop"
          @node-drop="handleDrop"
          ref="menuTree"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span class="node-main">
              <span class="node-name">{{ node.label }}</span>
              <el-tag v-if="data.productUnit" type="info" size="mini" class="node-unit">{{ data.productUnit }}</el-tag>
              <span v-if="data.icon" class="node-icon">{{ data.icon }}</span>
            </span>
            <span class="node-actions">
              <el-button
                v-if="node.level <= 2"
                type="text"
                size="mini"
                @click="append(data)"
              >添加</el-button>
              <el-button type="text" size="mini" @click="edit(data)">编辑</el-button>
              <el-button
                v-if="node.childNodes.length == 0"
                type="text"
                size="mini"
                @click="() => remove(node, data)"
              >删除</el-button>
            </span>
          </span>
        </el-tree>
      </div>

      <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="420px"
        :close-on-click-modal="false"
      >
        <el-form :model="category" label-width="90px">
          <el-form-item label="分类名称">
            <el-input v-model="category.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="图标">
            <el-input v-model="category.icon" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="计量单位">
            <el-input v-model="category.productUnit" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitData">确定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  props: {},
  data() {
    return {
      pCid: [],
      draggable: false,
      updateNodes: [],
      maxLevel: 0,
      title: "",
      dialogType: "", //edit,add
      category: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        productUnit: "",
        icon: "",
        catId: null
      },
      dialogVisible: false,
      menus: [],
      expandedKey: [],
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },

  //计算属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    getMenus() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        console.log("成功获取到菜单数据...", data.data);
        this.menus = data.data;
      });
    },
    batchDelete() {
      let catIds = [];
      let checkedNodes = this.$refs.menuTree.getCheckedNodes();
      console.log("被选中的元素", checkedNodes);
      for (let i = 0; i < checkedNodes.length; i++) {
        catIds.push(checkedNodes[i].catId);
      }
      this.$confirm(`是否批量删除【${catIds}】菜单?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(catIds, false)
          }).then(({ data }) => {
            this.$message({
              message: "菜单批量删除成功",
              type: "success"
            });
            this.getMenus();
          });
        })
        .catch(() => {});
    },
    batchSave() {
      this.$http({
        url: this.$http.adornUrl("/product/category/update/sort"),
        method: "post",
        data: this.$http.adornData(this.updateNodes, false)
      }).then(({ data }) => {
        this.$message({
          message: "菜单顺序等修改成功",
          type: "success"
        });
        //刷新出新的菜单
        this.getMenus();
        //设置需要默认展开的菜单
        this.expandedKey = this.pCid;
        this.updateNodes = [];
        this.maxLevel = 0;
        // this.pCid = 0;
      });
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log("handleDrop: ", draggingNode, dropNode, dropType);
      //1、当前节点最新的父节点id
      let pCid = 0;
      let siblings = null;
      if (dropType == "before" || dropType == "after") {
        pCid =
          dropNode.parent.data.catId == undefined
            ? 0
            : dropNode.parent.data.catId;
        siblings = dropNode.parent.childNodes;
      } else {
        pCid = dropNode.data.catId;
        siblings = dropNode.childNodes;
      }
      this.pCid.push(pCid);

      //2、当前拖拽节点的最新顺序，
      for (let i = 0; i < siblings.length; i++) {
        if (siblings[i].data.catId == draggingNode.data.catId) {
          //如果遍历的是当前正在拖拽的节点
          let catLevel = draggingNode.level;
          if (siblings[i].level != draggingNode.level) {
            //当前节点的层级发生变化
            catLevel = siblings[i].level;
            //修改他子节点的层级
            this.updateChildNodeLevel(siblings[i]);
          }
          this.updateNodes.push({
            catId: siblings[i].data.catId,
            sort: i,
            parentCid: pCid,
            catLevel: catLevel
          });
        } else {
          this.updateNodes.push({ catId: siblings[i].data.catId, sort: i });
        }
      }

      //3、当前拖拽节点的最新层级
      console.log("updateNodes", this.updateNodes);
    },
    updateChildNodeLevel(node) {
      if (node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          var cNode = node.childNodes[i].data;
          this.updateNodes.push({
            catId: cNode.catId,
            catLevel: node.childNodes[i].level
          });
          this.updateChildNodeLevel(node.childNodes[i]);
        }
      }
    },
    allowDrop(draggingNode, dropNode, type) {
      //1、被拖动的当前节点以及所在的父节点总层数不能大于3

      //1）、被拖动的当前节点总层数
      console.log("allowDrop:", draggingNode, dropNode, type);
      //
      this.countNodeLevel(draggingNode);
      //当前正在拖动的节点+父节点所在的深度不大于3即可
      let deep = Math.abs(this.maxLevel - draggingNode.level) + 1;
      console.log("深度：", deep);

      //   this.maxLevel
      if (type == "inner") {
        // console.log(
        //   `this.maxLevel：${this.maxLevel}；draggingNode.data.catLevel：${draggingNode.data.catLevel}；dropNode.level：${dropNode.level}`
        // );
        return deep + dropNode.level <= 3;
      } else {
        return deep + dropNode.parent.level <= 3;
      }
    },
    countNodeLevel(node) {
      //找到所有子节点，求出最大深度
      if (node.childNodes != null && node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          if (node.childNodes[i].level > this.maxLevel) {
            this.maxLevel = node.childNodes[i].level;
          }
          this.countNodeLevel(node.childNodes[i]);
        }
      }
    },
    edit(data) {
      console.log("要修改的数据", data);
      this.dialogType = "edit";
      this.title = "修改分类";
      this.dialogVisible = true;

      //发送请求获取当前节点最新的数据
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: "get"
      }).then(({ data }) => {
        //请求成功
        console.log("要回显的数据", data);
        this.category.name = data.data.name;
        this.category.catId = data.data.catId;
        this.category.icon = data.data.icon;
        this.category.productUnit = data.data.productUnit;
        this.category.parentCid = data.data.parentCid;
        this.category.catLevel = data.data.catLevel;
        this.category.sort = data.data.sort;
        this.category.showStatus = data.data.showStatus;
      });
    },
    appendRoot() {
      this.dialogType = "add";
      this.title = "添加顶级分类";
      this.dialogVisible = true;
      this.category.parentCid = 0;
      this.category.catLevel = 1;
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;
    },
    append(data) {
      console.log("append", data);
      this.dialogType = "add";
      this.title = "添加分类";
      this.dialogVisible = true;
      this.category.parentCid = data.catId;
      this.category.catLevel = data.catLevel * 1 + 1;
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;
    },

    submitData() {
      if (this.dialogType == "add") {
        this.addCategory();
      }
      if (this.dialogType == "edit") {
        this.editCategory();
      }
    },
    //修改三级分类数据
    editCategory() {
      var { catId, name, icon, productUnit } = this.category;
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "post",
        data: this.$http.adornData({ catId, name, icon, productUnit }, false)
      }).then(({ data }) => {
        this.$message({
          message: "菜单修改成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.getMenus();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      });
    },
    //添加三级分类
    addCategory() {
      console.log("提交的三级分类数据", this.category);
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.category, false)
      }).then(({ data }) => {
        this.$message({
          message: "菜单保存成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.getMenus();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      });
    },

    remove(node, data) {
      var ids = [data.catId];
      this.$confirm(`是否删除【${data.name}】菜单?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({ data }) => {
            this.$message({
              message: "菜单删除成功",
              type: "success"
            });
            //刷新出新的菜单
            this.getMenus();
            //设置需要默认展开的菜单
            this.expandedKey = [node.parent.data.catId];
          });
        })
        .catch(() => {});

      console.log("remove", node, data);
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getMenus();
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style scoped>
.category-page {
  padding: 16px 0;
}
.category-card {
  width: 100%;
  min-height: 620px;
}
.category-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
}
.category-card__title {
  display: flex;
  flex-direction: column;
}
.category-page__title {
  font-size: 18px;
  font-weight: 600;
}
.category-page__desc {
  color: #909399;
  margin-top: 4px;
  font-size: 13px;
}
.category-card__toolbar {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}
.category-tree-wrapper {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 18px;
  margin-top: 10px;
}
.custom-tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 2px 6px;
}
.node-main {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
}
.node-name {
  font-weight: 500;
  color: #303133;
}
.node-unit {
  margin-left: 0;
}
.node-icon {
  color: #909399;
  font-size: 12px;
}
.node-actions {
  display: flex;
  align-items: center;
  gap: 4px;
}
.category-tree-wrapper .el-tree-node__content {
  min-height: 44px;
}
.category-tree-wrapper .el-tree-node__content:hover {
  background-color: #f5f7fa;
}
.dialog-footer {
  text-align: right;
}
</style>