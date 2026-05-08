<!--  -->
<template>
  <div>
    <el-tree
      :data="menus"
      :props="defaultProps"
      :expand-on-click-node="false"
      :show-checkbox="true"
      node-key="catId"
      :default-expanded-keys="expandedKeys"
      draggable="true"
      :allow-drop="allowDrop"
    >
      <span class="tree-node" slot-scope="{ node, data: nodeData }">
        <span class="tree-node__label">{{
          nodeData.name || nodeData.label
        }}</span>
        <span class="tree-node__actions">
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click.stop="append(nodeData)"
            >新增</el-button
          >
          <el-button type="text" size="mini" @click.stop="edit(nodeData)"
            >修改</el-button
          >
          <el-button
            v-if="node.childNodes.length == 0"
            type="text"
            size="mini"
            class="btn-delete"
            @click.stop="remove(node, nodeData)"
            >删除</el-button
          >
        </span>
      </span>
    </el-tree>

    <el-dialog
      :title="dialogType === 'add' ? '新增分类' : '修改分类'"
      :visible.sync="dialogVisible"
      width="400px"
    >
      <el-form :model="newCategory" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="newCategory.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="newCategory.icon" placeholder="请输入图标URL" />
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input
            v-model="newCategory.productUnit"
            placeholder="请输入计量单位"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="dialogType === 'add' ? submitAppend() : submitEdit()"
          >确定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
      menus: [],
      expandedKeys: [],
      dataListLoading: false,
      dialogVisible: false,
      dialogType: "add",
      newCategory: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        icon: "",
        productUnit: "",
      },
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
  methods: {
    fetchCategories() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/product/category/list"),
        method: "get",
      })
        .then(({ data }) => {
          this.menus = data.categories || [];
        })
        .finally(() => {
          this.dataListLoading = false;
        });
    },
    allowDrop(draggingNode, dropNode, type) {
      // 只允许拖动到同级或下级
      if (type === "inner") {
        return true;
      }
      return draggingNode.data.catLevel === dropNode.data.catLevel;
    },
    append(data) {
      this.dialogType = "add";
      this.newCategory = {
        catId: null,
        name: "",
        parentCid: data.catId,
        catLevel: data.catLevel * 1 + 1,
        showStatus: 1,
        sort: 0,
        icon: "",
        productUnit: "",
      };
      this.dialogVisible = true;
    },
    submitAppend() {
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.newCategory, false),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "新增成功",
            type: "success",
            duration: 1500,
          });
          this.dialogVisible = false;
          this.fetchCategories();
          this.expandedKeys = [this.newCategory.parentCid];
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    edit(data) {
      this.dialogType = "edit";
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          const category = data.category || {};
          this.newCategory = {
            catId: category.catId,
            name: category.name,
            icon: category.icon || "",
            productUnit: category.productUnit || "",
            parentCid: category.parentCid,
            catLevel: category.catLevel,
            showStatus: category.showStatus,
            sort: category.sort,
          };
          this.dialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    submitEdit() {
      var { catId, name, icon, productUnit } = this.newCategory;
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "post",
        data: this.$http.adornData({ catId, name, icon, productUnit }, false),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "修改成功",
            type: "success",
            duration: 1500,
          });
          this.dialogVisible = false;
          this.fetchCategories();
          this.expandedKeys = [this.newCategory.parentCid];
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    remove(node, data) {
      var ids = [data.catId];
      this.$confirm(`确定删除【${data.name}】分类?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "删除成功",
                type: "success",
                duration: 1500,
              });
              this.fetchCategories();
              this.expandedKeys = [node.parent.data.catId];
            } else {
              this.$message.error(data.msg);
            }
          });
        })
        .catch(() => {});
    },
  },
  components: {},
  computed: {},
  watch: {},
  created() {
    this.fetchCategories();
  },
  mounted() {},
};
</script>
<style scoped>
.tree-node {
  display: inline-flex;
  align-items: center;
}

.tree-node__actions {
  display: none;
  margin-left: 8px;
}

.tree-node:hover .tree-node__actions {
  display: inline-flex;
  gap: 2px;
}

.btn-delete {
  color: #f56c6c;
}

.btn-delete:hover {
  color: #f56c6c;
  opacity: 0.8;
}
</style>
