import axios from 'axios'
import http from '@/utils/httpRequest'

export const S3_BASE = 'https://feng-mall-upload-picture.s3.ca-central-1.amazonaws.com/'

export async function uploadToS3 (file, onProgress) {
  const { data } = await http({
    url: http.adornUrl('/third-party/presign'),
    method: 'post',
    data: http.adornData({ filename: file.name, contentType: file.type }, false)
  })

  await axios.put(data.uploadUrl, file, {
    headers: { 'Content-Type': file.type },
    onUploadProgress: (e) => {
      if (onProgress) onProgress(Math.round((e.loaded / e.total) * 100))
    }
  })

  return data.s3Key
}
