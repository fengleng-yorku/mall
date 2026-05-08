<template>
  <el-dialog
    :title="!dataForm.brandId ? '新增品牌' : '修改品牌'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="500px"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="100px"
      class="brand-form"
    >
      <el-form-item label="品牌名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入品牌名称" />
      </el-form-item>
      <el-form-item label="品牌Logo" prop="logo">
        <div class="logo-uploader">
          <img v-if="previewUrl || dataForm.logo" :src="previewUrl || dataForm.logo" class="logo-preview" />
          <div v-else class="logo-placeholder">
            <i class="el-icon-plus" />
          </div>
          <input
            ref="fileInput"
            type="file"
            accept="image/*"
            class="logo-file-input"
            :disabled="uploading"
            @change="handleFile"
          />
        </div>
        <div v-if="uploading" class="upload-progress">
          <el-progress :percentage="progress" />
        </div>
        <div v-if="uploadError" class="upload-error">{{ uploadError }}</div>
      </el-form-item>
      <el-form-item label="品牌描述" prop="descript">
        <el-input v-model="dataForm.descript" placeholder="请输入品牌描述" />
      </el-form-item>
      <el-form-item label="显示状态" prop="showStatus">
        <el-switch
          v-model="dataForm.showStatus"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-text="显示"
          inactive-text="隐藏"
        />
      </el-form-item>
      <el-form-item label="检索首字母" prop="firstLetter">
        <el-input v-model="dataForm.firstLetter" placeholder="请输入检索首字母" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="dataForm.sort" :min="0" controls-position="right" />
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :disabled="uploading" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { uploadToS3, S3_BASE } from '@/composables/useS3Upload'

  export default {  
    data () {
      return {
        visible: false,
        uploading: false,
        progress: 0,
        uploadError: '',
        pendingFile: null,
        previewUrl: '',
        dataForm: {
          brandId: 0,
          name: '',
          logo: '',
          descript: '',
          showStatus: 1,
          firstLetter: '',
          sort: 0
        },
        dataRule: {
          name: [
            { required: true, message: '品牌名称不能为空', trigger: 'blur' }
          ],
          logo: [
            { required: true, message: '请上传品牌Logo', trigger: 'change' }
          ],
          descript: [
            { required: true, message: '品牌描述不能为空', trigger: 'blur' }
          ],
          firstLetter: [
            { required: true, message: '检索首字母不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.brandId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.pendingFile = null
          this.previewUrl = ''
          this.uploadError = ''
          if (this.dataForm.brandId) {
            this.$http({
              url: this.$http.adornUrl(`/product/brand/info/${this.dataForm.brandId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.brand.name
                this.dataForm.logo = data.brand.logo
                this.dataForm.descript = data.brand.descript
                this.dataForm.showStatus = data.brand.showStatus
                this.dataForm.firstLetter = data.brand.firstLetter
                this.dataForm.sort = data.brand.sort
              }
            })
          }
        })
      },
      handleFile (e) {
        const file = e.target.files[0]
        if (!file) return
        this.pendingFile = file
        this.previewUrl = URL.createObjectURL(file)
        this.dataForm.logo = this.previewUrl
        this.uploadError = ''
      },
      async doUpload () {
        if (!this.pendingFile) return
        const ext = this.pendingFile.name.split('.').pop()
        const renamedFile = new File([this.pendingFile], `${this.dataForm.name}.${ext}`, { type: this.pendingFile.type })
        this.uploading = true
        this.progress = 0
        try {
          const s3Key = await uploadToS3(renamedFile, (p) => { this.progress = p })
          this.dataForm.logo = S3_BASE + s3Key
          this.pendingFile = null
        } catch (err) {
          this.uploadError = '上传失败：' + err.message
          throw err
        } finally {
          this.uploading = false
        }
      },
      async dataFormSubmit () {
        this.$refs['dataForm'].validate(async (valid) => {
          if (valid) {
            try {
              await this.doUpload()
            } catch (e) {
              return
            }
            this.$http({
              url: this.$http.adornUrl(`/product/brand/${!this.dataForm.brandId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'brandId': this.dataForm.brandId || undefined,
                'name': this.dataForm.name,
                'logo': this.dataForm.logo,
                'descript': this.dataForm.descript,
                'showStatus': this.dataForm.showStatus,
                'firstLetter': this.dataForm.firstLetter,
                'sort': this.dataForm.sort
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>

<style scoped>
.brand-form {
  padding: 10px 20px 0;
}

.logo-uploader {
  position: relative;
  width: 100px;
  height: 100px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
}

.logo-uploader:hover {
  border-color: #409eff;
}

.logo-preview {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.logo-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #8c939d;
}

.logo-file-input {
  position: absolute;
  inset: 0;
  opacity: 0;
  cursor: pointer;
  width: 100%;
  height: 100%;
}

.upload-progress {
  margin-top: 6px;
  width: 200px;
}

.upload-error {
  margin-top: 4px;
  font-size: 12px;
  color: #f56c6c;
}
</style>
