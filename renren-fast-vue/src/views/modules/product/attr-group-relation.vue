<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="visible"
      title="管理关联属性"
      width="65%"
      top="5vh"
      @closed="dialogClose"
    >
      <!-- 选择属性内层弹框 -->
      <el-dialog
        width="50%"
        title="选择属性"
        :visible.sync="innerVisible"
        append-to-body
        top="8vh"
      >
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
          <el-form-item>
            <el-input
              v-model="dataForm.key"
              placeholder="搜索属性名"
              clearable
              prefix-icon="el-icon-search"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="getDataList()">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="innerSelectionChangeHandle"
          style="width: 100%;"
          empty-text="暂无可关联的属性"
        >
          <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
          <el-table-column prop="attrId" header-align="center" align="center" label="属性ID" width="80"></el-table-column>
          <el-table-column prop="attrName" header-align="center" align="center" label="属性名"></el-table-column>
          <el-table-column
            prop="valueSelect"
            header-align="center"
            align="center"
            label="可选值列表"
            show-overflow-tooltip
          ></el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          class="pagination"
        ></el-pagination>
        <div slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitAddRealtion">确认新增</el-button>
        </div>
      </el-dialog>

      <!-- 工具栏 -->
      <div class="relation-toolbar">
        <el-button type="primary" icon="el-icon-plus" size="small" @click="addRelation">新建关联</el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="small"
          @click="batchDeleteRelation"
          :disabled="dataListSelections.length <= 0"
        >批量删除</el-button>
      </div>

      <!-- 已关联属性列表 -->
      <el-table
        :data="relationAttrs"
        style="width: 100%"
        @selection-change="selectionChangeHandle"
        border
        empty-text="暂无关联属性"
      >
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="attrId" header-align="center" align="center" label="属性ID" width="80"></el-table-column>
        <el-table-column prop="attrName" header-align="center" align="center" label="属性名"></el-table-column>
        <el-table-column prop="valueSelect" header-align="center" align="center" label="可选值">
          <template slot-scope="scope">
            <template v-if="scope.row.valueSelect">
              <el-tag
                v-for="(item, index) in scope.row.valueSelect.split(';')"
                :key="index"
                size="small"
                class="value-tag"
              >{{ item }}</el-tag>
            </template>
            <span v-else class="empty-cell">-</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" header-align="center" align="center" width="100" label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="remove-btn" @click="relationRemove(scope.row.attrId)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      attrGroupId: 0,
      visible: false,
      innerVisible: false,
      relationAttrs: [],
      dataListSelections: [],
      dataForm: {
        key: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      innerdataListSelections: []
    };
  },
  computed: {},
  watch: {},
  methods: {
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    innerSelectionChangeHandle(val) {
      this.innerdataListSelections = val;
    },
    addRelation() {
      this.getDataList();
      this.innerVisible = true;
    },
    batchDeleteRelation() {
      let postData = this.dataListSelections.map(item => ({
        attrId: item.attrId,
        attrGroupId: this.attrGroupId
      }));
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(postData, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "删除成功" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      });
    },
    relationRemove(attrId) {
      let postData = [{ attrId, attrGroupId: this.attrGroupId }];
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(postData, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "删除成功" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      });
    },
    submitAddRealtion() {
      this.innerVisible = false;
      if (this.innerdataListSelections.length > 0) {
        let postData = this.innerdataListSelections.map(item => ({
          attrId: item.attrId,
          attrGroupId: this.attrGroupId
        }));
        this.$http({
          url: this.$http.adornUrl("/product/attrgroup/attr/relation"),
          method: "post",
          data: this.$http.adornData(postData, false)
        }).then(({ data }) => {
          if (data.code == 0) {
            this.$message({ type: "success", message: "新增关联成功" });
          }
          this.$emit("refreshData");
          this.init(this.attrGroupId);
        });
      }
    },
    init(id) {
      this.attrGroupId = id || 0;
      this.visible = true;
      this.$http({
        url: this.$http.adornUrl(`/product/attrgroup/${this.attrGroupId}/attr/relation`),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        this.relationAttrs = data.data;
      });
    },
    dialogClose() {},
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl(`/product/attrgroup/${this.attrGroupId}/attr/nonrelation`),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data ? data.data.list : [];
          this.totalPage = data.data ? data.data.totalCount : 0;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    }
  }
};
</script>

<style scoped>
.relation-toolbar {
  margin-bottom: 12px;
}

.pagination {
  margin-top: 12px;
  text-align: right;
}

.empty-cell {
  color: #c0c4cc;
}

.remove-btn {
  color: #f56c6c;
}

.remove-btn:hover {
  color: #f78989;
}

.value-tag {
  margin: 2px 3px;
}
</style>
