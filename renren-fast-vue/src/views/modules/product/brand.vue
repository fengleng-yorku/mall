<template>
  <div class="brand-page">
    <el-card class="brand-card">
      <div slot="header" class="brand-card__header">
        <div class="brand-card__title">
          <span class="brand-page__title">品牌管理</span>
          <span class="brand-page__desc"
            >管理商品品牌信息，支持新增、编辑、删除和状态切换</span
          >
        </div>
      </div>

      <div class="brand-search-wrapper">
        <el-form
          :inline="true"
          :model="dataForm"
          @keyup.enter.native="getDataList()"
          class="brand-search-form"
        >
          <el-form-item>
            <el-input
              v-model="dataForm.key"
              placeholder="请输入品牌名称"
              clearable
              prefix-icon="el-icon-search"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="getDataList()"
              icon="el-icon-search"
              >查询</el-button
            >
            <el-button
              v-if="isAuth('product:brand:save')"
              type="success"
              @click="addOrUpdateHandle()"
              icon="el-icon-plus"
              >新增品牌</el-button
            >
            <el-button
              v-if="isAuth('product:brand:delete')"
              type="danger"
              @click="deleteHandle()"
              :disabled="dataListSelections.length <= 0"
              icon="el-icon-delete"
              >批量删除</el-button
            >
          </el-form-item>
        </el-form>
      </div>

      <div class="brand-table-wrapper">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandle"
          style="width: 100%"
          :header-cell-style="{ background: '#f5f7fa', color: '#606266' }"
        >
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50"
          >
          </el-table-column>
          <el-table-column
            prop="brandId"
            header-align="center"
            align="center"
            label="品牌ID"
            width="80"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            header-align="center"
            align="center"
            label="品牌名称"
            min-width="120"
          >
          </el-table-column>
          <el-table-column
            prop="logo"
            header-align="center"
            align="center"
            label="品牌Logo"
            width="100"
          >
            <template slot-scope="scope">
              <img
                v-if="scope.row.logo"
                :src="scope.row.logo"
                class="brand-logo"
                alt="品牌Logo"
              />
              <span v-else class="no-logo">—</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="descript"
            header-align="center"
            align="center"
            label="品牌描述"
            min-width="150"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="showStatus"
            header-align="center"
            align="center"
            label="显示状态"
            width="100"
          >
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.showStatus"
                :active-value="1"
                :inactive-value="0"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="updatebrandstatus(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column
            prop="firstLetter"
            header-align="center"
            align="center"
            label="首字母"
            width="80"
          >
          </el-table-column>
          <el-table-column
            prop="sort"
            header-align="center"
            align="center"
            label="排序"
            width="80"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="180"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click="updateCatelogHandle(scope.row.brandId)"
                icon="el-icon-link"
                >关联分类</el-button
              >
              <el-button
                type="text"
                size="small"
                @click="addOrUpdateHandle(scope.row.brandId)"
                icon="el-icon-edit"
                >编辑</el-button
              >
              <el-button
                type="text"
                size="small"
                @click="deleteHandle(scope.row.brandId, scope.row.name)"
                icon="el-icon-delete"
                style="color: #f56c6c"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="brand-pagination-wrapper">
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          background
        >
        </el-pagination>
      </div>

      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
      ></add-or-update>

      <el-dialog
        title="关联分类"
        :visible.sync="cateRelationDialogVisible"
        width="30%"
      >
        <el-popover placement="right-end" v-model="popCatelogSelectVisible">
          <category-cascader
            :catelogPath.sync="catelogPath"
          ></category-cascader>
          <div style="text-align: right; margin: 0">
            <el-button
              size="mini"
              type="text"
              @click="popCatelogSelectVisible = false"
              >取消</el-button
            >
            <el-button type="primary" size="mini" @click="addCatelogSelect"
              >确定</el-button
            >
          </div>
          <el-button slot="reference">新增关联</el-button>
        </el-popover>
        <el-table :data="cateRelationTableData" style="width: 100%">
          <el-table-column prop="id" label="#"></el-table-column>
          <el-table-column prop="brandName" label="品牌名"></el-table-column>
          <el-table-column prop="catelogName" label="分类名"></el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click="
                  deleteCateRelationHandle(scope.row.id, scope.row.brandId)
                "
                >移除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cateRelationDialogVisible = false"
            >取 消</el-button
          >
          <el-button type="primary" @click="cateRelationDialogVisible = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import AddOrUpdate from "./brand-add-or-update";
import CategoryCascader from "../common/category-cascader";
export default {
  data() {
    return {
      dataForm: {
        key: "",
      },
      brandId: 0,
      catelogPath: [],
      dataList: [],
      cateRelationTableData: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      cateRelationDialogVisible: false,
      popCatelogSelectVisible: false,
    };
  },
  components: {
    AddOrUpdate,
    CategoryCascader,
  },
  activated() {
    this.getDataList();
  },
  methods: {
    addCatelogSelect() {
      //{"brandId":1,"catelogId":2}
      this.popCatelogSelectVisible =false;
      this.$http({
        url: this.$http.adornUrl("/product/categorybrandrelation/save"),
        method: "post",
        data: this.$http.adornData({brandId:this.brandId,catelogId:this.catelogPath[this.catelogPath.length-1]}, false)
      }).then(({ data }) => {
        this.getCateRelation();
      });
    },
    deleteCateRelationHandle(id, brandId) {
      this.$http({
        url: this.$http.adornUrl("/product/categorybrandrelation/delete"),
        method: "post",
        data: this.$http.adornData([id], false)
      }).then(({ data }) => {
        this.getCateRelation();
      });
    },
    updateCatelogHandle(brandId) {
      this.cateRelationDialogVisible = true;
      this.brandId = brandId;
      this.getCateRelation();
    },
    getCateRelation() {
      this.$http({
        url: this.$http.adornUrl("/product/categorybrandrelation/catelog/list"),
        method: "get",
        params: this.$http.adornParams({
          brandId: this.brandId
        })
      }).then(({ data }) => {
        this.cateRelationTableData = data.data;
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/product/brand/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    updatebrandstatus(row) {
      this.$http({
        url: this.$http.adornUrl("/product/brand/updateStatus"),
        method: "post",
        data: this.$http.adornData({
          brandId: row.brandId,
          showStatus: row.showStatus,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1500,
            onClose: () => {
              this.getDataList();
            },
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id, name) {
      var ids = id
        ? [id]
        : this.dataListSelections.map((item) => {
            return item.brandId;
          });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl("/product/brand/delete"),
          method: "post",
          data: this.$http.adornData(ids, false),
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
  },
};
</script>
<style scoped>
.brand-page {
  padding: 16px 0;
}
.brand-card {
  width: 100%;
  min-height: 600px;
}
.brand-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
}
.brand-card__title {
  display: flex;
  flex-direction: column;
}
.brand-page__title {
  font-size: 18px;
  font-weight: 600;
}
.brand-page__desc {
  color: #909399;
  margin-top: 4px;
  font-size: 13px;
}
.brand-search-wrapper {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
}
.brand-search-form {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}
.brand-table-wrapper {
  margin-bottom: 16px;
}
.brand-logo {
  width: 50px;
  height: 50px;
  object-fit: contain;
  border-radius: 4px;
  border: 1px solid #ebeef5;
}
.no-logo {
  color: #ccc;
  font-size: 14px;
}
.brand-pagination-wrapper {
  display: flex;
  justify-content: center;
  padding: 16px 0;
}
</style>
