<template>
  <section class="zhanwei-page tupian-guanli-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>图片管理</h2>
      <p>统一管理 uploads 目录下的首页、环境、登录、赞助、头像、默认图和验证码图片。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>图片分类</span>
        <select v-model="query.type" @change="loadImages">
          <option value="">全部分类</option>
          <option v-for="item in typeOptions" :key="item.value" :value="item.value">{{ item.label }}</option>
        </select>
      </label>
      <label><span>文件名</span><input v-model.trim="query.keyword" placeholder="按文件名搜索" @keyup.enter="loadImages" /></label>
      <button type="button" @click="loadImages">查询</button>
      <button type="button" class="plain" @click="resetQuery">重置</button>
      <button type="button" @click="openUpload">上传图片</button>
    </div>

    <div class="tupian-grid">
      <article v-for="item in imageList" :key="`${item.type}/${item.name}`" class="tupian-card">
        <button type="button" class="tupian-preview-btn" @click="openPreview(item)">
          <img :src="cacheUrl(item.url)" :alt="item.name" @error="fallbackImage" />
        </button>
        <div class="tupian-info">
          <strong>{{ item.name }}</strong>
          <span>{{ typeLabel(item.type) }}</span>
          <code>{{ item.url }}</code>
          <span>{{ fileSize(item.size) }} · {{ item.lastModified }}</span>
        </div>
        <div class="tupian-actions">
          <button type="button" @click="openPreview(item)">预览</button>
          <button type="button" @click="openReplace(item)">替换</button>
          <button type="button" @click="copyPath(item.url)">复制路径</button>
          <button type="button" class="danger" @click="removeImage(item)">删除</button>
        </div>
      </article>
      <p v-if="!imageList.length" class="kong">暂无图片</p>
    </div>

    <div v-if="uploadVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>上传图片</h3>
        <div class="modal-form">
          <label>
            <span>图片分类</span>
            <select v-model="uploadForm.type">
              <option v-for="item in typeOptions" :key="item.value" :value="item.value">{{ item.label }}</option>
            </select>
          </label>
          <label>
            <span>选择图片</span>
            <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="onUploadFileChange" />
          </label>
        </div>
        <p class="modal-hint">只允许 jpg、jpeg、png、webp，单张不超过 5MB。</p>
        <div class="modal-actions">
          <button type="button" @click="submitUpload">上传</button>
          <button type="button" class="plain" @click="uploadVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="replaceVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>替换图片</h3>
        <div class="replace-current">
          <img :src="cacheUrl(currentImage?.url)" :alt="currentImage?.name" @error="fallbackImage" />
          <div>
            <strong>{{ currentImage?.name }}</strong>
            <code>{{ currentImage?.url }}</code>
            <span>替换后会保留原文件名，页面引用该路径的位置会自动使用新图片。</span>
          </div>
        </div>
        <label class="file-field">
          <span>选择新图片</span>
          <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="onReplaceFileChange" />
        </label>
        <div class="modal-actions">
          <button type="button" @click="submitReplace">确认替换</button>
          <button type="button" class="plain" @click="replaceVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="previewVisible" class="modal-mask" @click.self="previewVisible = false">
      <div class="modal-panel preview-panel">
        <h3>{{ currentImage?.name }}</h3>
        <img :src="cacheUrl(currentImage?.url)" :alt="currentImage?.name" @error="fallbackImage" />
        <code>{{ currentImage?.url }}</code>
        <div class="modal-actions">
          <button type="button" @click="openInNewTab(currentImage?.url)">打开预览</button>
          <button type="button" @click="copyPath(currentImage?.url)">复制路径</button>
          <button type="button" class="plain" @click="previewVisible = false">关闭</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { deleteImage, getImageList, replaceImage, uploadImage } from '../../api/wenjian'
import { updateTupianBanben } from '../../utils/tupianBanben'

const typeOptions = [
  { value: 'home', label: '首页轮播' },
  { value: 'environment', label: '环境图片' },
  { value: 'login', label: '登录注册' },
  { value: 'zanzhu', label: '公益赞助' },
  { value: 'avatar', label: '用户头像' },
  { value: 'default', label: '默认图片' },
  { value: 'captcha', label: '验证码' }
]

const fallback = '/uploads/default/certificate-default.jpg'
const imageList = ref([])
const tishi = ref('')
const uploadVisible = ref(false)
const replaceVisible = ref(false)
const previewVisible = ref(false)
const currentImage = ref(null)
const uploadFile = ref(null)
const replaceFile = ref(null)
const cacheVersion = ref(Date.now())
const query = reactive({ type: '', keyword: '' })
const uploadForm = reactive({ type: 'home' })

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2600)
}

function typeLabel(type) {
  return typeOptions.find((item) => item.value === type)?.label || type
}

function cacheUrl(url) {
  if (!url) return fallback
  return `${url}?v=${cacheVersion.value}`
}

function refreshImageVersion() {
  updateTupianBanben()
  cacheVersion.value = Date.now()
}

function fallbackImage(event) {
  if (event.target.src.includes(fallback)) return
  event.target.src = fallback
}

function fileSize(size) {
  const value = Number(size || 0)
  if (value >= 1024 * 1024) return `${(value / 1024 / 1024).toFixed(2)} MB`
  if (value >= 1024) return `${(value / 1024).toFixed(1)} KB`
  return `${value} B`
}

async function loadImages() {
  const res = await getImageList(query)
  imageList.value = res.data || []
}

function resetQuery() {
  Object.assign(query, { type: '', keyword: '' })
  loadImages()
}

function openUpload() {
  uploadForm.type = query.type || 'home'
  uploadFile.value = null
  uploadVisible.value = true
}

function onUploadFileChange(event) {
  uploadFile.value = event.target.files?.[0] || null
}

async function submitUpload() {
  if (!uploadFile.value) {
    show('请选择要上传的图片')
    return
  }
  try {
    await uploadImage(uploadFile.value, uploadForm.type)
    uploadVisible.value = false
    refreshImageVersion()
    await loadImages()
    show('上传成功')
  } catch (error) {
    show(error.message || '上传失败')
  }
}

function openReplace(item) {
  currentImage.value = item
  replaceFile.value = null
  replaceVisible.value = true
}

function onReplaceFileChange(event) {
  replaceFile.value = event.target.files?.[0] || null
}

async function submitReplace() {
  if (!currentImage.value || !replaceFile.value) {
    show('请选择新图片')
    return
  }
  try {
    await replaceImage(replaceFile.value, currentImage.value.type, currentImage.value.name)
    replaceVisible.value = false
    refreshImageVersion()
    await loadImages()
    show('替换成功')
  } catch (error) {
    show(error.message || '替换失败')
  }
}

async function removeImage(item) {
  const defaultTip = item.name.includes('default') ? '这是默认图片，删除后相关页面会使用其它兜底显示。' : ''
  if (!window.confirm(`${defaultTip}确认删除图片 ${item.name}？`)) return
  try {
    await deleteImage(item.type, item.name)
    refreshImageVersion()
    await loadImages()
    show('删除成功')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

function openPreview(item) {
  currentImage.value = item
  previewVisible.value = true
}

function openInNewTab(url) {
  if (url) window.open(url, '_blank', 'noopener')
}

async function copyPath(url) {
  if (!url) return
  try {
    await navigator.clipboard.writeText(url)
    show('路径已复制')
  } catch (error) {
    const textarea = document.createElement('textarea')
    textarea.value = url
    document.body.appendChild(textarea)
    textarea.select()
    document.execCommand('copy')
    document.body.removeChild(textarea)
    show('路径已复制')
  }
}

onMounted(loadImages)
</script>

<style scoped>
.tupian-guanli-page {
  gap: 20px;
}

.caozuo-tishi {
  margin-top: 12px;
  border: 1px solid #bbf7d0;
  border-radius: 6px;
  padding: 9px 12px;
  color: #166534;
  background: #f0fdf4;
}

.tupian-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 16px;
}

.tupian-card {
  display: grid;
  gap: 12px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 12px;
  background: #ffffff;
}

.tupian-preview-btn {
  width: 100%;
  padding: 0;
  border-radius: 8px;
  overflow: hidden;
  background: #eef5f1;
}

.tupian-preview-btn img {
  width: 100%;
  aspect-ratio: 4 / 3;
  display: block;
  object-fit: cover;
}

.tupian-info {
  display: grid;
  gap: 6px;
}

.tupian-info strong {
  color: #0f3d3a;
  word-break: break-all;
}

.tupian-info span,
.modal-hint,
.replace-current span {
  color: #64748b;
  font-size: 13px;
  line-height: 1.6;
}

.tupian-info code,
.preview-panel code,
.replace-current code {
  border-radius: 5px;
  padding: 6px 8px;
  color: #334155;
  background: #f1f5f9;
  word-break: break-all;
}

.tupian-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tupian-actions button,
.modal-actions button {
  min-height: 34px;
  border-radius: 6px;
  padding: 0 12px;
  color: #ffffff;
  background: #0f766e;
  font-weight: 700;
}

.tupian-actions button.danger {
  background: #b91c1c;
}

.modal-actions button.plain {
  border: 1px solid #cbd5e1;
  color: #334155;
  background: #ffffff;
}

.kong {
  grid-column: 1 / -1;
  color: #94a3b8;
  text-align: center;
}

.modal-mask {
  position: fixed;
  inset: 0;
  z-index: 30;
  display: grid;
  place-items: center;
  padding: 20px;
  background: rgba(15, 23, 42, 0.42);
}

.modal-panel {
  width: min(720px, 100%);
  display: grid;
  gap: 16px;
  border-radius: 8px;
  padding: 22px;
  background: #ffffff;
  box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24);
}

.modal-panel h3 {
  margin: 0;
}

.modal-form {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.modal-form label,
.file-field {
  display: grid;
  gap: 7px;
  color: #334155;
  font-weight: 700;
}

.modal-form input,
.modal-form select,
.file-field input {
  width: 100%;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  padding: 9px 10px;
  background: #f8fafc;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.replace-current {
  display: grid;
  grid-template-columns: 180px minmax(0, 1fr);
  gap: 14px;
  align-items: center;
}

.replace-current img,
.preview-panel img {
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
  background: #eef5f1;
}

.replace-current img {
  aspect-ratio: 4 / 3;
}

.replace-current > div {
  display: grid;
  gap: 8px;
}

.preview-panel img {
  max-height: 62vh;
  object-fit: contain;
}

@media (max-width: 680px) {
  .modal-form,
  .replace-current {
    grid-template-columns: 1fr;
  }
}
</style>
